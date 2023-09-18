/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormikHelpers} from 'formik';

import {PRMPageRoute} from '../../../common/enums/prmPageRoute';
import DealRegistration from '../../../common/interfaces/dealRegistration';
import {Liferay} from '../../../common/services/liferay';
import createDealRegistrationProxyAPI from './createDealRegistrationProxyAPI';

export default async function submitForm(
	values: DealRegistration,
	formikHelpers: Omit<FormikHelpers<DealRegistration>, 'setFieldValue'>,
	siteURL: string
) {
	formikHelpers.setSubmitting(true);
	formikHelpers.setStatus(true);

	try {
		await createDealRegistrationProxyAPI(values);

		Liferay.Util.navigate(
			`${siteURL}/${PRMPageRoute.DEAL_REGISTRATION_LISTING}`
		);

		Liferay.Util.openToast({
			message: 'Deal successfully registered!',
			title: 'Success',
			type: 'success',
		});
	}
	catch (error: unknown) {
		formikHelpers.setSubmitting(false);
		formikHelpers.setStatus(false);

		Liferay.Util.openToast({
			message: 'Deal can not be registered.',
			title: 'Error',
			type: 'danger',
		});
	}
}
