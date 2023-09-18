/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormikHelpers} from 'formik';

import DealRegistration from '../../../common/interfaces/dealRegistration';
import {StepType} from '../enums/stepType';

export default interface DealRegistrationStepProps {
	onCancel: () => void;
	onContinue?: (
		formikHelpers: Omit<FormikHelpers<DealRegistration>, 'setFieldValue'>,
		nextStep: StepType
	) => void;
	onPrevious?: (previousStep: StepType) => void;
	onSaveAsDraft?: (
		values: DealRegistration,
		formikHelpers: Omit<FormikHelpers<DealRegistration>, 'setFieldValue'>
	) => void;

	onSubmit?: (
		values: DealRegistration,
		formikHelpers: Omit<FormikHelpers<DealRegistration>, 'setFieldValue'>
	) => void;
}
