/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';
import {useDrop} from 'react-dnd';
import {HTML5Backend, NativeTypes} from 'react-dnd-html5-backend';
import DndProvider from '~/shared/components/DndProvider';

interface ITargetProps extends React.HTMLAttributes<HTMLElement> {
	message: React.ReactNode;
	targetType: string;
}

const Target: React.FC<ITargetProps> = ({
	children,
	message,
	onDrop,
	targetType,
}) => {
	const [{isOver}, drop] = useDrop({
		accept: targetType,
		collect: (monitor: any) => ({
			endDrag: monitor.getDropResult(),
			isOver: monitor.isOver(),
		}),
		drop: (item: object, monitor: any) => {
			if (onDrop) {
				onDrop(monitor.getItem());
			}
		},
	});

	return (
		<div
			className={getCN('drop-target-container', {
				'file-over': isOver,
			})}
			ref={drop}
		>
			{children}

			<div className="drop-zone">{message}</div>
		</div>
	);
};

export const TYPES = NativeTypes;
export default Object.assign(
	(props: any) => (
		<DndProvider backend={HTML5Backend}>
			<Target {...props} />
		</DndProvider>
	),
	{TYPES: NativeTypes}
);
