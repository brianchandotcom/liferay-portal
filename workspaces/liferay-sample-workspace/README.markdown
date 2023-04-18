# Liferay Sample Workspace

The Liferay Sample workspace contains an example of every client extension and is meant to be the ***primary*** source of truth of how client extensions work by documenting via code.

Feel free to initiate your project by copying the samples found here. But make sure to rename your client extensions according to our established [naming conventions](#naming-conventions).

## Client Extensions

Client extensions are the recommended way of customizing Liferay. Modules and themes are supported for backwards compatibility.

To deploy all client extensions, go to `liferay-sample-workspace` and type `./gradlew deploy`.

To deploy a specific client extension (e.g. liferay-sample-custom-element-1), go to `liferay-sample-workspace` and type `./gradlew :client-extensions:liferay-sample-custom-element-1:deploy`.

### Naming Conventions

The standard directory name of a client extension is broken up into several parts. The first two parts, the owner and project, are separated by `-`.

For `liferay-sample-batch`, the owner is `liferay` and the project is `sample`. The owner and the project must not contain `-` since we use `-` to differentiate the owner from the project.

The third part is usually one of the available client extension types: batch, custom-element, fds-cell-renderer, global-css, global-js, iframe, notification-type, oauth-application-headless-server, oauth-application-user-agent, object-action, site-initializer, static-content, theme-css, theme-favicon, theme-js, theme-spritemap, or workflow-action.

For `liferay-sample-batch`, the third part is the client extension type `batch`.

For `liferay-sample-custom-element-1` and `liferay-sample-custom-element-2`, the third part, `custom-element`, means this is a custom element. The fourth parts, `1` and `2`, are a general description that can be anything.

If the third part it is not a client extension type, then the third part must be the special keyword `etc`.

For `liferay-sample-etc-cron` and `liferay-sample-etc-spring-boot` the third type is `etc`. The fourth parts, `cron` and `spring-boot` are general descriptions that can be anything.

### List of Client Extensions

- liferay-sample-batch

- liferay-sample-custom-element-1

	A sample custom element that is self contained (doesn't depend on any external dependency).

- liferay-sample-custom-element-2

	A sample custom element that uses `react-scripts` to build.

- liferay-sample-custom-element-3

	A sample custom element that uses `@angular/cli` to build.

- liferay-sample-custom-element-4

	A sample custom element that uses `react`, and `react-dom` packages that Liferay makes publicly available through import maps.

- liferay-sample-custom-element-5

	The sample custom element uses `@clayui/badge`, `react`, and `react-dom` packages that Liferay makes publicly available through import maps.

- liferay-sample-etc-cron

	Use Spring Boot and OAuth (server to server) to read from and write to Liferay in timed intervals.

	To see this in action on your local machine:

	1. Go to `liferay-sample-workspace` and type `./gradlew startDockerContainer logsDockerContainer` to start Liferay.

	1. Go to `liferay-sample-workspace` and type `./gradlew :client-extensions:liferay-sample-etc-cron:deploy`.

	1. Login to Liferay and go to Control Panel > Configuration > OAuth 2 Administration. Select `Liferay Sample Etc Cron`.

	1. Copy the client secret. Open `liferay-sample-workspace/client-extensions/liferay-sample-etc-cron/src/main/resources/application-default.properties` and replace `liferay-sample-etc-cron.oauth2.headless.server.client.secret=myfancypassword` with the real client secret.

	1. Go to `liferay-sample-workspace/client-extensions/liferay-sample-etc-cron` and type `./gradlew bootRun` to start Spring Boot.

- liferay-sample-etc-frontend-1

	The `client-extension.yaml` contains multiple frontend client extensions.

- liferay-sample-etc-frontend-2

	The `client-extension.yaml` contains multiple frontend client extensions that use code from `shared-utils.js` without duplicating or rerequesting it.

- liferay-sample-etc-spring-boot

	Use Spring Boot and OAuth (human to server) to interact with Liferay.

- liferay-sample-fds-cell-renderer

	A sample Frontend Data Set Cell Renderer frontend client extension that uses `@liferay/js-api` and `tsc` to make sure, when building, it conforms to Liferay's API.

- liferay-sample-global-css

	A simple Global CSS frontend client extension example.

- liferay-sample-global-js

	A simple Global JS frontend client extension example.

- liferay-sample-iframe-1

	A sample Iframe frontend client extension (with no code) that renders [https://arnab-datta.github.io/counter-app](https://arnab-datta.github.io/counter-app) inside.

- liferay-sample-iframe-2

	A sample project providing 3 Iframe frontend client extension that render some Wikipedia pages.

- liferay-sample-instance-settings

- liferay-sample-js-importmaps-entry-1

	A sample project providing a JS Importmaps Entry and Custom Element frontend client extensions where the custom element uses some functions from the JS Importmaps Entry code through a JavaScript browser module which is made available through a bare identifier declared in the import maps.

- liferay-sample-static-content

	A sample Static Content frontend client extension.

- liferay-sample-theme-css

	A sample Theme CSS frontend client extension that is built by Liferay's Workspace and uses `styled` as its base theme.

- liferay-sample-theme-favicon

	A sample Theme Favicon frontend client extension.

- liferay-sample-theme-spritemap-1

	A sample Theme Spritemap frontend client extension.

- liferay-sample-theme-spritemap-2

	A sample Theme Spritemap frontend client extension where the SVG file containing the spritemap is composed from several smaller SVG files.
