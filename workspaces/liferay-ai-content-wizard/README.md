## Getting Started with Liferay AI Content Wizard

Complete documentation for Liferay Workspace can be found
[here](https://learn.liferay.com/dxp/7.x/en/developing-applications/tooling/liferay-workspace.html).

## Running Liferay DXP locally

```
blade server init
blade server run
```

## Gradle Properties

Set the following in `gradle.properties` to change the default settings.

We recommend you to update `liferay.workspace.product` to use as most as possible the latest quarterly release.

## Client Extensions

To deploy the Client Extensions you must open each individual folder inside /client-extensions and run `blade gw deploy`

Is important to deploy the Custom Element CX before deploying the Site Initializer, because of an existing reference.

# Running

Backend: go to `liferay-content-wizard-etc-spring-boot` and run `gw bootRun`
Frontend: go to `liferay-content-custom-element` and run `yarn|npm run dev`
