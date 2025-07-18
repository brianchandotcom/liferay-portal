import {useCallback, useEffect, useMemo, useState} from 'react';
import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import {useAccount} from '../../../../hooks/data/useAccounts';
import ProductPurchaseSSATrial from '../../../ProductPurchase/services/ProductPurchaseSSATrial';
import zodSchema from '../../../../schema/zod';
import {OrderCustomFields} from '../../../../enums/Order';
import {Liferay} from '../../../../liferay/liferay';
import i18n from '../../../../i18n';
import ClayForm, {ClayInput} from '@clayui/form';
import {FormSection} from './FormSection';
import {Label} from '../../../../components/MarketplaceForm/Label';
import {Input} from './Input';
import useSSAProduct from '../../hooks/useSSAProduct';

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
		if (!account || !channel || !product) return null;

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

	const validateProjectId = async (projectId: string) => {
		try {
			const data = await productPurchase?.getDemoAvailability(projectId);

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
	};

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
			for (const err of result.error.errors) {
				if (err.path.length > 0) {
					const fieldName = err.path[0] as keyof FormFields;
					fieldErrors[fieldName] = err.message;
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
			type: 'success',
			title: i18n.translate('success'),
		});

		return true;
	}, [productPurchase, formData]);

	useEffect(() => {
		submitRef.current = onSubmit;
	}, [onSubmit]);

	return (
		<>
			<h2 className="mb-6">Add New Trial</h2>
			<ClayForm.Group>
				<FormSection
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

				<FormSection
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
						tooltip="placeholder"
						title="Email Address"
						value={formData.emailAddress}
					/>
				</div>
			</ClayForm.Group>
		</>
	);
};

export default SSAFormBody;
