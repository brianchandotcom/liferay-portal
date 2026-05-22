# Data Model

## Liferay One Objects

---

### Account Management

#### Account (`AccountEntry` — core extension)

**system:** `true`

| Field | Type | Notes |
|---|---|---|
| PK `accountEntryId` | long | |
| `externalReferenceCode` | string | Salesforce Account.Id (18-char); unique |
| `name` | string | |
| `description` | string | Prefer Salesforce; fall back to Marketplace |
| `logoId` | long | |
| `parentAccountEntryId` | long | Force NULL; child account hierarchy lives on Contract |
| `type` | string | `business` · `person` |
| `userId` | long | FK to User; Person-type accounts only |
| `status` | string | `approved` · `inactive` · `closed` |
| `taxId` | string | OOTB field |
| `defaultBillingAddressId` | long | FK to Address |
| `defaultShippingAddressId` | long | FK to Address |
| `internal` | boolean | Liferay employee test accounts |
| `maxRequestors` | int | Cap on requestor seats; null = unlimited |
| `creditLimit` | decimal | Liferay finance-set, A/R risk control |
| `availableCredit` | decimal | |
| `creditStatus`, `holdReason` | string | |

**Custom fields:** `accountTier` (Platinum / Gold / Silver / Bronze / Trial / Community), `accountCode` (migrated Koroneiki accountKey), `allowComplimentary` (boolean), `allowPermanentLicenses` (boolean), `allowSelfProvisioning` (boolean), `AccountType`

---

#### AccountFlag (`C_ACCNT_FLAG`)

**system:** `false`

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

---

#### AccountNote (`C_ACCNT_NOTE`)

**system:** `false`

| Field | Type | Notes |
|---|---|---|
| PK `accountNoteId` | long | |
| `uuid` | string | Preserved from Koroneiki for cross-system traceability |
| `externalReferenceCode` | string | Migrated Koroneiki `accountNoteKey` |
| FK `accountEntryId` | long | |
| `summary` | string | |
| `content` | Clob | |
| `format` | picklist | |
| `type` | picklist | |
| `priority` | picklist | |
| `status` | picklist | |
| `createDate` | datetime | |
| `createdByUserId` | long | FK to User |
| `createdByUserName` | string | Frozen at creation; do not overwrite on edit |
| `modifiedDate` | datetime | |
| `modifiedByUserId` | long | FK to User |
| `modifiedByUserName` | string | |
| `koroneikiAccountKey` | string | Parent Koroneiki accountKey; migration traceability |
| `koroneikiProjectName` | string | Name of the originating Koroneiki child project |

---

#### BannedEmailDomain (`C_BANNED_EMAIL`)

**system:** `false`

| Field | Type | Notes |
|---|---|---|
| PK `bannedEmailDomainId` | long | |
| `domain` | string | e.g. `mailinator.com`; unique |
| `reason` | string | |
| `addedAt` | datetime | |
| `addedByUserId` | long | |

---

#### CreditHold (`C_CREDIT_HOLD`)

**system:** `false`

Finance/A/R-set hard hold; overrides spend limits.

| Field | Type | Notes |
|---|---|---|
| PK `creditHoldId` | long | |
| FK `accountId` | long | |
| `reason` | string | |
| `startDate` | datetime | |
| `endDate` | datetime | Null = indefinite |
| `setByUserId` | long | Finance user |
| `note` | string | |

---

### Subscription Management

#### Commerce Product (CPDefinition with custom fields)

**system:** `true`

| Field | Type | Notes |
|---|---|---|
| PK `CProductId` | long | |
| `externalReferenceCode` | string | Salesforce Product2.Id (18-char); OOTB key for marketplace |
| `catalog` | string | `Salesforce` · `Liferay` · `AccountEntry` |
| `name` | string | |
| `description` | string | |
| `isPrimary` | boolean | Default `false` |
| `licenseKeyProductVersion` | string | Version string in generated keys, e.g. `dxp-7.4`; null for non-key products |
| `productFamily` | picklist | DXP · Portal · Analytics · Commerce · AIHub · CMP · Partner · Support · Training · Other |
| `metricCoverage` | string | Rules from SFDC Product Catalog |

