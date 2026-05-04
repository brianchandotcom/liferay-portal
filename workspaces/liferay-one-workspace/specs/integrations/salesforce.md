# Salesforce

Implements system-spec **D12**. Replaces the Dossiera relay; Salesforce publishes opportunity events to a Google Pub/Sub topic and the workspace subscribes directly.

## Overview

**Direction:** inbound — Salesforce → workspace.

**Transport:** Google Pub/Sub pull subscription.

**Topic:** existing SF-owned topic `projects/<gcp-proj>/topics/salesforce-opportunities` (confirm exact ID during phase 3 handoff — SF admin team owns).

**Subscription:** `projects/<gcp-proj>/subscriptions/one-salesforce-opportunities` (workspace-owned, new).

**Runtime:** `liferay-one-etc-spring-boot` registers `SalesforceOpportunitySubscriber` on startup.

**Dead-letter topic:** `projects/<gcp-proj>/topics/one-deadletter` (workspace-owned). Messages that fail after 5 redeliveries go here.

## Auth

OAuth2 Connected App credentials for the Salesforce Pub/Sub API. Credentials stored in `liferay-one-instance-settings` secret `salesforce-pubsub-credentials`. Token refreshed transparently by the Pub/Sub client library.

---

## Message Payload

Salesforce publishes one message per closed opportunity event (and per update). Payload is a JSON envelope on the Pub/Sub message body:

```json
{
	"account": {
		"billingCountry": "US",
		"billingState": "CA",
		"name": "Acme Co",
		"parentSalesforceAccountId": null,
		"region": "AMER",
		"salesforceId": "0011Q00000XXXXXX"
	},
	"contacts": [
		{
			"email": "ada@acme.co",
			"firstName": "Ada",
			"lastName": "Lovelace",
			"role": "Technical Admin",
			"salesforceId": "0031Q00000XXXXXX"
		}
	],
	"eventTime": "2026-04-22T14:37:22Z",
	"eventType": "OpportunityClosedWon | OpportunityUpdated | OpportunityAmended",
	"externalReferences": {
		"dossieraId": "DSS-123",
		"salesforceProjectId": "a0T1Q00000XXXXXX"
	},
	"opportunity": {
		"amount": 240000,
		"closeDate": "2026-04-21",
		"currency": "USD",
		"name": "Acme Co — DXP Gold 3yr",
		"salesforceId": "0061Q00000XXXXXX",
		"stage": "Closed Won",
		"type": "New Business | Renewal | Upgrade | Addendum"
	},
	"products": [
		{
			"developerCount": 10,
			"endDate": "2029-04-30",
			"environment": "Production",
			"instanceSize": "L",
			"originalEndDate": "2029-04-30",
			"productCode": "DXP-GOLD-3YR",
			"productFamily": "DXP",
			"quantity": 1,
			"recurring": true,
			"salesforceLineId": "00k1Q00000XXXXXX",
			"startDate": "2026-05-01",
			"unitPrice": 80000
		}
	]
}
```

Contract details:

- `eventType` determines flow: `OpportunityClosedWon` creates/updates AccountEntry + Commerce order; `OpportunityUpdated` updates only (no new order); `OpportunityAmended` adjusts an existing order's line items.
- `contacts[].role` values map to Account Roles: `Technical Admin` → `Customer_Admin`, `Operations` → `Customer_Manager`, `User` → `Customer_Member`. Unknown roles default to `Customer_Member`.
- `products[].environment` + `instanceSize` feed `Deployment` creation when a new deployment is needed; existing deployments matching `(accountEntryId, name)` are augmented.

---

## Key Flows

### Handler Flow

1. **Ingest** — deserialize message; log with correlation ID = `opportunity.salesforceId`.

1. **Idempotency check** — look up `ExternalLink(domain=salesforce, entityName=opportunity, entityId=opportunity.salesforceId)`. If present and `lastProcessedAt >= eventTime`, ack and drop (duplicate delivery).

1. **Upsert AccountEntry** —
   - Find by `salesforceId=account.salesforceId`; else by `koroneikiAccountCode = derived code`; else create.
   - On create: generate `koroneikiAccountCode` from account name (uppercased slug + collision suffix). Set `region`, `tier` (default from product family), `status=Active`, `internal=false` (configurable), `salesforceId`.
   - Create `ExternalLink` rows for `salesforceId`, `externalReferences.dossieraId` (migration-only), `externalReferences.salesforceProjectId`.

1. **Upsert contact Users + account roles** — for each contact:
   - Find Liferay User by email; else create.
   - Add account membership; assign Account Role per contact role mapping.

1. **Create / update Commerce order** —
   - For `OpportunityClosedWon`: create `CommerceOrder`. One `CommerceOrderItem` per `products[]` entry. For `recurring=true`, create a subscription-enabled item → `CommerceSubscriptionEntry` with `startDate` / `endDate` / `originalEndDate`.
   - For `OpportunityAmended`: load existing order via `ExternalLink`; adjust line items (add new, update quantities, mark removed lines cancelled).
   - Populate `CommerceOrderItem.salesforceOpportunityId` and `CommerceOrderItem.koroneikiProductPurchaseKey` (null on new flow; populated during migration for continuity).

1. **Upsert Deployment** — for products with `environment` set, find `Deployment(accountEntryId, name=environment)`; else create. Link to the new `CommerceSubscriptionEntry` via the M:N relationship.

1. **Enforce developer-count cap** — set `CommerceSubscriptionEntry.developerCount` per product; warning + Slack alert if exceeds a configured threshold (ported from DossieraCreateMessageSubscriber logic).

