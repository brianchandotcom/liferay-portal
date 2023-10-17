/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useState} from 'react';

import './index.scss';

import {useForm} from 'react-hook-form';

import StepWizard from '../GetAppPage/components/StepWizard/StepWizard';
import LicenseDetails from './LicenseDetails';
import SelectSubscription from './SelectSubscription';

export enum StepCreateLicense {
	LICENSE_KEY_DETAILS = 'licenseKeyDetails',
	SUBSCRIPTION = 'subscription',
}

export type CreateLicenseForm = {
	subscription: string;
};

const CreateLicense = () => {
	const [step, setStep] = useState<string>(StepCreateLicense.SUBSCRIPTION);

	const {setValue, watch} = useForm<CreateLicenseForm>({
		defaultValues: {
			subscription: undefined,
		},
	});

	const {subscription} = watch();

	const StepsInformation: any = {
		[StepCreateLicense.SUBSCRIPTION]: {
			backStep: StepCreateLicense.SUBSCRIPTION,
			component: (
				<SelectSubscription
					onSelectSubscription={(subscription: any) => {
						setValue('subscription', subscription);
					}}
					selectedSubscriptionValue={subscription}
				/>
			),
			nextStep: StepCreateLicense.LICENSE_KEY_DETAILS,
			stepTitle: 'Subscription',
			title: 'Subscription',
		},
		[StepCreateLicense.LICENSE_KEY_DETAILS]: {
			backStep: StepCreateLicense.SUBSCRIPTION,
			component: (
				<LicenseDetails expDate="01" keyType="02" startDate="03" />
			),
			nextStep: StepCreateLicense.SUBSCRIPTION,
			stepTitle: 'License Key Details',
			title: 'License Key Details',
		},
	};

	return (
		<div className="d-flex justify-content-center mb-7">
			<div className="d-flex flex-column justify-content-center mkt-create-license-content mt-7 p-6">
				<div className="align-self-center h1">
					Generate License Key(s)
				</div>

				<div className="d-flex justify-content-center mb-6 mt-6">
					<StepWizard
						className="col-8"
						currentStep={step}
						stepsInformation={StepsInformation}
						wizardSteps={{
							[StepCreateLicense.SUBSCRIPTION]:
								step !== StepCreateLicense.SUBSCRIPTION,
							[StepCreateLicense.LICENSE_KEY_DETAILS]: false,
						}}
					/>
				</div>

				<div>{StepsInformation[step].component}</div>

				<div className="d-flex justify-content-between mt-6">
					<ClayButton
						displayType="unstyled"
						onClick={() => {
							window.location.href = origin;
						}}
					>
						Cancel
					</ClayButton>
					{step === StepCreateLicense.SUBSCRIPTION ? (
						<ClayButton
							displayType="primary"
							onClick={() =>
								setStep(StepCreateLicense.LICENSE_KEY_DETAILS)
							}
						>
							Continue
						</ClayButton>
					) : (
						<div className="d-flex justify-content-end">
							<ClayButton
								className="mr-6"
								displayType="secondary"
								onClick={() =>
									setStep(StepCreateLicense.SUBSCRIPTION)
								}
							>
								Back
							</ClayButton>

							<ClayButton displayType="primary">
								Generate Key
							</ClayButton>
						</div>
					)}
				</div>
			</div>
		</div>
	);
};

export default CreateLicense;
