import Card from 'shared/components/Card';
import IntervalSelector from 'shared/components/IntervalSelector';
import React, {useCallback} from 'react';
import {BaseCardHeaderDefaultIProps} from 'shared/components/base-card/HeaderDefault';
import {DropdownRangeKey} from 'shared/components/dropdown-range-key/DropdownRangeKey';
import {INTERVAL_KEY_MAP, isHourlyRangeKey} from 'shared/util/time';
import {Text} from '@clayui/core';

const ActivityStreamCardHeader: React.FC<BaseCardHeaderDefaultIProps> = ({
	description = '',
	interval,
	label,
	legacy,
	onChangeInterval,
	onRangeSelectorsChange,
	rangeSelectors,
	showInterval,
	showRangeKey = true
}) => {
	const handleRangeSelectorsChange = useCallback((newVal: any) => {
		onRangeSelectorsChange && onRangeSelectorsChange(newVal);

		if (isHourlyRangeKey(newVal.rangeKey)) {
			onChangeInterval(INTERVAL_KEY_MAP.day);
		}
	}, []);

	const handleChangeInterval = useCallback(
		(newVal: any) => onChangeInterval && onChangeInterval(newVal),
		[]
	);

	return (
		<Card.Header className='align-items-center d-flex justify-content-between'>
			<div className='min-w-0'>
				<Card.Title>{label}</Card.Title>

				<Text color='secondary' size={4}>
					{description}
				</Text>
			</div>

			<div className='d-flex flex-shrink-0'>
				{showInterval && (
					<IntervalSelector
						activeInterval={interval}
						className='mr-3'
						disabled={isHourlyRangeKey(rangeSelectors.rangeKey)}
						onChange={handleChangeInterval}
					/>
				)}

				{showRangeKey && (
					<DropdownRangeKey
						legacy={legacy}
						onRangeSelectorChange={handleRangeSelectorsChange}
						rangeSelectors={rangeSelectors}
					/>
				)}
			</div>
		</Card.Header>
	);
};

export default ActivityStreamCardHeader;
