/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useNavigate} from 'react-router-dom';

import ProductPurchase from '../../../../../components/ProductPurchase';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';

const NoProjectAvailable = () => {
	const navigate = useNavigate();

	const {selectedAccount} = useProductPurchaseOutletContext();

	return (
		<ProductPurchase.Shell
			className="d-flex flex-column py-4"
			footerProps={{
				backButtonProps: {onClick: () => navigate('/')},
				continueButtonProps: {
					disabled: true,
				},
			}}
			title={`No projects available for ${selectedAccount.name}`}
		>
			It looks like this account doesn’t have any projects yet. Please
			check back later or contact your administrator to get access to
			projects.
			<p className="d-flex justify-content-center my-4 next-step-page-text-bold">
				Need help?&nbsp;{' '}
				<a href="mailto:support@liferay.com">support@liferay.com</a>
			</p>
		</ProductPurchase.Shell>
	);
};

export default NoProjectAvailable;
