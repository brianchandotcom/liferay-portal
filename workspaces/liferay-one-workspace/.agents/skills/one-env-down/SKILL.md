---

allowed-tools: [Bash]
description: Stop the local Liferay Docker containers while keeping volumes intact.
name: one-env-down

---

# Stop Liferay One Environment

Run from `workspaces/liferay-one-workspace/`.

Stops all containers but preserves volumes so the next `/one-env-up` resumes where it left off.

```bash
docker compose stop
```