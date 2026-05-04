# Data Model

## Liferay One Objects

---

### Account Management

#### Account (`AccountEntry` — core extension)

ERC: core extension of Liferay `AccountEntry`

| Field | Type | Notes |
|---|---|---|
| PK `accountId` | long | |
| FK `sfdcAccountId` | string | Salesforce account ID |
| `name` | string | |
| `type` | string | |
| `parentAccountId` | long | Must not create cycles |
| `koroneikiAccountCode` | string | Unique CI, uppercased, max 75; replaces `Koroneiki_Account.code_` |
| `region` | picklist | Americas, EMEA, APAC, … |
| `tier` | picklist | Platinum, Gold, Silver, Bronze, Trial, Community |
| `status` | picklist | Active, Suspended, Cancelled, Expired |
| `internal` | boolean | Liferay employee test accounts |
| `profileEmailAddress` | string | Primary notification address, separate from login email |
| `salesforceId` | string | Unique when non-null, 15/18-char SF ID |
| `dossieraId` | string | **Migration-only** — drop after cut-over |
| `maxRequestors` | int | Cap on requestor seats; null = unlimited |
| `creditLimit` ★ | decimal | Amount; Liferay finance-set, A/R risk control |
| `availableCredit` ★ | decimal | |
| `creditStatus` ★, `holdReason` ★ | string | |

★ = new fields added in this model.

**Field Mappings (from Koroneiki)**

| Section | Old Table | Old Field | → | New Table | New Field | Mapping | Transform / Rule |
|---|---|---|---|---|---|---|---|
| Direct | `Koroneiki_Account` | `uuid_` | → | `AccountEntry` | `uuid_` | Direct copy | Copy as-is |
| Direct | `Koroneiki_Account` | `accountKey` | → | `AccountEntry` | `externalReferenceCode` | salesforceAccountId | Copy as-is |
| Direct | `Koroneiki_Account` | `accountId` | → | `AccountEntry` | `accountEntryId` | Re-key | Generate new via Counter |
| Direct | `Koroneiki_Account` | `companyId` | → | `AccountEntry` | `companyId` | Direct copy | Copy as-is |
| Direct | `Koroneiki_Account` | `userId` | → | `AccountEntry` | `userId` | Direct copy | Copy as-is (creator) |
| Direct | `Koroneiki_Account` | `createDate` | → | `AccountEntry` | `createDate` | Direct copy | Copy as-is |
| Direct | `Koroneiki_Account` | `modifiedDate` | → | `AccountEntry` | `modifiedDate` | Direct copy | Copy as-is |
| Direct | `Koroneiki_Account` | `name` | → | `AccountEntry` | `name` | Direct copy | Copy as-is |
| Direct | `Koroneiki_Account` | `description` | → | `AccountEntry` | `description` | Direct copy | Copy as-is |
| Direct | `Koroneiki_Account` | `logoId` | → | `AccountEntry` | `logoId` | Direct copy | Copy as-is |
| Direct | `Koroneiki_Account` | `contactEmailAddress` | → | `EmailAddress` | `emailAddress` | EmailAddress table | EmailAddress table with AccountEntry classNameId |
| Direct | `Koroneiki_Account` | `parentAccountId` | → | `AccountEntry` | `parentAccountEntryId` | Force NULL | Always NULL — roots have no parent; Project hierarchy now lives on Subscription |
| Direct | `Koroneiki_Account` | `mvccVersion` | → | `AccountEntry` | `mvccVersion` | Reset | Set to 0 on insert |
| Direct | `Marketplace AccountEntry` | `type` | → | `AccountEntry` | `type` | | |
| Direct | `Marketplace AccountEntry` | `user` | → | `AccountEntry` | `user` | Type Person account user relationship | |
| Transformed | `Koroneiki_Account` | `website` | → | `Website` | `url` | Website table | |
| Transformed | `Koroneiki_Account` | `tier` | → | `AccountEntry custom field` | `accountTier` | Custom field | Move tier to custom field. Set `AccountEntry.type_` = "business" |
| Transformed | `Koroneiki_Account` | `status` | → | `AccountEntry` | `status` | Value-mapped | Map enum string → WorkflowConstants int (active=0/APPROVED, inactive=5/INACTIVE) |
| Derived | `Koroneiki_Account` | `userId` | → | `AccountEntry` | `userName` | Derived | Lookup `User_.screenName` by userId; `''` if user gone |
| Derived | `Koroneiki_Account` | `userId` | → | `AccountEntry` | `statusByUserId` | Derived | Use creator userId (or last AuditEntry actor) as best-effort |
| Derived | `(User_ lookup)` | | → | `AccountEntry` | `statusByUserName` | Derived | Lookup screenName for statusByUserId |
| Derived | `Koroneiki_Account` | `modifiedDate` | → | `AccountEntry` | `statusDate` | Derived | Use modifiedDate as proxy |
| Derived | Address rows | | → | `AccountEntry` | `defaultBillingAddressId` | Address-derived | Pick Address row where `typeId='billing'` (or `primary_=1` fallback) |
| Derived | Address rows | | → | `AccountEntry` | `defaultShippingAddressId` | Address-derived | Pick Address row where `typeId='shipping'` (or `primary_=1` fallback) |
| Contact-info | `Koroneiki_Account` | `profileEmailAddress` | → | `EmailAddress` | `emailAddress` | Renamed | EmailAddress table with AccountEntry classNameId |
| Contact-info | `Koroneiki_Account` | `phoneNumber` | → | `Phone` | `phone` | Phone table | Phone table with AccountEntry classNameId |
| Contact-info | `Koroneiki_Account` | `faxNumber` | → | `Phone` | `phone` | Phone table | Phone table with AccountEntry classNameId |
| Metadata | `Koroneiki_Account` | `code_` | → | `AccountEntry custom field` | `accountCode` | Custom field | Migrate as-is |
| Metadata | `Koroneiki_Account` | `region` | → | `AccountEntry custom field` | `region` | Custom field | Migrate as-is |
| Metadata | `Koroneiki_Account` | `language` | → | `AccountEntry custom field` | `languageId` | Custom field | Migrate as-is |
| Metadata | `Koroneiki_Account` | `internal_` | → | `AccountEntry custom field` | `internal` | Custom field | Migrate as-is; can remove later if unused |
| Metadata | `Koroneiki_Account` | `dataRegion` | → | `AccountEntry custom field` | `dataRegion` | Custom field | Migrate as-is |
| AccountField | `Koroneiki_AccountField` | `allowComplimentary` | → | `AccountEntry custom field` | `allowComplimentary` | Custom field | `'true'/'false'` string → boolean |
| AccountField | `Koroneiki_AccountField` | `allowPermanentLicenses` | → | `AccountEntry custom field` | `allowPermanentLicenses` | Custom field | `'true'/'false'` string → boolean |
| AccountField | `Koroneiki_AccountField` | `allowSelfProvisioning` | → | `AccountEntry custom field` | `allowSelfProvisioning` | Custom field | `'true'/'false'` string → boolean |
| AccountField | `Koroneiki_AccountField` | `projectSolution` | ✗ | remove | | | No longer used |

