/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../../../../../../../i18n';

const ContactSupport = () => (
	<p className="secondary-text">
		{`${i18n.translate('not-seeing-a-specific-project')} `}
		<a
			className="font-weight-bold project-selection-page-link"
			href="https://help.liferay.com/hc/requests/new"
			target="_blank"
		>
			{i18n.translate('contact-support')}
		</a>
	</p>
);

export default ContactSupport;
