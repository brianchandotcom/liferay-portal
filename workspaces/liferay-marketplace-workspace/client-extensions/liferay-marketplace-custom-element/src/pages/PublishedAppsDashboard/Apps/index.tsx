/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useState} from 'react';
import {useNavigate, useOutletContext} from 'react-router-dom';

import appsIcon from '../../../assets/icons/apps_fill_icon.svg';
import {DashboardPage} from '../../../components/DashBoardPage/DashboardPage';
import {
	AppProps,
	DashboardTable,
} from '../../../components/DashboardTable/DashboardTable';
import {PublishedAppsDashboardTableRow} from '../../../components/DashboardTable/PublishedAppsDashboardTableRow';
import {appTableHeaders} from '../PublishedDashboardPageUtil';

const Apps = () => {
	const [page, setPage] = useState(1);

	const {catalogId, publishedAppTable} = useOutletContext<any>();
	const navigate = useNavigate();

	return (
		<DashboardPage
			buttonDisabled={!(catalogId && catalogId > 0)}
			buttonMessage={
				<>
					<ClayIcon className="mr-1" symbol="plus" />
					New App
				</>
			}
			messages={{
				description: 'Manage and publish apps on the Marketplace',
				title: 'Apps',
			}}
			onButtonClick={() => {
				navigate(`/app/create?catalogId=${catalogId}`);
			}}
		>
			<DashboardTable<AppProps>
				emptyStateMessage={{
					description1: 'Publish apps and they will show up here.',
					description2: 'Click on “New App” to start.',
					title: 'No Apps Yet',
				}}
				icon={appsIcon}
				items={publishedAppTable.items}
				tableHeaders={appTableHeaders}
			>
				{(item) => (
					<PublishedAppsDashboardTableRow
						item={item}
						key={item.name}
					/>
				)}
			</DashboardTable>

			{!!publishedAppTable.items.length && (
				<ClayPaginationBarWithBasicItems
					active={page}
					activeDelta={publishedAppTable.pageSize}
					defaultActive={1}
					ellipsisBuffer={3}
					ellipsisProps={{
						'aria-label': 'More',
						'title': 'More',
					}}
					onActiveChange={setPage}
					showDeltasDropDown={false}
					totalItems={publishedAppTable.totalCount}
				/>
			)}
		</DashboardPage>
	);
};

export default Apps;
