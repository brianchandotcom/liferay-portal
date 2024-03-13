/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useLayoutEffect} from 'react';
import {useOutletContext} from 'react-router-dom';

import AccountForm from '../components/AccountForm';

const GetSolutionForm = () => {
	const {accountForm, navigate, onSubmit} = useOutletContext<any>();

	useLayoutEffect(() => {
		if (accountForm.accountQuantity > 1 && !accountForm.accountSelected) {
			navigate('/', {replace: true});
		}
	}, [accountForm.accountQuantity, accountForm.accountSelected, navigate]);

	return <AccountForm accountForm={accountForm} submitOrder={onSubmit} />;
};

export default GetSolutionForm;
