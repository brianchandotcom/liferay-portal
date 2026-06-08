/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {graphql} from '@apollo/client/react/hoc';
import ClayButton from '@clayui/button';
import {OrderedMap} from 'immutable';
import {isArray, isString, omit} from 'lodash';
import React from 'react';
import MetadataTag from '~/settings/recommendations/components/MetadataTag';
import RecommendationPageAssetsQuery from '~/settings/recommendations/queries/RecommendationPageAssetsQuery';
import {
	EXCLUDE,
	Filter,
	getFilterValueBreakdown,
} from '~/settings/recommendations/utils/utils';
import Modal from '~/shared/components/modal';
import {withBaseResults, withStatefulPagination} from '~/shared/hoc';
import {getMapResultToProps} from '~/shared/hoc/mappers/metrics';
import {
	TITLE,
	createOrderIOMap,
	getSortFromOrderIOMap,
} from '~/shared/util/pagination';
import {OrderParams} from '~/shared/util/records';

const withData = () =>
	graphql(RecommendationPageAssetsQuery, {
		options: ({
			delta,
			itemFilters,
			orderIOMap,
			page,
			query,
			useNegateValue,
		}: {
			delta: number;
			itemFilters: Filter[];
			orderIOMap: OrderedMap<string, OrderParams>;
			page: number;
			query: string;
			useNegateValue: boolean;
		}) => ({
			fetchPolicy: 'no-cache',
			variables: {
				keywords: query,
				propertyFilters: itemFilters.map(({name, value}) => ({
					filter: value,
					negate: useNegateValue ? name === EXCLUDE : false,
				})),
				size: delta,
				sort: getSortFromOrderIOMap(orderIOMap),
				start: (page - 1) * delta,
			},
		}),
		props: getMapResultToProps(({pageAssets: {pageAssets, total}}) => ({
			items: pageAssets,
			total,
		})),
	});

const TableWithData = withStatefulPagination(
	withBaseResults(withData, {
		emptyDescription: Liferay.Language.get(
			'there-were-no-matching-items-for-this-string'
		),
		emptyTitle: Liferay.Language.get('no-matches-found'),
		getColumns: ({secondColumnHeader}: {secondColumnHeader?: string}) => [
			{
				accessor: 'title',
				className: 'table-cell-expand text-truncate',
				label: Liferay.Language.get('page-name'),
			},
			{
				accessor: secondColumnHeader || 'url',
				className: 'secondary-info table-cell-expand text-truncate',
				dataFormatter: (val: unknown) => {
					if (isString(val)) {
						return val;
					}
					else if (isArray(val)) {
						return val
							.map(({value}: {value: string}) => value)
							.join(', ');
					}
				},
				label: secondColumnHeader || 'url',
				sortable: false,
			},
		],
		showDropdownRangeKey: false,
	}),
	{
		initialDelta: 10,
		initialOrderIOMap: createOrderIOMap(TITLE),
	},
	(props: {[key: string]: any}) => omit(props, 'onSearchValueChange'),
	false
);

interface IMatchingPagesModalProps {
	itemFilters: Filter[];
	onClose: () => void;
	useNegateValue: boolean;
}

const MatchingPagesModal: React.FC<IMatchingPagesModalProps> = ({
	itemFilters,
	onClose,
	useNegateValue = false,
}) => {
	const {name, value} = itemFilters[0];

	const {exactMatchSign, metadataTag, rule} = getFilterValueBreakdown(value);

	const customFilter = itemFilters.length === 1 && metadataTag;

	return (
		<Modal className="matching-pages-modal-root" size="xl">
			<Modal.Header
				onClose={onClose}
				title={Liferay.Language.get('matching-pages')}
			/>

			<Modal.Body>
				{!!customFilter && !useNegateValue && (
					<div>
						<span className="include-exclude">
							{`${
								name === EXCLUDE
									? Liferay.Language.get('exclude')
									: Liferay.Language.get('include')
							}:`}
						</span>

						<MetadataTag value={metadataTag} />

						<span className="rule">
							{exactMatchSign ? `"${rule}"` : rule}
						</span>
					</div>
				)}
			</Modal.Body>

			<TableWithData
				itemFilters={itemFilters}
				secondColumnHeader={customFilter}
				useNegateValue={useNegateValue}
			/>

			<Modal.Footer>
				<ClayButton
					className="button-root"
					displayType="primary"
					onClick={onClose}
				>
					{Liferay.Language.get('done')}
				</ClayButton>
			</Modal.Footer>
		</Modal>
	);
};

export default MatchingPagesModal;
