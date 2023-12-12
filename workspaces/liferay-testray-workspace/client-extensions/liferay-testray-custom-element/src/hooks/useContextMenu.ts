/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {ContextMenuState} from '../components/ContextMenu';

const useContextMenu = (active: boolean) => {
	const [contextMenuState, setContextMenuState] = useState<ContextMenuState>({
		actions: [],
		item: {},
		position: {x: 0, y: 0},
		rowIndex: 0,
		visible: false,
	});

	const handleContext = ({
		actions,
		event,
		item,
		rowIndex,
	}: {
		actions: any;
		event: React.MouseEvent;
		item: any;
		rowIndex: number;
	}) => {
		event.preventDefault();

		setContextMenuState({
			actions,
			item,
			position: {
				x: event.clientX,
				y: event.clientY,
			},
			rowIndex,
			visible: !contextMenuState.visible,
		});
	};

	useEffect(() => {
		if (!active) {
			return;
		}

		const handleClick = () =>
			setContextMenuState({
				...contextMenuState,
				visible: false,
			});

		if (contextMenuState.visible) {
			window.addEventListener('click', handleClick);
		}

		return () => {
			if (!active) {
				return;
			}

			return window.removeEventListener('click', handleClick);
		};
	}, [contextMenuState, setContextMenuState, active]);

	return {
		contextMenuState,
		handleContext,
		setContextMenuState,
	};
};

export default useContextMenu;
