/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useEffect} from 'react';
import {useNavigate} from 'react-router-dom';

import AccountSelectionCheckout from '../../../components/Checkout/AccountSelection';
import i18n from '../../../i18n';
import {useOAuth2OutletContext} from '../OAuth2AuthorizeOutlet';

const AccountSelection = () => {
	const {myUserAccount, selectedAccount, setSelectedAccount, singleAccount} =
		useOAuth2OutletContext();

	const navigate = useNavigate();

	useEffect(() => {
		if (singleAccount) {
			navigate('/project-selection');
		}
	}, [singleAccount, navigate]);

	return (
		<div className="border mt-2 p-4 pt-2 rounded">
			<h1 className="align-items-center d-flex flex-column mt-2 p-2 pb-5">
				{i18n.translate('account-selection')}
			</h1>

			<p className="secondary-text">
				{i18n.translate(
					'please-select-the-account-you-wish-to-link-to-your-liferay-dxp-below'
				)}
			</p>

			<AccountSelectionCheckout
				checkPersonalAccount
				onSelectAccount={setSelectedAccount}
				selectedAccount={selectedAccount}
				showAccountsAvailableText={false}
				showContactSupport={false}
				userAccount={myUserAccount}
			/>

			<div className="d-flex justify-content-end mt-3">
				<ClayButton
					disabled={!selectedAccount}
					onClick={() => navigate('/project-selection')}
				>
					{i18n.translate('continue')}
				</ClayButton>
			</div>
		</div>
	);
};

export default AccountSelection;
