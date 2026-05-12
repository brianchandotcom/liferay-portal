---

description: Playwright integration + E2E tests for the workspace.
name: tests

---

# Workspace Tests

Playwright covers the surface exposed after deployment in this workspace. Unit tests live with the code they exercise, not here — see [`Layout`](#layout).

All tests run against a local Liferay instance by default (`BASE_URL=http://localhost:8080`).

## First Run Bootstrap

From the workspace root:

```bash
yarn bootstrap:tests      # Create tests/.env, install deps, fetch Chromium
```

`bootstrap:tests` is idempotent — safe to rerun on a fresh checkout.

## Testing Strategy

Three tiers, each with a clear home and trigger:

| Tier | Tooling | Lives In | When to Add |
|---|---|---|---|
| Unit | Vitest (React) | Colocated with source in each client extension: `src/**/*.test.{ts,tsx}` | Pure logic — a component renders a state, a function transforms input. No portal required. |
| Unit | JUnit 5 + Spring MockMvc | Colocated with source in each Spring Boot extension: `src/test/java/**/*Test.java` | Spring controller logic — routes, status codes, response bodies. No portal required. |
| Integration | Playwright `request` (API only, no browser) | `tests/integration` | Anything that depends on a booted portal: headless REST, OAuth2 scope enforcement, Object definitions. |
| E2E | Playwright (browser) | `tests/e2e` | Test whole flows through the UI. This is a last resort — push behavior down to integration when possible. |

Decision heuristic: if it can run without booting the portal, it is a unit test. If it runs without a browser, it is an integration test. Otherwise, it is an E2E test.

## Spring Boot Unit Tests

Java unit tests live under `src/test/java` inside each Spring Boot client extension and use JUnit 5 with Spring's `MockMvc` for controller-layer testing. They do not require a running portal or database.

Run from the Spring Boot extension directory:

```bash
cd client-extensions/<your-etc-spring-boot>
../../gradlew test
```

Or from the workspace root:

```bash
./gradlew :client-extensions:<your-etc-spring-boot>:test
```

Tests use `MockMvcBuilders.standaloneSetup(...)` to instantiate controllers directly — no Spring application context is loaded. Add a test class under `src/test/java` for each controller you want to cover.

## Layout

```
tests/
├── playwright.config.ts         # Two projects: integration + e2e
├── e2e/
│   ├── fixtures/                # Playwright test.extend wrappers
│   ├── pages/                   # Page object model
│   ├── specs/                   # *.spec.ts run by the e2e project
│   └── utils/                   # Login, constants, shared helpers
└── integration/
    ├── fixtures/                # api fixture
    ├── helpers/                 # APIHelpers — auth + JSON wrapping
    └── specs/                   # *.spec.ts run by the integration project
```

## Running

From this `tests` directory:

```bash
yarn playwright                  # Both projects
yarn playwright:integration      # Integration only (no browser)
yarn playwright:e2e              # E2E only
yarn playwright:ui               # Playwright UI mode
```

Or, from the workspace root:

```bash
yarn test:integration
yarn test:e2e
```

Run a single spec:

```bash
yarn playwright integration/specs/sanity.spec.ts
```

> `test` is intentionally not wired as a script in this package — `./gradlew build` auto-invokes any `yarn test` it finds, and running Playwright during a gradle build would require a running Liferay instance. Vitest owns the `test` slot in each client extension at build time; Playwright runs on demand via these scripts.

## Auth

Integration tests authenticate via the `api` fixture:

- If `OAUTH_CLIENT_ID` and `OAUTH_CLIENT_SECRET` are set, the helper fetches a bearer token from `/o/oauth2/token`.
- Otherwise it falls back to basic auth with `LIFERAY_ADMIN_EMAIL` / `LIFERAY_ADMIN_PASSWORD` (defaults: `test@liferay.com` / `test`).

The default basic auth path works out of the box with the seed admin user from `docker compose up`.

## Conventions

- **Page objects** extend `BasePage` and expose `Locator`s as readonly fields.
- **Fixtures** are shallow — they instantiate page objects or helpers and pass them to the test. Keep login/logout in `utils/`, not fixtures, so specs can opt in.
- **Specs** import the tier's fixture(s) via `mergeTests`, call `.describe` once per surface, and keep assertions behavioral (what the user sees or what the API returns), not structural.
- **Selectors** prefer `getByRole` → `getByLabel` → `getByTestId` → CSS. Do not use XPath.
- **Data setup** runs through `APIHelpers` — never seed through the UI when the API can do it.
- **Secrets** come from `.env` at the workspace root (gitignored) or environment. Never commit a real OAuth secret.