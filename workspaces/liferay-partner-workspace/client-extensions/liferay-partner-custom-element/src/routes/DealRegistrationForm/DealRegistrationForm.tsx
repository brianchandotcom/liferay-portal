/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormikHelpers, setNestedObjectValues} from 'formik';
import {useState} from 'react';

import PRMFormik from '../../common/components/PRMFormik';
import {PRMPageRoute} from '../../common/enums/prmPageRoute';
import useLiferayNavigate from '../../common/hooks/useLiferayNavigate';
import DealRegistration from '../../common/interfaces/dealRegistration';
import {Liferay} from '../../common/services/liferay';
import {Status} from '../../common/utils/constants/status';
import isObjectEmpty from '../../common/utils/isObjectEmpty';
import {StepType} from './enums/stepType';
import General from './steps/General';
import generalSchema from './steps/General/schema/yup';
import Review from './steps/Review';
import submitForm from './utils/submitForm';

const initialFormValues: DealRegistration = {
	additionalContact: {emailAddress: '', firstName: '', lastName: ''},
	additionalInformationAboutTheOpportunity: '',
	currency: {},
	leadStatusDetails: Status.EXPRESSED_INTEREST.name,
	mdfActivityAssociated: {},
	partnerAccount: {},
	partnerFirstName: '',
	partnerLastName: '',
	primaryProspect: {
		businessUnit: '',
		department: {},
		emailAddress: '',
		firstName: '',
		jobRole: {},
		lastName: '',
		phone: '',
	},
	projectCategories: [],
	projectNeed: [],
	projectTimeline: '',
	prospect: {
		accountName: '',
		address: '',
		city: '',
		country: {},
		industry: {},
		postalCode: '',
		state: {},
	},
};

type StepComponent = {
	[key in StepType]?: JSX.Element;
};

const DealRegistrationForm = () => {
	const [step, setStep] = useState<StepType>(StepType.GENERAL);
	const siteURL = useLiferayNavigate();

	const onCancel = () => {
		Liferay.Util.navigate(
			`${siteURL}/${PRMPageRoute.DEAL_REGISTRATION_LISTING}`
		);
	};

	const onContinue = async (
		formikHelpers: Omit<FormikHelpers<DealRegistration>, 'setFieldValue'>,
		nextStep: StepType
	) => {
		const validationErrors = await formikHelpers.validateForm();

		if (isObjectEmpty(validationErrors)) {
			setStep(nextStep);

			return;
		}

		formikHelpers.setTouched(setNestedObjectValues(validationErrors, true));
	};

	const onPrevious = (previousStep: StepType) => setStep(previousStep);

	const StepFormComponent: StepComponent = {
		[StepType.GENERAL]: (
			<General
				onCancel={onCancel}
				onContinue={onContinue}
				validationSchema={generalSchema}
			/>
		),
		[StepType.REVIEW]: (
			<Review onCancel={onCancel} onPrevious={onPrevious} />
		),
	};

	return (
		<PRMFormik
			initialValues={initialFormValues}
			onSubmit={(values, formikHelpers) =>
				submitForm(values, formikHelpers, siteURL)
			}
		>
			{StepFormComponent[step]}
		</PRMFormik>
	);
};

export default DealRegistrationForm;
