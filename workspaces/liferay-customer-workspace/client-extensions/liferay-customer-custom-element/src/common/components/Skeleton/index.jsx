/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';

const Skeleton = ({align, count = 1, height, width, ...props}) => {
	return (
		<div {...props}>
			{[...new Array(count)].map((_, index) => (
				<div
					className={classNames(
						'skeleton rounded',
						{
							'ml-auto': align === 'right',
							'mr-auto': align === 'left',
							'mx-auto': align === 'center',
						},
						{
							'mt-3': index > 0,
						}
					)}
					key={index}
					style={{
						height: `${height}px`,
						width: `${width - index * 100}px`,
					}}
				/>
			))}
		</div>
	);
};

Skeleton.Rounded = ({height, width}) => {
	return (
		<div
			className="rounded-sm skeleton"
			style={{height: `${height}px`, width: `${width}px`}}
		/>
	);
};

Skeleton.Square = ({height, width}) => (
	<div
		className="skeleton"
		style={{height: `${height}px`, width: `${width}px`}}
	/>
);

export default Skeleton;
