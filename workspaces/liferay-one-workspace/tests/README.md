---

name: tests
description: Playwright integration + E2E tests for the Liferay One workspace.

---

# Liferay One Workspace — Tests

Playwright covers the post-deploy surface of this workspace. Unit tests live with the code they exercise, not here — see [`Layout`](#layout).

All tests run against a local Liferay instance by default (`BASE_URL=http://localhost:8080`).

## First-run bootstrap

From the workspace root:

```bash
./quickstart/run.sh       # bring up liferay + extensions
yarn bootstrap:tests      # create .env, install deps, fetch chromium
```

`bootstrap:tests` is idempotent — safe to re-run on a fresh checkout.

## Testing strategy

Three tiers, each with a clear home and trigger:

| Tier | Tooling | Lives in | When to add |
|---|---|---|---|
| Unit | Vitest (React) / JUnit (Spring Boot) | Colocated with source: `client-extensions/liferay-one-custom-element/src/**/*.test.tsx`, `client-extensions/liferay-one-etc-spring-boot/src/test/java/**` | Pure logic — a component renders a state, a translator maps a payload, a validator rejects input. No portal required. |
| Integration | Playwright `request` (API only, no browser) | `tests/integration/` | Anything that depends on a booted portal or Spring Boot: headless REST against Object definitions, OAuth2 scope enforcement, Object Actions, webhook endpoints, Pub/Sub shadow-mode assertions. |
| E2E | Playwright (browser) | `tests/e2e/` | Whole flows through the UI: Marketplace checkout, Support ticket + attachment, Admin pages. Last resort — push behaviour down to integration when possible. |

Decision heuristic: if it can run without booting the portal, it's a unit test. If it runs without a browser, it's integration. Otherwise E2E.

## Layout

```
tests/
├── playwright.config.ts         # Two projects: integration + e2e
├── e2e/
│   ├── fixtures/                # Playwright test.extend wrappers
│   ├── pages/                   # Page-object model
│   ├── specs/                   # *.spec.ts run by the e2e project
│   └── utils/                   # Login, constants, shared helpers
└── integration/
    ├── fixtures/                # oneApi fixture
    ├── helpers/                 # OneApiHelpers — auth + JSON wrapping
    └── specs/                   # *.spec.ts run by the integration project
```

## Running

From this `tests/` directory:

```bash
yarn playwright                  # both projects
yarn playwright:integration      # integration only (no browser)
yarn playwright:e2e              # e2e only
yarn playwright:ui               # Playwright UI mode
```

Or, from the workspace root:

```bash
yarn test:integration
yarn test:e2e
```

Run a single spec:

```bash
yarn playwright e2e/specs/smoke.spec.ts
```

> `test` is intentionally not wired as a script in this package — `./gradlew build` auto-invokes any `yarn test` it finds, and running Playwright during gradle build would require a running Liferay instance. Vitest owns the build-time `test` slot in `liferay-one-custom-element/`; Playwright runs on demand via these scripts.

## Auth

Integration tests authenticate via the `oneApi` fixture using basic auth with `ONE_ADMIN_EMAIL` / `ONE_ADMIN_PASSWORD` (defaults: `test@liferay.com` / `test`). The seed admin user created by `quickstart/run.sh` works out of the box.

## Conventions

- **Page objects** extend `BasePage` and expose `Locator`s as readonly fields.
- **Fixtures** are shallow — they instantiate page objects or helpers and pass them to the test. Keep login/logout in `utils/`, not fixtures, so specs can opt in.
- **Specs** import the tier's fixture(s) via `mergeTests`, call `.describe` once per surface, and keep assertions behavioural (what the user sees or what the API returns), not structural.
- **Selectors** prefer `getByRole` → `getByLabel` → `getByTestId` → CSS. No XPath.
- **Data setup** runs through `OneApiHelpers` — never seed through the UI when the API can do it.
- **Secrets** come from `.env` at the workspace root (gitignored) or environment. Never commit credentials.