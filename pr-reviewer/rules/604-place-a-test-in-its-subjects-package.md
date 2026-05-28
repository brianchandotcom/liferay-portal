# 604: Place a Test in Its Subject's Package

A unit test lives in the same package as the class it tests, with a `Test` suffix on the class name: `com.liferay.foo.Bar` has its unit test at `com.liferay.foo.BarTest` in `src/test/java`. An integration test lives in the subject's package with a trailing `.test` subpackage: `com.liferay.foo.Bar` has its integration test at `com.liferay.foo.test.BarTest` in `src/testIntegration/java`.

**Rationale:** Matching the test's package to the subject's package makes the pair findable from either side — `git ls-files` returns them next to each other, and the IDE jumps from the class to its test without a lookup. A unit test in the same package can exercise package private members; an integration test sits one level deeper under `.test` because it lives in a separate test module and the suffix avoids colliding with same named classes in the subject module.

A violation is a test whose package does not match the subject's: the same `Test` suffix on the class but a different package root (`com.liferay.oauth.client.persistence.*` for a class in `com.liferay.portal.security.sso.openid.connect.*`) is a violation, even when the test compiles and runs correctly.

**Example:** https://github.com/brianchandotcom/liferay-portal/pull/175571 — `OpenIdConnectProviderPortalInstanceLifecycleListener` lives in `com.liferay.portal.security.sso.openid.connect.internal.configuration.instance.lifecycle`, but the integration test was placed in `com.liferay.oauth.client.persistence.internal.configuration.instance.lifecycle.test`. The test's package should mirror the subject's, ending in `...openid.connect.internal.configuration.instance.lifecycle.test`.
