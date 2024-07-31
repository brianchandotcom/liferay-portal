/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getIconUrl} from '../../GetApp/utils/getIcon';
import AccountEmailInfo from '../pages/Apps/App/Licenses/CreateLicense/AccountInfo';
import OrderDetailsHeader from './OrderDetailsHeader';

type LicenseDetailsModalHeaderProps = {
	modalData: any;
	myUserAccount: UserAccount;
	product: DeliveryProduct;
};

const LicenseDetailsModalHeader: React.FC<LicenseDetailsModalHeaderProps> = ({
	modalData,
	myUserAccount,
	product,
}) => (
	<div className="d-flex flex-row justify-content-between">
		<div className="flex-row mb-1">
			<div className="font-weight-bold h6 text-primary text-uppercase">
				Activation Key details
			</div>

			<OrderDetailsHeader
				className="d-flex flex-row justify-content-between mt-3"
				hasOrderDescription={modalData?.description}
				image={getIconUrl(product)}
				name={product?.name}
				version={modalData?.productVersion}
			/>
		</div>

		<AccountEmailInfo userAccount={myUserAccount} />
	</div>
);

export default LicenseDetailsModalHeader;
