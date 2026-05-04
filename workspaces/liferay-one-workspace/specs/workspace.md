# Workspace Shell

## Location

`workspaces/liferay-one-workspace/`

## Client Extensions

| Extension | Description |
|---|---|
| `liferay-one-custom-element` | React + TypeScript — all dynamic UI for Marketplace, Support, Admin |
| `liferay-one-etc-spring-boot` | Spring Boot REST service for provisioning, GCS, Jira, license gen, Salesforce Pub/Sub subscriber |
| `liferay-one-etc-cron` | Spring Boot + Quartz — all scheduled tasks (EntitlementSync, TrialLifecycleTick, etc.) |
| `liferay-one-global-css` | Shared color tokens + global styles |
| `liferay-one-instance-settings` | Secrets and external credentials (not checked into repo) |
| `liferay-one-site-initializer` | Single site initializer serving Marketplace, Support, Admin page groups |

### Object Names

PascalCase, no prefix: `AccountFlag`, `SupportTicket`, `LicenseKey`.

### Field Names

camelCase. Booleans phrased as questions: `internal`, `clustered`, `hasDisasterDataCenterRegion`.

### Friendly URL Separators

4 lowercase letters matching the ERC suffix. Must be unique across all Objects in the workspace.