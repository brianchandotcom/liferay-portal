# Data Warehouse (BigQuery)

Google BigQuery is the data warehouse backing customer usage metrics and analytics exports. Today the workspace accesses BigQuery exclusively through Google Cloud Functions (`customer_usage_api`, `composable_usage_api`) — there is no direct SDK connection. As the migration adds `UsageEvent` and `UsageReport` objects and richer per-environment analytics, direct BigQuery access will be introduced.

## Overview

**Direction:** outbound — workspace → BigQuery (currently via GCF intermediary; direct access in future phases).

**Platform:** Google BigQuery.

**Current access path:**

```
workspace (Spring Boot)
    → GCF (customer_usage_api / composable_usage_api)
        → BigQuery
```

**Future access path (phase 5+):**

```
workspace (Spring Boot)
    → BigQuery (direct, via google-cloud-bigquery SDK)
```

See [`google-cloud-functions.md`](./google-cloud-functions.md) for the GCF transport layer (auth, caching, error handling). This document covers what BigQuery stores and how that data maps to the liferay-one data model.

---

## Auth

Currently, the workspace authenticates to the GCF functions using service-account ID tokens; the GCF functions themselves hold the credentials to query BigQuery. The workspace never holds BigQuery credentials today.

For future direct access, a dedicated service account with `roles/bigquery.dataViewer` (read-only) scoped to the relevant datasets will be required. Credentials should follow the same pattern as GCS — service-account JSON stored as a `liferay-one-instance-settings` secret.

---

## Key Flows

### Current Usage — Environment Usage Metrics

Two logical data streams are read today, one per environment type.

**SaaS / Analytics Cloud environments** — surfaced via `customer_usage_api`. Account-scoped; no time dimension on the request (latest snapshot).

**Composable / PaaS environments** — surfaced via `composable_usage_api`. Account-scoped; monthly time dimension (`yyyy-MM`).

`AccountsRestController.getUsage()` fetches product purchases from Koroneiki, determines the environment type (SaaS vs. composable), and calls the corresponding GCF function. The raw JSON is wrapped in `SaaSUsageStrategy` or `ExperienceUsageStrategy`, which handle unit conversion and bundle the usage figures with entitlement limits before returning.

**REST endpoint exposed to the UI:** `GET /accounts/{externalReferenceCode}/usage`

### Scheduled Export (Daily Snapshot)

A scheduled task exports Object record snapshots to BigQuery daily. This provides the Data team with fresh data for dashboards and entitlement auditing.

**Tables exported:**

| BigQuery table | Source object | Notes |
|---|---|---|
| `one_account` | `AccountEntry` | |
| `one_subscription` | `CommerceSubscriptionEntry` | |
| `one_environment` | `Environment` / `TrialProvisioning` | |
| `one_entitlement` | `Entitlement` | |
| `one_usage_event` | `UsageEvent` | Phase 5+ |

Export runs at 02:00 UTC daily. Rows are upserted (not appended) using the Liferay Object primary key as the BigQuery row key.

### Future — Direct BigQuery Integration (Phase 5+)

Expected use cases requiring direct BigQuery access:

- **Usage events and reports** — `UsageEvent` and `UsageReport` objects will receive inbound usage data from customer environments. A scheduled job or streaming pipeline exports `UsageEvent` rows to BigQuery for analytical queries.
- **Cross-environment analytics** — pre-aggregated BigQuery views queried directly from the workspace on demand, with results cached aggressively.
- **Entitlement enforcement support** — metered entitlements (`Entitlement.prepaidQuota`, overage detection) require comparing real-time consumption against contracted limits. BigQuery may serve as the source of truth for consumption figures that feed the `EntitlementSync` process.

---

## Data Mapping

### SaaS / Analytics Cloud Metrics

| Metric | BigQuery field | Unit |
|---|---|---|
| Anonymous page views | `totalAnonymousPageViewsCount` | count |
| Monthly active logged-in users | `totalMonthlyActiveLoggedInUsersCount` | count |
| Sites | `totalSitesCount` | count |
| Client extensions CPU | `totalClientExtensionsCapacityCPUCount` | vCPU |
| Client extensions RAM | `totalClientExtensionsCapacityRAM` | GiB |
| Document library storage | `totalStorageCapacityDocumentLibrary` | GiB |

