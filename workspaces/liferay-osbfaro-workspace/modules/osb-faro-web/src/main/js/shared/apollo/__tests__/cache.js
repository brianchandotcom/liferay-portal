import {deepMergeMetric} from '../cache';

describe('Apollo cache deepMergeMetric', () => {
	it('fuses sibling sub-fields written by different queries under the same parent', () => {
		const fromLocationsQuery = {
			__typename: 'Site',
			sessionsMetric: {
				__typename: 'Metric',
				geolocation: [{value: 100, valueKey: 'BR'}],
			},
		};

		const fromDevicesQuery = {
			__typename: 'Site',
			sessionsMetric: {
				__typename: 'Metric',
				browser: [{value: 10, valueKey: 'Chrome'}],
				device: [{value: 8, valueKey: 'Desktop'}],
				value: 50,
			},
		};

		expect(deepMergeMetric(fromLocationsQuery, fromDevicesQuery)).toEqual({
			__typename: 'Site',
			sessionsMetric: {
				__typename: 'Metric',
				browser: [{value: 10, valueKey: 'Chrome'}],
				device: [{value: 8, valueKey: 'Desktop'}],
				geolocation: [{value: 100, valueKey: 'BR'}],
				value: 50,
			},
		});
	});

	it('preserves both sides regardless of which query writes first', () => {
		const locations = {sessionsMetric: {geolocation: ['L']}};
		const devices = {sessionsMetric: {browser: ['D']}};

		expect(deepMergeMetric(locations, devices)).toEqual({
			sessionsMetric: {browser: ['D'], geolocation: ['L']},
		});

		expect(deepMergeMetric(devices, locations)).toEqual({
			sessionsMetric: {browser: ['D'], geolocation: ['L']},
		});
	});

	it('replaces arrays wholesale rather than concatenating', () => {
		const existing = {sessionsMetric: {geolocation: ['old']}};
		const incoming = {sessionsMetric: {geolocation: ['new1', 'new2']}};

		expect(deepMergeMetric(existing, incoming)).toEqual({
			sessionsMetric: {geolocation: ['new1', 'new2']},
		});
	});

	it('overwrites primitive values with the incoming value', () => {
		expect(deepMergeMetric({a: 1}, {a: 2})).toEqual({a: 2});
		expect(deepMergeMetric({a: 'old'}, {a: 'new'})).toEqual({a: 'new'});
	});

	it('preserves existing fields when incoming omits them', () => {
		expect(deepMergeMetric({a: 1, b: 2}, {a: 99})).toEqual({a: 99, b: 2});
	});

	it('returns incoming when existing is null or undefined', () => {
		expect(deepMergeMetric(null, {a: 1})).toEqual({a: 1});
		expect(deepMergeMetric(undefined, {a: 1})).toEqual({a: 1});
	});

	it('returns incoming when incoming is not a plain object (replace semantics)', () => {
		expect(deepMergeMetric({a: 1}, null)).toBeNull();
		expect(deepMergeMetric({a: 1}, 'string')).toBe('string');
		expect(deepMergeMetric({a: 1}, [1, 2])).toEqual([1, 2]);
	});

	it('merges deeply nested objects', () => {
		const existing = {a: {b: {c: 1}}};
		const incoming = {a: {b: {d: 2}}};

		expect(deepMergeMetric(existing, incoming)).toEqual({
			a: {b: {c: 1, d: 2}},
		});
	});
});
