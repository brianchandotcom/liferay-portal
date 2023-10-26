/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import './index.scss';

import {useForm} from 'react-hook-form';
import {useNavigate, useParams} from 'react-router-dom';

import FooterButtons from '../../components/FooterButtons';
import {useMarketplaceContext} from '../../context/MarketplaceContext';
import {Liferay} from '../../liferay/liferay';
import zodSchema, {zodResolver} from '../../schema/zod';
import ProvisioningKoroneikiOAuth2 from '../../services/oauth/ProvisioningKoroneikiOAuth2';
import ProductCard from '../GetAppPage/components/ProductCard/ProductCard';
import StepWizard from '../GetAppPage/components/StepWizard/StepWizard';
import useGetProductById from '../GetAppPage/hooks/useGetProductById';
import useGetProductCreatorAccount from '../GetAppPage/hooks/useGetProductCreatorAccount';
import AccountEmailInfo from './AccountInfo';
import LicenseDetails from './LicenseDetails';
import SelectSubscription from './SelectSubscription';
import {
	CreateLicenseForm,
	LicenseKeyProps,
	StepCreateLicense,
	StepsInformation,
} from './Types';

const CreateLicense = () => {
	const [step, setStep] = useState<string>(StepCreateLicense.SUBSCRIPTION);
	const {myUserAccount} = useMarketplaceContext();

	const navigate = useNavigate();
	const params = useParams();
	const productId = String(params.appId);
	const {product} = useGetProductById('attachments', productId);

	const productCreatorAccount = useGetProductCreatorAccount(product);

	const LicenseKeyInfo: LicenseKeyProps = {
		licenseKeyData: {
			endDate: 'Oct 24, 2024',
			keyType: 'Trial',
			startDate: 'Sep 24, 2023',
		},
	};

	const {
		formState: {errors, isSubmitting},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<CreateLicenseForm>({
		defaultValues: {
			IP: '',
			description: '',
			hostName: '',
			macAddresses: '',
			subscription: undefined,
		},
		mode: 'all',
		resolver: zodResolver(zodSchema.generateLicenseKey),
	});

	useEffect(() => {
		if (product) {
			const {familyName, givenName} = myUserAccount;
			setValue(
				'description',
				`${givenName} ${familyName} - ${product?.name.en_US} - ${LicenseKeyInfo?.licenseKeyData.keyType}`
			);
		}
	}, [
		LicenseKeyInfo?.licenseKeyData.keyType,
		myUserAccount,
		product,
		setValue,
	]);

	const {IP, description, hostName, macAddresses, subscription} = watch();

	const disableGenerateButton =
		(IP === '' && hostName === '' && macAddresses === '') ||
		description === '';

	const inputProps = {
		errors,
		register,
		required: true,
	};

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
			component: <LicenseDetails inputProps={inputProps} />,
			nextStep: StepCreateLicense.SUBSCRIPTION,
			stepTitle: 'License Key Details',
			title: 'License Key Details',
		},
	};

	const ExtendBanner = () => (
		<>
			<div className="align-items-center d-flex mb-3 row">
				<small className="col-6 col-md-4 font-weight-bold m-0">
					Key type
				</small>
				<small className="col-6 col-md-4 subscription-banner-text">
					{LicenseKeyInfo.licenseKeyData.keyType}
				</small>
			</div>

			<div className="align-items-center d-flex row">
				<small className="col-6 col-md-4 font-weight-bold m-0">
					Start Date - Exp. Date
				</small>
				<small className="col-6 col-md-4 subscription-banner-text text-nowrap">
					{LicenseKeyInfo.licenseKeyData.startDate} &ndash;{' '}
					{LicenseKeyInfo.licenseKeyData.endDate}
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
			disabled:
				isSubmitting ||
				(!subscription && step === StepCreateLicense.SUBSCRIPTION) ||
				(disableGenerateButton &&
					step !== StepCreateLicense.SUBSCRIPTION),
			displayType: 'primary',
			show: true,
			text: 'Generate Key',
		},
	};

	const handleNextButton = async (form: CreateLicenseForm) => {
		if (step === StepCreateLicense.SUBSCRIPTION) {
			setStep(StepCreateLicense.LICENSE_KEY_DETAILS);
		}

		if (step === StepCreateLicense.LICENSE_KEY_DETAILS) {
			const licenseKey = await ProvisioningKoroneikiOAuth2.createLicenseKey(
				form
			);

			licenseKey.id = 12345;

			const downloadedKey = await ProvisioningKoroneikiOAuth2.downloadLicenseKey(
				licenseKey.id
			);

			navigate('/');

			Liferay.Util.openToast({
				message: `License Key created successfully: ${downloadedKey}`,
				type: 'success',
			});
		}
	};

	return (
		<div className="align-items-center d-flex flex-column mb-6 mkt-create-license mt-6">
			<div className="mt-6 product-card-content">
				<ProductCard
					ExtendBanner={ExtendBanner}
					RightSideBanner={() => (
						<AccountEmailInfo userAccount={myUserAccount} />
					)}
					creatorAccount={productCreatorAccount as Account}
					product={product as Product}
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
					onClickNext={handleSubmit(handleNextButton)}
				/>
			</div>
		</div>
	);
};

export default CreateLicense;
