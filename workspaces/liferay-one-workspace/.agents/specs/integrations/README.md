# Integrations

External-system contracts for `liferay-one-workspace`.

## External Systems

| System | Direction | Purpose | Doc |
|---|---|---|---|
| **Salesforce** | Inbound (Pub/Sub) | Opportunity → Subscription/Account sync | [`salesforce.md`](./salesforce.md) |
| **Jira (JSM)** | Outbound + Inbound webhook | Support ticket creation/sync, BusinessEvent labels, security vulnerabilities | [`jira.md`](./jira.md) |
| **GCS (Google Cloud Storage)** | Outbound | Large-file attachments for tickets and publisher assets | [`google-cloud-storage.md`](./google-cloud-storage.md) |
| **Liferay Cloud / Console** | Outbound | Trial and paid cloud instance provisioning and decommission | [`liferay-cloud.md`](./liferay-cloud.md) |
| **Data Warehouse (BigQuery)** | Outbound | Environment usage metrics export | [`data-warehouse.md`](./data-warehouse.md) |
| **GCF (Google Cloud Functions)** | Inbound | Usage metric data for SaaS environments | [`google-cloud-functions.md`](./google-cloud-functions.md) |