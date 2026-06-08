/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {DataTypes} from '~/event-analysis/utils/types';

import FilterInfo from './FilterInfo';

interface IInfoCardPopoverProps {
	dataType?: DataTypes;
	description?: string;
	name: string;
	onEditClick?: (id: string) => void;
}

const InfoCardPopover: React.FC<IInfoCardPopoverProps> = ({
	dataType,
	description,
	name,
	onEditClick,
}) => (
	<div className="info-card-popover-root">
		<FilterInfo
			dataType={dataType}
			description={description}
			name={name}
			onEditClick={onEditClick}
			showDescription
		/>
	</div>
);

export default InfoCardPopover;
