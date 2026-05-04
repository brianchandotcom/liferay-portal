# Google Cloud Functions (GCF)

Serverless compute for lightweight async tasks and usage metric retrieval. The workspace calls GCF functions for entitlement rule evaluation, license key generation, Salesforce data transformation, and environment usage metrics.

## Overview

**Direction:** outbound — workspace → GCF (HTTP GET / POST).

**Functions:**

| Function | Path | Purpose |
|---|---|---|
| `customer_usage_api` | `/customer_usage_api/api/v1/customer/usage/accounts/{accountKey}` | SaaS / Analytics Cloud usage metrics |
| `composable_usage_api` | `/composable_usage_api/api/v1/accounts/{accountKey}/usage/month/{month}` | Composable / PaaS usage metrics |
| `entitlement_rules` | `/entitlement_rules/api/v1/evaluate` | Complex scripted entitlement rule evaluation |
| `license_key_signer` | `/license_key_signer/api/v1/sign` | Cryptographic license key generation |
| `salesforce_transform` | `/salesforce_transform/api/v1/transform` | Salesforce data transformation / normalization |

**Runtime:** `liferay-one-etc-spring-boot` calls `GoogleCloudFunctionService` synchronously on each usage request. Responses are cached in-process.

---

## Auth

Each function has its own service account JSON key, passed as an environment variable. On each request the client:

1. Loads the service account JSON key bytes from config.

1. Creates `IdTokenCredentials` with the function URL as the target audience (`{baseUrl}/{functionPath}`).

1. Attaches the resulting Bearer token via `HttpCredentialsAdapter`.

Credentials are not cached across requests — a fresh ID token is acquired per call (token lifetime is 1 hour; the Google Auth library handles refresh transparently).

**Config properties:**

```properties
liferay.customer.gcf.base.url=${LIFERAY_CUSTOMER_GCF_BASE_URL}
liferay.customer.gcf.customer.service.account.key=${LIFERAY_CUSTOMER_GCF_CUSTOMER_SERVICE_ACCOUNT_KEY}
liferay.customer.gcf.composable.service.account.key=${LIFERAY_CUSTOMER_GCF_COMPOSABLE_SERVICE_ACCOUNT_KEY}
liferay.one.gcf.entitlement.service.account.key=${LIFERAY_ONE_GCF_ENTITLEMENT_SERVICE_ACCOUNT_KEY}
liferay.one.gcf.license.service.account.key=${LIFERAY_ONE_GCF_LICENSE_SERVICE_ACCOUNT_KEY}
```

Keys are full service-account JSON strings, never file paths. Delivered via `spring-boot.env` at container startup.

---

## Key Flows

### `customer_usage_api` — SaaS / Analytics Cloud

**Request:**

```
GET {baseUrl}/customer_usage_api/api/v1/customer/usage/accounts/{accountKey}
Authorization: Bearer {idToken}
```

`accountKey` is the stable account identifier (`Account.koroneikiAccountCode`).

**Response:**

```json
{
	"totalAnonymousPageViewsCount": 123456,
	"totalClientExtensionsCapacityCPUCount": 8,
	"totalClientExtensionsCapacityRAM": 16.5,
	"totalMonthlyActiveLoggedInUsersCount": 5000,
	"totalSitesCount": 42,
	"totalStorageCapacityDocumentLibrary": 500.25
}
```

### `composable_usage_api` — Composable / PaaS

**Request:**

```
GET {baseUrl}/composable_usage_api/api/v1/accounts/{accountKey}/usage/month/{month}
Authorization: Bearer {idToken}
```

`month` is formatted `yyyy-MM` (e.g. `2026-04`).

**Response:**

```json
{
	"clientExtensionsCPU": 4.0,
	"clientExtensionsRAM": 8.0,
	"databaseStorage": 42.5,
	"documentLibraryAndBackupStorage": 300.0,
	"logStorage": 12.3,
	"networkTraffic": 150.7
}
```

### `entitlement_rules` — Entitlement Rule Evaluation

Complex scripted entitlement rules (e.g., multi-product bundle checks, geo-restricted features) are offloaded to GCF to avoid embedding heavyweight rule-engine dependencies in the workspace.

**Request:**

```
POST {baseUrl}/entitlement_rules/api/v1/evaluate
Authorization: Bearer {idToken}
{
  "accountKey": "<AccountEntry.koroneikiAccountCode>",
  "ruleId": "<Entitlement.ruleId>",
  "context": {
    "subscriptions": [...],
    "environments": [...],
    "usageMetrics": {...}
  }
}
```

**Response:**

```json
{
	"expiresAt": "2027-04-30T23:59:59Z",
	"granted": true,
	"reason": "All conditions satisfied"
}
```

Called from `EntitlementSyncTask` and from `GET /entitlements/{accountKey}`.

### `license_key_signer` — License Key Generation

Cryptographic signing of license keys for on-prem and Decoupled Deploy Tools deployments.

**Request:**

