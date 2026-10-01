import BooleanFilter from './BooleanFilter';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import DateFilter from './DateFilter';
import DurationFilter from './DurationFilter';
import FilterInfo from '../../FilterInfo';
import NumberFilter from './NumberFilter';
import React from 'react';
import StringFilter from './StringFilter';
import {
	Attribute,
	AttributeOwnerTypes,
	DataTypes,
	Filter,
} from 'event-analysis/utils/types';
import {useAttributes} from '../../../context/attributes';

const FILTERS_MAP = {
	[DataTypes.Boolean]: BooleanFilter,
	[DataTypes.Date]: DateFilter,
	[DataTypes.Duration]: DurationFilter,
	[DataTypes.Number]: NumberFilter,
	[DataTypes.String]: StringFilter,
};

interface IFilterOptionsProps extends React.HTMLAttributes<HTMLDivElement> {
	attribute: Attribute;
	attributeOwnerType: AttributeOwnerTypes;
	eventId: string;
	filterId?: string;
	onActiveChange: (active: boolean) => void;
	onAttributeChange: (attribute?: Attribute) => void;
	onEditClick?: (id: string) => void;
}

const FilterOptions: React.FC<IFilterOptionsProps> = ({
	attribute,
	attributeOwnerType,
	eventId,
	filterId,
	onActiveChange,
	onAttributeChange,
	onEditClick,
}) => {
	const {addFilter, editFilter, filters} = useAttributes();

	const {
		dataType,
		description,
		displayName,
		id: attributeId,
		name,
	} = attribute;

	const FilterBody = FILTERS_MAP[dataType];

	const filter = filterId ? filters[filterId] : undefined;

	const onSubmit = (newFilter: Filter) => {
		if (filterId) {
			editFilter({
				attribute,
				filter: newFilter,
				id: filterId,
			});
		}
		else {
			addFilter({
				attribute,
				filter: newFilter,
			});
		}

		onAttributeChange(undefined);

		onActiveChange(false);
	};

	return (
		<div className="attribute-options">
			<div className="options-header">
				<ClayButton
					className="button-root back-to-attributes-button"
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

			<FilterBody
				attributeId={attributeId}
				attributeOwnerType={attributeOwnerType}
				description={description}
				displayName={displayName ?? ''}
				eventId={eventId}
				filter={
					filter?.attributeId === attributeId ? filter : undefined
				}
				onSubmit={onSubmit}
			/>
		</div>
	);
};

export default FilterOptions;
