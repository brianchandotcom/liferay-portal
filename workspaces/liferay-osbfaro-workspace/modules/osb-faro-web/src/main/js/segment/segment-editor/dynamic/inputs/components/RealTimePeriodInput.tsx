import Form from 'shared/components/form';
import React, {useMemo, useState} from 'react';
import {
	DAYS,
	HOURS,
	HOURS_IN_A_DAY,
	MAX_DAYS,
	TIME_WINDOW_OPTIONS,
} from '../../utils/constants';
import {Option, Picker} from '@clayui/core';

interface IRealTimePeriodInputProps {
	initialInterval?: number;
	onChange: (interval: number, timeWindow: string) => void;
	initialTimeWindow?: string;
}

export const DEFAULT_OPTIONS = {
	interval: 1,
	timeWindow: DAYS,
};

const RealTimePeriodInput: React.FC<IRealTimePeriodInputProps> = ({
	initialInterval = DEFAULT_OPTIONS.interval,
	initialTimeWindow = DEFAULT_OPTIONS.timeWindow,
	onChange,
}) => {
	const [interval, setInterval] = useState<number>(initialInterval);
	const [timeWindow, setTimeWindow] = useState(initialTimeWindow);

	const INTERVAL_OPTIONS = useMemo(() => {
		const max = timeWindow === HOURS ? HOURS_IN_A_DAY : MAX_DAYS;
		return Array.from({length: max}, (_, i) => i + 1);
	}, [timeWindow]);

	const handleIntervalChange = (value: React.Key) => {
		const numberVal = Number(value);
		setInterval(numberVal);
		onChange(numberVal, timeWindow);
	};

	const handleTimePeriodChange = (newKey: React.Key) => {
		const newKeyStr = String(newKey);
		let newInterval = interval;
		if (interval > HOURS_IN_A_DAY && newKeyStr === HOURS) {
			newInterval = HOURS_IN_A_DAY;
			setInterval(newInterval);
		}

		setTimeWindow(newKeyStr);
		onChange(newInterval, newKeyStr);
	};

	return (
		<Form.Group autoFit>
			<Form.GroupItem className="unit" label shrink>
				{Liferay.Language.get('in-the-last').toLowerCase()}
			</Form.GroupItem>
			<Picker
				className="operator-input"
				items={INTERVAL_OPTIONS}
				onSelectionChange={handleIntervalChange}
				selectedKey={interval.toString()}
				shrink
			>
				{(number) => <Option key={number}>{number.toString()}</Option>}
			</Picker>
			<Picker
				className="operator-input ml-2"
				items={TIME_WINDOW_OPTIONS}
				onSelectionChange={handleTimePeriodChange}
				selectedKey={timeWindow}
				shrink
			>
				{TIME_WINDOW_OPTIONS.map(({label, value}) => (
					<Option key={value}>{label.toLowerCase()}</Option>
				))}
			</Picker>
		</Form.Group>
	);
};

export default RealTimePeriodInput;
