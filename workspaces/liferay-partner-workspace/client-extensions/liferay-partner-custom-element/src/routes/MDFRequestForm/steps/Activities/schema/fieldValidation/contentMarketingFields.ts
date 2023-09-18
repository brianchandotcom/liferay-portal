/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {string} from 'yup';

const getContentMarketingFieldsValidation = () => {
	const basicEventFields = {
		gatedLandingPage: string()
			.trim()
			.max(255, 'You have exceeded the character limit')
			.required('Required'),
		goalOfContent: string()
			.trim()
			.max(255, 'You have exceeded the character limit')
			.required('Required'),
		hiringOutsideWriterOrAgency: string().required('Required'),
		primaryThemeOrMessage: string()
			.trim()
			.max(255, 'You have exceeded the character limit')
			.required('Required'),
	};

	return basicEventFields;
};

export default getContentMarketingFieldsValidation;
