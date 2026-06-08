/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import React from 'react';
import {
	Attribute,
	AttributeOwnerTypes,
	Breakdowns,
	DataTypes,
	IBreakdownProps,
} from '~/event-analysis/utils/types';

import {
	AddBreakdown,
	EditBreakdown,
	withAttributesConsumer,
} from '../../../context/attributes';
import FilterInfo from '../../FilterInfo';
import DateBreakdown from './DateBreakdown';
import DurationBreakdown from './DurationBreakdown';
import NumberBreakdown from './NumberBreakdown';

const BREAKDOWNS_MAP: Partial<Record<DataTypes, React.FC<IBreakdownProps>>> = {
	[DataTypes.Date]: DateBreakdown,
	[DataTypes.Duration]: DurationBreakdown,
	[DataTypes.Number]: NumberBreakdown,
};

interface IBreakdownOptionsProps extends React.HTMLAttributes<HTMLDivElement> {
	addBreakdown: AddBreakdown;
	attribute: Attribute;
	attributeOwnerType: AttributeOwnerTypes;
	breakdownId?: string;
	breakdowns: Breakdowns;
	editBreakdown: EditBreakdown;
	onActiveChange: (active: boolean) => void;
	onAttributeChange: (attribute?: Attribute) => void;
	onEditClick?: (id: string) => void;
}

const BreakdownOptions: React.FC<IBreakdownOptionsProps> = ({
	addBreakdown,
	attribute,
	attributeOwnerType,
	breakdownId,
	breakdowns,
	editBreakdown,
	onActiveChange,
	onAttributeChange,
	onEditClick,
}) => {
	const {
		dataType,
		description,
		displayName,
		id: attributeId,
		name,
	} = attribute;

	const breakdown = breakdownId ? breakdowns[breakdownId] : undefined;

	const BreakdownBody = BREAKDOWNS_MAP[dataType];

	if (!BreakdownBody) {
		return null;
	}

	return (
		<div className="attribute-options">
			<div className="options-header">
				<ClayButton
					className="back-to-attributes-button button-root"
					displayType="unstyled"
					onClick={() => onAttributeChange(undefined)}
					size="sm"
				>
					<ClayIcon
						className="icon-root mr-2"
						symbol="angle-left-small"
					/>

					{Liferay.Language.get('back-to-attributes')}
				</ClayButton>

				<FilterInfo
					dataType={dataType}
					name={displayName || name}
					onEditClick={onEditClick}
				/>
			</div>

			<BreakdownBody
				attributeId={attributeId}
				attributeOwnerType={attributeOwnerType}
				breakdown={
					breakdown?.attributeId === attributeId
						? breakdown
						: undefined
				}
				description={description}
				displayName={displayName ?? ''}
				onSubmit={(newBreakdown: IBreakdownProps['breakdown']) => {
					if (breakdownId) {
						editBreakdown({
							attribute,
							breakdown: newBreakdown!,
							id: breakdownId,
						});
					}
					else {
						addBreakdown({
							attribute,
							breakdown: newBreakdown!,
						});
					}

					onAttributeChange(undefined);

					onActiveChange(false);
				}}
			/>
		</div>
	);
};

export default withAttributesConsumer(BreakdownOptions);
