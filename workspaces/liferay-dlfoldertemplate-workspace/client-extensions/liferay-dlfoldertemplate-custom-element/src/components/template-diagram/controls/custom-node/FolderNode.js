/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {memo} from 'react';
import {Handle, Position} from 'reactflow';

import './FolderNode.css';

import {ClayButtonWithIcon} from '@clayui/button';
import {Text} from '@clayui/core';
import ClayIcon from '@clayui/icon';

const FolderNode = ({data:node, onAdd, onSelect}) => {
	return (
		<div className="folder-node-box">
			{!node.root && (
				<Handle
					className="top-connector"
					position={Position.Top}
					type="target"
				/>
			)}
			<Handle
				className="bottom-connector"
				id="a"
				position={Position.Bottom}
				type="source"
			/>
			<div
				className="node-icon"
				onClick={() => {
					onSelect(node);
				}}
			>
				<div className="node-icon-box">
					<ClayIcon symbol="folder" />
				</div>
			</div>
			<div
				className="node-title"
				onClick={() => {
					onSelect(node);
				}}
			>
				<div className="node-title-content">
					<Text size={2} truncate>
						{node.label}
					</Text>
				</div>
			</div>
			<div className="node-actions">
				<ClayButtonWithIcon
					aria-label="Create New Node"
					className="add-action"
					displayType="info"
					onClick={() => {
						onAdd(node.nodeId);
					}}
					size="xs"
					symbol="plus"
					title="Create New Node"
				/>
			</div>
		</div>
	);
};

export default memo(FolderNode);
