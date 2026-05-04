# Google Cloud Storage

Large-file attachment backend for `TicketAttachment` and `PublisherAssetAttachment`.

## Overview

**Direction:** bidirectional.
- Outbound: create signed upload URLs, signed download URLs, object deletes.
- Inbound: none (no GCS webhooks consumed).

**Buckets:**
- `one-ticket-attachments-{env}` — support ticket attachments. Lifecycle: auto-delete 30 days after upload (defensive; explicit deletes from workspace usually clean first).
- `one-publisher-assets-{env}` — publisher asset attachments. Lifecycle: retain until object deletion requested.

Both buckets use `{env}` suffix in (`dev`, `staging`, `prod`).

---

## Auth

GCP service-account JSON stored as `liferay-one-instance-settings` secret `gcp-service-account.json`. Same service account as the Pub/Sub subscriber (D12). Role bindings on the GCS buckets: `roles/storage.objectAdmin` scoped to the two buckets only.

---

## Key Flows

### Upload Flow (Resumable)

Same pattern for `TicketAttachment` and `PublisherAssetAttachment`. Caller is a browser (custom element) or a direct API client.

**Step 1 — Initiate upload**

Client calls `POST /o/one/v1/ticket-attachments/initiate-upload` (or `/publisher-assets/{id}/attachments/initiate-upload`).

Workspace:

1. Validates `fileName`, `fileSize` (≤ 200 MB for publisher assets, ≤ 50 MB for ticket attachments), MIME type allow-list.

1. Creates `TicketAttachment` (or `PublisherAssetAttachment`) row with `uploadStatus=Uploading` / `state=Draft`.

1. Generates a GCS resumable-upload session URL:

   ```
   POST https://storage.googleapis.com/upload/storage/v1/b/{bucket}/o?uploadType=resumable&name={gcsObject}
   ```
4. Returns `{ attachmentId, resumableUploadUrl, gcsObject }` to the client.

GCS object key patterns:
- Ticket: `tickets/{accountEntryId}/{yyyymmdd}/{uuid}-{sanitizedFileName}`
- Publisher: `publishers/{publisherId}/assets/{assetId}/{uuid}-{sanitizedFileName}`

**Step 2 — Client uploads directly to GCS**

Client PUTs the file bytes to the resumable URL. Workspace is not in the data path — file bytes flow browser → GCS.

**Step 3 — Complete upload**

Client calls `POST /o/one/v1/ticket-attachments/{id}/complete-upload` with the MD5 of the uploaded bytes.

Workspace:

1. Checks GCS object exists (`HEAD gs://{bucket}/{gcsObject}`).

1. Compares reported MD5 against GCS object metadata. Mismatch → delete object, reject.

1. Flips `uploadStatus=Complete`; records `fileSize`, `md5Checksum`.

1. For `TicketAttachment`: leaves `state=Draft` until the user approves. Approval triggers Jira comment post (see `jira.md §2`).

1. For `PublisherAssetAttachment`: kicks off async processing (virus scan, Commerce catalog registration); flips `processed=true` on success.

**Step 4 — Failed / abandoned uploads**

Client may abandon. Workspace runs `AttachmentCleanup` nightly — deletes GCS objects whose matching Liferay row is `uploadStatus=Uploading` and older than 24h.

### Download Flow

Workspace generates a signed GET URL, returns it to the client, client downloads directly from GCS.

```
GET /o/one/v1/ticket-attachments/by-id/{id}/download
```

Workspace:

1. Authorizes caller — must have `ticket.read` scope + account membership.

1. Generates signed URL valid for 15 min (ticket attachments) or 60 min (publisher assets).

1. Returns `302 Location: {signedUrl}` (or JSON body `{ url, expiresAt }`, caller-preference).

For Jira-comment-embedded links, the signed URL expiry is 7 days. Jira users need long-lived access; the tradeoff is documented — signed URLs in Jira are effectively short-term bearer credentials.

### Delete Flow

`DELETE /o/one/v1/ticket-attachments/{id}` flips `state=Trashed`. The actual GCS object deletion happens asynchronously via the `TicketAttachmentTrashDrain` scheduled task.

```
DELETE gs://{bucket}/{gcsObject}
```

The two-phase delete (soft-trash → drain) protects against accidental deletes and lets admins recover.

---

## Data Mapping

### Size and MIME Constraints

| Attachment type | Max size | Allowed MIME |
|---|---|---|
| `TicketAttachment` | 50 MB | `text/*`, `application/pdf`, `image/png`, `image/jpeg`, `application/zip`, `application/octet-stream` |
| `PublisherAssetAttachment` | 200 MB | `application/zip`, `application/java-archive`, `application/x-tar`, `application/gzip` |

Per-type caps live in `liferay-one-instance-settings` config so they are tunable without a release.

---

## Error Handling

| Failure | Behavior |
|---|---|
| GCS 5xx on signed-URL generation | Retry × 3 exponential; circuit-break after 5 failures/60s |
| GCS 4xx on signed-URL generation | Fail fast; log |
| MD5 mismatch on complete-upload | Delete object; reject upload; client may re-initiate |
| Missing GCS object on complete-upload | Reject; mark row `uploadStatus=Failed` |
| GCS quota exceeded | Slack alert; clients get 429 |

---

## Security

- **No public ACLs.** Buckets are private; all access via signed URLs.
- **Signed URLs scoped to single object** (no prefix or wildcard grants).
- **Virus scan** — publisher asset uploads flow through ClamAV (via Cloud Run service) before `processed=true`. Ticket attachments are not scanned (reviewed by support agents).
- **MIME-type allow-list** enforced by workspace, not GCS.
- **Audit trail** — every upload/download logs `{userId, accountEntryId, attachmentId, gcsObject, action, timestamp}` to the workspace audit log.

---

## Observability

- Metric `one.gcs.uploads.initiated` — counter
- Metric `one.gcs.uploads.completed` — counter, labels `{status, type}`
- Metric `one.gcs.downloads.served` — counter
- Metric `one.gcs.signed-url.latency` — histogram
- Metric `one.gcs.storage.bytes` — gauge (from GCS monitoring export)

---

## Open Questions

1. **Bucket region** — today's customer-workspace uses multi-region US. New bucket strategy should match customer-data residency commitments — confirm with legal before phase 5.

1. **7-day Jira ADF link** — long expiry is a pragmatic compromise. Alternative is a workspace-side link redirector (`/t/<token>` → 302 to signed URL regenerated on demand). Adds ops burden; defer unless security review pushes back.

1. **ClamAV on ticket attachments** — currently out of scope. Add if security team requests.

1. **Lifecycle policy on publisher-assets bucket** — no auto-delete means storage grows. Consider 5-year retention after `PublisherAsset` withdrawal.