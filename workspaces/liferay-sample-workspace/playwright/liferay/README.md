# Playwright

The parent directory, `playwright`, holds the Playwright tests for this workspace. This directory, `playwright/liferay`, holds what Liferay provides for them.

## Running the Tests

Install Docker, JDK 21, Node.js, and Yarn, and then run from `playwright`:

```bash
yarn install
npx playwright install chromium
npx playwright test
```

If Liferay is not already running, the tests start it in Docker with `liferay/start.sh` and remove it, including its data, when they finish.

Starting Liferay takes several minutes. To keep it between runs, run `liferay/start.sh` before the tests and `liferay/stop.sh` when done. The tests never remove a Liferay they did not start.

Each run writes an HTML report to `playwright-report` and a JUnit report to `test-results/TEST-playwright.xml` for CI.

## Configuration

The tests run against `PORTAL_URL`, which defaults to `http://localhost:8080`, and sign in with `LIFERAY_USER_PASSWORD`, which defaults to `test`. To change them, set them in `playwright/.env.local`.

Put the portal properties and feature flags that the tests need in `playwright/env/portal-setup-wizard.properties`, and OSGi configurations in `playwright/env/osgi/configs`.

## Layout

Everything in `playwright/liferay` is synced from the Liferay Sample Workspace and overwritten when it changes. Do not edit it. Everything else in `playwright` belongs to this workspace.

```
playwright
├── package.json             The Playwright version and the packages the tests use
├── playwright.config.ts     How the tests run
├── yarn.lock
├── tests                    The tests
└── liferay                  Synced; do not edit
    ├── baseConfig.ts        The defaults playwright.config.ts starts from
    ├── core                 A copy of modules/test/playwright/core from Liferay Portal
    ├── globalSetup.ts       Starts Liferay when it is not running
    ├── package.json         The packages core needs
    ├── start.sh             Starts Liferay for the tests
    └── stop.sh              Removes that Liferay and its data
```

When Playwright is first synced into a workspace, the files outside `liferay` start as copies of the Liferay Sample Workspace's. Later syncs never change them.

## Writing Tests

Merge `loginTest()` from `liferay/core` to sign in, and `dataApiHelpersTest()` to get an `apiHelpers` object that deletes what it creates after each test:

```typescript
const test = mergeTests(loginTest(), dataApiHelpersTest());
```

## Choosing the Playwright Version and Adding Packages

Change `playwright/package.json`:

- Set the Playwright version in `devDependencies`.
- Add the packages the tests need to `devDependencies`.
- Override the version of a package that `liferay/core` needs with `resolutions`.

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
		"liferay"
	]
}
```

Keep `"workspaces": ["liferay"]`. It adds the packages that `liferay/core` needs, which `liferay/package.json` lists and which change along with `liferay/core`. That file never lists `@playwright/test`, so the Playwright version is always this workspace's choice.

Run `yarn install` after a change, and commit `yarn.lock`.

## Changing How the Tests Run

Change `playwright/playwright.config.ts`. It spreads `baseConfig` and then sets what this workspace needs, for example more workers or video on failure:

```typescript
export default defineConfig({
	...baseConfig,
	use: {
		...baseConfig.use,
		video: 'retain-on-failure',
	},
	workers: process.env.CI ? 2 : undefined,
});
```

Keep `globalSetup` from `baseConfig`. It starts Liferay when it is not running.