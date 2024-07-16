/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useCurrentKoroneikiAccount from '../../../../../common/hooks/useCurrentKoroneikiAccount';
import SubscriptionsOverview from './components/SubscriptionsOverview';
import SupportOverview from './components/SupportOverview/';

const Overview = () => {
	const {data, loading} = useCurrentKoroneikiAccount();
	const koroneikiAccount = data?.koroneikiAccountByExternalReferenceCode;

	return (
		<>
			<SupportOverview
				koroneikiAccount={koroneikiAccount}
				loading={loading}
			/>

			<SubscriptionsOverview
				koroneikiAccount={koroneikiAccount}
				loading={loading}
			/>
		</>
	);
};

export default Overview;