**Marketplace AccountEntry custom fields (ExpandoColumn)**

| Field | Disposition |
|---|---|
| `AccountType` | Keep → `AccountEntry custom field` |
| `Tax ID` | Map to OOTB Tax ID field |
| `CatalogId` | Remove |
| `Contact Email` | Remove |
| `Contact Phone` | Remove |
| `Create Date` | Remove |
| `Github Username` | Remove |
| `Homepage URL` | Remove |
| `Industry` | Remove |
| `Paypal Email Address` | Remove |
| `Tax Documents` | Remove |

**Address**

Re-key all `Address` rows; parent account's address is primary. Migrate addresses from child accounts to parent account. Open question: which address takes precedence if multiple? Send to Salesforce for reconciliation.

**Other Account relationships**

| Legacy Table | Disposition |
|---|---|
| `Koroneiki_AccountNote` | Migrate to `AccountNote` custom object |
| `Koroneiki_AuditEntry` | Migrate to OOTB Audit framework (investigate feasibility) |
| `Koroneiki_ContactAccountRole` | Migrate to OOTB "assign user to account role"; open question: migrate all child account contacts to parent account? |
| `Koroneiki_ExternalLink` | Migrate to `ExternalLink` custom object (all external links) |

**Migration sources**
- `kor.Koroneiki_Account` — 18,390 rows
- `e5a2_lpartition_1860468.O_KoroneikiAccount` — 2,313 rows (side-car; reconcile against Koroneiki primary)
- `kor.Koroneiki_ExternalLink` (domain=salesforce/dossiera) — feeds `salesforceId` / `dossieraId`
- `logoId` — **not migrated** (deprecated)

---

#### AccountFlag (`ONE_CUS_ACCNT_FLAG`)

| Field | Type | Notes |
|---|---|---|
| PK `accountFlagId` | long | |
| FK `accountId` | long | |
| `flagCode` | picklist | |
| `flagValue` | picklist | |
| `startDate`, `endDate` | datetime | |
| `note` | string | |
| `finished` | boolean | |
| `accountKey` | string | Denormalized account identifier |

**Migration source:** `e5a2_lpartition_1860468.O_AccountFlag` — 557 rows

---

#### AccountNote (`ONE_CUS_ACCNT_NOTE`)

| Field | Type | Notes |
|---|---|---|
| PK `accountNoteId` | long | |
| FK `accountId` | long | |
| `summary` | string | |
| `content` | Clob | |
| `format` | picklist | |
| `type` | picklist | |
| `priority` | picklist | |
| `status` | picklist | |
| `creatorName`, `creatorUID` | string | Frozen at creation; do not overwrite on edit |
| `modifierName`, `modifierUID` | string | |

