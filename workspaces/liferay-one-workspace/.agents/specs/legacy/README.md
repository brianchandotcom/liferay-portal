# System Consolidation Audit

This directory contains a higher-level audit of four legacy Liferay business systems being consolidated into a new unified workspace built on Liferay Objects:

- **[Koroneiki](./koroneiki.md)** — customer/product/entitlement ERP (the system of record)
- **[Provisioning](./provisioning.md)** — Salesforce → Koroneiki orchestrator
- **[Marketplace](./marketplace.md)** — publisher/commerce storefront
- **[Support](./support.md)** — customer support portal (Jira-backed)
- **[Integrations](./integrations.md)** — how the four systems (and their external dependencies) talk to each other

The goal of the audit is to capture enough information about each system's **data model, business logic, and integration surface** to rebuild it as Liferay Objects in the consolidated workspace. It is intentionally high-level — exhaustive table-by-table data dictionaries are out of scope.

## Source locations

| System | Codebase | Database (MySQL on 127.0.0.1:3307) |
|---|---|---|
| Koroneiki | `<liferay-portal-ee>/modules/dxp/apps/osb/osb-koroneiki/` | `kor` |
| Provisioning | `<liferay-portal-ee>/modules/dxp/apps/osb/osb-provisioning/` | `prov` |
| Marketplace | `<liferay-portal>/workspaces/liferay-marketplace-workspace/` | `e5a2_lpartition_11706165` |
| Support | `<liferay-portal>/workspaces/liferay-customer-workspace/` | `e5a2_lpartition_1860468` |

## Scale at a glance

| Metric | Koroneiki | Provisioning | Marketplace | Support |
|---|---:|---:|---:|---:|
| Core entities / Objects | 19 | 2 local (+ proxies Koroneiki) | 12 live (4 defined-but-not-deployed) | 26 live |
| Largest table | AuditEntry (17.4M) | LicenseKey (230K) | GetAppInformation (496) | BannedEmailDomain (4.8K) |
| Accounts / customers | 18,390 | (via Koroneiki) | — | 2,313 (KoroneikiAccount mirror) |
| Total "business" rows | ~18.5M | ~512K | ~950 | ~16K |
| Scheduled tasks | 1 (entitlement sync, 15min) | 0 (event-driven only) | 7 (cron, every 6h) | 5 (cleanup, heat-tags, overdue events) |
| External integrations | osb-entity-web, RabbitMQ fan-out | Dossiera/SF, Koroneiki, ~~Zendesk~~*, ~~LCS~~* | Koroneiki, Salesforce, Stripe-absent, Console, Cloud, Analytics, Marketo, PayPal | Jira (3 projects), GCS, Koroneiki, Slack, email |

_\* Zendesk and LCS are already retired at the business layer; the code paths in `osb-provisioning` that target them are vestigial (decisions D6 and D8 in the system spec)._

## How to read this audit

1. **Start with [integrations.md](./integrations.md)** for the big picture — who calls whom and which concepts overlap.

1. **Then per-system docs** for the data model, business logic, API surface, and migration notes.

1. Every per-system doc ends with **"Open Questions / Gotchas"** and **"Migration Notes"** — those are the pointed inputs for the planning phase.

## Known gaps in this audit

- **osb-provisioning has license-key tables in its DB that the audited source does not declare** (`Provisioning_LicenseKey`, `Provisioning_SubscriptionEntry`, `Provisioning_LicenseEntry`, `Provisioning_ProductVersion`, `Provisioning_CommonLicenseKey` — together ≈233K rows). These are likely owned by a sibling `osb-provisioning-license*` module not covered here, or by LCS. See [provisioning.md §6](./provisioning.md#6-row-counts).
- **The RabbitMQ → Google Pub/Sub bridge** that relays `koroneiki.*` topics from Koroneiki to Marketplace is not in any of the audited codebases. Confirm with infrastructure team.
- **Support's `KoroneikiAccount` sync mechanism** is external to the Support workspace. Unknown what runs it.
- **`EntitlementDefinition.definition`** — 62 raw-SQL rules are live in Koroneiki. None of them have been reviewed individually; this is a planning-phase task.
- **Consolidation team should re-verify** that `ContactSales`, `ProductFeedback`, `DXPFreeActivationKeyRequest`, `AIHubBetaPrivateAccessRequest` are still intended Marketplace Objects — they're in the site-initializer source but not deployed to the live DB.

## Next steps

1. **Still open** — resolve the three phase-blockers in [integrations.md §9](./integrations.md#9-open-integration-questions-for-the-planning-phase): Provisioning license-key module ownership (Q1), RabbitMQ↔Pub/Sub bridge (Q2), Support KoroneikiAccount sync mechanism (Q3).

1. ~~Extract and review all 62 entitlement rules~~ — **done.**

1. **Still open** — confirm the `Provisioning_*` license tables' ownership (overlaps with #1 above).

1. ~~Decide the unified Account/Subscription/Contact model~~ — **done.** Locked in as D1, D2, D3, D4 in the system spec.

1. ~~Plan data migration for Koroneiki's 17M-row `AuditEntry`~~ — **done.** Archive to flat file (JSON lines on GCS), don't migrate.