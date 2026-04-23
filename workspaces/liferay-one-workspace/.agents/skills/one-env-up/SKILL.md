---

allowed-tools: [Bash, Glob, Grep, Read]
description: Start the local Liferay Docker environment. On first run, bootstraps from scratch (builds image, starts containers, deploys client extensions). On subsequent runs, starts existing containers.
name: one-env-up

---

# Start Liferay One Environment

Run from `workspaces/liferay-one-workspace/`.

## 1. Detect First Run

Check whether the `liferay:local` Docker image exists:

```bash
docker image inspect liferay:local &>/dev/null
```

- **Not found** → first run: proceed to step 2.
- **Found** → day-to-day: proceed to step 3.

## 2. First Run (Bootstrap)

Run `scripts/bootstrap.sh`. It builds the Docker image, tags it, starts the containers, waits for Liferay to be healthy, and deploys the client extensions.

```bash
scripts/bootstrap.sh
```

Liferay is ready when it prints `Done. Liferay is running at http://localhost:8080.`

## 3. Day-to-Day Start

Start the existing containers in the background:

```bash
docker compose up --detach
```

Wait until `http://localhost:8080/c/portal/status` returns HTTP 200, then report success.