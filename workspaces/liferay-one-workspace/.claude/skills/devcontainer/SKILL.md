---

allowed-tools: [Bash, Glob, Grep, Read]
argument-hint: "[up|down|status|logs|reset] [options]"
description: Manage the liferay-one-workspace devcontainer stack. Use when the user wants to start, stop, check, or inspect the local dev environment.
name: devcontainer

---

# Manage the Devcontainer Stack

All commands run from `workspaces/liferay-one-workspace/`. Scripts live under `.devcontainer/`.

## Resolve the subcommand

Use `${ARGUMENTS}` first. When missing, run `status` by default.

| Subcommand | Script | Notes |
|---|---|---|
| `up` | `.devcontainer/up.sh` | Start the stack. Accepts `--core` (skip extensions) and `--foreground`. |
| `down` | `.devcontainer/down.sh` | Stop the stack; volumes preserved. Accepts `--core` (mirror of up). |
| `status` | `.devcontainer/status.sh` | Print service names, health, and ports. |
| `logs [service]` | `.devcontainer/logs.sh` | Tail logs. Default service: `liferay`. Accepts `--follow` / `--tail N`. |
| `reset` | `.devcontainer/reset.sh --yes` | **Destructive.** Wipe volumes (database + document library). Confirm with the user before running. |

## Run the command

```bash
# Examples
.devcontainer/up.sh
.devcontainer/up.sh --core
.devcontainer/status.sh
.devcontainer/logs.sh liferay --follow
.devcontainer/reset.sh --yes   # only after explicit user confirmation
```

## Report

- **up**: confirm the services that came up and flag any that are unhealthy.
- **down**: confirm the stack stopped.
- **status**: print the table as-is.
- **logs**: stream or print the output; stop when the user interrupts.
- **reset**: confirm volumes were wiped and remind the user to re-run `up`.
If `up.sh` exits non-zero due to a port conflict, show which ports are occupied and suggest running `status` to check what's running.