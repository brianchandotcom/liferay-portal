/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import ClaySticker from '@clayui/sticker';
import {Outlet, useLocation, useNavigate} from 'react-router-dom';

import {useMarketplaceContext} from '../../context/MarketplaceContext';
import CommerceSelectAccountImpl from '../../services/rest/CommerceSelectAccount';
import {postOrder} from '../../utils/api';
import {StepWizardRevamp} from '../GetApp/components/StepWizard/StepWizard';
import useAccountForm from './hooks/useAccountForm';

type GetSolutionOutletProps = {
	product: DeliveryProduct;
};

const productCustomFields = [
	'Github Username',
	'Project Name',
	'Site Initializer',
];

const getProductCustomFields = (customFields: CustomField[]) => {
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

const steps = [
	{
		id: 1,
		path: '/',
		title: 'Account Selection',
	},
	{id: 2, path: '/form', title: 'Create Trial'},
];

const GetSolutionOutlet: React.FC<GetSolutionOutletProps> = ({product}) => {
	const {channel} = useMarketplaceContext();
	const accountForm = useAccountForm();
	const account = accountForm.watch('accountSelected');
	const sku = product.skus?.[0]?.id;
	const navigate = useNavigate();
	const location = useLocation();

	const customFields =
		product?.customFields?.filter((item) =>
			productCustomFields.find((field) => item.name === field)
		) || [];

	const onSubmit = async (responeAccount?: Account) => {
		const accountId = Number(account?.id || responeAccount?.id);

		await postOrder({
			account: {
				id: accountId,
				type:
					(account?.type as string) ||
					(responeAccount?.type as string),
			},
			accountExternalReferenceCode: account?.externalReferenceCode,
			accountId,
			channel: {
				currencyCode: channel?.currencyCode,
				id: channel?.id,
				type: channel?.type,
			},
			channelId: channel?.id,
			currencyCode: 'USD',
			customFields: getProductCustomFields(customFields),
			orderItems: [
				{
					id: 0,
					quantity: 1,
					skuId: Number(sku),
				},
			],
			orderStatus: 1,
			orderTypeExternalReferenceCode: 'SOLUTIONS7',
			shippingAmount: 0,
			shippingWithTaxAmount: 0,
		});

		await CommerceSelectAccountImpl.selectAccount(accountId);

		navigate('/finish', {replace: true});
	};

	return (
		<div className="align-items-center d-flex flex-column justify-content-center purchased-solutions">
			<div className="product-card">
				<div className="mr-5">
					<ClaySticker size="xl">
						<ClaySticker.Image
							alt="placeholder"
							src={product?.urlImage?.replace('https', 'http')}
						/>
					</ClaySticker>
				</div>

				<h2 className="mb-0">
					<span className="mr-2">{product?.name}</span>

					<span>
						<ClayLink className="font-weight-bold">Trial</ClayLink>
					</span>
				</h2>
			</div>

			<div className="align-items-center d-flex flex-column justify-content-center purchased-solutions-container">
				<div className="border d-flex flex-column justify-content-center p-6 purchased-solutions-body rounded">
					{accountForm.accountQuantity >
						accountForm.SINGLE_ACCOUNT && (
						<div className="d-flex justify-content-center mb-5">
							<StepWizardRevamp
								className="col-8"
								currentStep={1}
								stepIndex={steps.findIndex(
									(step) => step.path === location.pathname
								)}
								steps={steps}
							/>
						</div>
					)}

					<Outlet
						context={{
							accountForm,
							navigate,
							onSubmit,
							product,
						}}
					/>
				</div>
			</div>
		</div>
	);
};

export default GetSolutionOutlet;