---

#### BannedEmailDomain (`ONE_REF_BANNED_EMAIL`)

| Field | Type | Notes |
|---|---|---|
| PK `bannedEmailDomainId` | long | |
| `domain` | string | e.g. `mailinator.com`; unique |
| `reason` | string | |
| `addedAt` | datetime | |
| `addedByUserId` | long | |

**Migration source:** `e5a2_lpartition_1860468.O_BannedEmailDomain` — ~4,800 rows

---

#### ExternalLink (`ONE_CUS_EXT_LINK`)

| Field | Type | Notes |
|---|---|---|
| PK `externalReferenceId` | long | |
| `domain` | string | External system name (`salesforce`, `dossiera`, `jira`) |
| `entityName`, `entityId` | string | External record identifier |
| `ownerClassName`, `ownerClassPK` | string / long | Polymorphic FK to the one.liferay record |
| `label` | string | Human-readable label |

**Migration source:** `kor.Koroneiki_ExternalLink` — 254,202 rows

---

#### Publisher (`ONE_MKT_PUBLISHER`)

| Field | Type | Notes |
|---|---|---|
| PK `publisherId` | long | |
| FK `accountId` | long | |
| `publisherName` | string | |
| `slug` | string | URL-safe unique identifier |
| `emailAddress` | string | |
| `description` | Clob | |
| `logo` | attachment | |
| `commerceCatalogId` | long | Linked Commerce catalog |
| `approvalStatus` | picklist | |
| `payoutMethod` | string | |
| `payoutReference` | string | |
| `website` | string | |

**Migration source:** `e5a2_lpartition_11706165.O_PublisherDetails` — 134 rows

---

#### TrialProvisioning (`ONE_MKT_TRIAL_PROV`)

Tracks the lifecycle of a cloud trial provisioning request.

| Field | Type | Notes |
|---|---|---|
| PK `trialProvisioningId` | long | |
| FK `subscriptionId` | long | |
| FK `accountEntryId` | long | |
| `provisioningStatus` | picklist | `Pending` · `Provisioning` · `Active` · `Expiring` · `Expired` · `Failed` |
| `provisioningFlow` | string | Matches `OrderType.provisioningFlow` |
| `externalProvisioningId` | string | ID returned by Liferay Cloud / Console API |
| `startedAt` | datetime | |
| `completedAt` | datetime | |
| `failureReason` | string | |

**State machine:**

```
Pending → Provisioning → Active → Expiring → Expired
                       ↘ Failed
```

Transitions driven by `TrialProvisioning.onAfterAdd` Object Action (Pending → Provisioning → Active) and `TrialLifecycleTick` scheduled task (Active → Expiring → Expired).

---

#### Team (`ONE_CUS_TEAM`)

| Field | Type | Notes |
|---|---|---|
| PK `teamId` | long | |
| FK `accountId` | long | |
| `name` | string | |
| `description` | string | |
| `system` | boolean | System-managed teams cannot be deleted |
| `teamKey` | string | Stable external ID |

**Migration sources**
- `kor.Koroneiki_Team` — 18,570 rows
- `kor.Koroneiki_ContactTeamRole` — 29,262 rows → Team ↔ User membership (role-within-team dropped)
- `kor.Koroneiki_TeamRole` — 2 rows → **dropped**
- `kor.Koroneiki_TeamAccountRole` — 39 rows → **dropped**

---

### Subscription Management

> **D4 amendment:** Commerce's native subscription model doesn't cover all required fields (`developerCount`, `billingCadence`, multi-line billing, usage metering, credit holds, invoice tracking). The design uses Commerce for the order and catalog layer (`CommerceOrder`, `CommerceOrderItem`, `CPDefinition`) but adds custom `Subscription` and `SubscriptionItem` Objects for subscription contract and billing fields. Commerce subscription lifecycle events still drive provisioning and entitlement flows.

#### Subscription

| Field | Type | Notes |
|---|---|---|
| PK `subscriptionId` | long | New key |
| FK `accountEntryId` | long | AccountEntry |
| `externalReferenceCode` | string | salesforceContactId |
| `status` | string | Need job to update on some cadence |
| `startDate` | datetime | Aggregate of SubscriptionItems — earliest startDate |
| `endDate` | datetime | Aggregate of SubscriptionItems — latest endDate |
| `subscriptionOwner` | string | Email address |
| `term` | int | e.g. 12 (months) — contract term from Salesforce |
| `contractBillingCadence` | int | e.g. 12 (months) — usually 12 months |
| `overageBillingCadence` | int | e.g. 3 (months) |
| `currency` | string | Get from auto prov message |
| `renewalState` | string | Auto prov renewal |
| `originalEndDate` | datetime | Sales-original end before extensions; defaults to `endDate` |
| `billingCadence` | string | |
| `renewalDate` | datetime | |
| `developerCount` | int | Cap on developer seats; null = unlimited |
| `koroneikiProductPurchaseKey` | string | **Migration-only** |