**CPSpecificationOption values (Marketplace app metadata)**

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
| `License Term` |
| `License Type` |
| `Liferay Product Capabilities` |
| `Liferay Product Categories` |
| `Liferay Version` |
| `Lifetime License` |
| `Number of CPUs` |
| `Our Selection` |
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
| `Solution Details Blocks` |
| `Solution Header Description` |
| `Solution Header Title` |
| `Solution Header Video Description` |
| `Solution Header Video URL` |
| `Solution Type` |
| `Source Code URL` |
| `Support Email` |
| `Support Email Address` |
| `Support Phone` |
| `Support URL` |
| `Type` |

**Categories**

| Category |
|---|
| Marketplace App Category |
| Marketplace App Tags |
| Marketplace Availability |
| Marketplace Category |
| Liferay Platform Offering |
| Liferay Version |
| Product Type |
| Solution Category |
| Solution Tags |

---

#### CommerceOrder (OOTB + custom fields)

**system:** `true`

| Field | Type | Notes |
|---|---|---|
| PK `commerceOrderId` | long | |
| `externalReferenceCode` | string | Salesforce Opportunity.Id |
| FK `accountEntryId` | long | |
| FK `commerceOrderTypeId` | long | |
| `currencyCode` | string | e.g. USD, EUR |
| `orderStatus` | int | 0=pending · 1=open · 2=in-progress · 5=completed · 6=cancelled |
| `paymentStatus` | int | |
| `paymentMethodKey` | string | |
| `total`, `subtotal`, `shippingAmount`, `taxAmount`, `totalWithTaxAmount` | decimal(30,16) | |
| `billingAddressId`, `shippingAddressId` | long | FK to Address |
| `purchaseOrderNumber` | string | |
| `couponCode` | string | |
| `transactionId` | string | Payment processor transaction ID |
| `printedNote` | longtext | |
| `userId` | long | |
| `createDate`, `modifiedDate` | datetime | |

**Custom fields:** `contractId` (FK to Contract), `marketplaceOrderType`, `projectName`, `cloudProjectName`, `ldpWorkspaceName`

---

#### CommerceOrderItem (OOTB + custom fields)

**system:** `true`

Replaces `SubscriptionItem`. Each row corresponds to a Salesforce OpportunityLineItem.

| Field | Type | Notes |
|---|---|---|
| PK `orderItemId` | long | |
| `externalReferenceCode` | string | Salesforce OpportunityLineItem.Id |
| FK `orderId` | long | Parent CommerceOrder |
| FK `CProductId` | long | |
| FK `CPInstanceId` | long | |
| `quantity` | decimal(30,16) | |
| `unitPrice`, `finalPrice` | decimal(30,16) | |
| `name` | longtext | Product name at time of purchase; denormalized |
| `sku` | string | SKU at time of purchase |
| `subscription` | boolean | |
| `subscriptionInfo` | string | |
| `userId` | long | |
| `createDate`, `modifiedDate` | datetime | |

**Custom fields:** `cloudRegion` (e.g. `us-central1`), `machineType` (`Standard` · `High`), `orderType` (`New Business` · `Renewal`), `sizing` (int), `startDate`, `endDate`, `effectiveEndDate` (endDate + 30-day grace period), `status` (`Approved` · `Canceled` · `On Hold`), `spendLimit` (double; per-product spend cap), `opportunitySoldBy`

---

#### Contract (`C_CONTRACT`)

**system:** `false`

> Temporary custom object until Liferay core provides a system `Contract` object. A migration ticket will be required at that point.

| Field | Type | Notes |
|---|---|---|
| PK `contractId` | long | |
| `externalReferenceCode` | string | Salesforce Contract.Id (18-char) |
| FK `accountEntryId` | long | |
| `startDate` | datetime | |
| `endDate` | datetime | |
| `contractTerm` | int | Term in months, e.g. 12 |
| `contractBillingCadence` | int | e.g. 12 (months) |
| `overageBillingCadence` | int | e.g. 3 (months) |
| `ownerEmailAddress` | string | Resolved from Salesforce Contract owner |
| `status` | string | Computed from CommerceOrderItem statuses |
| `renewalState` | string | |
| `spendLimit` | double | Account-level spend cap from Salesforce |

---

#### Entitlement (`C_ENTITLEMENT`)

**system:** `false`

Materialized grant records derived from CommerceOrderItems via EntitlementDefinitions.

