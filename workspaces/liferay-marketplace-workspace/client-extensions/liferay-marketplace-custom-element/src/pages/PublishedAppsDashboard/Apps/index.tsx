/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useState} from 'react';
import {useOutletContext} from 'react-router-dom';

import appsIcon from '../../../assets/icons/apps_fill_icon.svg';
import {
	AppProps,
	DashboardTable,
} from '../../../components/DashboardTable/DashboardTable';
import {PublishedAppsDashboardTableRow} from '../../../components/DashboardTable/PublishedAppsDashboardTableRow';
import {getSiteURL} from '../../../components/InviteMemberModal/services';
import {DashboardPage} from '../../DashBoardPage/DashboardPage';
import {appTableHeaders} from '../PublishedDashboardPageUtil';

const appMessages = {
	description: 'Manage and publish apps on the Marketplace',
	emptyStateMessage: {
		description1: 'Publish apps and they will show up here.',
		description2: 'Click on “New App” to start.',
		title: 'No Apps Yet',
	},
	title: 'Apps',
};

const Apps = () => {
	const [page, setPage] = useState(1);

	const {catalogId, publishedAppTable} = useOutletContext<any>();

	return (
		<DashboardPage
			buttonMessage={
				<>
					<ClayIcon className="mr-1" symbol="plus" />
					New App
				</>
			}
			messages={appMessages}
			onButtonClick={() => {
				window.location.href =
					getSiteURL() + `/create-new-app?catalogId=${catalogId}`;
			}}
		>
			<DashboardTable<AppProps>
				emptyStateMessage={appMessages.emptyStateMessage}
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