**Project-level fields (from `Koroneiki_AccountField`)**

| Old Field | New Field | Type |
|---|---|---|
| `gsOpportunity` | `gsOpportunity` | boolean |
| `liferayVersion` | `liferayVersion` | string |
| `premiumService` | `premiumService` | boolean |
| `ldpWorkspaceName` | `ldpWorkspaceName` | string |

**Open questions**
- What is a use case for the Subscription table vs SubscriptionItem or Entitlement?
- Differences for what goes in SubscriptionItem vs Commerce Order Item. How do marketplace apps live in this structure?
- Where does an aggregation of SubscriptionItems live (e.g. `ProductPurchaseView`, `SubscriptionGroup`)?
- Can you have 2 LDPs in the same subscription?
- Primary contact is per opportunity — possibly make it per subscription.

**Migration source:** `kor.Koroneiki_ProductPurchase` — 65,271 rows

---

#### SubscriptionItem

| Field | Type | Notes |
|---|---|---|
| PK `subscriptionItemId` | long | |
| FK `subscriptionId` | long | |
| `externalReferenceCode` | string | opportunityLineItemId |
| `opportunityId` | string | From `Koroneiki_ExternalLink` (domain=salesforce, entityName=account) |
| `productId` | long | → Commerce Product ID (from `Koroneiki_ProductPurchase.productEntryId`) |
| `currency` | string | From auto provisioning `currencyIsoCode` |
| `quoteLineId` | string | New Salesforce CPQ value needed in auto prov message |
| `quantity` | int | Direct copy from `Koroneiki_ProductPurchase.quantity` |
| `unitPrice` | double | From auto provisioning `totalPrice` |
| `startDate` | datetime | From `Koroneiki_ProductPurchase.startDate` |
| `endDate` | datetime | From `Koroneiki_ProductPurchase.originalEndDate` |
| `effectiveEndDate` | datetime | From `Koroneiki_ProductPurchase.endDate` (grace period endDate) |
| `type` (addedVia) | string | `salesforce` / `marketplace` |
| `status` | string | Approved / Canceled / On Hold |
| `cloudRegion` | string | From `Koroneiki_ProductField.cloudRegion` (e.g. `us-central`) |
| `machineType` | string | From `Koroneiki_ProductField.machineType` (Standard / High) |
| `orderType` | string | From `Koroneiki_ProductField.productType` (New Business / Renewal) |
| `sizing` | int | From `Koroneiki_ProductField.sizing` (optional; needed for licenses) |
| `salesforceOpportunityId` | string | Populated by Salesforce Pub/Sub subscriber |
| `billings`, `renewal`, `addOn` | boolean | Flags |
| `koroneikiProductPurchaseKey` | string | **Migration-only** |

**Open questions**
- Need to consider amended opportunity handling.
- If we map out SubscriptionItem with Salesforce items/bundles, how will we map or migrate/consolidate data?
- What do we do for migration for purchases manually changed on Koroneiki?

---

#### Entitlement (`ONE_ENT_ENTITLEMENT`)

Materialized grant records. Do NOT port rows from legacy — regenerate on first EntitlementSync run.

| Field | Type | Notes |
|---|---|---|
| PK `entitlementId` | long | |
| FK `entitlementDefinitionId` | long | |
| `entitlementDefinitionCode` | string | Denormalized for query performance |
| `targetClassName`, `targetClassPK` | string / long | Polymorphic FK to the granted resource |
| FK `subscriptionItemId` | long | |
| FK `subscriptionId` | long | Denormalized |
| FK `usageDefinitionId` | long | For metered entitlements; FK → `UsageDefinition` |
| `prepaidQuota` | double | Soft cap (Excel: `quantity`) |
| `maxQuantity` | double | Hard cap |
| `overageRate` | double | |
| `enforcement` | string | |
| `grantedAt`, `lastConfirmedAt` | datetime | |
| `effectiveStart`, `effectiveEnd` | datetime | |
| `name` | string | Marketing Activities / cpu / maxservers / maxNodes |
| `grantType` | string | fixed / rollover / prepaid / metered |
| `value` | string | |

**Fields migrated to name-value-quantity pair (from `Provisioning_LicenseKey`)**

| Legacy Field |
|---|
| `maxServers` |
| `maxConcurrentUsers` |
| `maxUsers` |
| `maxHttpSessions` |
| `maxClusterNodes` |

