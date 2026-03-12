/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import AlertBox from '../../../../../components/AlertBox';
import ProductPurchase from '../../../../../components/ProductPurchase';
import i18n from '../../../../../i18n';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';

const NoProjectAvailable = () => {
	const {
		actions: {previousStep},
		selectedAccount,
	} = useProductPurchaseOutletContext();

	return (
		<ProductPurchase.Shell
			className="d-flex flex-column"
			footerProps={{
				backButtonProps: {onClick: previousStep},
				continueButtonProps: {
					disabled: true,
				},
			}}
			title={i18n.translate('project-selection')}
		>
			<div className="align-items-center d-flex flex-column justify-content-center text-center">
				<AlertBox className="mb-4" />

				<h2>
					{i18n.sub(
						'no-projects-available-for-x',
						selectedAccount.name
					)}
				</h2>

				<p className="px-2">
					It looks like this account does not have any projects yet.
					Please check back later or contact your administrator to get
					access to projects.
					<p className="d-flex justify-content-center my-4 next-step-page-text-bold">
						Need help?&nbsp;{' '}
						<a href="mailto:support@liferay.com">
							support@liferay.com
						</a>
					</p>
				</p>
			</div>
		</ProductPurchase.Shell>
	);
};

export default NoProjectAvailable;
