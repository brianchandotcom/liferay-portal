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
