---

allowed-tools: [Bash, Glob, Grep, Read]
description: Bootstrap or rebuild the local Liferay Docker environment for the liferay-one-workspace. Use when the user asks to bootstrap, start, rebuild, or reset the local Liferay container.
name: one-bootstrap

---

# Bootstrap Liferay One Workspace

Manage the local Liferay Docker environment via `scripts/bootstrap.sh`. Run from `workspaces/liferay-one-workspace/`.

## Commands

| Command | What it does |
|---------|-------------|
| `up` (default) | Extract hotfix/license → build image → tag → `docker compose up` → wait for health → deploy client extensions |
| `start` | `docker compose start` |
| `stop` | `docker compose stop` |
| `down` | `docker compose down` |
| `clean` | `docker compose down --volumes` |

## Usage

```bash
# Full bootstrap (first run or after image/config changes)
scripts/bootstrap.sh up

# Day-to-day start/stop
scripts/bootstrap.sh start
scripts/bootstrap.sh stop

# Tear down (keep volumes)
scripts/bootstrap.sh down

# Full reset — wipes all volumes
scripts/bootstrap.sh clean
```

Liferay is ready when `up` prints `Done. Liferay is running at http://localhost:8080.`
