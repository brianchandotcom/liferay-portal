/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Spritemap from './spritemap';

type TestrayIconsProps = {
	className?: string;
	fill?: string;
	size?: number;
	symbol: string;
};

const TestrayIcons: React.FC<TestrayIconsProps> = ({
	className,
	fill,
	size = 20,
	symbol,
}) => {
	return (
		<svg
			className={className}
			fill={fill}
			height={size}
			viewBox="0 0 30 30"
			width={size}
		>
			{(Spritemap as any)[symbol]}
		</svg>
	);
};

export default TestrayIcons;
