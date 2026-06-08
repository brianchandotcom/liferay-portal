/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React from 'react';
import {DataTypes} from '~/event-analysis/utils/types';
import {DATA_TYPE_LABELS_MAP} from '~/event-analysis/utils/utils';
import Label from '~/shared/components/Label';

interface IFilterInfoProps {
	dataType?: DataTypes;
	description?: string;
	name: string;
	onEditClick?: (id: string) => void;
	showDescription?: boolean;
}

const FilterInfo: React.FC<IFilterInfoProps> = ({
	dataType,
	description,
	name,
	onEditClick,
	showDescription,
}) => (
	<div className="filter-info-root">
		<div className="align-items-center d-flex filter-name justify-content-between">
			{name}

			{onEditClick && (
				<ClayButton
					aria-label={Liferay.Language.get('edit')}
					borderless
					className="button-root"
					displayType="secondary"
					onClick={() => onEditClick(name)}
					size="sm"
				>
					<ClayIcon className="icon-root" symbol="pencil" />
				</ClayButton>
			)}
		</div>

		{showDescription && (
			<div className="description">
				{description || Liferay.Language.get('no-description')}
			</div>
		)}

		{dataType && (
			<Label display="info" uppercase>
				{DATA_TYPE_LABELS_MAP[dataType]}
			</Label>
		)}
	</div>
);

export default FilterInfo;