1. **Entitlement re-sync** — POST `/entitlements/recompute?accountEntryId={id}` (fire-and-forget).

1. **Notifications** — send onboarding email if new-biz; skip on renewal/amendment. Fire "Analytics Cloud welcome" notification when a product matches Analytics Cloud SKUs.

1. **Ack** — ack the Pub/Sub message.

Any step that writes but fails midway rolls back (Spring `@Transactional` on the handler method). Pub/Sub nacks → redelivery.

### Opportunity-Type vs Account-Existence Warnings

Ported from DossieraCreateMessageSubscriber:

| Opportunity type | Account exists | Warning |
|---|---|---|
| New Business | yes | `[Warning] Opportunity marked New Business but account exists` |
| Renewal / Amendment | no | `[Warning] Opportunity marked Renewal but no existing account` |
| Upgrade | no | `[Warning] Opportunity marked Upgrade but no existing account` |

Warnings are non-fatal — prepend `[Warning]` to notification subject lines and log to Slack-bridged channel.

---

## Data Mapping

### Contact Role → Account Role

| Salesforce contact role | Liferay Account Role |
|---|---|
| Technical Admin | `Customer_Admin` |
| Operations | `Customer_Manager` |
| Financial | `Customer_Manager` |
| User | `Customer_Member` |
| Partner Manager | `Partner_Manager` |
| Partner | `Partner_Member` |
| _(unknown)_ | `Customer_Member` |

### Product Family → Tier Default

Used only when creating a new AccountEntry and `tier` isn't already set.

| Product family (SF) | `AccountEntry.tier` default |
|---|---|
| DXP (Platinum SKU) | `Platinum` |
| DXP (Gold SKU) | `Gold` |
| DXP (Silver SKU) | `Silver` |
| DXP (Limited SKU) | `Bronze` |
| Trial products | `Trial` |
| No paid product | `Community` |

Exact SKU-to-tier mapping lives in a configuration table (`OrderType.defaultTier` — add a field; or an explicit `SkuToTier` picklist — decide in phase 3).

### Environment String → `Deployment.name`

| SF environment | `Deployment.name` |
|---|---|
| Production | Production |
| Non-Production / Staging | Non-Production |
| Dev / Development | Development |
| Backup / DR | Backup |
| HA / High Availability | HA Production |

### Referenced Salesforce Objects

Salesforce is the system of record for these. liferay-one holds FK references only.

**Account**

| Field | Notes |
|---|---|
| PK `sfdcAccountId` | |
| `name`, `type`, `parentAccount` | |
| `currency` | |

**Contract / Project**

| Field | Notes |
|---|---|
| PK `sfdcContractId` | |
| `term`, `start`, `end` | |
| `opportunityId`, `signedAt` | |

**CPQ Quote Line**

| Field | Notes |
|---|---|
| PK `quoteLineId` | |
| FK `productId`, `quantity` | |
| `unitPrice` | locked at quote acceptance |

**Product Catalog**

| Field | Notes |
|---|---|
| PK `productId`, `sku` | |
| `name`, `pricebookEntries` | |
| `metricCoverage` | entitlement rules |

---

## Error Handling

| Failure | Behavior |
|---|---|
| JSON parse error | Log + move to dead-letter immediately (don't retry unparseable messages) |
| Duplicate (idempotency hit) | Ack, drop |
| Transient DB error | Nack → Pub/Sub redelivers with backoff |
| `AccountEntry` collision (code dup beyond suffix limit) | Log + Slack alert; nack; dead-letter after 5 redeliveries |
| Jira / Liferay Cloud dependency failure during post-processing | Don't fail the message — post-processing runs in a separate `@Async` block so ingest succeeds. Failures log to `IntegrationFailure` and retry on a 1h scheduled task |
| Validation failure (banned email on a contact) | Log warning; skip that contact only; continue processing |

Dead-letter queue alerts route to the support-engineering Slack channel.

---

## Observability

- Metric `one.salesforce.messages.received` — counter
- Metric `one.salesforce.messages.processed` — counter, labels `{eventType, outcome}`
- Metric `one.salesforce.processing.duration` — histogram
- Metric `one.salesforce.deadletter.count` — gauge from dead-letter subscription
- Dashboard: `grafana.internal/d/one-salesforce` (TBD)
- Log correlation: all writes within a message's scope tagged with `correlation_id=opportunity.salesforceId`

---

## Migration Notes

- **Phase 3 cut-over** — stand up this subscriber in parallel with the Dossiera relay. Validate against the same topic for 48h (both handlers process, but only Dossiera writes to Koroneiki; the new subscriber writes to a staging schema). Compare results.
- **SF-side config** — no changes required. Existing topic; only the subscription endpoint swaps.
- **Historical opportunities** — not replayed. New messages from cutover forward. For back-fill of rows that exist only in Koroneiki, use the phase-2 bulk extract.

---

## Open Questions

1. **Exact topic and subscription IDs** — confirm with SF admin team before phase 3.

1. **Envelope schema stability** — verify the payload matches what Salesforce actually publishes (SF admin may have modified fields since the Dossiera integration was last reviewed). Capture actual payloads from the existing Dossiera queue for a week before cut-over.

1. **Ordering guarantees** — confirmed: no ordering key on the SF topic. The edge case (amendment arriving before create) is considered unlikely in practice, but the handler must be defensive: if an `OpportunityAmended` message arrives and no matching `ExternalLink(domain=salesforce, entityName=opportunity)` is found, log a warning, nack the message, and let Pub/Sub redeliver with backoff. After 3 redeliveries without a match, route to dead-letter for manual review.

1. **Dead-letter SLA** — define an SLA on dead-letter resolution. Default: 24h to human triage.