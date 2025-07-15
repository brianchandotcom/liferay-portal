/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import {useEffect, useMemo, useState} from 'react';

import useModalContext from '../../../../hooks/useModalContext';
import ClayForm from '@clayui/form';
import zodSchema from '../../../../schema/zod';
import {SSAFormSection} from './Section';
import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import HeadlessCommerceDeliveryCatalog from '../../../../services/rest/HeadlessCommerceDeliveryCatalog';
import SearchBuilder from '../../../../core/SearchBuilder';
import {ProductType} from '../../../../enums/Product';
import ProductPurchaseSSATrial from '../../../ProductPurchase/services/ProductPurchaseSSATrial';
import {useAccount} from '../../../../hooks/data/useAccounts';
import {OrderCustomFields} from '../../../../enums/Order';

type ValidationErrors = Partial<Record<keyof FormFields, string>>;

export type FormFields = {
	demoDuration: string;
	emailAddress: string;
	fullName: string;
	githubUsername: string;
	objective: string;
	projectId: string;
	site: string;
};

const SSAFormBody = () => {
	const [product, setProduct] = useState<DeliveryProduct>();
	const {channel} = useMarketplaceContext();
	const {data: account} = useAccount();

	const [formData, setFormData] = useState<FormFields>({
		demoDuration: '',
		emailAddress: '',
		fullName: '',
		githubUsername: '',
		objective: '',
		projectId: '',
		site: '',
	});

	useEffect(() => {
		if (!channel?.id) return;

		const fetchProduct = async () => {
			const result =
				await HeadlessCommerceDeliveryCatalog.getProductsPage(
					channel?.id,
					new URLSearchParams({
						'accountId': '-1',
						'filter': SearchBuilder.lambda(
							'specificationValues',
							ProductType.SSA_SAAS
						),
						'nestedFields': 'skus',
						'skus.accountId': '-1',
					})
				);

			setProduct(result?.items?.[0]);
		};

		fetchProduct();
	}, [channel?.id]);

	const testTrial = formData.objective === 'Test';

	useEffect(() => {
		if (testTrial) {
			setFormData((prevData) => ({
				...prevData,
				demoDuration: '1',
			}));
		}
	}, [testTrial]);

	const productPurchase = new ProductPurchaseSSATrial(
		account as Account,
		channel as Channel,
		product as DeliveryProduct
	);

	const [errors, setErrors] = useState<ValidationErrors>({});

	const onChange = ({
		label,
		value,
	}: {
		label: keyof FormFields;
		value: string;
	}) => {
		setFormData((prevData) => ({
			...prevData,
			[label]: value,
		}));

		setErrors((prevErrors) => ({...prevErrors, [label]: undefined}));
	};

	const onSubmit = async (event: React.FormEvent) => {
		event.preventDefault();

		// productPurchase.getDemoAvailability(formData.projectId);

		const result = zodSchema.ssaTrialForm.safeParse(formData);

		if (!result.success) {
			const fieldErrors: ValidationErrors = {};
			for (const err of result.error.errors) {
				if (err.path.length > 0) {
					const fieldName = err.path[0] as keyof FormFields;
					fieldErrors[fieldName] = err.message;
				}
			}
			setErrors(fieldErrors);

			return;
		}
		const order = await productPurchase.createOrder({
			customFields: {
				[OrderCustomFields.TRIAL_SETTINGS]: JSON.stringify({
					trialSettings: {
						consoleInviteEmailAddresses: [
							formData.emailAddress,
						],
						ssaSettings: {
							duration: formData.demoDuration,
							objective: formData.objective,
							projectId: formData.projectId,
							sendNotificationEmail: true,
						},
					},
				}),
			},
		} as Cart);
		console.log(order);

		setErrors({});
	};

	return (
		<>
			<h2 className="mb-6">Add New Trial</h2>
			<ClayForm.Group>
				<SSAFormSection
					leftSection={{
						handleChange: onChange,
						error: errors.projectId || '',
						tooltip: 'placeholder',
						label: 'projectId',
						required: true,
						title: 'Project ID',
						maxLength: 9,
						value: formData.projectId,
					}}
					rightSection={{
						disabled: true,
						handleChange: onChange,
						label: 'site',
						tooltip: 'placeholder',
						placeholder: 'Blank Site',
						title: 'Solution',
					}}
					title="Main"
				/>

				<SSAFormSection
					leftSection={{
						handleChange: onChange,
						error: errors.objective || '',
						label: 'objective',
						options: ['Test', 'Trial'],
						placeholder: 'Select an Option',
						required: true,
						title: 'Objective',
						tooltip: 'placeholder',
						type: 'select',
						value: formData.objective,
					}}
					rightSection={{
						error: errors.demoDuration || '',
						handleChange: onChange,
						label: 'demoDuration',
						placeholder: 'Value between 1 and 60',
						tooltip: 'placeholder',
						required: true,
						type: 'number',
						title: 'Duration (days)',
						value: testTrial ? '1' : formData.demoDuration,
						disabled: testTrial,
					}}
					title="Usage"
				/>

				<SSAFormSection
					bottomSection={{
						error: errors.githubUsername || '',
						handleChange: onChange,
						label: 'githubUsername',
						tooltip: 'placeholder',
						title: 'Github Username',
						value: formData.githubUsername,
					}}
					leftSection={{
						handleChange: onChange,
						error: errors.fullName || '',

						label: 'fullName',
						title: 'Full Name',
						tooltip: 'placeholder',
						value: formData.fullName,
					}}
					rightSection={{
						handleChange: onChange,
						label: 'emailAddress',
						tooltip: 'placeholder',
						title: 'Email Address',
						error: errors.emailAddress || '',
						value: formData.emailAddress,
					}}
					title="Additional Admin"
				/>
				<hr />
				<div className="d-flex justify-content-end">
					<Button className="mr-2" displayType="secondary">
						cancel
					</Button>
					<Button
						displayType="primary"
						type="submit"
						onClick={onSubmit}
					>
						create
					</Button>
				</div>
			</ClayForm.Group>
		</>
	);
};

const useSSAForm = () => {
	const modalContext = useModalContext();

	modalContext.onClose;

	return {
		openModal: () =>
			modalContext.onOpenModal({
				body: <SSAFormBody />,
				size: 'md',
			}),
	};
};

export {useSSAForm};
