# Jira / JSM Integration

Implements system-spec **D6**. Jira is the authoritative ticket store. The workspace holds cached render fields and pushes outbound writes (issue create, comments, labels, Assets).

## Overview

**Direction:** bidirectional.

- Outbound: issue create, comment post, attachment link, label sync, Assets write-back.
- Inbound: issue-status webhook (opportunistic — updates `SupportTicket.cached*` without waiting for the 1h TTL).

**Projects:**
- `LRHC` — Liferay Help Center (customer-facing support tickets).
- `LRFLS` — First Line Support (triage).
- `LSV` — Security Vulnerabilities (read-only proxy for `/security-vulnerabilities` page).

**Jira Assets (formerly Insight):**
- Object schema: `Koroneiki`. Object type: `Account`. Workspace: `{configured}`.
- The workspace writes Account records into Assets so Jira agents see account metadata alongside their tickets.

---

## Auth

- **REST calls:** Atlassian API token + email basic auth. Token stored in `liferay-one-instance-settings` secret `jira-api-token`. Rotate quarterly.
- **Webhook verification:** shared HMAC secret (`jira-webhook-secret`) signed in header `X-Jira-Signature`. Request rejected if HMAC invalid.

---

## Key Flows

### 1. Create Issue

Triggered by `SupportTicket.onAfterAdd`.

```
POST {jiraBaseUrl}/rest/api/3/issue
```

Payload:

```jsonc
{
  "fields": {
    "project": { "key": "LRHC" },
    "issuetype": { "name": "Support Ticket" },
    "summary": "<SupportTicket.subject>",
    "description": { /* ADF */ },
    "priority": { "name": "<mapped>" },
    "reporter": { "emailAddress": "<reporter>" },
    "customfield_{accountFieldId}": { "value": "<AccountEntry.koroneikiAccountCode>" }
  }
}
```

On success: stash `response.key` in `SupportTicket.jiraIssueKey`.

### 2. Post Comment (Attachment-Approved Flow)

Triggered by `TicketAttachment.onAfterAdd(state=Approved)` / `onAfterUpdate(state→Approved)`.

```
POST {jiraBaseUrl}/rest/api/3/issue/{issueKey}/comment
```

Payload: ADF document with a linked download URL (GCS signed URL, 7-day TTL).

```jsonc
{
  "body": {
    "type": "doc", "version": 1,
    "content": [
      {
        "type": "paragraph",
        "content": [
          { "type": "text", "text": "Attachment: " },
          {
            "type": "text",
            "text": "<fileName>",
            "marks": [{ "type": "link", "attrs": { "href": "<signedUrl>" } }]
          },
          { "type": "text", "text": " (<fileSize> bytes, md5 <md5>)" }
        ]
      }
    ]
  }
}
```

On failure: persist the ADF doc in `TicketAttachment.draftCommentBody`; the hourly `TicketAttachmentDraftCommentRetry` task retries.

### 3. Heat-Tag Label Sync

Triggered by `BusinessEvent.onAfterUpdate` and daily by `JiraHeatTagSync`.

```
PUT {jiraBaseUrl}/rest/api/3/issue/{issueKey}
```

Payload adds labels:
- `impacting_business_event`
- `<heat>_be` where `<heat>` is lowercased `BusinessEvent.heat` (`cold_be`, `warm_be`, `hot_be`, `critical_be`)

Replaces pre-existing heat labels (`*_be` prefix) on the issue.

### 4. Jira Assets Koroneiki Write-Back

Triggered by `AccountEntry.onAfterUpdate` (throttled to once per 5 min per account) and by `JiraHeatTagSync` daily replay.

```
POST {atlassianAssetsUrl}/jsm/assets/workspace/{assetsWorkspaceId}/v1/object/create
PUT  {atlassianAssetsUrl}/jsm/assets/workspace/{assetsWorkspaceId}/v1/object/{objectId}
```

Track the Assets object ID via `ExternalLink(domain=jira, entityName=assets-object, entityId=<objectId>, ownerClass=AccountEntry)`.

