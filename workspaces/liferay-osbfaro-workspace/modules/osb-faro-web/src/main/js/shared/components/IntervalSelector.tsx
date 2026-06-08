/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import getCN from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';
import {Interval} from '~/shared/types';
import omitDefinedProps from '~/shared/util/omitDefinedProps';
import {INTERVAL_KEY_MAP} from '~/shared/util/time';

const {day, month, week} = INTERVAL_KEY_MAP;

const INTERVAL_LANG_MAP = {
	[day]: Liferay.Language.get('day'),
	[month]: Liferay.Language.get('month'),
	[week]: Liferay.Language.get('week'),
};

interface IntervalSelectorIProps extends React.HTMLAttributes<HTMLDivElement> {
	activeInterval?: Interval;
	disabled?: boolean;
	onChange: (val: any) => void;
}

const IntervalSelector: React.FC<IntervalSelectorIProps> = ({
	activeInterval = day,
	className,
	disabled,
	onChange,
	...otherProps
}) => {
	const classes = getCN('interval-selector-root', className);

	return (
		<ClayButton.Group
			{...omitDefinedProps(otherProps, IntervalSelector.propTypes)}
			className={classes}
		>
			{[day, week, month].map((interval) => (
				<ClayButton
					className={getCN(
						'button-root interval-option text-uppercase',
						{
							active: interval === activeInterval,
						}
					)}
					disabled={disabled}
					displayType="secondary"
					key={interval}
					onClick={() => onChange(interval)}
					small
				>
					{INTERVAL_LANG_MAP[interval].slice(0, 1)}
				</ClayButton>
			))}
		</ClayButton.Group>
	);
};

IntervalSelector.propTypes = {
	activeInterval: PropTypes.oneOf([day, month, week]),
	disabled: PropTypes.bool,
	onChange: PropTypes.func.isRequired,
};

IntervalSelector.defaultProps = {
	activeInterval: day,
	disabled: false,
};

export default IntervalSelector;
