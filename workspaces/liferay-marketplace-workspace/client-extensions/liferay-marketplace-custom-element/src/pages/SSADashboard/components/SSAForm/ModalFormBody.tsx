/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import {useCallback, useEffect, useMemo, useState} from 'react';

import Loading from '../../../../components/Loading';
import {Label} from '../../../../components/MarketplaceForm/Label';
import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import {OrderCustomFields} from '../../../../enums/Order';
import i18n from '../../../../i18n';
import {Liferay} from '../../../../liferay/liferay';
import zodSchema from '../../../../schema/zod';
import trialOAuth2 from '../../../../services/oauth/Trial';
import HeadlessCommerceDeliveryCatalog from '../../../../services/rest/HeadlessCommerceDeliveryCatalog';
import ProductPurchaseSSATrial from '../../../ProductPurchase/services/ProductPurchaseSSATrial';
import {FieldGroup} from './FieldGroup';

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
	const [errors, setErrors] = useState<ValidationErrors>({});

	const [formData, setFormData] = useState<FormFields>({
		demoDuration: '',
		emailAddress: '',
		objective: '',
		projectId: '',
		site: '',
	});
	const {channel, properties} = useMarketplaceContext();
	const [product, setProduct] = useState<DeliveryProduct | null>(null);
	const accountId = properties.accountId;

	useEffect(() => {
		const fetchProduct = async () => {
			const product = await HeadlessCommerceDeliveryCatalog.getProduct(
				channel.channelId,
				properties.productId,
				new URLSearchParams({
					'accountId': '-1',
					'nestedFields': 'skus',
					'skus.accountId': '-1',
				})
			);

			setProduct(product);
		};

		fetchProduct();
	}, [channel, properties]);

	const productPurchase = useMemo(() => {
		if (!accountId || !channel || !product) {
			return null;
		}

		return new ProductPurchaseSSATrial(
			{id: Number(accountId)} as Account,
			channel,
			product
		);
	}, [accountId, channel, product]);

	const isTestTrial = formData.objective === 'Test';

	useEffect(() => {
		if (isTestTrial) {
			setFormData((prevData) => ({
				...prevData,
				demoDuration: '1',
			}));

			setErrors((prevErrors) => ({
				...prevErrors,
				demoDuration: undefined,
			}));
		}
	}, [isTestTrial]);

	const validateProjectId = useCallback(
		async (projectId: string) => {
			try {
				return trialOAuth2.checkDomainAvailability(projectId);
			}
			catch (error: any) {
				console.error(error.message);

				if (error.status === 409) {
					setErrors((prevErrors) => ({
						...prevErrors,
						projectId: 'Project ID already exists',
					}));
				}

				return false;
			}
		},
		[setErrors]
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

		const trialSettings = {
			...(formData.emailAddress
				? {consoleInviteEmailAddresses: [formData.emailAddress]}
				: {}),
			duration: formData.demoDuration,
			projectId: formData.projectId,
		};

		await productPurchase?.createOrder({
			customFields: {
				[OrderCustomFields.TRIAL_SETTINGS]:
					JSON.stringify(trialSettings),
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
			<h2 className="mb-6">{i18n.translate('add-new-trial')}</h2>

			<ClayForm.Group>
				<div className="mb-5 pr-2 w-100">
					<h4>{i18n.translate('main')}</h4>

					<hr className="mb-5" />

					<Label className="mb-2" info="placeholder" required>
						{i18n.translate('project-id')}
					</Label>

					<ClayInput.Group>
						<ClayInput
							className="bg-white input-group-inset input-group-inset-after marketplace-form-input"
							maxLength={9}
							onChange={({target: {value}}) =>
								onChange({label: 'projectId', value})
							}
						/>
						<ClayInput.GroupInsetItem after tag="span">
							.saas.demo.lxc.liferay.com
						</ClayInput.GroupInsetItem>
					</ClayInput.Group>

					{errors.projectId && (
						<p className="mb-0 mt-1 text-danger">
							{errors.projectId}
						</p>
					)}
				</div>
				<FieldGroup
					primaryField={{
						disabled: true,
						handleChange: onChange,
						label: 'site',
						placeholder: i18n.translate('blank-site'),
						title: i18n.translate('solution'),
						tooltip: i18n.translate('blank-site'),
					}}
				/>

				<FieldGroup
					primaryField={{
						error: errors.objective || '',
						handleChange: onChange,
						label: 'objective',
						options: ['Test', 'Trial'],
						placeholder: i18n.translate('select-an-option'),
						required: true,
						title: i18n.translate('objective'),
						tooltip: i18n.translate('select-an-option'),
						type: 'select',
						value: formData.objective,
					}}
					secondaryField={{
						disabled: isTestTrial,
						error: errors.demoDuration || '',
						handleChange: onChange,
						label: 'demoDuration',
						placeholder: i18n.translate('value-between-1-and-60'),
						required: true,
						title: i18n.translate('duration-days'),
						tooltip: i18n.translate('value-between-1-and-60'),
						type: 'number',
						value: isTestTrial ? '1' : formData.demoDuration,
					}}
					title="Usage"
				/>
				<FieldGroup
					primaryField={{
						error: errors.emailAddress || '',
						handleChange: onChange,
						label: 'emailAddress',
						title: 'Email Address',
						tooltip: i18n.translate('email-address'),
						value: formData.emailAddress,
					}}
					title={i18n.translate('additional-admin')}
				/>
			</ClayForm.Group>

			{false && (
				<Loading.FullScreen>
					Hang tight, the submission of <b>{product?.name}</b> is
					being sent to <b>Liferay</b>
				</Loading.FullScreen>
			)}
		</>
	);
};

export default SSAFormBody;
