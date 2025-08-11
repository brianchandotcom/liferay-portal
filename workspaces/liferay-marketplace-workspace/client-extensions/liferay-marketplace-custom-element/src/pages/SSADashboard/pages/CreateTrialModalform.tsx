/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {Size} from '@clayui/modal/lib/types';
import {useCallback, useEffect, useMemo, useState} from 'react';

import Loading from '../../../components/Loading';
import Form from '../../../components/MarketplaceForm';
import Modal from '../../../components/Modal';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import {OrderCustomFields, OrderStatus as Status} from '../../../enums/Order';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import zodSchema from '../../../schema/zod';
import trialOAuth2 from '../../../services/oauth/Trial';
import HeadlessCommerceDeliveryCatalog from '../../../services/rest/HeadlessCommerceDeliveryCatalog';
import ProductPurchaseSSATrial from '../../ProductPurchase/services/ProductPurchaseSSATrial';
import {useSSADashboardOutlet} from '../SSADashboardOutlet';
import {FieldGroup} from '../components/SSAForm/FieldGroup';

export type FormFields = {
	demoDuration: string;
	emailAddress: string;
	objective: string;
	projectId: string;
	site: string;
};

type ValidationErrors = Partial<Record<keyof FormFields, string>>;

type CreateTrialModalFormProps = {
	items?: PlacedOrder[];
	modal: {
		observer: any;
		onClose: () => void;
		open: boolean;
	};
	mutate: any;
};