### 5. Security-Vulnerabilities Read Proxy

`GET /o/one/v1/jira/security-vulnerabilities/{path...}` proxies through to:

```
GET {jiraBaseUrl}/rest/api/3/search?jql=project=LSV+...
```

Response is passed through with minimal filtering. Cached for 5 min.

### 6. Live Issue Fetch

`GET /o/one/v1/jira/issue/{issueKey}` — live fetch with 1h workspace-side cache. Serves the SupportTicket render-time refresh.

```
GET {jiraBaseUrl}/rest/api/3/issue/{issueKey}?fields=summary,status,priority,assignee,reporter
```

On 404: mark local `SupportTicket.statusCached="Deleted"`, warn in UI.

### Inbound Webhook

**Endpoint:** `POST /o/one/v1/jira/webhook`

**Events subscribed (configured in Jira):**
- `jira:issue_updated`
- `jira:issue_created` (for issues created by agents, not from the workspace)
- `jira:issue_deleted`

**Handler:**

1. Verify HMAC.

1. Look up `SupportTicket` by `webhookEvent.issue.key`. If not found, create a new row (lazy creation pattern).

1. Update `statusCached`, `priorityCached`, `assigneeEmail`, `subject`, `lastSyncedAt=now`.

1. Ack with 200.

Webhook failures return 5xx → Jira retries per its own schedule (Atlassian does not guarantee delivery; the 1h TTL acts as a safety net).

---

## Data Mapping

### Priority Mapping

| Workspace priority (internal) | Jira priority |
|---|---|
| Critical | Highest |
| High | High |
| Medium | Medium |
| Low | Low |

Priority mapping table stored in `liferay-one-instance-settings` config `jira-priority-map` (not hardcoded so agents can tune without a release).

### Assets Attribute Mapping

| Liferay field | Jira Assets attribute |
|---|---|
| `AccountEntry.name` | `name` |
| `AccountEntry.koroneikiAccountCode` | `key` |
| `AccountEntry.region` | `region` |
| `AccountEntry.tier` | `tier` |
| `AccountEntry.status` | `status` |
| count of active deployments | `deploymentCount` |
| count of active subscriptions | `activeSubscriptionCount` |

---

## Error Handling

| Failure | Behavior |
|---|---|
| 401 / 403 (auth) | Log + Slack alert; fail fast (no retry) |
| 429 (rate limit) | Respect `Retry-After`; retry up to 5 times |
| 5xx | Exponential backoff × 3 |
| Network timeout | Circuit breaker trips after 5 failures in 60s |
| Webhook HMAC invalid | 401; log security event |
| Assets write-back failure | Log to `IntegrationFailure`; retry in `JiraHeatTagSync` daily replay (idempotent) |

**Rate limits:**
- REST API: ~150 req / 5 min per user token.
- Assets API: ~100 req / 5 min.

Clients use token-bucket rate limiters sized to 80% of the cap. Retries use exponential backoff with `Retry-After` header respected on 429.

---

## Observability

- Metric `one.jira.requests.total` — counter `{endpoint, status}`
- Metric `one.jira.latency` — histogram
- Metric `one.jira.ratelimited` — counter
- Metric `one.jira.webhook.received` — counter
- Circuit-breaker state metric `one.jira.circuit.state` — gauge (0/1)

---

## Open Questions

1. **Jira project mapping** — `LRHC` / `LRFLS` routing: is it by ticket type, customer tier, or operator choice? Today's customer-workspace routes customer-portal submissions to `LRHC`; escalations to `LRFLS`. Confirm during phase 5.

1. **Assets write-back throttle** — 5 min per account may still swamp the Assets API for 18K accounts during a bulk backfill. Consider batching or a slow-roll on initial sync.

1. **Webhook event delivery reliability** — Jira webhook delivery is best-effort. Confirm the 1h TTL on `SupportTicket.lastSyncedAt` is acceptable for the longest cache miss window.

1. **ADF versioning** — Atlassian evolves the ADF schema. Pin to version 1; revisit when Jira announces breaking changes.