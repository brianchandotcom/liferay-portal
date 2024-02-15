/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useOutletContext} from 'react-router-dom';

import {DashboardPage} from '../../../components/DashBoardPage/DashboardPage';
import {getSiteURL} from '../../../components/InviteMemberModal/services';
import {Liferay} from '../../../liferay/liferay';
import PurchasedAppsTable from './components/PurchasedAppsTable';

const Apps = () => {
	const {page, purchasedAppTable, setPage} = useOutletContext<any>();

	return (
		<DashboardPage
			buttonMessage="Add Apps"
			messages={{
				description: 'Manage apps purchase from the Marketplace',
				title: 'My Apps',
			}}
			onButtonClick={() => {
				Liferay.Util.navigate(getSiteURL() || '/');
			}}
		>
			<PurchasedAppsTable items={purchasedAppTable.items ?? []} />

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
