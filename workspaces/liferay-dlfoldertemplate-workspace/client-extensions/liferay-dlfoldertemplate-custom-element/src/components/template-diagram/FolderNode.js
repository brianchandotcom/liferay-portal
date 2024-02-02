/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {memo} from 'react';
import {Handle, Position} from 'reactflow';

const FolderNode = ({data: node, nodeWidth, onAdd, onSelect}) => {
	return (
		<>
			<div
				className="card card-horizontal card-type-directory"
				onClick={() => onSelect(node)}
				style={{width: nodeWidth}}
			>
				{!node.root && <Handle position={Position.Top} type="target" />}

				<Handle position={Position.Bottom} type="source" />

				<div className="card-body">
					<div className="card-row">
						<div className="autofit-col">
							<span className="bg-light sticker sticker-primary">
								<span className="inline-item">
									<ClayIcon symbol="folder" />
								</span>
							</span>
						</div>
						<div className="autofit-col autofit-col-expand autofit-col-gutters">
							<section className="autofit-section">
								<span className="text-truncate-inline">
									<span
										className="lfr-portal-tooltip text-truncate"
										title={node.label}
									>
										{node.label}
									</span>
								</span>
							</section>
						</div>
					</div>
				</div>
			</div>
			<div
				className="d-flex justify-content-center position-absolute w-100"
				style={{bottom: '12px'}}
			>
				<ClayButtonWithIcon
					aria-label="Create New Node"
					displayType="info"
					onClick={() => {
						onAdd(node.nodeId);
					}}
					size="xs"
					symbol="plus"
					title="Create New Node"
				/>
			</div>
		</>
	);
};

export default memo(FolderNode);
