/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Form from '../../../../../../components/MarketplaceForm';
import i18n from '../../../../../../i18n';

const ContactUs = () => {
	return (
		<div>
			<h3>{i18n.translate('contact-us')}</h3>

			<hr />

			<Form.Label className="mt-3" htmlFor="email" required>
				Email
			</Form.Label>

			<Form.Input
				name="email"
				placeholder="name@yourdomain.com"
				type="name@yourdomain.com"
			/>
		</div>
	);
};

export default ContactUs;
