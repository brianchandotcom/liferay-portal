#!/bin/bash
#
# Test plan for branch: master (LPD-65978 — CKEditor sample module split)
# Generated: 2026-05-12
# Estimated time: ~15m / 20m budget
#
# Changes: 2 commits, 28 files changed
# Affected areas:
#   - frontend-editor-ckeditor-sample-web (simplified to CKEditor4+AlloyEditor only)
#   - frontend-editor-ckeditor5-sample-web (new module, all CKEditor5 samples)
#
# !!WARNING!!
# The Playwright tests below depend on CKEditorSamplePage.gotoTab(), which
# navigates a two-level tab structure (outer: CKEditor 4 / CKEditor 5, inner:
# specific editor). Our changes removed the outer navigation bar — the legacy
# portlet now renders ckeditor4/view.jsp directly, and CKEditor5 samples live
# in a separate portlet. As a result:
#
#   - ALL ckeditor5/* specs will FAIL: they try to click a "CKEditor 5" tab
#     that no longer exists in the legacy portlet.
#   - ALL ckeditor4/* specs will FAIL: they try to click a "CKEditor 4" tab
#     that no longer exists.
#   - alloyeditor-web specs will FAIL for the same reason.
#   - editorConfigContributor.spec.ts @LPD-63018 will FAIL: it clicks
#     getByRole('link', {name: 'CKEditor 4'}) which no longer renders.
#
# The following infrastructure files must be updated before these tests pass:
#   modules/test/playwright/tests/frontend-editor-ckeditor-sample-web/pages/CKEditorSamplePage.ts
#   modules/test/playwright/tests/frontend-editor-ckeditor-sample-web/fixtures/ckeditorSamplePageTest.ts
#   modules/test/playwright/tests/frontend-editor-ckeditor-sample-web/fixtures/ckeditor5/*.ts
#   modules/test/playwright/tests/client-extension-web/main/editorConfigContributor.spec.ts
#
# The integration tests (frontend-editor-test) are NOT affected — they use
# synthetic EditorConfigContributor implementations unrelated to our modules.
#

REPO_ROOT="$(cd "$(dirname "${0}")" && pwd)"
EXIT_CODE=0

# EditorConfigContributor resolution logic — confirms contributor priority rules
# are intact after removing two contributors from the legacy module and adding
# two to the new ckeditor5-sample module.
"${REPO_ROOT}/gradlew" \
	--project-dir "${REPO_ROOT}/modules/apps/frontend-editor/frontend-editor-test" \
	testIntegration \
	--tests "EditorConfigContributorTest" || EXIT_CODE=1

# EditorConfigTransformer logic — unaffected by our changes; included as a
# sanity check that the transformer mechanism still works after deploying the
# new ckeditor5-sample module alongside the legacy one.
"${REPO_ROOT}/gradlew" \
	--project-dir "${REPO_ROOT}/modules/apps/frontend-editor/frontend-editor-test" \
	testIntegration \
	--tests "EditorConfigTransformerTest" || EXIT_CODE=1

# CKEditor4 alloy spec — exercises the AlloyEditor tab in the legacy portlet.
# EXPECTED TO FAIL: gotoTab(CK_EDITOR_4, ALLOY) clicks a missing "CKEditor 4" outer tab.
npx \
	--prefix "${REPO_ROOT}/modules/test/playwright" \
	playwright test \
	tests/frontend-editor-ckeditor-web/main/tests/ckeditor4/alloy.spec.ts || EXIT_CODE=1

# CKEditor4 classic spec — exercises the Classic tab in the legacy portlet.
# EXPECTED TO FAIL: same outer-tab navigation issue.
npx \
	--prefix "${REPO_ROOT}/modules/test/playwright" \
	playwright test \
	tests/frontend-editor-ckeditor-web/main/tests/ckeditor4/classic.spec.ts || EXIT_CODE=1

# CKEditor5 advanced_classic spec — exercises the Advanced Classic editor.
# EXPECTED TO FAIL: tries to navigate to CKEditor5 tab which no longer exists
# in the legacy portlet; must be redirected to the new ckeditor5-sample portlet.
npx \
	--prefix "${REPO_ROOT}/modules/test/playwright" \
	playwright test \
	tests/frontend-editor-ckeditor-web/main/tests/ckeditor5/advanced_classic.spec.ts || EXIT_CODE=1

# CKEditor5 balloon spec — exercises the Balloon editor.
# EXPECTED TO FAIL: same portlet redirection issue.
npx \
	--prefix "${REPO_ROOT}/modules/test/playwright" \
	playwright test \
	tests/frontend-editor-ckeditor-web/main/tests/ckeditor5/balloon.spec.ts || EXIT_CODE=1

# editorConfigContributor spec — @LPD-63018 test clicks getByRole('link', {name: 'CKEditor 4'})
# which no longer renders after we removed the outer navigation bar.
# EXPECTED TO FAIL for @LPD-63018 only; other tests in this spec are unaffected.
npx \
	--prefix "${REPO_ROOT}/modules/test/playwright" \
	playwright test \
	tests/client-extension-web/main/editorConfigContributor.spec.ts || EXIT_CODE=1

exit ${EXIT_CODE}