### Composable / PaaS Metrics

| Metric | BigQuery field | Unit (raw) | Displayed as |
|---|---|---|---|
| Database storage | `databaseStorage` | bytes | GiB |
| Client extensions CPU | `clientExtensionsCPU` | vCPU | vCPU |
| Client extensions RAM | `clientExtensionsRAM` | bytes | GiB |
| Log storage | `logStorage` | bytes | GiB / TiB |
| Network traffic | `networkTraffic` | bytes | TiB |
| Document library + backup storage | `documentLibraryAndBackupStorage` | bytes | TiB |

### Usage Response Shape (Both Strategies)

```json
{
	"maxCount": 100,
	"maxCountUnits": "GiB",
	"percentage": "42.5",
	"usedCount": 42.5,
	"usedCountUnits": "GiB"
}
```

---

## Configuration (Future Direct Access)

When direct BigQuery access is introduced, the following properties will be needed:

```properties
liferay.one.bigquery.project-id=${LIFERAY_ONE_BIGQUERY_PROJECT_ID}
liferay.one.bigquery.dataset=${LIFERAY_ONE_BIGQUERY_DATASET}
liferay.one.bigquery.service-account-key=${LIFERAY_ONE_BIGQUERY_SERVICE_ACCOUNT_KEY}
```

Exact property names TBD — align with the pattern used by `liferay.customer.gcf.*` in the current Spring Boot service.

---

## Error Handling

For current GCF-mediated access, see [`google-cloud-functions.md §Error Handling`](./google-cloud-functions.md).

For future direct access:
- Retry up to 3 times with exponential backoff for transient errors.
- Fail fast on 4xx (permission / not-found).
- BigQuery query timeouts: 30s for interactive queries, 5 min for batch/scheduled.
- Set per-query `maximumBytesBilled` cap to guard against runaway queries (BigQuery bills by bytes scanned).

---

## Observability

No dedicated metrics exist today. Planned for when direct access is added:

- Metric `one.bigquery.queries.total` — counter, labels `{dataset, query_name, outcome}`
- Metric `one.bigquery.query.duration` — histogram, label `{query_name}`
- Metric `one.bigquery.bytes.processed` — counter (track to detect runaway queries)
- Metric `one.bigquery.export.rows` — counter, labels `{table}` (daily export monitoring)

---

## Migration Notes

- **`accountKey` → `koroneikiAccountCode`** — BigQuery tables currently keyed on the Koroneiki account key. `Account.koroneikiAccountCode` is preserved during migration, so GCF-mediated queries continue to work without table changes at cut-over.
- **`deploymentKey` for per-environment queries** — if BigQuery tables are extended to support per-environment granularity, the key will be `Environment.deploymentKey` (preserved from `AccountSubscription.accountSubscriptionERC`).
- **GCF ownership** — the GCF functions that wrap BigQuery are owned by an external team. Any schema changes needed for the new data model must be coordinated with that team before phase 5.
- **Usage Report back-fill** — `kor.Koroneiki_ProductConsumption` (148,901 rows) maps to `UsageReport`. The granularity differs; do not attempt a 1:1 row conversion. Use BigQuery as the authoritative source for historical consumption data when reconstructing reports post-migration.

---

## Open Questions

1. **Direct BigQuery timeline** — which phase introduces the direct SDK connection? Phase 5 is assumed; confirm with the data-platform team.

1. **Dataset and table contracts** — the GCF functions abstract away the BigQuery schema today. Before direct access is added, the workspace team needs the full table/view schema from the data-platform team.

1. **Write path** — does the workspace write `UsageEvent` rows directly to BigQuery, or does BigQuery pull from an export of the Liferay Object layer? Decide before phase 5 design.

1. **Billing / quota** — establish per-query cost budgets and add query-level `maximumBytesBilled` caps before opening direct access.

1. **Data residency** — confirm the BigQuery project/region aligns with customer-data residency commitments (see also `google-cloud-storage.md` open question #1).

1. **Per-environment metric keys** — confirm with the data-platform team whether composable usage can be keyed on `deploymentKey` in addition to (or instead of) `accountKey`, to support the new per-`Environment` granularity.