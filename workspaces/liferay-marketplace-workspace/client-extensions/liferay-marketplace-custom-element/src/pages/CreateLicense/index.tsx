/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useState} from 'react';

import './index.scss';

import ClaySticker from '@clayui/sticker';
import {useForm} from 'react-hook-form';

import emptyPictureIcon from '../../assets/icons/avatar.svg';
import ProductCard from '../GetAppPage/components/ProductCard/ProductCard';
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
	const [step, setStep] = useState<any>(StepCreateLicense.SUBSCRIPTION);

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

	const cartUtil: any = {
		cart: {
			id: undefined,
		},
	};

	const productCreatorAccount: any = {
		name: 'Joana',
	};

	const product: any = {
		attachments: [],
		name: {en_US: 'Test Joana Product'},
		productSpecifications: [],
		skus: [
			{
				sku: 'TESTFREEPRODUCTSKU',
				skuOptions: [],
			},
		],
	};

	const userAccount: any = {
		emailAddress: 'joana@liferay.com',
	};

	const AccountEmailInfo = () => (
		<div className="align-items-center d-flex">
			<div className="account-banner-name-text align-items-end d-flex flex-column m-2">
				<strong>{productCreatorAccount?.name}</strong>

				<div className="account-banner-email-text">
					{userAccount?.emailAddress}
				</div>
			</div>

			<ClaySticker shape="circle" size="sm">
				<ClaySticker.Image
					alt="placeholder"
					height="24"
					src={productCreatorAccount?.logoURL ?? emptyPictureIcon}
					width="24"
				/>
			</ClaySticker>
		</div>
	);

	const ExtendBanner = () => (
		<>
			<div className="align-items-center d-flex mb-3 row">
				<small className="col-6 col-md-4 font-weight-bold m-0">
					Key type
				</small>
				<small className="col-6 col-md-4 subscription-banner-text">
					Trial
				</small>
			</div>

			<div className="align-items-center d-flex row">
				<small className="col-6 col-md-4 font-weight-bold m-0">
					Start Date - Exp. Date
				</small>
				<small className="col-6 col-md-4 subscription-banner-text text-nowrap">
					Sep 24, 2023 &ndash; Oct 24, 2024
				</small>
			</div>
		</>
	);

	return (
		<div className="align-items-center d-flex flex-column">
			<div className="w-100">
				<ProductCard
					ExtendBanner={ExtendBanner}
					RightSideBanner={AccountEmailInfo}
					cartUtil={cartUtil}
					creatorAccount={productCreatorAccount}
					isSelectSubscription={true}
					product={product}
					showExtendBanner={
						step === StepCreateLicense.LICENSE_KEY_DETAILS
					}
					userAccount={userAccount}
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
