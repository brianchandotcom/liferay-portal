# Liferay One — Unified Portal Specs

Source-of-truth for the `liferay-one-workspace` implementation. Each document narrows a decision to the level an engineer can build from without follow-up.

Target workspace: `workspaces/liferay-one-workspace/`

## Reading order

1. [`workspace.md`](./workspace.md) — shell layout, client extensions, naming conventions

1. [`data-model.md`](./data-model.md) — full entity index, ERC + FriendlyURL registry, field mappings

1. [`api.md`](./api.md) — headless conventions, custom REST contracts, OAuth2 scopes

1. [`ui.md`](./ui.md) — site-initializer page groups, custom elements, navigation, full site map

1. [`integrations/`](./integrations/) — external-system contracts (Salesforce, Jira, GCS, Liferay Cloud, BigQuery, GCF)

## Conventions

- Objects are referenced by `externalReferenceCode` (ERC), not display name — ERCs are the stable identifiers.
- ERC format: `ONE_{domainPrefix}_{objectSlug}` (max 40 chars). Domain prefixes: `CUS`, `COM`, `ENT`, `MKT`, `SUP`, `REF`.
- Object names: PascalCase (`AccountFlag`, `SupportTicket`, `LicenseKey`).
- Field names: camelCase; boolean questions phrased as questions (`internal`, `clustered`, `hasDisasterDataCenterRegion`).
- Friendly URL separators: 4 lowercase letters matching the ERC suffix.