**62 active rules** — 59 object-filter rules + 3 scripted rules. Five rules (#06 Developer Tools, #08 DXP, #21 PaaS, #24 SaaS, #54–59 Contact rules) require `isPrimary=true` on Commerce Product.

**Open questions**
- What if there is a gap in SubscriptionItems?
- Should support seats be an entitlement? Probably not.
- CMP Trial: 3-month access with no SubscriptionItem — how is entitlement handled?
- Think about cloud native subscriptionUuid workflow and pod count products with max node count.
- If an amended opportunity comes in, expire entitlement and create new SubscriptionItem.

---

#### EntitlementDefinition (`ONE_ENT_DEFINITION`)

| Field | Type | Notes |
|---|---|---|
| PK `entitlementDefinitionId` | long | |
| `name` | string | |
| `code` | string | Unique; stable cross-system identifier |
| `description` | string | |
| `targetClassName` | string | `AccountEntry` or `User` |
| `ruleType` | string | `filter` · `scripted` |
| `ruleBody` | Clob | JSON |
| `cascadeAfter` | long | Optional FK to another `EntitlementDefinition` |
| `status` | string | `Active` · `Inactive` |
| `legacyKoroneikiId` | long | **Migration-only** |

**Migration source:** `kor.Koroneiki_EntitlementDefinition` — 62 rows

---

#### UsageDefinition

> Also referred to as `ConsumptionMetric` in some arch docs. The Excel data model and the `UsageEvent` / `Entitlement` FK columns all use `usageDefinitionId`.

| Field | Type | Notes |
|---|---|---|
| PK `usageDefinitionId` | long | |
| `externalReferenceCode` | string | New stable key |
| `unit` | string | e.g. GB, page views, vcpu, AI tokens |
| `aggregation` | string | count / sum |
| `period` | string | Per month, day, hour |
| `quantity` (baseUnitQuantity) | double | How many (GB, page views) |
| `overageRate` | double | |
| `overageCurrency` | string | USD / EUR / JPY |
| `version` | int | Immutable history; new version on rule change |

**Notes:** Should expire entitlements and make new ones if changed. No current usage for version field.

---

#### UsageEvent

| Field | Type | Notes |
|---|---|---|
| PK `usageEventId` | long | |
| FK `environmentId` | long | |
| FK `subscriptionId` | long | Can search subscriptionItems and entitlements through subscriptionId |
| FK `usageDefinitionId` | long | |
| `eventTimestamp` | datetime | |
| `quantity` | double | |
| `dedupKey` | string | Idempotent; client-assigned |

**Notes:** Store one unique record per received usage event.

---

#### Property

| Field | Type | Notes |
|---|---|---|
| `accountEntryId` | long | |
| `classNameId` | long | order / account / subscriptionitem |
| `classPK` | long | orderId / accountId / subscriptionItemId |
| `name` | string | e.g. `nonprodsubscriptionuuid` |
| `value` | string | e.g. `12345-abcde` |
| `metadataJson` | text | JSON metadata blob |

**Sources**
- Custom Marketplace Properties (Commerce Order Custom Fields) — JSON metadata
- `Koroneiki_ProductField.nonProductionSubscriptionUuid`
- `Koroneiki_ProductField.productionSubscriptionUuid`

---

### Environment Management

#### Environment (`ONE_COM_DEPLOYMENT`)

| Field | Type | Notes |
|---|---|---|
| PK `environmentId` | long | |
| FK `subscriptionId` | long | |
| `deploymentKey` | string | Stable external ID, uppercased; preserved from `AccountSubscription.accountSubscriptionERC` |
| `deploymentGroupERC` | string | Optional grouping; replaces `AccountSubscriptionGroup` |
| `type` | picklist | `cloud_native` · `on_prem` (SaaS, PaaS, CNE, On-prem) |
| `name` | picklist | Production · Non-Production · Development · Backup · HA Production |
| `instanceSize` | picklist | XS · S · M · L · XL · XXL |
| `productVersion` | string | DXP/Portal version string: `7.4`, `2024.q3`, … |
| `hasDisasterDataCenterRegion` | boolean | Default `false` |
| `startDate`, `endDate` | datetime | |
| `activationMode` | string | `file_key` · `heartbeat` (is it license key mode or cloud heartbeat mode) |
| `region` | FK | → `Region` |
| `dataCenterId` | FK | → `DataCenter` |
| `status` | picklist | Active · Decommissioned · Suspended; default `Active` |
| `lastHeartbeatAt` | datetime | |
| `currentEntitlementHash` | string | Identity ID so heartbeat server knows what server it is |

**License Key Environment Fields (from `Provisioning_LicenseKey`)**
- `hostName` / `domain`
- `ipAddresses`
- `macAddresses`
- `serverId`

**Heartbeat / mTLS Certificate Authority Design**

For `activationMode=heartbeat`: the Kubernetes operator sends periodic mTLS POSTs to `/internal/heartbeat/{environmentId}`. On valid heartbeat, returns a signed entitlement bundle; 24 consecutive missed heartbeats (24 h window) → `status=Suspended`.

| CA Level | Details |
|---|---|
| Root CA | Offline; HSM / GCP KMS |
| Intermediate CA | Online; signs per-environment leaf certs; rotated annually |
| Per-environment leaf | 90-day validity; auto-renewed; Subject CN = `env-{environmentId}` |
| Revocation | CRL at `{baseUrl}/.well-known/heartbeat-crl.pem` |

**Migration sources**
- `e5a2_lpartition_1860468.O_AccountSubscription` — 4,572 rows
- `e5a2_lpartition_1860468.O_AccountSubscriptionGroup` — 3,073 rows → `deploymentGroupERC` field
- `e5a2_lpartition_1860468.O_AccountSubscriptionTerm` — 13 rows → **dropped**
- `e5a2_lpartition_1860468.O_DXPCloudEnvironment` — 5 rows → fold into Environment
- `e5a2_lpartition_1860468.O_LXCEnvironment` / `O_LiferayExperienceCloudEnvironment` — 9 rows → fold
- `e5a2_lpartition_1860468.O_CloudNativeEnvironment` — 16 rows → fold

---

#### Activation — File Key (`ONE_COM_LICENSE_KEY`)

| Field | Type | Notes |
|---|---|---|
| PK `activationKeyId` | long | |
| FK `environmentId` | long | |
| `key` | string | **Unique; byte-identical from legacy** |
| `payload` | Clob | Signed artifact |
| `productVersion` | string | e.g. `dxp-7.4`, `dxp-2024.q3` |
| `issuedAt`, `expiresAt` | datetime | |
| `maxServers` | int | null = unlimited; legacy sentinel: `10000` |
| `maxDevelopers` | int | null = unlimited |
| `hostNames` | string | Newline-delimited (DXP license format) |
| `ipAddresses` | string | Newline-delimited |
| `clustered` | boolean | |
| `licenseType` | string | Production · NonProduction · Development · Trial · Internal |
| `status` | string | `Active` · `Expired` · `Revoked` · `Superseded` |
| `legacyLicenseKeyId` | long | **Migration-only** |

**Legacy field mappings (from OSB / Provisioning)**

| Old Field | New Field | Notes |
|---|---|---|
| `assetReceiptLicenseUuid` | → `commerceOrderId` | |
| `accountKey` | → `accountEntryId` | |
| `productPurchaseKey` | → `subscriptionItemId` | TBD |
| `licenseEntryId` | → | TBD |
| `productKey` | → `CProductId` | |
| `accountCode` | → `accountEntryCode` | |
| `licenseEntryName` | → `licenseName` | |
| `licenseEntryType` | → `licenseType` | |
| `productId` | → `productId` | e.g. Commerce: `9a473157-06a6-44b6-b017-a360ffaf5f38` |

> **File Key Generation Flow** (for `activationMode=file_key`)

1. Resolve `CommerceSubscriptionEntry` → `SubscriptionItem` → `CPDefinition.licenseKeyProductVersion`.

1. Resolve `Environment.name` (Production / Non-Production / etc.) → `licenseType`.

1. Build key payload: `key`, `productVersion`, `licenseType`, `startDate`, `expiresAt`, `maxServers`, `maxDevelopers`, `hostNames`, `ipAddresses`, `clustered`.

1. Sign payload with private key stored in `liferay-one-instance-settings` secret `license-signing-private-key` → `LicenseKey.payload`.

1. Write `LicenseKey` row with `status=Active`.

1. Email key artifact to `AccountEntry.profileEmailAddress` + primary `Customer_Admin`.

**Preservation constraint:** Every existing `key` string must land in the new table unchanged. Regenerating keys breaks running deployments.

**Open questions**
- What is the minimum license version still needed to generate for?
- Do we need to migrate all of them? Very old licenses, trial licenses, etc.
- Get rid of `LicenseEntry`, hard-code what we can.

**Migration sources**
- `prov.Provisioning_LicenseKey` — 230,466 rows
- `prov.OSB_LicenseKey` — 201,897 legacy rows (overlap check required)
- `prov.Provisioning_CommonLicenseKey` — 476 rows (investigate; may be definition templates)
- `prov.Provisioning_LicenseEntry` — 48 rows; `Provisioning_ProductVersion` — 36 rows

---

#### DataCenter (`ONE_REF_DATA_CENTER`)

| Field | Type | Notes |
|---|---|---|
| PK `dataCenterId` | long | |
| FK `regionId` | long | → Region |
| `code` | string | Unique short identifier |
| `name` | string | |
| `type` | string | `DXPCloud` · `AnalyticsCloud` · `LXC` |
| `cloudProvider` | string | `AWS` · `GCP` · `Azure` · `Liferay` |
| `providerRegion` | string | e.g. `us-east-1` |
| `active` | boolean | |
| `capacity` | int | null = unlimited |

---

#### Region (`ONE_REF_REGION`)

| Field | Type | Notes |
|---|---|---|
| PK `regionId` | long | |
| `code` | string | Seed values: AMER, EMEA, APAC, LATAM, ANZ, JPN, CHN, IND |
| `name` | string | |
| `timeZone` | string | IANA time zone string |
| `supportCoverage` | string | Business-hours description |
| `active` | boolean | |

---

### Commerce

#### Commerce Product (CPDefinition with custom fields)

| Field | Type | Notes |
|---|---|---|
| PK `CProductId` | long | |
| `externalReferenceCode` | string | salesforceProductId / OOTB key for marketplace |
| `catalog` | string | Salesforce / Liferay / AccountEntry |
| `name` | string | |
| `description` | string | |
| `isPrimary` | boolean | Required for 5 entitlement rules; default `false` |
| `licenseKeyProductVersion` | string | Version string in generated keys (`dxp-7.4`, `dxp-2024.q3`); null for non-key products |
| `productFamily` | picklist | DXP · Portal · Analytics · Commerce · AIHub · CMP · Partner · Support · Training · Other |
| `koroneikiProductKey` | string | **Migration-only** |
| `metricCoverage` | string | Rules from SFDC Product Catalog |

**Specifications to migrate from `Koroneiki_ProductField`**

| Old Field | → | New Field | Notes |
|---|---|---|---|
| `type` | → | `type` | Primary / Regular / Add-on / Marketplace App |
| `licenses` | → | `license` | Move to SubscriptionItem or Entitlement |
| `display-group-name` | ✗ | remove | |
| `display-name` | ✗ | remove | |
| `database-size` | → | Product Specification | Add Specification Group for "Liferay SaaS Plans" |
| `document-library-size` | → | Product Specification | Add Specification Group for "Liferay SaaS Plans" |
| `domains` | → | Product Specification | Domain count (entitlement number) |
| `high-database` | → | Product Specification | Specification Group "Machine Type High" |
| `high-extensions-ram` | → | Product Specification | |
| `high-extensions-vcpus` | → | Product Specification | |
| `high-logs` | → | Product Specification | |
| `high-storage` | → | Product Specification | |
| `high-traffic-networking` | → | Product Specification | |
| `project-workspaces` | → | Product Specification | Specification Group "Liferay SaaS Plans" |
| `ram` | → | Product Specification | Specification Group "Liferay SaaS Plans" |
| `sites` | → | Product Specification | Specification Group "Liferay SaaS Plans" |
| `standard-database` | → | Product Specification | Specification Group "Machine Type Standard" |
| `standard-extensions-ram` | → | Product Specification | Specification Group "Machine Type Standard" |
| `standard-extensions-vcpus` | → | Product Specification | Specification Group "Machine Type Standard" |
| `standard-logs` | → | Product Specification | Specification Group "Machine Type Standard" |
| `standard-storage` | → | Product Specification | Specification Group "Machine Type Standard" |
| `standard-traffic-networking` | → | Product Specification | Specification Group "Machine Type Standard" |
| `transactions` | → | Product Specification | Specification Group "Liferay SaaS Plans" |
| `vcpu` | → | Product Specification | Specification Group "Liferay SaaS Plans" |

**CPSpecificationOption values to migrate (Marketplace app metadata)**

| Specification |
|---|
| `App API Reference URL` |
| `App Beta` |
| `App Documentation URL` |
| `App Entry` |
| `App Entry UUID` |
| `App Installation Guide URL` |
| `App Settings` |
| `App Usage Terms URL` |
| `Current Requirements` |
| `Developer Name` |
| `Downloadable Cloud App` |
| `Latest Version` |
| `Licence Duration` |
| `License` |
| `License Term` |
| `License Type` |
| `Liferay Product Capabilities` |
| `Liferay Product Categories` |
| `Liferay Version` |
| `Lifetime License` |
| `Number of CPUs` |
| `Orphan` |
| `Our Selection` |
| `Partnership Type` |
| `Past Versions Work With` |
| `Price Model` |
| `Product Downloads` |
| `Product Notes` |
| `Publisher Name` |
| `Publisher Web site URL` |
| `Ram in GB` |
| `Solution Company Description` |
| `Solution Company Email` |
| `Solution Company Phone` |
| `Solution Company Website` |
| `Solution Contact Email` |

**SEO:** Migrate `FriendlyURLEntryLocalization.Friendly URL` as-is.

**Categories to migrate**

| Category |
|---|
| Marketplace App Category |
| Marketplace App Tags |
| Marketplace Availability |
| Marketplace Category |
| Marketplace Liferay Platform Offering |
| Marketplace Liferay Version |
| Marketplace Product Type |
| Marketplace Solution Category |
| Marketplace Solution Tags |

**Migration sources**
- `kor.Koroneiki_ProductEntry` — 505 rows
- `kor.Koroneiki_ProductField` — 260,826 name/value pairs

---

### User & Organization

#### User

| Field | Type | Notes |
|---|---|---|
| `userId` | long | |
| `uuid_` | string | User custom field |
| `emailAddress` | string | |
| `firstName` | string | |
| `middleName` | string | |
| `lastName` | string | |
| `verified` | boolean | User custom field |

**Migration note:** Need to migrate from both Koroneiki Contact and Marketplace users and reconcile.

**Marketplace custom fields to drop**

| Field |
|---|
| `Accesssecret`, `Accesstoken`, `Clientid` |
| `Googleaccesstoken`, `Googlerefreshtoken` |
| `Hscontactcache`, `Hsguestcache` |
| `Osbagreedtocontacevents`, `Osbagreedtocontactsales`, `Osbagreedtocontacttrainings`, `Osbagreedtocontacttriallicenses` |
| `Requestsecret`, `Requesttoken` |

---

#### Organization

| Field | Type | Notes |
|---|---|---|
| PK `organizationId` | long | |
| `name` | string | |
| `accountEntryId` | long | Custom field; relates org to AccountEntry (e.g. Accenture FLS org → Accenture AccountEntry) |

**Migration source:** `support.liferay.com` portal — **FLS and Liferay orgs only**. Do not migrate customer orgs.

**Open question:** Need a reliable way to relate orgs to AccountEntry — e.g. Accenture FLS org → Accenture AccountEntry.

---

## ERC and FriendlyURL Registry

| Object | ERC | Separator | Source |
|---|---|---|---|
| `AccountFlag` | `ONE_CUS_ACCNT_FLAG` | `cpaf` | ported (customer) |
| `AccountNote` | `ONE_CUS_ACCNT_NOTE` | `cpan` | new |
| `BannedEmailDomain` | `ONE_REF_BANNED_EMAIL` | `cpbd` | ported (customer) |
| `BusinessEvent` | `ONE_SUP_BIZ_EVENT` | `cpbe` | ported (customer) |
| `BusinessEventVersion` | `ONE_SUP_BIZ_EVENT_VER` | `cpbv` | ported (customer) |
| `CallbackRequest` | `ONE_SUP_CALLBACK_REQ` | `cpcr` | ported (customer) |
| `DataCenter` | `ONE_REF_DATA_CENTER` | `cpdc` | new |
| `Entitlement` | `ONE_ENT_ENTITLEMENT` | `cpen` | new |
| `EntitlementDefinition` | `ONE_ENT_DEFINITION` | `cped` | new |
| `Environment` (Deployment) | `ONE_COM_DEPLOYMENT` | `cpdp` | new |
| `ExternalLink` | `ONE_CUS_EXT_LINK` | `cpel` | new |
| `LicenseKey` (Activation — File Key) | `ONE_COM_LICENSE_KEY` | `cplk` | new |
| `OrderType` | `ONE_MKT_ORDER_TYPE` | `cpot` | new |
| `Publisher` | `ONE_MKT_PUBLISHER` | `cppu` | new |
| `PublisherAsset` | `ONE_MKT_PUB_ASSET` | `cppa` | new |
| `PublisherAssetAttachment` | `ONE_MKT_PUB_ASSET_ATTACH` | `cpaa` | new |
| `PublisherSalesSummary` | `ONE_MKT_PUB_SALES_SUM` | `cpss` | new |
| `Region` | `ONE_REF_REGION` | `cprg` | new |
| `ReplacementActivationKeyRequest` | `ONE_SUP_REPL_ACT_KEY` | `cprk` | ported (customer) |
| `Report` | `ONE_MKT_REPORT` | `cprr` | new |
| `RequestPublisherAccount` | `ONE_MKT_REQ_PUB_ACCNT` | `cprp` | new |
| `Subscription` | `ONE_COM_SUBSCRIPTION` | `cpsc` | new |
| `SubscriptionItem` | `ONE_COM_SUB_ITEM` | `cpsi` | new |
| `SupportTicket` | `ONE_SUP_TICKET` | `cpst` | new |
| `SupportTicketEscalation` | `ONE_SUP_TICKET_ESC` | `cpse` | ported (customer) |
| `Team` | `ONE_CUS_TEAM` | `cpte` | new |
| `TicketAttachment` | `ONE_SUP_TICKET_ATTACH` | `cpta` | ported (customer) |
| `TrialProvisioning` | `ONE_MKT_TRIAL_PROV` | `cptp` | new |
| `UsageDefinition` | `ONE_ENT_USAGE_DEF` | `cpud` | new |
| `UsageEvent` | `ONE_ENT_USAGE_EVT` | `cpue` | new |
| `Property` | `ONE_COM_PROPERTY` | `cppr` | new |

---

## See Also

- [`api.md`](./api.md) — headless conventions and custom REST contracts
- [`integrations/salesforce.md`](./integrations/salesforce.md) — Salesforce-owned objects and inbound Pub/Sub