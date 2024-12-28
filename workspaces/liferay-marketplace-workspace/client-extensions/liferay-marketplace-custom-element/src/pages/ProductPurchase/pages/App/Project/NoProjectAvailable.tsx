/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ProductPurchase from '../../../../../components/ProductPurchase';
import i18n from '../../../../../i18n';
import {Liferay} from '../../../../../liferay/liferay';

const NoProjectAvailable = () => (
	<ProductPurchase.Shell
		className="d-flex flex-column py-4"
		footerProps={{
			backButtonProps: {className: 'd-none'},
			continueButtonProps: {
				children: i18n.translate('sign-in-with-a-different-account'),
				onClick: () => Liferay.Util.navigate('/c/portal/logout'),
			},
		}}
		title={i18n.translate('no-cloud-projects-available')}
	>
		You are attempting to Purchase a Cloud APP that is currently only
		available for Liferay SaaS and Liferay PaaS customers. You currently
		appear to not have access to any Cloud Projects. Please login as a user
		that has access to a project or contact your project administrator to
		add you to a project.
	</ProductPurchase.Shell>
);

export default NoProjectAvailable;
