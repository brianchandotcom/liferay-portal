/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useOutletContext} from 'react-router-dom';

import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import i18n from '../../../i18n';
import {useGetAppContext} from '../GetAppContextProvider';
import {GetAppOutletContext} from '../GetAppOutlet';
import AccountSelection from '../components/AccountSelection';
import Container from '../containers/Container';
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
				isFreeApp={isFreeApp}
				onSelectAccount={(account: Account) =>
					dispatch({payload: account, type: 'SET_ACCOUNT'})
				}
				selectedAccount={account}
				userAccount={myUserAccount}
			/>
		</Container>
	);
};

export default GetAppPage;
