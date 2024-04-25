/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Dispatch, useLayoutEffect} from 'react';
import {useNavigate, useOutletContext} from 'react-router-dom';

import AccountSelection from '../../../components/Checkout/AccountSelection';
import {getSiteURL} from '../../../components/InviteMemberModal/services';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';

const GetSolutionAccount = () => {
	const {accountSelected, accounts, setSelectedAccount} = useOutletContext<{
		accountForm: any;
		accountSelected?: Account;
		accounts: Account[];
		setAccounts: Dispatch<Account[]>;
		setSelectedAccount: Dispatch<Account>;
	}>();
	const {myUserAccount} = useMarketplaceContext();
	const navigate = useNavigate();

	const accountsCount = accounts.length;

	useLayoutEffect(() => {
		if (accountsCount === 1) {
			navigate('/form', {replace: true});
		}
	}, [accountsCount, navigate]);

	const handleNextStep = () => navigate('form');

	return (
		<div>
			<h1 className="my-4 text-center">Account Selection</h1>

			<AccountSelection
				onSelectAccount={setSelectedAccount}
				selectedAccount={accountSelected}
				userAccount={myUserAccount}
			/>

			<div className="align-items-center d-flex justify-content-between mt-6 w-100">
				<ClayButton
					className="font-weight-bold"
					displayType="unstyled"
					onClick={() =>
						Liferay.Util.navigate(
							`${Liferay.ThemeDisplay.getPortalURL()}${getSiteURL()}/solutions-marketplace`
						)
					}
				>
					{i18n.translate('cancel')}
				</ClayButton>

				<ClayButton
					disabled={!accountSelected}
					onClick={handleNextStep}
				>
					{i18n.translate('continue')}
				</ClayButton>
			</div>
		</div>
	);
};

export default GetSolutionAccount;
