# OSB Faro Workspace

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
|---------|--------------------------------------------------|
| `local` | Default. Uses `configs/local/`. Debug log level. |
| `stg` | Cloud build. Uses `configs/cloud/`. Info log level. |
| `int` | Cloud build. Uses `configs/cloud/`. Info log level. |
| `prd` | Cloud build. Uses `configs/cloud/`. Info log level. Image tagged `prd-YYYYMMDD`. |

## Building the Docker Image

### Local

```bash
./gradlew dockerDeploy buildDockerImage
```

Produces `liferay/com-liferay-osb-faro:latest`.

### Cloud environment

```bash
FARO_ENVIRONMENT_NAME=stg ./gradlew dockerDeploy buildDockerImage
```

Produces `liferay/com-liferay-osb-faro:latest`.

### Production (date-stamped tag)

```bash
FARO_ENVIRONMENT_NAME=prd ./gradlew dockerDeploy buildDockerImage
```

Produces `liferay/com-liferay-osb-faro:prd-YYYYMMDD`.

### Custom tag

```bash
./gradlew dockerDeploy buildDockerImage -Pdocker.image.tag=my-tag
```

## CI/CD

The `Jenkinsfile` at the workspace root drives the CI pipeline. It:

1. Runs `./gradlew dockerDeploy` with `FARO_ENVIRONMENT_NAME=$FARO_ENVIRONMENT`

1. Injects the license into `build/docker/deploy/license.xml`

1. Builds the Docker image with `FARO_ENVIRONMENT_NAME`, `LABEL_BUILD_DATE`, and `LABEL_VCS_REF` as build args

1. Scans with Prisma Cloud

1. Pushes to DockerHub as `liferaycloud/com-liferay-osb-faro:<tag>`

## Gradle Properties

See `gradle.properties` for active settings. Key property:

#### `liferay.workspace.product`

Pins the DXP version used for the bundle URL, Docker base image, and target platform.
Current value: `dxp-2026.q1.5-lts`.