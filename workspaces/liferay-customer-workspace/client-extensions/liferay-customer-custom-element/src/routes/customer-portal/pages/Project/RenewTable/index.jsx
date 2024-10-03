/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';
import {useGetMyUserAccount} from '~/common/services/liferay/graphql/user-accounts';
import RenewTableFooter from '~/routes/customer-portal/containers/ActivationKeysTable/components/RenewTableFooter';
import {hasAdminUserAccount} from '~/routes/customer-portal/containers/ActivationKeysTable/utils/hasAdminUserAccount';
import ActivationKeysTable from '../../../containers/ActivationKeysTable';
import {useCustomerPortal} from '../../../context';

const RenewTable = ({hasComplimentaryKey, isDXPTable, isRenewTable}) => {
	const productName = isDXPTable ? 'DXP' : 'Portal';

	const [{project, oAuthToken}] = useCustomerPortal();
	const {data: myAccount} = useGetMyUserAccount();

	const isAdminUserAccount = hasAdminUserAccount(myAccount);

	const [keysSelectedCount, setKeysSelectedCount] = useState('');
	const [activationKeysChecked, setActivationKeysChecked] = useState('');
	const [renewKeysFilterChecked, setRenewKeysFilterChecked] = useState('');

	const initialFilter = isDXPTable
		? "(startswith(productName,'DXP') or startswith(productName,'Digital'))"
		: "startswith(productName,'Portal')";

	return (
		<div className="container renew-table">
			<ActivationKeysTable
				hasComplimentaryKey={hasComplimentaryKey}
				initialFilter={initialFilter}
				isRenewTable={isRenewTable}
				productName={productName}
				project={project}
				oAuthToken={oAuthToken}
				setActivationKeysChecked={setActivationKeysChecked}
				setKeysSelectedCount={setKeysSelectedCount}
				setRenewKeysFilterChecked={setRenewKeysFilterChecked}
			/>

			<RenewTableFooter
				activationKeysChecked={activationKeysChecked}
				isAdminUserAccount={isAdminUserAccount}
				isRenewTable={isRenewTable}
				keysSelectedCount={keysSelectedCount}
				productName={productName}
				project={project}
				renewKeysFilterChecked={renewKeysFilterChecked}
			/>
		</div>
	);
};

export default RenewTable;
