/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useState} from 'react';

import './index.scss';

import {useForm} from 'react-hook-form';

import FooterButtons from '../../components/FooterButtons';
import {Liferay} from '../../liferay/liferay';
import ProductCard from '../GetAppPage/components/ProductCard/ProductCard';
import StepWizard from '../GetAppPage/components/StepWizard/StepWizard';
import AccountEmailInfo from './AccountInfo';
import LicenseDetails from './LicenseDetails';
import SelectSubscription from './SelectSubscription';
import {
	CreateLicenseForm,
	ProductCardProps,
	StepCreateLicense,
	StepsInformation,
} from './Types';

const CreateLicense = () => {
	const [step, setStep] = useState<string>(StepCreateLicense.SUBSCRIPTION);

	const {setValue, watch} = useForm<CreateLicenseForm>({
		defaultValues: {
			subscription: undefined,
		},
	});

	const {subscription} = watch();

	const stepsInformation: StepsInformation = {
		[StepCreateLicense.SUBSCRIPTION]: {
			backStep: StepCreateLicense.SUBSCRIPTION,
			component: (
				<SelectSubscription
					onSelectSubscription={(subscription: string) => {
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

	const ProductCardInfo: ProductCardProps = {
		licenseKeyData: {
			endDate: 'Oct 24, 2024',
			keyType: 'Trial',
			startDate: 'Sep 24, 2023',
		},
		product: {
			attachments: [],
			name: {en_US: 'Test Product'},
			productSpecifications: [],
			skus: [
				{
					price: 0,
					sku: 'TESTFREEPRODUCTSKU',
					skuOptions: [],
				},
			],
		},
		productCreatorAccount: {
			logoURL: undefined,
			name: 'Test Name',
		},
		userAccount: {
			emailAddress: 'test@liferay.com',
		},
	};

	const ExtendBanner = () => (
		<>
			<div className="align-items-center d-flex mb-3 row">
				<small className="col-6 col-md-4 font-weight-bold m-0">
					Key type
				</small>
				<small className="col-6 col-md-4 subscription-banner-text">
					{ProductCardInfo.licenseKeyData.keyType}
				</small>
			</div>

			<div className="align-items-center d-flex row">
				<small className="col-6 col-md-4 font-weight-bold m-0">
					Start Date - Exp. Date
				</small>
				<small className="col-6 col-md-4 subscription-banner-text text-nowrap">
					{ProductCardInfo.licenseKeyData.startDate} &ndash;{' '}
					{ProductCardInfo.licenseKeyData.endDate}
				</small>
			</div>
		</>
	);

	const ButtonsInfo = {
		cancelButton: {
			displayType: 'unstyled',
			show: true,
		},
		customizedButton: {
			displayType: 'secondary',
			show: step !== StepCreateLicense.SUBSCRIPTION,
			text: 'Back',
		},
		nextButton: {
			className: 'ml-6',
			disabled: !subscription,
			displayType: 'primary',
			show: true,
			text: 'Generate Key',
		},
	};

	return (
		<div className="align-items-center d-flex flex-column mb-6 mkt-create-license mt-6">
			<div className="mt-6 product-card-content">
				<ProductCard
					ExtendBanner={ExtendBanner}
					RightSideBanner={() => (
						<AccountEmailInfo
							productCreatorAccount={
								ProductCardInfo.productCreatorAccount
							}
							userAccount={ProductCardInfo.userAccount}
						/>
					)}
					creatorAccount={
						ProductCardInfo.productCreatorAccount as Account
					}
					product={(ProductCardInfo.product as any) as Product}
					showExtendBanner={
						step === StepCreateLicense.LICENSE_KEY_DETAILS
					}
				/>
			</div>

			<div className="d-flex flex-column generate-license-content justify-content-center mb-7 mt-7 p-6">
				<div className="align-self-center h1">
					Generate License Key(s)
				</div>

				<div className="d-flex justify-content-center mb-6 mt-6">
					<StepWizard
						className="col-8"
						currentStep={step}
						stepsInformation={stepsInformation}
						wizardSteps={{
							[StepCreateLicense.SUBSCRIPTION]:
								step !== StepCreateLicense.SUBSCRIPTION,
							[StepCreateLicense.LICENSE_KEY_DETAILS]: false,
						}}
					/>
				</div>

				<div>
					{stepsInformation[step as keyof StepsInformation].component}
				</div>

				<FooterButtons
					className="d-flex justify-content-between mt-6"
					dataButtons={ButtonsInfo}
					onClickCancel={() => {
						window.location.href = Liferay.ThemeDisplay.getCanonicalURL();
					}}
					onClickCustomizedButton={() =>
						setStep(StepCreateLicense.SUBSCRIPTION)
					}
					onClickNext={() =>
						setStep(StepCreateLicense.LICENSE_KEY_DETAILS)
					}
				/>
			</div>
		</div>
	);
};

export default CreateLicense;
