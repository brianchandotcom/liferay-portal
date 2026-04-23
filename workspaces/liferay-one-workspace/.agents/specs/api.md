# API Surface

Splits into three layers:

1. **Headless** — auto-generated per-Object REST + GraphQL (90% of read/write paths).

1. **Custom REST** — `liferay-one-etc-spring-boot` for orchestration workflows.

1. **Webhooks / async subscribers** — inbound Pub/Sub subscriber, Jira webhook.

All auth via OAuth2. No ServiceProducer impersonation (retired).

---

## 1. Headless — Auto-Generated

Every workspace Object exposes:

- REST: `GET|POST|PUT|PATCH|DELETE /o/c/{pluralName}` and `/o/c/{pluralName}/{id}` (or `/by-external-reference-code/{erc}`)
- GraphQL: `c{pluralName}`, `c{singularName}`, `createC{singularName}`, `updateC{singularName}`, `deleteC{singularName}`

Account-restricted Objects (`accountEntryRestricted: true`) auto-filter to the calling user's account membership. Worker_* roles see all accounts they're assigned to.

---

## 2. Custom REST (`liferay-one-etc-spring-boot`)

Base path: `/o/one/v1`. Auth: `Authorization: Bearer <oauth2-token>`.

### 2.1 Trial Lifecycle

| Method | Path | Purpose | Scope |
|---|---|---|---|
| POST | `/trial/provision/{subscriptionId}` | Provision trial cloud instance; create `TrialProvisioning` | `subscription.write` |
| POST | `/trial/expire/{subscriptionId}` | Mark expired; call cloud decomm | `subscription.write` |
| POST | `/trial/notify-end/{subscriptionId}` | Send end-of-trial email | `subscription.write` |
| GET | `/trial/availability?orderTypeExternalReferenceCode={erc}` | Check seat availability | `subscription.read` |

### 2.2 License Keys

| Method | Path | Purpose | Scope |
|---|---|---|---|
| POST | `/license-key/generate/{subscriptionId}` | Generate `LicenseKey` row; sign; return key string | `license.admin` |
| POST | `/license-key/{id}/revoke` | `status=Revoked`; notify | `license.admin` |
| GET | `/license-key/{id}/download` | Return signed key artifact | `license.read` |
| POST | `/license-key/regenerate/{id}` | Issue replacement; supersede original | `license.admin` |

### 2.3 Ticket Attachments (GCS-backed)

Pattern: initiate → upload to GCS → complete → approve.

| Method | Path | Purpose | Scope |
|---|---|---|---|
| POST | `/ticket-attachments/initiate-upload` | Create `TicketAttachment` row (Draft); return GCS resumable URL | `ticket.write` |
| POST | `/ticket-attachments/{id}/complete-upload` | Validate MD5; flip state=Approved | `ticket.write` |
| GET | `/ticket-attachments/by-id/{id}/download` | Return signed download URL (15 min expiry) | `ticket.read` |
| DELETE | `/ticket-attachments/{id}` | Set `state=Trashed` | `ticket.write` |

### 2.4 Publisher Asset Uploads (GCS-backed)

| Method | Path | Purpose | Scope |
|---|---|---|---|
| POST | `/publisher-assets/{id}/attachments/initiate-upload` | | `publisher.write` |
| POST | `/publisher-assets/{id}/attachments/{attachmentId}/complete-upload` | | `publisher.write` |

### 2.5 Jira Bridge

| Method | Path | Purpose | Scope |
|---|---|---|---|
| GET | `/jira/issue/{issueKey}` | Live Jira fetch (1h cache); updates `SupportTicket.cached*` | `ticket.read` |
| DELETE | `/jira/cache` | Admin-only cache flush | `admin` |
| GET | `/jira/security-vulnerabilities/{path...}` | Read-only LSV project proxy | `ticket.read` |
| POST | `/jira/webhook` | Inbound Jira webhook (HMAC-verified) | none (HMAC only) |

### 2.6 Liferay Cloud

| Method | Path | Purpose | Scope |
|---|---|---|---|
| POST | `/console/provisioning/{subscriptionId}` | Deploy DXP instance | `console.write` |
| GET | `/console/subscriptions/{subscriptionId}` | Status check | `console.read` |
| POST | `/analytics/provision/{subscriptionId}` | Provision Analytics Cloud workspace | `analytics.write` |

