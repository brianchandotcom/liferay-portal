/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useOutletContext} from 'react-router-dom';

import appsIcon from '../../../assets/icons/apps_fill_icon.svg';
import {DashboardTable} from '../../../components/DashboardTable/DashboardTable';
import {PurchasedAppsDashboardTableRow} from '../../../components/DashboardTable/PurchasedAppsDashboardTableRow';
import {getSiteURL} from '../../../components/InviteMemberModal/services';
import {DashboardPage} from '../../DashBoardPage/DashboardPage';
import {PurchasedAppProps} from '../PurchasedAppsDashboardOutlet';
import {tableHeaders} from '../PurchasedDashboardPageUtil';

const appMessages = {
	description: 'Manage apps purchase from the Marketplace',
	title: 'My Apps',
};

const Apps = () => {
	const {page, purchasedAppTable, setPage} = useOutletContext<any>();

	return (
		<DashboardPage
			buttonMessage="Add Apps"
			messages={appMessages}
			onButtonClick={() => {
				window.location.href = getSiteURL();
			}}
		>
			<DashboardTable<PurchasedAppProps>
				emptyStateMessage={{
					description1:
						'Purchase and install new apps and they will show up here.',
					description2: 'Click on “Add Apps” to start.',
					title: 'No Apps Yet',
				}}
				icon={appsIcon}
				items={purchasedAppTable.items}
				tableHeaders={tableHeaders}
			>
				{(item) => (
					<PurchasedAppsDashboardTableRow
						item={item}
						key={item.name}
					/>
				)}
			</DashboardTable>

			{!!purchasedAppTable.items.length && (
				<ClayPaginationBarWithBasicItems
					active={page}
					activeDelta={purchasedAppTable.pageSize}
					defaultActive={1}
					ellipsisBuffer={3}
					ellipsisProps={{
						'aria-label': 'More',
						'title': 'More',
					}}
					onActiveChange={setPage}
					showDeltasDropDown={false}
					totalItems={purchasedAppTable?.totalCount}
				/>
			)}
		</DashboardPage>
	);
};

export default Apps;
