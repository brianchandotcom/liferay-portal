# Playwright

This directory holds the Playwright tests for the Liferay Sample Workspace.

## Running the Tests

Install Docker, JDK 21, Node.js, and Yarn, and then run:

```bash
yarn install
npx playwright install chromium
npx playwright test
```

If Liferay is not already running, the tests start it in Docker with `../scripts/bootstrap.sh` and remove it, including its data, when they finish.

## Configuration

The tests run against `PORTAL_URL`, which defaults to `http://localhost:8080`, and sign in with `LIFERAY_USER_PASSWORD`, which defaults to `test`. To change them, set them in `.env.local`.

Put the portal properties and feature flags that the tests need in `env/portal-setup-wizard.properties`, and OSGi configurations in `env/osgi/configs`.

## Layout

- `core` is a copy of `modules/test/playwright/core` from the Liferay Portal repository. Do not edit it; to update it, copy that directory over it again.
- `tests` holds the tests and the files specific to this workspace.

## Writing Tests

Merge `loginTest()` from `core` to sign in, and `dataApiHelpersTest()` to get an `apiHelpers` object that deletes what it creates after each test:

```typescript
const test = mergeTests(loginTest(), dataApiHelpersTest());
```