### 2.7 Entitlements

| Method | Path | Purpose | Scope |
|---|---|---|---|
| POST | `/entitlements/recompute` | Admin-trigger full `EntitlementSync` | `admin` |
| POST | `/entitlements/recompute?definitionCode={code}` | Scoped to one definition | `admin` |
| POST | `/entitlements/recompute?accountEntryId={id}` | Scoped to one account | `admin` |

### 2.8 Health

| Method | Path | Scope |
|---|---|---|
| GET | `/ready` | none (public) |
| GET | `/health` | none (public) |

### 2.9 Error Contract

All endpoints return:

```json
{
	"error": {
		"code": "string",
		"details": {
		},
		"message": "string"
	}
}
```

HTTP status: 400 validation failure · 401 missing token · 403 scope insufficient · 404 not found · 409 conflict · 429 rate limited · 5xx dependency failure.

---

## 3. OAuth2 Configuration

### 3.1 Scopes

| Scope | Grants |
|---|---|
| `customer.read` | Read AccountEntry / Team / ExternalLink |
| `customer.write` | Write AccountFlag / AccountNote / Team / AccountEntry custom fields |
| `subscription.read` | Read CommerceSubscriptionEntry and `TrialProvisioning` |
| `subscription.write` | Write subscription custom fields; call trial lifecycle endpoints |
| `license.read` | Read LicenseKey; download own-account key |
| `license.admin` | Generate / revoke / regenerate keys |
| `entitlement.read` | Read Entitlement |
| `ticket.read` | Read SupportTicket / TicketAttachment / Jira proxy GETs |
| `ticket.write` | Create tickets, upload attachments |
| `publisher.read` | Read Publisher and assets |
| `publisher.write` | Write Publisher, upload asset attachments |
| `console.read` / `console.write` | DXP Console proxy |
| `analytics.read` / `analytics.write` | Analytics Cloud proxy |
| `admin` | Trigger recompute endpoints, cache flush, internal ops |
| `provisioning.internal` | Internal-only: license key gen/revoke, heartbeat, trial provision/expire — granted only to `one-internal-provisioning` |

### 3.2 Registered Applications

| Application | Scopes | Caller |
|---|---|---|
| `one-salesforce-subscriber` | `customer.write`, `subscription.write` | D12 Pub/Sub subscriber identity |
| `one-jira-webhook` | `ticket.write` | Jira webhook POSTs |
| `one-custom-element` | All scopes (narrowed per-user by role at request time) | Browser-facing React custom element |
| `one-license-generator` | `license.admin` | Internal license-gen service |
| `one-liferay-cloud-callback` | `subscription.write`, `license.admin` | Liferay Cloud callbacks |
| `one-internal-provisioning` | `provisioning.internal` | Object Actions and scheduled tasks calling internal provisioning endpoints; not externally exposed |

---

## 4. Retired APIs

- **Koroneiki Phloem** `/o/koroneiki-rest/v1.0/*` — downstream callers migrate to headless + custom endpoints
- **Provisioning portlet JSON-WS** — replaced by Admin UI + headless
- **Zendesk integration endpoints** — dropped with provisioning phase-3 retire

### Caller Re-Pointing Checklist

| Caller | Today | New |
|---|---|---|
| Provisioning DossieraCreateMessageSubscriber | Phloem REST | Retires — logic absorbed by D12 subscriber |
| Marketplace `/koroneiki` controllers | Phloem REST | Headless `/o/c/accountEntries` etc. |
| Support `AccountsRestController.scheduledHeatTagUpdate` | Phloem + Jira Assets | Headless + Jira |
| LCS, osb-entity-web | Phloem REST | N/A — both retiring |
| Any Salesforce GCF | Phloem REST | Direct workspace REST (new OAuth2 app) |
| Customer-portal UI | Phloem | Headless via `one-custom-element` app |

---

## Open Questions

1. **Rate limiting** — GCS initiate-upload endpoint needs limiting (10/min/user) as a Spring Boot filter.

1. **Jira proxy caching** — 1h cache in Spring Boot memory lost on restart. Consider Redis for clustered cache.

1. **Custom element auth** — Confirm CSRF posture for mutating endpoints.

1. **Webhook signing** — Jira webhook HMAC secret rotation is manual. Schedule quarterly rotation.