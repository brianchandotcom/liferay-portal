import * as API from 'shared/api';
import Card from 'shared/components/Card';
import ClayLink from '@clayui/link';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React, {useMemo} from 'react';
import SearchableEntityTable from 'shared/components/SearchableEntityTable';
import URLConstants from 'shared/util/url-constants';
import {
	ACCOUNT_NAME,
	COUNTRY,
	createOrderIOMap,
	FIRST_ACTIVITY_DATE,
	LAST_ACTIVITY_DATE,
	NAME,
	PROFILE_TYPE
} from 'shared/util/pagination';
import {
	Conjunctions,
	ProfileTypes,
	RelationalOperators
} from 'segment/segment-editor/dynamic/utils/constants';
import {FilterByType, FilterOptionType, RangeSelectors} from 'shared/types';
import {getSafeRangeSelectors} from 'shared/util/util';
import {IndividualsListCDPColumns} from 'shared/util/table-columns';
import {Map, Set} from 'immutable';
import {Sizes} from 'shared/util/constants';
import {useParams} from 'react-router-dom';
import {useRequest} from 'shared/hooks/useRequest';
import {useStatefulPagination} from 'shared/hooks/useStatefulPagination';

const ORDER_BY_OPTIONS = [
	{
		label: Liferay.Language.get('name'),
		value: NAME
	},
	{
		label: Liferay.Language.get('account-name'),
		value: ACCOUNT_NAME
	},
	{
		label: Liferay.Language.get('country'),
		value: COUNTRY
	},
	{
		label: Liferay.Language.get('first-seen'),
		value: FIRST_ACTIVITY_DATE
	},
	{
		label: Liferay.Language.get('last-active'),
		value: LAST_ACTIVITY_DATE
	},
	{
		label: Liferay.Language.get('profile-type'),
		value: PROFILE_TYPE
	}
];

const DEFAULT_FILTER_BY_OPTIONS: FilterOptionType[] = [
	{
		key: 'profileTypes',
		label: Liferay.Language.get('profile-type'),
		values: [
			{
				label: Liferay.Language.get('known'),
				value: ProfileTypes.KNOWN
			},
			{
				label: Liferay.Language.get('anonymous'),
				value: ProfileTypes.ANONYMOUS
			}
		]
	},
	{
		key: 'activityStatus',
		label: Liferay.Language.get('activity-status'),
		values: [
			{
				label: Liferay.Language.get('active'),
				value: 'ACTIVE'
			},
			{
				label: Liferay.Language.get('inactive'),
				value: 'INACTIVE'
			}
		]
	}
];

function transformCountriesInQueryString(countries: string[]) {
	if (!countries || countries.length === 0) {
		return;
	}

	return countries
		.map(
			country =>
				`(demographics/country/value ${RelationalOperators.EQ} '${country}')`
		)
		.join(Conjunctions.Or);
}

interface IIndividualsList {
	rangeSelectors: RangeSelectors;
}

const IndividualsList: React.FC<IIndividualsList> = ({rangeSelectors}) => {
	const {channelId = '', groupId = ''} = useParams<{
		channelId: string;
		groupId: string;
	}>();

	const {rangeEnd, rangeKey, rangeStart} =
		getSafeRangeSelectors(rangeSelectors);

	const paginationParams = useStatefulPagination(undefined, {
		initialFilterBy: Map({
			activityStatus: Set(['ACTIVE'])
		}) as FilterByType,
		initialOrderIOMap: createOrderIOMap(NAME)
	});

	const {data: countriesData, loading: countriesLoading} = useRequest({
		dataSourceFn: API.individuals.fetchFieldValues,
		variables: {
			channelId,
			fieldMappingFieldName: 'country',
			groupId
		}
	});

	const FILTER_BY_OPTIONS: FilterOptionType[] = useMemo(() => {
		const countries = countriesData?.items;

		if (countries?.length) {
			return [
				{
					key: 'countries',
					label: Liferay.Language.get('country'),
					values: countries.map((country: string) => ({
						label: country,
						value: country
					}))
				},
				...DEFAULT_FILTER_BY_OPTIONS
			];
		}

		return DEFAULT_FILTER_BY_OPTIONS;
	}, [countriesData, countriesLoading]);

	const activityStatusValues =
		paginationParams.filterBy.get('activityStatus');

	const selectedFilters = {
		activityStatus:
			activityStatusValues?.size === 2
				? undefined
				: activityStatusValues?.first(),
		filter: transformCountriesInQueryString(
			paginationParams.filterBy.get('countries')?.toArray()
		),
		profileTypes:
			paginationParams.filterBy.get('profileTypes')?.toArray() || []
	};

	const renderNoResults = () => (
		<NoResultsDisplay
			description={
				<>
					{Liferay.Language.get(
						'connect-a-data-source-with-people-data'
					)}

					<ClayLink
						className='d-block mb-3'
						href={URLConstants.DataSourceConnection}
						key='DOCUMENTATION'
						target='_blank'
					>
						{Liferay.Language.get(
							'access-our-documentation-to-learn-more'
						)}
					</ClayLink>
				</>
			}
			icon={{
				border: false,
				size: Sizes.XXXLarge,
				symbol: 'ac_satellite'
			}}
			spacer
			title={Liferay.Language.get(
				'no-individuals-synced-from-data-sources'
			)}
		/>
	);

	return (
		<Card>
			<Card.Title className='card-header'>
				{Liferay.Language.get('individual-profiles')}
			</Card.Title>
			<Card.Body className='no-padding'>
				<div className='individuals-dashboard-known-individuals-root'>
					<SearchableEntityTable
						{...paginationParams}
						columns={[
							IndividualsListCDPColumns.getNameEmail({
								channelId,
								groupId
							}),
							IndividualsListCDPColumns.accountNames,
							IndividualsListCDPColumns.country,
							IndividualsListCDPColumns.firstSeen,
							IndividualsListCDPColumns.lastActive,
							IndividualsListCDPColumns.profileType,
							IndividualsListCDPColumns.activityStatus
						]}
						dataSourceFn={API.individuals.search}
						dataSourceParams={{
							activityStatus: selectedFilters.activityStatus,
							channelId,
							filter: selectedFilters.filter,
							groupId,
							profileTypes: selectedFilters.profileTypes.length
								? selectedFilters.profileTypes
								: undefined,
							rangeEnd,
							rangeKey,
							rangeStart
						}}
						filterByOptions={FILTER_BY_OPTIONS}
						key='individuals-list-table'
						noResultsRenderer={renderNoResults}
						orderByOptions={ORDER_BY_OPTIONS}
						rowIdentifier='id'
					/>
				</div>
			</Card.Body>
		</Card>
	);
};

export default IndividualsList;
