# Liferay Cloud (LXC / DXP Cloud / Console / Provisioning Hub)

Portal-instance lifecycle for trials and paid cloud subscriptions.

## Overview

**Direction:** bidirectional.
- Outbound: provision, deprovision, status, deploy-app operations.
- Inbound: lifecycle callbacks (instance-ready, instance-failed, usage-reported).

**Systems covered:**
- **Liferay Cloud / LXC** — the SaaS platform hosting trials and paid cloud portals.
- **Provisioning Hub** — the orchestrator that spins up DXP trial instances (today at `POST /instances`).
- **Console** — DXP instance-management UI; the workspace proxies Console operations for deploy-app flows.

Today these are three somewhat distinct endpoints in the Marketplace codebase. The workspace consolidates them behind typed clients in `liferay-one-etc-spring-boot` but preserves the three upstream concerns because the LXC / Hub / Console teams ship them separately.

---

## Auth

OAuth2 client credentials issued by LXC. Stored in `liferay-one-instance-settings` secret `lxc-client-credentials` with fields `clientId`, `clientSecret`, `tokenUrl`. Token refreshed silently by the client.

---

## Key Flows

### Trial Provisioning (Provisioning Hub)

Entry point: `POST /o/one/v1/trial/provision/{subscriptionId}`.

Workspace logic:

1. Load `CommerceSubscriptionEntry` + associated `TrialProvisioning` row.

1. Look up `OrderType` via `TrialProvisioning.orderTypeId`; reject if `provisioningFlow` is not `trial-cloud` or `paid-cloud`.

1. Call Provisioning Hub:

   ```
   POST {provisioningHubBaseUrl}/instances
   Authorization: Bearer <lxc-token>
   {
     "accountKey": "<AccountEntry.koroneikiAccountCode>",
     "productCode": "<CPDefinition.externalReferenceCode>",
     "tier": "<OrderType.defaultTier>",
     "durationDays": <OrderType.trialDurationDays>,
     "contact": { "email": "<primary contact>", "name": "..." },
     "region": "<AccountEntry.region>"
   }
   ```
4. On success: stash `response.instanceId` in `TrialProvisioning.cloudInstanceId`, `response.instanceUrl` in workspace cache, flip `provisioningStatus=InProgress`.

1. LXC callback (inbound) flips `provisioningStatus=Active`.

### Trial Expiry

Entry point: `POST /o/one/v1/trial/expire/{subscriptionId}` — triggered by `TrialLifecycleTick` when `TrialProvisioning.trialEndDate < now`.

Workspace:

1. Load `TrialProvisioning`; reject if already `Expired` / `Decommissioned`.

1. Call Provisioning Hub:

   ```
   DELETE {provisioningHubBaseUrl}/instances/{cloudInstanceId}
   ```
3. Flip `provisioningStatus=Expired` immediately; `Decommissioned` after LXC callback confirms tear-down.

### Paid Cloud Provisioning

Same flow as trial, triggered by Commerce subscription activation via `LicenseKeyLifecycleListener`. Additional request fields: `paidCloud=true`, `billingReference=<CommerceSubscriptionEntry.id>`, extended `durationDays` based on subscription term.

### Console Deploy-App

Entry point: `POST /o/one/v1/console/provisioning/{subscriptionId}`. Used when a customer buys a marketplace app that needs deployment to their DXP instance.

```
POST {consoleBaseUrl}/api/v1/instances/{consoleInstanceId}/apps
{
  "publisherAssetAttachmentId": "<gcs path>",
  "targetEnvironment": "Production"
}
```

Workspace:

1. Load `TrialProvisioning` → confirm `provisioningStatus=Active`.

1. Resolve the Console instance ID via `ExternalLink(domain=liferay-cloud, entityName=console-instance, ownerClass=AccountEntry)`.

1. Fetch the publisher asset's GCS signed URL.

1. Call Console `POST /apps` with the signed URL (Console pulls directly from GCS).

1. Poll `GET {consoleBaseUrl}/api/v1/instances/{id}/deployments/{deploymentId}` until status `Succeeded` / `Failed`.

1. Return result to caller.

### Console Status

`GET /o/one/v1/console/subscriptions/{subscriptionId}` — returns currently-deployed apps + instance metadata. Pass-through to Console's `GET /instances/{id}`.

### LXC Callbacks (Inbound)

LXC calls back to `POST /o/one/v1/console/callback` (HMAC-signed) when instance state changes.

| Event | Action |
|---|---|
| `instance.ready` | `TrialProvisioning.provisioningStatus=Active`; send welcome email |
| `instance.failed` | `provisioningStatus=Failed`; capture `errorMessage`; Slack alert |
| `instance.decommissioned` | `provisioningStatus=Decommissioned` |
| `usage.reported` | Record a `UsageReport` row (internal Spring Boot table) |

HMAC secret rotated with `lxc-client-credentials`.

### Seat Availability (For On-Hold Trials)

`GET /o/one/v1/trial/availability?orderTypeExternalReferenceCode={erc}` — for `TrialLifecycleTick` to know whether to promote on-hold trials.

Pass-through to:

```
GET {provisioningHubBaseUrl}/capacity?productCode={erc}
```

Response: `{ "availableSeats": 12, "totalSeats": 50 }`. Workspace caches for 5 min.

---

## Data Mapping

| Workspace object | LXC concept | Notes |
|---|---|---|
| `TrialProvisioning` | `Instance` | `cloudInstanceId` holds the LXC instance ID |
| `AccountEntry.koroneikiAccountCode` | `accountKey` | Stable cross-system identifier |
| `AccountEntry.region` | `region` | Maps to LXC datacenter region |
| `OrderType.defaultTier` | `tier` | Controls instance sizing |
| `CommerceSubscriptionEntry.id` | `billingReference` | Paid cloud only |

---

## Error Handling

| Failure | Behavior |
|---|---|
| 401 / 403 | Slack alert; fail fast |
| 429 | Backoff + retry × 3 |
| 5xx on provision | Mark `TrialProvisioning.provisioningStatus=Failed`; Slack alert; user-retriable via admin |
| Timeout on provision | Don't auto-retry (avoid double-provision); mark `Failed` and require manual replay |
| Deprovision failure | Log; retry hourly until success (idempotent) |

**Rate limits:**
- Provisioning Hub: ~20 req/min per client.
- LXC API: ~60 req/min.
- Console: ~120 req/min.

Trial-lifecycle cron (`TrialLifecycleTick`) runs every 6h — batch processing fits comfortably within these limits.

---

## Observability

- Metric `one.lxc.provisioning.requests` — counter, labels `{operation, status}`
- Metric `one.lxc.provisioning.duration` — histogram
- Metric `one.lxc.callbacks.received` — counter, labels `{event}`
- Metric `one.lxc.trial.active` — gauge (from `TrialProvisioning.provisioningStatus=Active` count)

---

## Open Questions

1. **Three systems, one team?** Today LXC, Provisioning Hub, and Console have separate APIs. Confirm whether consolidation is planned on their side — if so, simplify the client count.

1. **Console instance ID discovery** — today the workspace's post-purchase controller hardcodes logic to find the Console instance for a given account. If multiple Console instances per account become common, define the disambiguation rule.

1. **Callback reliability** — LXC callback delivery SLA unknown. If missed, `TrialProvisioning.provisioningStatus` hangs at `InProgress` indefinitely. Add a `TrialLifecycleTick` safety net that polls `GET /instances/{id}` for rows stuck > 30 min.

1. **Trial-to-paid conversion** — when a customer converts a trial to paid, is it the same instance or a new one? Today: same instance, just a Commerce subscription change. Confirm new design matches.