| Field | Type | Notes |
|---|---|---|
| PK `entitlementId` | long | |
| FK `entitlementDefinitionId` | long | |
| FK `orderItemId` | long | Parent CommerceOrderItem that grants this entitlement |
| FK `contractId` | long | Denormalized for fast lookup |
| FK `usageDefinitionId` | long | For metered entitlements |
| `name` | string | e.g. `database-size` · `vcpu` · `maxServers` · `licenseGeneration` |
| `grantType` | string | `fixed` · `rollover` · `prepaid` · `metered` |
| `quantity` | double | Soft cap — alerts at this level; overridden by `sizing` where applicable |
| `maxQuantity` | double | Hard cap — blocks at this level |
| `startDate` | datetime | |
| `endDate` | datetime | |

---

#### EntitlementDefinition (`C_ENTITLEMENT_DEFINITION`)

**system:** `false`

Product-level entitlement template. One CProduct → many EntitlementDefinitions. When a CommerceOrderItem is created for a product, one Entitlement is auto-generated per active EntitlementDefinition for that product.

| Field | Type | Notes |
|---|---|---|
| PK `entitlementDefinitionId` | long | |
| `externalReferenceCode` | string | e.g. `dxp-cloud-standard-database-size` |
| FK `CProductId` | long | 1 product to many EntitlementDefinitions |
| `name` | string | e.g. `database-size`, `vcpu`, `maxServers`, `licenseGeneration` |
| `displayName` | string | Human-readable, e.g. `Database Storage`, `License Generation` |
| `unit` | string | GB · vCPU · count · requests · seats · boolean |
| `defaultQuantity` | double | Default; overridden at order item level via `sizing` |
| `grantType` | string | `fixed` · `rollover` · `metered` · `prepaid` |
| FK `usageDefinitionId` | long | Nullable; only for metered/usage-type entitlements |
| `machineType` | string | `Standard` · `High` · null |
| `active` | boolean | Default `true`; set `false` to deprecate without deleting |

**License generation:** Presence of an EntitlementDefinition with `name = 'licenseGeneration'` (`grantType = fixed`, `unit = boolean`) indicates the product can generate license keys. This replaces the old boolean `licenses` flag on products.

---

#### InvoiceRequest (`C_INVOICE_REQUEST`)

**system:** `false`

| Field | Type | Notes |
|---|---|---|
| PK `invoiceRequestId` | long | |
| FK `subscriptionId` | long | |
| `type` | string | |
| `status` | string | |
| `requestedAt` | datetime | |

---

#### UsageDefinition (`C_USAGE_DEFINITION`)

**system:** `false`

> Also referred to as `ConsumptionMetric` in some arch docs.

| Field | Type | Notes |
|---|---|---|
| PK `usageDefinitionId` | long | |
| `externalReferenceCode` | string | e.g. `storage-gb`, `page-views-monthly` |
| `unit` | string | e.g. GB, page views, vcpu, AI tokens |
| `aggregation` | string | count / sum |
| `period` | string | Per month, day, hour |
| `quantity` | double | Base unit quantity |
| `overageRate` | double | |
| `overageCurrency` | string | USD / EUR / JPY |

---

#### UsageEvent (`C_USAGE_EVENT`)

**system:** `false`

| Field | Type | Notes |
|---|---|---|
| PK `usageEventId` | long | |
| FK `environmentId` | long | |
| FK `subscriptionId` | long | |
| FK `usageDefinitionId` | long | |
| `eventTimestamp` | datetime | |
| `quantity` | double | |
| `dedupeKey` | string | Idempotent; client-assigned |

---

#### UsageReport (`C_USAGE_REPORT`)

**system:** `false`

Aggregated periodic report over UsageEvents.

| Field | Type | Notes |
|---|---|---|
| PK `usageReportId` | long | |
| FK `subscriptionId` | long | |
| FK `usageDefinitionId` | long | |
| `generatorClassName` | string | |
| `aggregateQuantity` | double | |
| `generatedAt` | datetime | |
| `dateFrom` | datetime | |
| `dateTo` | datetime | |

---

### Environment Management

#### Environment (`C_ENVIRONMENT`)

**system:** `false`

| Field | Type | Notes |
|---|---|---|
| PK `environmentId` | long | |
| FK `subscriptionId` | long | FK to Contract |
| `type` | string | `SaaS` · `PaaS` · `CNE` · `On-Prem` |
| `region` | string | Cloud only; blank for on-prem |
| `activationMode` | string | `license-key` · `heartbeat` |
| `status` | string | `active` · `deactivated` · `expired` |
| `lastHeartbeatAt` | datetime | Cloud only |
| `currentEntitlementHash` | string | Identity hash; enables change detection on heartbeat |
| `hostName` | string | On-prem only |
| `domains` | string | On-prem only; allowed domain list |
| `ipAddresses` | longtext | On-prem only |
| `macAddresses` | longtext | On-prem only |
| `serverId` | longtext | On-prem only |

