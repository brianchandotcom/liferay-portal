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
- `tests` holds the tests and everything else that belongs to this workspace, including `package.json`, which decides the Playwright version and the packages the tests use.
- Everything else is synced from the Liferay Sample Workspace and overwritten when it changes.

## Writing Tests

Merge `loginTest()` from `core` to sign in, and `dataApiHelpersTest()` to get an `apiHelpers` object that deletes what it creates after each test:

```typescript
const test = mergeTests(loginTest(), dataApiHelpersTest());
```

## Choosing the Playwright Version and Adding Packages

When Playwright is first synced into a workspace, `tests/package.json`, `tests/.yarnrc`, and `tests/yarn.lock` start as copies of the Liferay Sample Workspace's. Later syncs never change them.

Yarn installs from `tests/package.json`, so this workspace decides what it gets:

- Set the Playwright version in `devDependencies`.
- Add the packages the tests need to `devDependencies`.
- Override the version of a package that `core` needs with `resolutions`.

```json
{
	"devDependencies": {
		"@playwright/test": "1.55.1",
		"typescript": "5.7.2"
	},
	"name": "playwright-tests",
	"private": true,
	"resolutions": {
		"dotenv": "16.4.5"
	},
	"version": "0.0.0",
	"workspaces": [
		".."
	]
}
```

Keep `"workspaces": [".."]`. It adds the packages that `core` needs, which `package.json` in this directory lists and which change along with `core`. That file never lists `@playwright/test`, so the Playwright version is always this workspace's choice.

Keep `tests/.yarnrc` as well. It installs every package into `node_modules` in this directory, where `core`, `playwright.config.ts`, and the tests all find the same copy. Playwright stops with "Requiring @playwright/test second time" if it finds two.

Run `yarn install` from this directory or from `tests`, and commit `tests/yarn.lock`.

Scripts in `tests/package.json` run from `tests`, so call the tools they use through `npx`, for example `"typecheck": "npx tsc --noEmit"`. `npx` finds them in `node_modules` in this directory.