```
POST {baseUrl}/license_key_signer/api/v1/sign
Authorization: Bearer {idToken}
{
  "accountKey": "<AccountEntry.koroneikiAccountCode>",
  "productCode": "<CPDefinition.externalReferenceCode>",
  "maxUsers": 100,
  "expiresAt": "2027-04-30",
  "features": ["dxp", "analytics"]
}
```

**Response:**

```json
{
	"issuedAt": "2026-05-01T00:00:00Z",
	"licenseKey": "XXXXX-XXXXX-XXXXX-XXXXX",
	"signature": "<base64-encoded signature>"
}
```

Called from `LicenseKeyLifecycleListener` when a new subscription activates or renews.

### `salesforce_transform` — Salesforce Data Transformation

Normalizes raw Salesforce Pub/Sub payloads — field remapping, enum normalization, derived-field computation — before the workspace handler processes them. Keeps transformation logic out of the Spring Boot service for easier iteration.

**Request:**

```
POST {baseUrl}/salesforce_transform/api/v1/transform
Authorization: Bearer {idToken}
{
  "rawPayload": { /* raw Salesforce message envelope */ }
}
```

**Response:** normalized payload conforming to the schema in `salesforce.md §Message Payload`.

---

## Caller Flow (Usage)

`AccountsRestController.getUsage()` drives the usage functions:

1. Resolve `AccountEntry` from `externalReferenceCode`.

1. Fetch product purchases; determine environment type (SaaS vs. composable).

1. Call the appropriate GCF function via `GoogleCloudFunctionService`.

1. Wrap the raw JSON in `SaaSUsageStrategy` or `ExperienceUsageStrategy`.

1. Return merged usage + entitlement data to the caller.

---

## Caching

Responses are cached in-process using Caffeine.

| Property | Value |
|---|---|
| Cache name | `accountUsage` |
| Max entries | 1,000 |
| Eviction | Scheduled — full cache clear every hour (`0 0 * * * *`) |
| Annotation | `@Cacheable("accountUsage")` on both fetch methods |

Hourly full-eviction is intentionally coarse — usage data is informational and slight staleness is acceptable. If near-real-time accuracy is required in a future phase, switch to per-entry TTL.

---

## Error Handling

| Condition | Behavior |
|---|---|
| 404 Not Found | Returns `null`; caller renders "no data available" |
| Any other non-2xx | Throws exception with status code + message + `accountKey` |
| JSON parse error | Exception propagates to REST controller; controller logs at ERROR |
| Network failure | Exception propagates; no retry in `GoogleCloudFunctionService` |

`setThrowExceptionOnExecuteError(false)` is set on the HTTP client so error codes are inspected manually rather than thrown on receipt. `httpResponse.disconnect()` is guaranteed in a `finally` block.

No retry logic exists in the service layer today. Transient failures surface as HTTP 500 to the browser. If reliability becomes a concern, add retries with exponential backoff in `GoogleCloudFunctionService`.

---

## Observability

No dedicated metrics are emitted today. Failures are logged at ERROR level. Planned for phase 5:

- Metric `one.gcf.requests.total` — counter, labels `{function, outcome}`
- Metric `one.gcf.request.duration` — histogram, label `{function}`
- Metric `one.gcf.cache.hit.ratio` — gauge (from Caffeine stats)

---

## Migration Notes

- **`accountKey` mapping** — the current caller passes `koroneikiAccountCode` as the account key. In the new model, `Account.koroneikiAccountCode` carries forward unchanged, so GCF call sites require no change during migration.
- **Ownership** — usage functions (`customer_usage_api`, `composable_usage_api`) are owned by an external team (Cloud/Infrastructure). Confirm function URLs and service-account grants are provisioned for the new workspace's GCP project before phase 5 cut-over.
- **Per-environment granularity** — the current usage API is account-scoped. If the product direction moves toward per-`Environment` usage (tied to `Environment.deploymentKey`), the GCF contracts will need updating — raise with the owning team early in phase 4.
- **Composable month parameter** — the `yyyy-MM` month must be passed by the caller; there is no default. Verify the new `UsageReport` model's `periodStart`/`periodEnd` fields can be used as the authoritative source for this value post-migration.

---

## Open Questions

1. **GCF function URLs** — confirm exact base URL per environment (`dev`, `staging`, `prod`) with the Cloud Infrastructure team before phase 5.

1. **Service account scope** — verify the new workspace GCP project has the same service-account grants as the current customer-workspace. Do not assume they carry over automatically.

1. **Per-environment usage** — assess whether account-scoped aggregation is sufficient for the new `Environment` / `Subscription` model or whether the GCF API contracts need to be extended.

1. **Retry policy** — decide whether to add retry-with-backoff in `GoogleCloudFunctionService` or push retries into a queue-backed pattern before exposing to the UI.

1. **Entitlement rules ownership** — confirm whether `entitlement_rules` GCF is workspace-owned or shared infrastructure. If shared, coordinate schema changes with dependent teams.