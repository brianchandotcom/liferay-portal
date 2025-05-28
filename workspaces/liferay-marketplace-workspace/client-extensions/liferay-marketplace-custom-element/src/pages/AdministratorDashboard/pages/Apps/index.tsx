/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import { useSearchParams } from 'react-router-dom';

import {
	AppActions,
	ListViewTypes,
} from '../../../../components/ListView/hooks/ListViewContext';
import Page from '../../../../components/Page';
import {
	ProductType,
	ProductTypeLabels,
} from '../../../../enums/Product';
import useListTypeDefinition from '../../../../hooks/useListTypeDefinition';
import i18n from '../../../../i18n';
import { LIFERAY_VERSION_PICKLIST } from '../../../PublisherDashboard/pages/NewAppFlow/constants';
import { AdministratorAppsListView } from './AdministratorAppsListView';
import InfoCard from '../../components/InfoCard';
import useAppsMetricks from '../../hooks/useAppsMetricks';

type FilterItem<T> = {
	name: string;
	children: {
		name: string;
		onClick: (dispatch: React.Dispatch<AppActions>) => void;
	}[];
};

function createFilterGroup<T extends string | number>(
	values: T[] | Record<string, T>,
	label: string
): FilterItem<T> {
	const isAppType = !Array.isArray(values)

	const items =
		Array.isArray(values)
			? values
			: Object.keys(values).map((key) => values[key]);

	return {
		name: i18n.translate(label as any),
		children: items.map((item) => ({
			name: isAppType ? ProductTypeLabels[item as keyof typeof ProductTypeLabels] : String(item),
			onClick: (dispatch: React.Dispatch<AppActions>) => dispatch({
				type: ListViewTypes.SET_FILTERS,
				payload: {
					filters: {
						filter: {
							specificationValues: item,
						},
					},
				},
			})
		})),
	};
}

const percentage = (total: number, partial: number): number => {
	if (!total) {
		return 0;
	}

	return Math.round((partial / total) * 100);
};

export default function Apps() {
	const { data } = useListTypeDefinition(LIFERAY_VERSION_PICKLIST);
	const [searchParams] = useSearchParams();

	const pageFilter = searchParams.get('filter');

	const LiferayVersion =
		data?.listTypeEntries?.map((version) => version.name).reverse() ?? [];

	const filterItems = [
		{
			label: 'app-type',
			value: ProductType
		},
		{
			label: 'liferay-version',
			value: LiferayVersion
		},
	].map(({ label, value }) =>
		createFilterGroup(value, label)
	);

	const {
		products,
		inReview,
		inreviewLastlastweek,
		inreviewBeforeLastWeek,
		approved,
		approvedLastWeek,
		approvedBeforeLastWeek
	} = useAppsMetricks('week')

	return (
		<>
			<div className="d-flex flex-wrap mb-3">
				<InfoCard
					className='mr-3'
					expanded
					growth={percentage(products, inreviewLastlastweek - inreviewBeforeLastWeek)}
					growthContext={`+${inreviewLastlastweek - inreviewBeforeLastWeek} this week`}
					symbol="squares-clock"
					title="App Awaiting Review"
					value={inReview}
				/>

				<InfoCard
					expanded
					growth={percentage(products, approvedLastWeek - approvedBeforeLastWeek)}
					growthContext={`+${approvedLastWeek - approvedBeforeLastWeek} this week`}
					symbol="squares"
					title="Recently Published"
					value={approved}
				/>
			</div>

			<Page
				pageRendererProps={{ className: 'border py-2 rounded-lg' }}
				title="Apps"
			>
				<AdministratorAppsListView
					filter={pageFilter as string}
					listViewProps={{
						managementToolbarProps: {
							filterItems,
							visible: true,
						},
						paginationOptions: { displayType: 'always' },
					}}

				/>
			</Page>
		</>
	);
}
