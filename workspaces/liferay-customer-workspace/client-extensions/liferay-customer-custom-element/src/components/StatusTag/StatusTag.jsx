/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import {STATUS_DISPLAY} from './utils/constants/statusDisplays';

const StatusTag = ({currentStatus}) => {
	const statusDisplay = STATUS_DISPLAY[currentStatus];

	return (
		<ClayLabel
			className={classNames(
				'px-2 m-0 font-weight-normal text-paragraph-sm',
				{
					[`label-tonal-${statusDisplay?.displayType}`]: statusDisplay?.displayType,
				}
			)}
		>
			{statusDisplay && statusDisplay.label}
		</ClayLabel>
	);
};

export default StatusTag;