const CreateTrialModalForm: React.FC<CreateTrialModalFormProps> = ({
	items,
	modal,
	mutate,
}) => {
	const {ssaAccount} = useSSADashboardOutlet();
	const [errors, setErrors] = useState<ValidationErrors>({});
	const [isSubmitting, setIsSubmitting] = useState(false);
	const [submitSuccessful, setSubmittingSuccessful] = useState(false);
	const [order, setOrder] = useState<any>();

	const [formData, setFormData] = useState<FormFields>({
		demoDuration: '',
		emailAddress: '',
		objective: '',
		projectId: '',
		site: '',
	});

	const {properties} = useMarketplaceContext();
	const [product, setProduct] = useState<DeliveryProduct | null>(null);

	useEffect(() => {
		const fetchProduct = async () => {
			const product = await HeadlessCommerceDeliveryCatalog.getProduct(
				Liferay.CommerceContext.commerceChannelId,
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
	}, [properties]);

	const productPurchase = useMemo(() => {
		if (!ssaAccount || !product) {
			return null;
		}

		return new ProductPurchaseSSATrial(ssaAccount, product);
	}, [product, ssaAccount]);

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

	const onChange = ({label, value}: {label: string; value: string}) => {
		setFormData((prevData) => ({
			...prevData,
			[label]: value,
		}));

		setErrors((prevErrors) => ({...prevErrors, [label]: undefined}));
	};

	const onSubmit = useCallback(async () => {
		setIsSubmitting(true);
		try {
			const validationResults =
				zodSchema.ssaTrialForm.safeParse(formData);

			if (!validationResults.success) {
				const fieldErrors: ValidationErrors = {};
				for (const error of validationResults.error.errors) {
					if (error.path.length) {
						const fieldName = error.path[0] as keyof FormFields;
						fieldErrors[fieldName] = error.message;
					}
				}
				setErrors(fieldErrors);

				setIsSubmitting(false);

				return;
			}

			try {
				await trialOAuth2.checkDomainAvailability(formData.projectId);
			}
			catch (error: any) {
				console.error(error.message);

				if (error.status === 409) {
					setErrors((prevErrors) => ({
						...prevErrors,
						projectId: 'Project ID already exists',
					}));
				}

				setIsSubmitting(false);

				return;
			}

			const consoleInviteEmailAddresses = Array.from(
				new Set(
					formData.emailAddress
						? [
								formData.emailAddress,
								Liferay.ThemeDisplay.getUserEmailAddress(),
							]
						: [Liferay.ThemeDisplay.getUserEmailAddress()]
				)
			);

			const trialSettings = {
				consoleInviteEmailAddresses,
				duration: formData.demoDuration,
				projectId: formData.projectId,
			};

			const order = await productPurchase?.createOrder({
				customFields: {
					[OrderCustomFields.TRIAL_SETTINGS]:
						JSON.stringify(trialSettings),
				},
			} as Cart);

			if (!order) {
				return setIsSubmitting(false);
			}

			mutate(
				(orders: any) => ({
					...orders,
					items: [
						{
							...order,
							orderStatusInfo: {
								code: 10,
								label: Status.PROCESSING,
								label_i18n: Status.PROCESSING,
							},
						},
						...orders.items,
					],
				}),
				{revalidate: false}
			);

			setErrors({});

			Liferay.Util.openToast({
				message: 'Trial is being provisioned.',
				title: i18n.translate('success'),
				type: 'success',
			});
			setOrder(order);
			setIsSubmitting(false);

			setSubmittingSuccessful(true);
		}
		catch (error) {
			console.error(error);

			setIsSubmitting(false);

			Liferay.Util.openToast({
				message: i18n.translate('an-unexpected-error-occurred'),
				type: 'danger',
			});

			modal.onClose();
		}
	}, [formData, modal, mutate, productPurchase]);

	useEffect(() => {
		if (items && order && submitSuccessful) {
			const inProgress = items.some(
				(item: PlacedOrder) =>
					item?.id === order?.id &&
					item?.orderStatusInfo?.label === Status.PROCESSING
			);

			if (!inProgress) {
				setSubmittingSuccessful(false);
				modal.onClose();
			}
		}
	}, [items, modal, order, submitSuccessful]);

	useEffect(() => {
		if (!modal.open) {
			setSubmittingSuccessful(false);
		}
	}, [modal.open]);

	if (!modal.open) {
		return null;
	}

	if (submitSuccessful) {
		return (
			<Modal
				observer={modal.observer}
				size={'md' as any}
				title={i18n.translate('ssa-trial-installation-in-progress')}
				visible={modal.open}
			>
				<div className="m-8">
					<span className="mb-5">
						<Loading />
					</span>

					<p className="mt-8 text-center">
						{i18n.translate(
							'the-installation-process-is-ongoing-and-may-take-some-time-navigating-to-other-sections-will-not-cancel-the-process'
						)}
					</p>
				</div>
				<hr className="mt-4" />
				<div className="d-flex justify-content-end">
					<Button
						displayType="secondary"
						onClick={() => {
							modal.onClose();
						}}
					>
						{i18n.translate('go-to-ssa-trial-listing')}
					</Button>
				</div>
			</Modal>
		);
	}

	return (
		<Modal
			observer={modal.observer}
			size={'md' as Size}
			title={i18n.translate('add-new-trial')}
			visible={modal.open}
		>
			<>
				<ClayForm.Group>
					<div className="mb-5 pr-2 w-100">
						<h4>{i18n.translate('main')}</h4>

						<hr className="mb-5" />

						<Form.Label className="mb-2" info="Project ID">
							{i18n.translate('project-id')}
						</Form.Label>

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
							placeholder: i18n.translate(
								'value-between-1-and-60'
							),
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

				<hr />

				<div className="d-flex justify-content-end">
					<Button
						className="mr-2"
						disabled={isSubmitting}
						displayType="secondary"
						onClick={() => {
							setIsSubmitting(false);
							modal.onClose();
						}}
					>
						{i18n.translate('cancel')}
					</Button>
					<Button
						disabled={isSubmitting}
						displayType="primary"
						onClick={async () => await onSubmit()}
					>
						<div className="align-items-center d-flex">
							{isSubmitting && (
								<ClayLoadingIndicator className="mr-3 my-0" />
							)}
							{i18n.translate('create')}
						</div>
					</Button>
				</div>
			</>
		</Modal>
	);
};

export default CreateTrialModalForm;
