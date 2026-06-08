/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {SectionHeader} from '~/shared/components/SectionHeader';

import AccountIndividuals from './components/AccountIndividuals';
import AccountInfo, {IAccount} from './components/AccountInfo';
import LifecycleStatus from './components/LifecycleStatus';
import TopAssets from './components/TopAssets';
import TopCategoriesAndTags from './components/TopCategoriesAndTags';

interface IProfileProps {
	account?: IAccount;
	loading?: boolean;
}

const Profile: React.FC<IProfileProps> = ({account, loading}) => (
	<section>
		<SectionHeader
			icon="plus-squares"
			title={Liferay.Language.get('account-details')}
		/>

		<div className="account-profile-cards mb-3">
			<LifecycleStatus />
			<AccountInfo account={account} loading={loading} />
			<TopAssets />
			<TopCategoriesAndTags />
		</div>
		<AccountIndividuals />
	</section>
);

export default Profile;
