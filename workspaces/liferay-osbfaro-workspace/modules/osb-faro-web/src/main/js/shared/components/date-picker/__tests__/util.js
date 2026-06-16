import * as Util from '../util';
import moment from 'moment';

describe('Util', () => {
	describe('isAboveMaxRange', () => {
		it('should return true if the date is above the max range', () => {
			expect(
				Util.isAboveMaxRange(
					{
						end: moment(0).add(13, 'months'),
						start: moment(0)
					},
					365
				)
			).toBe(true);
		});

		it('should return false if the date is below the max range', () => {
			expect(
				Util.isAboveMaxRange(
					{
						end: moment(0).add(11, 'months'),
						start: moment(0)
					},
					365
				)
			).toBe(false);
		});

		it('should return false if the date is not a range', () => {
			expect(Util.isAboveMaxRange(moment(0), 365)).toBe(false);
		});

		it('should return false if maxRange is not provided', () => {
			expect(Util.isAboveMaxRange(moment(0))).toBe(false);
		});
	});

	describe('isDateOrRange', () => {
		it.each`
			value                                     | valid
			${moment(0)}                              | ${true}
			${{end: moment(2000), start: moment(0)}}  | ${true}
			${{end: null, start: moment(0)}}          | ${true}
			${{end: null, start: null}}               | ${true}
			${null}                                   | ${true}
			${undefined}                              | ${true}
			${23}                                     | ${false}
			${'January 23rd'}                         | ${false}
			${{end: moment(2000), startz: moment(0)}} | ${false}
		`('should return $valid for $value', ({valid, value}) => {
			expect(Util.isDateOrRange(value)).toBe(valid);
		});
	});

	describe('isInRange', () => {
		it('should return true if the date is in the range', () => {
			expect(
				Util.isInRange(
					{
						end: moment(0).add(3, 'days'),
						start: moment(0)
					},
					moment(0).add(1, 'days')
				)
			).toBe(true);
		});

		it('should return false if the date is not in the range', () => {
			expect(
				Util.isInRange(
					{
						end: moment(0).add(3, 'days'),
						start: moment(0)
					},
					moment(0).add(10, 'days')
				)
			).toBe(false);

			expect(
				Util.isInRange(
					{
						end: moment(0).add(3, 'days'),
						start: moment(0)
					},
					moment(0)
				)
			).toBe(false);
		});
	});

	describe('isRange', () => {
		it.each`
			value                                 | valid
			${null}                               | ${false}
			${{}}                                 | ${false}
			${{start: moment(0)}}                 | ${false}
			${{foo: moment(0), start: moment(0)}} | ${false}
			${{end: moment(0), start: moment(0)}} | ${true}
			${{end: null, start: moment(0)}}      | ${true}
			${{end: moment(0), start: null}}      | ${true}
		`('should return $valid for $value', ({valid, value}) => {
			expect(Util.isRange(value)).toBe(valid);
		});
	});

	describe('updateRange', () => {
		it('should update an empty range object with a new value', () => {
			expect(
				Util.updateRange({end: null, start: null}, moment(0))
			).toMatchObject({
				end: null,
				start: expect.anything()
			});
		});

		it('should update a half range object with an end', () => {
			expect(
				Util.updateRange({end: null, start: moment(0)}, moment(23))
			).toMatchObject({
				end: expect.anything(),
				start: expect.anything()
			});
		});

		it('should set the new start of the range', () => {
			const newRange = Util.updateRange(
				{end: moment(200), start: moment(100)},
				moment(50)
			);

			expect(newRange.end).toBe(null);
			expect(newRange.start.milliseconds()).toBe(50);
		});

		it('should swap the start to end if the new end is before', () => {
			const newRange = Util.updateRange(
				{end: null, start: moment(100)},
				moment(50)
			);

			expect(newRange.start.milliseconds()).toBe(50);
			expect(newRange.end.milliseconds()).toBe(100);
		});
	});
});
