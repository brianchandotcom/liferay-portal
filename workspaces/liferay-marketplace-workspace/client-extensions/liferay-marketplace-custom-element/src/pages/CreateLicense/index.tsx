/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useState} from 'react';

import './index.scss';

import {useForm} from 'react-hook-form';

import ProductCard from '../GetAppPage/components/ProductCard/ProductCard';
import StepWizard from '../GetAppPage/components/StepWizard/StepWizard';
import AccountEmailInfo from './AccountEmail';
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
			component: <LicenseDetails />,
			nextStep: StepCreateLicense.SUBSCRIPTION,
			stepTitle: 'License Key Details',
			title: 'License Key Details',
		},
	};

	const subscriptionDataInfo: any = {
		licenseKeyData: {
			endDate: 'Oct 24, 2024',
			keyType: 'Trial',
			startDate: 'Sep 24, 2023',
		},
		product: {
			attachments: [],
			name: {en_US: 'Test Joana Product'},
			productSpecifications: [],
			skus: [
				{
					sku: 'TESTFREEPRODUCTSKU',
					skuOptions: [],
				},
			],
		},
		productCreatorAccount: {
			logoURL: null,
			name: 'Joana',
		},
	};

	const ExtendBanner = () => (
		<>
			<div className="align-items-center d-flex mb-3 row">
				<small className="col-6 col-md-4 font-weight-bold m-0">
					Key type
				</small>
				<small className="col-6 col-md-4 subscription-banner-text">
					{subscriptionDataInfo.licenseKeyData.keyType}
				</small>
			</div>

			<div className="align-items-center d-flex row">
				<small className="col-6 col-md-4 font-weight-bold m-0">
					Start Date - Exp. Date
				</small>
				<small className="col-6 col-md-4 subscription-banner-text text-nowrap">
					{subscriptionDataInfo.licenseKeyData.startDate} &ndash;{' '}
					{subscriptionDataInfo.licenseKeyData.endDate}
				</small>
			</div>
		</>
	);

	return (
		<div className="align-items-center d-flex flex-column">
			<div className="w-100">
				<ProductCard
					ExtendBanner={ExtendBanner}
					RightSideBanner={() => (
						<AccountEmailInfo
							productCreatorAccount={
								subscriptionDataInfo.productCreatorAccount
							}
							userAccount={subscriptionDataInfo.userAccount}
						/>
					)}
					creatorAccount={subscriptionDataInfo.productCreatorAccount}
					product={subscriptionDataInfo.product}
					showExtendBanner={
						step === StepCreateLicense.LICENSE_KEY_DETAILS
					}
				/>
			</div>

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
							disabled={!subscription}
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
