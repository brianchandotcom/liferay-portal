/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './FolderStructureDesigner.css';

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';
import {ReactFlowProvider} from 'reactflow';

import Diagram from './Diagram';

const FolderStructureDesigner = ({templateId}) => {
	const [key, setKey] = useState(0);

	const reloadDiagram = () => {
		setKey((prevKey) => prevKey + 1);
	};

	return (
		<>
			<ClayButton
				className="reload-button"
				onClick={reloadDiagram}
				size="sm"
			>
				<span className="inline-item inline-item-before">
					<ClayIcon symbol="reload" />
				</span>
				Reload
			</ClayButton>
			<ReactFlowProvider key={key}>
				<Diagram key={key} templateId={templateId}></Diagram>
			</ReactFlowProvider>
		</>
	);
};

export default FolderStructureDesigner;
