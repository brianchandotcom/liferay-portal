import AttributeFilterBox from './AttributeFilterBox';
import EventPropertiesQuery, {
	EventPropertiesData,
	EventPropertiesVariables,
} from '../../queries/EventPropertiesQuery';
import React from 'react';
import {ATTRIBUTES_PAGE_SIZE} from './attribute-conjunction-input/utils';
import {AttributesDataSourceFn} from './attribute-conjunction-input';
import {
	AttributeConjunctionChangeParams,
	AttributeFilterState,
	Criterion,
} from '../../utils/types';
import {cloneAttributes} from 'event-analysis/utils/utils';
import {IPaginatedDataSourceParams} from 'shared/hooks/usePaginatedRequest';
import {NAME} from 'shared/util/pagination';
import {OrderByDirections} from 'shared/util/constants';
import {SafeResults} from 'shared/hoc/util';
import {useApolloClient, useQuery} from '@apollo/client';

const SORT = {
	column: NAME,
	type: OrderByDirections.Ascending,
};

interface IAttributeFilterSectionProps {
	conjunctionCriterion: Criterion;
	eventId: string;
	onChange: (params: AttributeConjunctionChangeParams) => void;
	onClear: () => void;
	touched: AttributeFilterState;
	valid: AttributeFilterState;
}

const AttributeFilterSection: React.FC<IAttributeFilterSectionProps> = ({
	conjunctionCriterion,
	eventId,
	onChange,
	onClear,
	touched,
	valid,
}) => {
	const client = useApolloClient();

	// The query pages from zero. The first page doubles as the one the picker
	// requests on open, which Apollo then serves from its cache.

	const getVariables = ({
		page,
		pageSize,
		query,
	}: IPaginatedDataSourceParams): EventPropertiesVariables => ({
		eventId,
		keyword: query,
		page: page - 1,
		size: pageSize,
		sort: SORT,
	});

	const result = useQuery<EventPropertiesData, EventPropertiesVariables>(
		EventPropertiesQuery,
		{
			skip: !eventId,
			variables: getVariables({
				page: 1,
				pageSize: ATTRIBUTES_PAGE_SIZE,
				query: '',
			}),
		}
	);

	if (!eventId) {
		return null;
	}

	const attributesDataSourceFn: AttributesDataSourceFn = (params) =>
		client
			.query({
				query: EventPropertiesQuery,
				variables: getVariables(params),
			})
			.then(({data}) => ({
				items: cloneAttributes(data.eventProperties.eventProperties),
				total: data.eventProperties.total,
			}));

	return (
		<SafeResults {...result} page={false} pageDisplay={false}>
			{(data: any) => {
				const rawAttributes =
					data?.eventProperties?.eventProperties || [];
				const attributes = cloneAttributes(rawAttributes);

				if (!attributes.length) {
					return null;
				}

				return (
					<AttributeFilterBox
						attributes={attributes}
						attributesDataSourceFn={attributesDataSourceFn}
						conjunctionCriterion={conjunctionCriterion}
						onChange={onChange}
						onClear={onClear}
						touched={touched}
						valid={valid}
					/>
				);
			}}
		</SafeResults>
	);
};

export default AttributeFilterSection;
