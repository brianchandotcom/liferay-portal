/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import {useCallback, useEffect, useMemo, useState} from 'react';

import {Label} from '../../../../components/MarketplaceForm/Label';
import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import {OrderCustomFields} from '../../../../enums/Order';
import {useAccount} from '../../../../hooks/data/useAccounts';
import i18n from '../../../../i18n';
import {Liferay} from '../../../../liferay/liferay';
import zodSchema from '../../../../schema/zod';
import ProductPurchaseSSATrial from '../../../ProductPurchase/services/ProductPurchaseSSATrial';
import useSSAProduct from '../../hooks/useSSAProduct';
import {FormSection} from './FormSection';
import {Input} from './Input';

type ValidationErrors = Partial<Record<keyof FormFields, string>>;

export type FormFields = {
	demoDuration: string;
	emailAddress: string;
	objective: string;
	projectId: string;
	site: string;
};

const SSAFormBody = ({
	submitRef,
}: {
	submitRef: React.MutableRefObject<() => void>;
}) => {
	const {channel} = useMarketplaceContext();
	const {data: account} = useAccount();
	const product = useSSAProduct(channel?.id);

	const productPurchase = useMemo(() => {
		if (!account || !channel || !product) {
			return null;
		}

		return new ProductPurchaseSSATrial(account, channel, product);
	}, [account, channel, product]);

	const initialFormData: FormFields = {
		demoDuration: '',
		emailAddress: '',
		objective: '',
		projectId: '',
		site: '',
	};

	const [errors, setErrors] = useState<ValidationErrors>({});
	const [formData, setFormData] = useState<FormFields>(initialFormData);

	const isTestTrial = formData.objective === 'Test';

	useEffect(() => {
		if (isTestTrial) {
			setFormData((prevData) => ({
				...prevData,
				demoDuration: '1',
			}));
		}
	}, [isTestTrial]);

	const validateProjectId = useCallback(
		async (projectId: string) => {
			try {
				const data =
					await productPurchase?.getDemoAvailability(projectId);

				return data;
			}
			catch (error: any) {
				if (error.status === 409) {
					setErrors((prevErrors) => ({
						...prevErrors,
						projectId: 'Project ID already exists',
					}));

					return false;
				}
				else {
					console.error(error.message);

					return false;
				}
			}
		},
		[productPurchase]
	);

	const onChange = ({label, value}: {label: string; value: string}) => {
		setFormData((prevData) => ({
			...prevData,
			[label]: value,
		}));

		setErrors((prevErrors) => ({...prevErrors, [label]: undefined}));
	};

	const onSubmit = useCallback(async () => {
		const result = zodSchema.ssaTrialForm.safeParse(formData);

		if (!result.success) {
			const fieldErrors: ValidationErrors = {};
			for (const error of result.error.errors) {
				if (error.path.length) {
					const fieldName = error.path[0] as keyof FormFields;
					fieldErrors[fieldName] = error.message;
				}
			}
			setErrors(fieldErrors);

			return;
		}

		const data = await validateProjectId(formData.projectId);

		if (!data) {
			return;
		}

		await productPurchase?.createOrder({
			customFields: {
				[OrderCustomFields.TRIAL_SETTINGS]: JSON.stringify({
					...(formData.emailAddress
						? {consoleInviteEmailAddresses: [formData.emailAddress]}
						: {}),
					ssaSettings: {
						duration: formData.demoDuration,
						objective: formData.objective,
						projectId: formData.projectId,
						sendNotificationEmail: true,
					},
				}),
			},
		} as Cart);

		setErrors({});

		Liferay.Util.openToast({
			message: 'Trial is being provisioned.',
			title: i18n.translate('success'),
			type: 'success',
		});

		return true;
	}, [productPurchase, formData, validateProjectId]);

	useEffect(() => {
		submitRef.current = onSubmit;
	}, [onSubmit, submitRef]);

	return (
		<>
			<h2 className="mb-6">Add New Trial</h2>
			<ClayForm.Group>
				<FormSection
					leftSection={{
						error: errors.projectId || '',
						handleChange: onChange,
						label: 'projectId',
						maxLength: 9,
						required: true,
						title: 'Project ID',
						tooltip: 'placeholder',
						value: formData.projectId,
					}}
					rightSection={{
						disabled: true,
						handleChange: onChange,
						label: 'site',
						placeholder: 'Blank Site',
						title: 'Solution',
						tooltip: 'placeholder',
					}}
					title="Main"
				/>

				<FormSection
					leftSection={{
						error: errors.objective || '',
						handleChange: onChange,
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
						disabled: isTestTrial,
						error: errors.demoDuration || '',
						handleChange: onChange,
						label: 'demoDuration',
						placeholder: 'Value between 1 and 60',
						required: true,
						title: 'Duration (days)',
						tooltip: 'placeholder',
						type: 'number',
						value: isTestTrial ? '1' : formData.demoDuration,
					}}
					title="Usage"
				/>

				<div>
					<h4>Additional Admin</h4>
					<hr className="mb-4" />
					<Label info="placeholder">Email Address</Label>

					<Input
						error={errors.emailAddress || ''}
						handleChange={onChange}
						label="emailAddress"
						title="Email Address"
						tooltip="placeholder"
						value={formData.emailAddress}
					/>
				</div>
			</ClayForm.Group>
		</>
	);
};

export default SSAFormBody;
