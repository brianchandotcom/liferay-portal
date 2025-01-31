/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '~/utils/I18n';

import './BusinessEvents.css';

const BusinessEvents = () => {
	return (
		<>
			<h1>{i18n.translate('business-events')}</h1>
		</>
	);
};

export default BusinessEvents;
