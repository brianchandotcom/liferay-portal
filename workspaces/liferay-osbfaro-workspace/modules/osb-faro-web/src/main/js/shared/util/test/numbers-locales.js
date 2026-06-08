/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {toFixedPoint, toLocale, toRounded} from '../numbers';

describe('Numbers Locales', () => {
	describe('en-US', () => {
		const locale = 'en-US';

		it('toLocale should format correctly', () => {
			expect(toLocale(1234.56, locale)).toBe('1,234.56');
		});

		it('toFixedPoint should format correctly', () => {
			expect(toFixedPoint(1234.56, locale)).toBe('1,235');
		});

		it('toRounded should format correctly', () => {
			expect(toRounded(1234.56, 1, locale)).toBe('1,234.6');
			expect(toRounded(1234, 1, locale)).toBe('1,234');
			expect(toRounded(0.300001, 2, locale)).toBe('0.30');
		});
	});

	describe('pt-BR', () => {
		const locale = 'pt-BR';

		it('toLocale should format correctly', () => {
			expect(toLocale(1234.56, locale)).toBe('1.234,56');
		});

		it('toFixedPoint should format correctly', () => {
			expect(toFixedPoint(1234.56, locale)).toBe('1.235');
		});

		it('toRounded should format correctly', () => {
			expect(toRounded(1234.56, 1, locale)).toBe('1.234,6');
			expect(toRounded(1234, 1, locale)).toBe('1.234');
			expect(toRounded(0.300001, 2, locale)).toBe('0,30');
		});
	});

	describe('ja-JP', () => {
		const locale = 'ja-JP';

		it('toLocale should format correctly', () => {
			expect(toLocale(1234.56, locale)).toBe('1,234.56');
		});

		it('toFixedPoint should format correctly', () => {
			expect(toFixedPoint(1234.56, locale)).toBe('1,235');
		});

		it('toRounded should format correctly', () => {
			expect(toRounded(1234.56, 1, locale)).toBe('1,234.6');
			expect(toRounded(1234, 1, locale)).toBe('1,234');
			expect(toRounded(0.300001, 2, locale)).toBe('0.30');
		});
	});

	describe('zh-CN', () => {
		const locale = 'zh-CN';

		it('toLocale should format correctly', () => {
			expect(toLocale(1234.56, locale)).toBe('1,234.56');
		});

		it('toFixedPoint should format correctly', () => {
			expect(toFixedPoint(1234.56, locale)).toBe('1,235');
		});

		it('toRounded should format correctly', () => {
			expect(toRounded(1234.56, 1, locale)).toBe('1,234.6');
			expect(toRounded(1234, 1, locale)).toBe('1,234');
			expect(toRounded(0.300001, 2, locale)).toBe('0.30');
		});
	});
});
