# Liferay Learn Quickstart

A local version of [learn.liferay.com](https://learn.liferay.com) can be completely deployed using Docker. Once the
environment has been deployed, it is possible to test incremental changes as needed.

## Step 1: Set Up Liferay Learn Environment

1. Ensure you have Docker installed and running (check by running `docker --version`).
2. Ensure you have the [Liferay Learn Repo](https://github.com/liferay/liferay-learn) cloned on your machine.
3. From within the quickstart directory of the Liferay Learn workspace, run `./init.sh`
4. When prompted, specify the directory of the Liferay Learn Repo.

---
**NOTE:**
You can regenerate the learn documentation and redeploy all client extensions by automatically
running `docker start deploy` from the command line.
---

## Optional Step 2: Manually Deploy Client Extensions

1. Ensure you have Java 11 installed and ready to use (check by running `java --version`).
2. In your Liferay Portal Repo, navigate
   to `workspaces/liferay-learn-workspace/client-extensions`.
2. Deploy every extension by running: `../gradlew clean deploy "-Ddeploy.docker.container.id=liferay"`.
3. Extensions can also be deployed one by one from each of their respective directories by running:
   `../../gradlew clean deploy "-Ddeploy.docker.container.id=liferay"`

## Optional Step 3: Manually Deploy Learn Documentation

1. Ensure you have Java 11 installed and ready to use (check by running `java --version`).
2. Log into your [Liferay instance](http://localhost:8081) and navigate to `Control Panel`
   &rarr; `OAuth 2 Administration`.
2. Click on `Liferay Learn Etc Cron Oauth Application Headless Server` and copy both `Client ID` and `Client Secret`.
3. Export the following environment variables in your terminal (replacing the values with appropriate content from prior
   steps).

   ```bash
   export LIFERAY_LEARN_ETC_CRON_GIT_REPOSITORY_DIR=[Your Path to Liferay Learn Repo]
   export LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_ID=[Your Client ID]
   export LIFERAY_LEARN_ETC_CRON_LIFERAY_OAUTH_CLIENT_SECRET=[Your Client Secret]
   export LIFERAY_LEARN_ETC_CRON_LIFERAY_URL=http://localhost:8081
   export LIFERAY_LEARN_ETC_CRON_LIFERAY_SITE_FRIENDLY_URL_PATH=guest
   ``` 

4. In your Liferay Portal Repo, navigate
   to `workspaces/liferay-learn-workspace/client-extensions/liferay-learn-etc-cron`.
5. From the Liferay Learn ETC Cron directory run `../../gradlew run`.