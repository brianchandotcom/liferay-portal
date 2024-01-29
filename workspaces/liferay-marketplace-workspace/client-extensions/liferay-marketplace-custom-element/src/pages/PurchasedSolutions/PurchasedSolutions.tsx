/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import {useEffect, useState} from 'react';

import {
	getOrderTypes,
	getProductById,
	getProductSKU,
	getProductSpecifications,
	postOrder,
} from '../../utils/api';

import './PurchasedSolutions.scss';

import ClaySticker from '@clayui/sticker';

import emptyPictureIcon from '../../assets/icons/avatar.svg';
import {useMarketplaceContext} from '../../context/MarketplaceContext';
import {Liferay} from '../../liferay/liferay';
import StepWizard from '../GetApp/components/StepWizard/StepWizard';
import AccountForm from './AccountForm';
import CreatedProjectCard from './CreatedProjectCard';
import PurchasedSolutionsAccountSelection from './PurchasedSolutionsAccountSelection';
import useAccountForm from './hooks/useAccountForm';

const productCustomFields = [
	'Github Username',
	'Project Name',
	'Site Initializer',
];

export enum StepType {
	ACCOUNT = 'account',
	FORM = 'form',
	CHECKOUT = 'checkout',
}

const PurchasedSolutions: React.FC = () => {
	const queryString = window.location.search;
	const {channel} = useMarketplaceContext();
	const urlParams = new URLSearchParams(queryString);
	const productId = Number(urlParams.get('productId')) + 1;

	const [step, setStep] = useState<StepType>(StepType.FORM);
	const [product, setProduct] = useState<Product>();
	const [sku, setSku] = useState<number>();
	const [disabledButton, setDisabledButton] = useState<boolean>(false);
	const [specifications, setSpecifications] = useState<
		ProductSpecification[]
	>([]);

	const [orderType, setOrderType] = useState<OrderType>();

	const accountForm = useAccountForm(step, setStep);

	const findOrderTypeByName = (
		orderTypes: OrderType[],
		nameOrderType = 'SOLUTIONS7'
	) => {
		return orderTypes.find(
			({externalReferenceCode}: OrderType) =>
				externalReferenceCode === nameOrderType
		);
	};

	useEffect(() => {
		(async () => {
			const skuProduct = await getProductSKU({
				appProductId: Number(productId),
			});

			const specifications = await getProductSpecifications({
				appProductId: productId,
			});

			setSpecifications(specifications);

			if (!skuProduct.items[0] || productId === 1 || productId === null) {
				setDisabledButton(true);

				Liferay.Util.openToast({
					message:
						'We are unable to start your trial. Please contact our sales team via email - sales@liferay.com',
					type: 'danger',
				});
			} else {
				const product = await getProductById({
					productId: Number(productId),
				});
				setSku(skuProduct.items[0].id);
				setProduct(product);
			}
		})();
	}, [productId]);

	const customFields =
		product?.customFields?.filter((item) =>
			productCustomFields.find((field) => item.name === field)
		) || [];

	const getProductCustomFields = () => {
		let data = {};

		productCustomFields.forEach((fieldName) => {
			customFields.forEach((field) => {
				if (field.name === fieldName) {
					data = {...data, [fieldName]: field.customValue.data};
				}
			});
		});

		return data;
	};

	const trialLenght =
		specifications &&
		specifications?.find(
			(specification) =>
				specification?.specificationKey === 'trial-length'
		);

	const fetchDataAndSetState = async () => {
		const orderTypes = await getOrderTypes();

		if (!channel || !orderTypes.length) {
			setDisabledButton(true);

			return Liferay.Util.openToast({
				message:
					'We are unable to start your trial. Please contact our sales team via email - sales@liferay.com',
				type: 'danger',
			});
		}

		const projectOrderType = findOrderTypeByName(
			orderTypes,
			trialLenght?.value?.en_US as string
		);

		setOrderType(projectOrderType);
	};

	useEffect(() => {
		fetchDataAndSetState();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [trialLenght]);

	const account = accountForm.watch('accountSelected');

	const agreeToTermsAndConditions = accountForm.watch(
		'agreeToTermsAndConditions'
	);

	const hasAllValidations =
		agreeToTermsAndConditions && accountForm.formState.isValid;

	const onSubmit = async (responeAccount?: Account) => {
		await postOrder({
			account: {
				id: Number(account?.id) || Number(responeAccount?.id),
				type:
					(account?.type as string) ||
					(responeAccount?.type as string),
			},
			accountExternalReferenceCode: account?.externalReferenceCode,
			accountId: Number(account?.id) || Number(responeAccount?.id),
			channel: {
				currencyCode: channel?.currencyCode,
				id: channel?.id,
				type: channel?.type,
			},
			channelId: channel?.id,
			currencyCode: 'USD',
			customFields: getProductCustomFields(),
			orderItems: [
				{
					id: 0,
					quantity: 1,
					skuId: Number(sku),
				},
			],
			orderStatus: 1,
			orderTypeExternalReferenceCode: orderType?.externalReferenceCode,
			shippingAmount: 0,
			shippingWithTaxAmount: 0,
		});
	};

	const StepsAccount = {
		[StepType.FORM]: {
			component: (
				<AccountForm
					accountForm={accountForm}
					disabledButton={disabledButton}
					setStep={setStep}
					submitOrder={onSubmit}
				/>
			),
			stepTitle: 'Create Trial',
		},
		[StepType.ACCOUNT]: {
			component: (
				<PurchasedSolutionsAccountSelection
					accountForm={accountForm}
					onSubmit={onSubmit}
					setStep={setStep}
				/>
			),
			stepTitle: 'Create Trial',
		},
		[StepType.CHECKOUT]: {
			component: <CreatedProjectCard product={product} />,
		},
	};

	return (
		<div className="align-items-center d-flex flex-column justify-content-center purchased-solutions">
			{step !== StepType.CHECKOUT && (
				<div className="product-card">
					<div className="mr-5">
						{!product ? (
							<img alt="Circle Icon" src={emptyPictureIcon} />
						) : (
							<ClaySticker size="xl">
								<ClaySticker.Image
									alt="placeholder"
									src={product?.thumbnail}
								/>
							</ClaySticker>
						)}
					</div>

					<h2 className="mb-0">
						<span className="mr-2">{product?.name?.en_US}</span>

						<span>
							<ClayLink className="font-weight-bold">
								Trial
							</ClayLink>
						</span>
					</h2>
				</div>
			)}

			<div className="align-items-center d-flex flex-column justify-content-center purchased-solutions-container">
				<div className="border d-flex flex-column justify-content-center p-6 purchased-solutions-body rounded">
					<div className="d-flex justify-content-center mb-5">
						{accountForm.accountQuantity >
							accountForm.SINGLE_ACCOUNT &&
							step !== StepType.CHECKOUT && (
								<StepWizard
									className="col-6"
									currentStep={step}
									stepsInformation={{
										[StepType.FORM]: StepsAccount.form,
										[StepType.ACCOUNT]:
											StepsAccount.account,
									}}
									wizardSteps={{
										[StepType.ACCOUNT]:
											!!accountForm.getValues(
												'accountSelected'
											) && step !== StepType.ACCOUNT,
										[StepType.FORM]:
											hasAllValidations &&
											step !== StepType.FORM,
									}}
								/>
							)}
					</div>

					<div>{StepsAccount[step].component}</div>
				</div>
			</div>
		</div>
	);
};

export default PurchasedSolutions;
