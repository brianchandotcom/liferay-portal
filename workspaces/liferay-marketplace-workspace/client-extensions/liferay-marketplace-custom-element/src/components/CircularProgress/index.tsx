/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CircularProgressbarWithChildren} from 'react-circular-progressbar';

type CircularProgressProps = {
	height: number;
	pathColor: string;
	progress: number;
	progressColor: string;
	width: number;
};

const CircularProgress: React.FC<CircularProgressProps> = ({
	height,
	pathColor,
	progress,
	progressColor,
	width,
}) => (
	<CircularProgressbarWithChildren
		styles={{
			path: {
				stroke: progressColor,
				strokeLinecap: 'round',
				transition: 'all ease-in-out 0.5s',
			},
			root: {
				height,
				width,
			},
			trail: {
				stroke: pathColor,
				strokeLinecap: 'round',
				transition: 'all ease-in-out 0.5s',
			},
		}}
		value={progress}
	>
		<strong style={{fontSize: 10}}>{`${progress}%`}</strong>
	</CircularProgressbarWithChildren>
);

export default CircularProgress;