---

#### LicenseKey (`C_LICENSE_KEY`)

**system:** `false`

| Field | Type | Notes |
|---|---|---|
| PK `licenseKeyId` | long | |
| `uuid` | string | Preserved from Provisioning for cross-system traceability |
| `createdByUserId` | long | FK to User |
| `createdByUserName` | string | Denormalized |
| `createDate` | datetime | |
| `modifiedByUserId` | long | FK to User |
| `modifiedByUserName` | string | Denormalized |
| `modifiedDate` | datetime | |
| FK `accountEntryId` | long | |
| FK `entitlementId` | long | |
| `orderId` | long | FK to CommerceOrderItem via `assetReceiptLicenseUuid` |
| `entitlementName` | string | Name of the attached entitlement |
| FK `CProductId` | long | |
| `accountEntryCode` | string | Denormalized |
| `accountEntryName` | string | Denormalized |
| `licenseName` | string | Hard-coded from LicenseEntry on migration |
| `licenseType` | string | `production` · `cluster` · `developer` · `enterprise` · `oem` · `per-user` · `limited` · `virtual-cluster` · `free` · `developer-cluster` |
| `licenseVersion` | int | |
| `productName` | string | Denormalized from CProduct |
| `productExternalId` | string | UUID-format product ID |
| `productVersion` | string | e.g. `7.4`, `2026.Q1` |
| `productVersionLabel` | string | Human-readable version label |
| `clusterId` | long | |
| `owner` | string | |
| `maxServers` | int | |
| `maxConcurrentUsers` | long | |
| `maxUsers` | long | |
| `maxHttpSessions` | int | |
| `maxClusterNodes` | int | |
| `sizing` | string | Copied from CommerceOrderItem for license generation convenience |
| `name` | string | |
| `description` | string | |
| `licenseKey` | longtext | The actual license XML/key payload |
| `startDate` | datetime | |
| `customExpirationDate` | datetime | |
| `additionalInfo` | longtext | |
| `complimentary` | boolean | |
| `active` | boolean | |

---

### Other

#### Property (`C_PROPERTY`)

**system:** `false`

| Field | Type | Notes |
|---|---|---|
| `accountEntryId` | long | |
| `classNameId` | long | e.g. classNameId of CommerceOrderItem, AccountEntry |
| `className` | string | Denormalized, e.g. `CommerceOrderItem` |
| `classPK` | long | PK of the target entity instance |
| `name` | string | e.g. `koroneikiAccountKey`, `nonProductionSubscriptionUuid` |
| `value` | string | e.g. `12345-abcde` |
| `metadataJson` | text | JSON metadata blob |

> External system references (formerly `ExternalLink`) are stored as Property rows: `name = '{domain}:{entityName}'`, `value = entityId`.

---

## ERC and FriendlyURL Registry

| Object | ERC | Separator |
|---|---|---|
| `AccountFlag` | `C_ACCNT_FLAG` | `cpaf` |
| `AccountNote` | `C_ACCNT_NOTE` | `cpan` |
| `BannedEmailDomain` | `C_BANNED_EMAIL` | `cpbd` |
| `Contract` | `C_CONTRACT` | `cpct` |
| `CreditHold` | `C_CREDIT_HOLD` | `cpch` |
| `Entitlement` | `C_ENTITLEMENT` | `cpen` |
| `EntitlementDefinition` | `C_ENTITLEMENT_DEFINITION` | `cped` |
| `Environment` | `C_ENVIRONMENT` | `cpdp` |
| `InvoiceRequest` | `C_INVOICE_REQUEST` | `cpir` |
| `LicenseKey` | `C_LICENSE_KEY` | `cplk` |
| `Property` | `C_PROPERTY` | `cppr` |
| `UsageDefinition` | `C_USAGE_DEFINITION` | `cpud` |
| `UsageEvent` | `C_USAGE_EVENT` | `cpue` |
| `UsageReport` | `C_USAGE_REPORT` | `cpur` |

---

## See Also

- [`api.md`](./api.md) — headless conventions and custom REST contracts
- [`integrations/salesforce.md`](./integrations/salesforce.md) — Salesforce-owned objects and inbound Pub/Sub