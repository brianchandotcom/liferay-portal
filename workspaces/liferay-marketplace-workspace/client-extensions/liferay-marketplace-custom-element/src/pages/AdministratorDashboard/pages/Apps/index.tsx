/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useSearchParams} from 'react-router-dom';

import {
	AppActions,
	ListViewTypes,
} from '../../../../components/ListView/hooks/ListViewContext';
import Page from '../../../../components/Page';
import {ProductType, ProductTypeLabels} from '../../../../enums/Product';
import useListTypeDefinition from '../../../../hooks/useListTypeDefinition';
import i18n from '../../../../i18n';
import {LIFERAY_VERSION_PICKLIST} from '../../../PublisherDashboard/pages/NewAppFlow/constants';
import InfoCard from '../../components/InfoCard';
import useAppsMetrics from '../../hooks/useAppsMetrics';
import {percentage} from '../../util';
import AdministratorAppsListView from './AdministratorAppsListView';

export default function Apps() {
	const [searchParams] = useSearchParams();
	const {data} = useListTypeDefinition(LIFERAY_VERSION_PICKLIST);

	const liferayVersions =
		data?.listTypeEntries?.map((version) => version.name).reverse() ?? [];

	const {
		approved = 0,
		approvedBeforeLastWeek = 0,
		approvedLastWeek = 0,
		inReview = 0,
		inReviewLastWeek = 0,
		inReviewBeforeLastWeek = 0,
		products = 0,
	} = useAppsMetrics('week');

	return (
		<>
			<div className="d-flex flex-wrap mb-3">
				<InfoCard
					className="mr-3"
					expanded
					growth={percentage(
						products,
						inReviewLastWeek - inReviewBeforeLastWeek
					)}
					growthContext={`+${inReviewLastWeek - inReviewBeforeLastWeek} this week`}
					symbol="squares-clock"
					title="App Awaiting Review"
					value={inReview}
				/>

				<InfoCard
					expanded
					growth={percentage(
						products,
						approvedLastWeek - approvedBeforeLastWeek
					)}
					growthContext={`+${approvedLastWeek - approvedBeforeLastWeek} this week`}
					symbol="squares"
					title="Recently Published"
					value={approved}
				/>
			</div>

			<Page
				pageRendererProps={{className: 'border py-2 rounded-lg'}}
				title="Apps"
			>
				<AdministratorAppsListView
					filter={searchParams.get('filter') as string}
					listViewProps={{
						managementToolbarProps: {
							filterItems: [
								{
									name: i18n.translate('app-type'),
									children: Object.values(ProductType).map(
										(productType) => ({
											name: ProductTypeLabels[
												productType
											],
											onClick: (
												dispatch: React.Dispatch<AppActions>
											) =>
												dispatch({
													payload: {
														filters: {
															filter: {
																specificationValues:
																	productType,
															},
														},
													},
													type: ListViewTypes.SET_FILTERS,
												}),
										})
									),
								},
								{
									children: liferayVersions.map(
										(liferayVersion) => ({
											onClick: (
												dispatch: React.Dispatch<AppActions>
											) =>
												dispatch({
													payload: {
														filters: {
															filter: {
																specificationValues:
																	liferayVersion,
															},
														},
													},
													type: ListViewTypes.SET_FILTERS,
												}),
											name: liferayVersion,
										})
									),
									name: i18n.translate('liferay-version'),
								},
							],
							visible: true,
						},
						paginationOptions: {displayType: 'always'},
					}}
				/>
			</Page>
		</>
	);
}
