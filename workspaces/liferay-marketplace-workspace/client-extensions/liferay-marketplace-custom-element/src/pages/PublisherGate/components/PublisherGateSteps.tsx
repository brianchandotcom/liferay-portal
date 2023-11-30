/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';
import {useForm} from 'react-hook-form';
import {z} from 'zod';

import zodSchema, {zodResolver} from '../../../schema/zod';
import fetcher from '../../../services/fetcher';
import PublisherGateForm from './PublisherGateForm';
import PublisherGateSummary from './PublisherGateSummary';
import PubliserhRequestedCard from './PublisherRequestedCard';

export type PublisherForm = z.infer<typeof zodSchema.becomePublisherForm>;

export enum StepType {
	FORM = 'form',
	SUMMARY = 'summary',
	REQUESTED = 'requested',
}

const PublisherGateSteps = () => {
	const [step, setStep] = useState<StepType>(StepType.SUMMARY);
	const form = useForm<PublisherForm>({
		defaultValues: {
			emailAddress: '',
			extension: '',
			firstName: '',
			lastName: '',
			phone: {
				code: '+1',
				flag: 'en-us',
			},
			phoneNumber: '',
			requestDescription: '',
		},
		mode: 'all',
		resolver: zodResolver(zodSchema.becomePublisherForm),
	});

	const submit = async (form: PublisherForm) => {
		const formData = {...form, intlCode: form?.phone?.code};

		delete formData.phone;

		try {
			await fetcher.post('o/c/requestpublisheraccounts/', formData, {});

			setStep(StepType.REQUESTED);
		}
		catch (error) {
			console.error(error);
		}
	};

	const StepsAccount = {
		[StepType.FORM]: {
			component: <PublisherGateForm form={form} setStep={setStep} />,
		},
		[StepType.SUMMARY]: {
			component: (
				<PublisherGateSummary
					form={form}
					setStep={setStep}
					submit={submit}
				/>
			),
		},
		[StepType.REQUESTED]: {
			component: <PubliserhRequestedCard />,
		},
	};

	return StepsAccount[step].component;
};

export default PublisherGateSteps;
