import {
	FEATURE_FLAGS,
	FEATURE_FLAGS_STORAGE_KEY,
	isFeatureFlagEnabled,
	setFeatureFlag,
} from 'shared/util/feature-flags';

describe('feature-flags', () => {
	afterEach(() => {
		window.localStorage.clear();
	});

	it('returns the registered default when there is no override', () => {
		expect(isFeatureFlagEnabled('ENABLE_GLOBAL_FILTER')).toBe(false);
	});

	it('returns the persisted override over the default', () => {
		setFeatureFlag('ENABLE_GLOBAL_FILTER', true);

		expect(isFeatureFlagEnabled('ENABLE_GLOBAL_FILTER')).toBe(true);
	});

	it('persists overrides under the dedicated storage key', () => {
		setFeatureFlag('ENABLE_CSVFILE', true);

		expect(
			JSON.parse(window.localStorage.getItem(FEATURE_FLAGS_STORAGE_KEY))
		).toEqual({ENABLE_CSVFILE: true});
	});

	it('keeps previously stored overrides when toggling another flag', () => {
		setFeatureFlag('ENABLE_CSVFILE', true);
		setFeatureFlag('ENABLE_GLOBAL_FILTER', true);

		expect(
			JSON.parse(window.localStorage.getItem(FEATURE_FLAGS_STORAGE_KEY))
		).toEqual({
			ENABLE_CSVFILE: true,
			ENABLE_GLOBAL_FILTER: true,
		});
	});

	it('falls back to defaults when the stored value is corrupted', () => {
		window.localStorage.setItem(FEATURE_FLAGS_STORAGE_KEY, 'not json');

		expect(isFeatureFlagEnabled('ENABLE_GLOBAL_FILTER')).toBe(false);
	});

	it('exposes a key and default value for every registered flag', () => {
		FEATURE_FLAGS.forEach((definition) => {
			expect(definition.key).toBeTruthy();
			expect(typeof definition.defaultValue).toBe('boolean');
		});
	});
});
