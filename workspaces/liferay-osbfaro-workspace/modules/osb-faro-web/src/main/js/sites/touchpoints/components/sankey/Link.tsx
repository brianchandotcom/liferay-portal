/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Layer} from 'recharts';

import {getFill} from './utils';

export const Link = function Link(props: any) {
	const {
		hovered,
		index,
		linkWidth,
		onNodeChange,
		payload,
		selectedNode,
		sourceControlX,
		sourceX,
		sourceY,
		targetControlX,
		targetX,
		targetY,
	} = props;

	let fill = null;
	let id = null;

	if (payload.source.main) {
		id = payload.target.id;
		fill = getFill({
			hovered,
			index,
			payload: {
				color: payload.target.color,
				id: payload.target.id,
				main: false,
			},
			selectedNode,
		});
	}
	else {
		id = payload.source.id;
		fill = getFill({
			hovered,
			index,
			payload: {
				color: payload.source.color,
				id: payload.source.id,
				main: false,
			},
			selectedNode,
		});
	}

	return (
		<Layer
			crossOrigin={undefined}
			fr={undefined}
			key={`CUSTOM_LINK_${index}`}
			opacity={0.2}
			path={undefined}
		>
			<path
				d={`
				 M${sourceX},${sourceY + linkWidth / 2}
				 C${sourceControlX},${sourceY + linkWidth / 2}
				   ${targetControlX},${targetY + linkWidth / 2}
				   ${targetX},${targetY + linkWidth / 2}
				 L${targetX},${targetY - linkWidth / 2}
				 C${targetControlX},${targetY - linkWidth / 2}
				   ${sourceControlX},${sourceY - linkWidth / 2}
				   ${sourceX},${sourceY - linkWidth / 2}
				 Z
			   `}
				fill={fill}
				onBlur={() => {}}
				onMouseEnter={() => onNodeChange(id)}
				onMouseLeave={() => onNodeChange(null)}
				strokeWidth="0"
			/>
		</Layer>
	);
};
