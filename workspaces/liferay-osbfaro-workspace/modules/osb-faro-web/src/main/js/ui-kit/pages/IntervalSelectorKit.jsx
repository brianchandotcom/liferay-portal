import IntervalSelector from 'shared/components/IntervalSelector';
import React, {useState} from 'react';
import {INTERVAL_KEY_MAP} from 'shared/util/time';

const IntervalSelectorKit = () => {
	const [interval, setInterval] = useState(INTERVAL_KEY_MAP.day);

	return (
		<div>
			<IntervalSelector
				activeInterval={interval}
				onChange={setInterval}
			/>
		</div>
	);
};

export default IntervalSelectorKit;
