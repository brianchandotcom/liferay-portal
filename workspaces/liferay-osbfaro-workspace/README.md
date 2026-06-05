# OSB Faro Workspace

## Prerequisites

Download Docker for [Mac](https://www.docker.com/docker-mac) or [Windows](https://www.docker.com/docker-windows).

## Folder Structure

```
osb-faro-workspace
├── configs
│   ├── common              # Applied to all environments
│   ├── local               # Local development overrides
│   └── cloud               # Staging/integration/production overrides
├── docker
│   ├── 100_liferay_image_setup.sh  # Startup script (selects config by FARO_ENVIRONMENT_NAME)
│   ├── context.xml
│   ├── healthcheck.sh
│   ├── log4j               # DEBUG-level log4j configs (local)
│   ├── rewrite.config
│   ├── system-ext.properties
│   └── cloud
│       └── log4j           # INFO-level log4j configs (cloud builds)
├── modules
│   └── osb/osb-faro        # OSGi modules
├── themes
├── Dockerfile.ext           # Docker image extensions (ENV, EXPOSE, COPY, RUN)
├── build.gradle
├── gradle.properties
└── Jenkinsfile
```

## Environment Variable

All Docker build and runtime behaviour is controlled by a single variable:

| Value | Description |
|---|---|
| `local` | Default. Uses `configs/local/`. Debug log level. |
| `stg` | Cloud build. Uses `configs/cloud/`. Info log level. |
| `int` | Cloud build. Uses `configs/cloud/`. Info log level. |
| `prd` | Cloud build. Uses `configs/cloud/`. Info log level. Image tagged `prd-YYYYMMDD`. |

## Development

### Redeploying Faro Apps

```bash
./gradlew deploy
```

### Running Frontend Development Server

1. Run `./gradlew packageRunStart`.

1. Then open [http://localhost:3000](http://localhost:3000).

### Frontend Architecture

If you are new to the project, here is a nonexhaustive list of the technologies we are using. It is important to be familiar with them if you are going to contribute:

- [Apollo](https://www.apollographql.com/) for our GraphQL implementation.
- [Babel](https://babeljs.io/) so we can write modern JavaScript.
- [Clay](https://clayui.com/) as our CSS and markup foundation.
- [ESLint](https://eslint.org/) as our linter.
- [Jest](https://facebook.github.io/jest/) as our unit-testing framework.
- [Lodash](https://lodash.com/) as our JS utility library.
- [MJML](https://mjml.io/) as our email framework.
- [Prettier](https://prettier.io/) to format our code.
- [React](https://reactjs.org/) for our view layer.
- [Redux](https://redux.js.org/) for state management.
- [Sass](https://sass-lang.com/) as our CSS pre-processor.
- [Testing Library/React](https://testing-library.com/docs/react-testing-library/intro/) as our testing utilities.
- [TypeScript](https://www.typescriptlang.org/) as our programming language.
- [Webpack](https://webpack.js.org/) for bundling and fast re-deploying.

Our code is organized into components and pages. Components are reusable views, and pages are specialized components that are mapped to a URL using `react-router`. Most of our components are located under `shared`. Our pages will be under another directory that relates to a sub-application in Faro. Right now there are these: `assets`, `contacts`, `event-analysis`, `experiments`, `settings`, `sites`, `test`, `touchpoints`, and `ui-kit`.

## Building the Docker Image

### Local

```bash
./gradlew dockerDeploy buildDockerImage
```

Produces `liferay/com-liferay-osb-faro:latest`.

### Cloud Environment

```bash
FARO_ENVIRONMENT_NAME=stg ./gradlew dockerDeploy buildDockerImage
```

Produces `liferay/com-liferay-osb-faro:latest`.

### Production (Date-Stamped Tag)

```bash
FARO_ENVIRONMENT_NAME=prd ./gradlew dockerDeploy buildDockerImage
```

Produces `liferay/com-liferay-osb-faro:prd-YYYYMMDD`.

### Custom Tag

```bash
./gradlew dockerDeploy buildDockerImage -Pdocker.image.tag=my-tag
```

## CI/CD

The `Jenkinsfile` at the workspace root drives the CI pipeline. It:

1. Runs `./gradlew dockerDeploy` with `FARO_ENVIRONMENT_NAME=${FARO_ENVIRONMENT}`

1. Injects the license into `build/docker/deploy/license.xml`

1. Builds the Docker image with `FARO_ENVIRONMENT_NAME`, `LABEL_BUILD_DATE`, and `LABEL_VCS_REF` as build args

1. Scans with Prisma Cloud

1. Pushes to DockerHub as `liferaycloud/com-liferay-osb-faro:<tag>`

## Gradle Properties

See `gradle.properties` for active settings. Key property:

#### `liferay.workspace.product`

Pins the DXP version used for the bundle URL, Docker base image, and target platform.
Current value: `dxp-2026.q1.5-lts`.

## Pull Requests

Before you open your pull request, make sure you have these:

- Commit messages that are linked back to a ticket or story on [Jira](https://issues.liferay.com).
- Unit tests (if applicable, and they generally are).
- All unit tests passing: `./gradlew packageRunTest`.
- Beautifully formatted code: `./gradlew formatSource`.

After submitting your PR, GitHub will automatically add reviewers. They will either approve your pull request, or leave a review and request some changes. Feel free to ask questions and clarify, as this is a collaborative process.