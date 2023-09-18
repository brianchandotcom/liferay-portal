/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormikHelpers} from 'formik';

import MDFRequest from '../../../common/interfaces/mdfRequest';
import {StepType} from '../enums/stepType';

export default interface MDFRequestStepProps {
	disableCompany?: boolean;
	onCancel: () => void;
	onContinue?: (
		formikHelpers: Omit<FormikHelpers<MDFRequest>, 'setFieldValue'>,
		nextStep: StepType
	) => void;
	onPrevious?: (previousStep: StepType) => void;
	onSaveAsDraft?: (
		values: MDFRequest,
		formikHelpers: Omit<FormikHelpers<MDFRequest>, 'setFieldValue'>
	) => void;
}
