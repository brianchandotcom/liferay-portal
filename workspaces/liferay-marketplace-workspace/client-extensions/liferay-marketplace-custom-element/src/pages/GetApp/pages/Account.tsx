/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import {useGetAppContext} from '../GetAppContextProvider';
import AccountSelection from '../components/AccountSelection';
import {PaymentMethod} from '../enums/paymentMethod';
import getProductPriceModel from '../utils/getProductPriceModel';

export type GetAppForm = {
	account?: Account;
	product?: DeliveryProduct;
	project: string;
	selectedPaymentMethod: PaymentMethod;
	selectedSKU?: DeliverySKU;
	selectedTimeline?: string;
	userAccount?: UserAccount;
};

const GetAppPage = () => {
	const [{account, product}, dispatch] = useGetAppContext();
	const {isFreeApp} = getProductPriceModel(product);
	const {myUserAccount} = useMarketplaceContext();

	return (
		<div className="d-flex flex-column">
			<h1 className="my-4 text-center">Account Selection</h1>

			<div className="mt-2">
				<AccountSelection
					isFreeApp={isFreeApp}
					onSelectAccount={(account: Account) =>
						dispatch({payload: account, type: 'SET_ACCOUNT'})
					}
					selectedAccount={account}
					userAccount={myUserAccount}
				/>
			</div>
		</div>
	);
};

export default GetAppPage;
