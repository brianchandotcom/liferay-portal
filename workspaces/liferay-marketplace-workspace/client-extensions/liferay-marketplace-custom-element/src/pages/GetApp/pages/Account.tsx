/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useOutletContext} from 'react-router-dom';

import AccountSelection from '../../../components/Checkout/AccountSelection';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import i18n from '../../../i18n';
import {useGetAppContext} from '../GetAppContextProvider';
import {GetAppOutletContext} from '../GetAppOutlet';
import Container from '../containers/Container';
import LicenseTermsCheckbox from '../containers/LicenseTermsCheckbox';
import getProductPriceModel from '../utils/getProductPriceModel';

const GetAppPage = () => {
	const [
		{
			account,
			formState: {isValid},
			isCloudApp,
			product,
			stepState,
		},
		dispatch,
	] = useGetAppContext();
	const {handleGetApp, loading} = useOutletContext<GetAppOutletContext>();
	const {isFreeApp} = getProductPriceModel(product);
	const {myUserAccount} = useMarketplaceContext();

	const isFreeDXPApp = isFreeApp && !isCloudApp;

	return (
		<Container
			className="d-flex flex-column"
			footerProps={{
				primaryButtonProps: {
					children: i18n.translate(
						isFreeDXPApp ? 'get-app' : 'continue'
					),
					disabled: !isValid || loading,
					onClick: () => {
						if (isFreeDXPApp) {
							return handleGetApp();
						}

						stepState.onNext();
					},
				},
				secondaryButtonProps: {visible: false},
			}}
			title="Account Selection"
		>
			<AccountSelection
				checkPersonalAccount
				enabledAccountRoles={['Account Administrator', 'Account Buyer']}
				onSelectAccount={(account: Account) =>
					dispatch({payload: account, type: 'SET_ACCOUNT'})
				}
				selectedAccount={account}
				showContactSupport={!isFreeApp}
				userAccount={myUserAccount}
			>
				{isFreeApp && <LicenseTermsCheckbox />}
			</AccountSelection>
		</Container>
	);
};

export default GetAppPage;
