# Liferay Partner Quickstart

A local version of [partner.liferay.com](https://partner.liferay.com) can be completely deployed using Docker. Once the
environment has been deployed, it is possible to test incremental changes as needed.

## Step 1: Set Up Environment

1. Ensure you have Docker installed and running (check by running `docker --version`).

1. From within the liferay-partner-workspace directory run `./gradlew quickstart`

---

**NOTE:**
You can redeploy client extensions manually from each of their respective directories by running:
`../../gradlew clean deploy "-Ddeploy.docker.container.id=liferay"`