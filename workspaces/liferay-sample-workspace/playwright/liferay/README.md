# Playwright

`playwright` holds end to end tests for this workspace.

- `playwright/liferay` is managed and provided by Liferay. Do not edit it.

## Running the Tests

Run from `playwright`:

```bash
yarn install
npx playwright install chromium
npx playwright test
```

- **Reports:** Reports are written to `playwright-report` (HTML) and `test-results/TEST-playwright.xml` (JUnit) by default.
- **Startup:** The tests start up an environment with Docker Compose when it is not already running and will shut it down when the tests finish. If an environment is already up when the tests are invoked, it will be used instead.

## Configuration

Set these in `playwright/.env.local`:

- `LIFERAY_USER_PASSWORD` defaults to `test`.
- `PORTAL_URL` defaults to `http://localhost:8080`.

## What Liferay Provides

Everything is in `liferay/core`:

- `fixtures/backendPageTest` provides `backendPage`, a second page signed in as the administrator.
- `fixtures/dataApiHelpersTest` provides `apiHelpers`, which calls the headless APIs and cleans up data after each test.
- `fixtures/featureFlagsTest` sets feature flags for the tests.
- `fixtures/loginTest` signs in before each test.
- `utils` holds helpers that wait for alerts, page loads, and elements to appear or disappear.

Combine the fixtures with `mergeTests`:

```typescript
const test = mergeTests(loginTest(), dataApiHelpersTest());
```

## Customizing

- `playwright/package.json` is unique to the workspace.
- `playwright/playwright.config.ts` extends from `playwright/liferay/baseConfig.ts`.
- `playwright/tsconfig.json` extends from `playwright/liferay/tsconfig.base.json`.