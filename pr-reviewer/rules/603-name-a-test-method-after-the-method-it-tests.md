# 603: Name a Test Method After the Method It Tests

Name a test method `test` followed by the exact name of the method it tests, including that method's own `is` or `has` prefix. The test for `isQuotaExceeded()` is `testIsQuotaExceeded`, not `testQuotaExceeded`; the test for `isValidConnection()` is `testIsValidConnection`. Append a scenario qualifier for a variant, as in `testIsValidConnectionWithNullAPIKey`.

**Rationale:** A test name that matches the method under test lets a reader jump from a failing test straight to the method, and it keeps the suite's names parallel to the API. Dropping the `is`, writing `testQuotaExceeded` for `isQuotaExceeded`, breaks that mapping and hides which method the test covers.

A violation is a test method whose name does not begin with `test` plus the method it tests, most commonly one that omits the `is` or `has` prefix of the method under test.

**Example:** for a method `isQuotaExceeded()`, the test `testQuotaExceeded` should be `testIsQuotaExceeded`, mirroring how `testIsValidConnection` maps to `isValidConnection()`.