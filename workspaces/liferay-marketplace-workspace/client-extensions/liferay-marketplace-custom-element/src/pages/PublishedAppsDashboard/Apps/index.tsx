/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useNavigate, useOutletContext} from 'react-router-dom';

import {DashboardPage} from '../../../components/DashBoardPage/DashboardPage';
import PublishedAppsTable from './components/PublishedAppsTable';

const Apps = () => {
	const {catalogId, page, publishedProductTable, setPage} = useOutletContext<
		any
	>();
	const navigate = useNavigate();

	return (
		<DashboardPage
			buttonDisabled={!(catalogId && catalogId > 0)}
			buttonMessage="New App"
			messages={{
				description: 'Manage and publish apps on the Marketplace',
				title: 'Apps',
			}}
			onButtonClick={() => {
				navigate(`/app/create?catalogId=${catalogId}`);
			}}
		>
			<PublishedAppsTable items={publishedProductTable?.items ?? []} />

			{!!publishedProductTable.items.length && (
				<ClayPaginationBarWithBasicItems
					active={page}
					activeDelta={publishedProductTable.pageSize}
					defaultActive={1}
					ellipsisBuffer={3}
					ellipsisProps={{
						'aria-label': 'More',
						'title': 'More',
					}}
					onActiveChange={setPage}
					showDeltasDropDown={false}
					totalItems={publishedProductTable.totalCount}
				/>
			)}
		</DashboardPage>
	);
};

export default Apps;
