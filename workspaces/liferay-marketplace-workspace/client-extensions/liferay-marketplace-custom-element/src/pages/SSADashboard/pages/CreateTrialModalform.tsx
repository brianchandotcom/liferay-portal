/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {Size} from '@clayui/modal/lib/types';
import {MultiSelect} from '@clayui/multi-select/lib/MultiSelect';
import {zodResolver} from '@hookform/resolvers/zod';
import classNames from 'classnames';
import {useCallback, useEffect, useMemo, useState} from 'react';
import {useForm} from 'react-hook-form';

import {Input} from '../../../components/Input/Input';
import Loading from '../../../components/Loading';
import Form from '../../../components/MarketplaceForm';
import Modal from '../../../components/Modal';
import Select from '../../../components/Select/Select';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import {OrderCustomFields, OrderStatus as Status} from '../../../enums/Order';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import zodSchema from '../../../schema/zod';
import trialOAuth2 from '../../../services/oauth/Trial';
import HeadlessCommerceDeliveryCatalog from '../../../services/rest/HeadlessCommerceDeliveryCatalog';
import ProductPurchaseSSATrial from '../../ProductPurchase/services/ProductPurchaseSSATrial';
import {useSSADashboardOutlet} from '../SSADashboardOutlet';

type CreateTrialModalFormProps = {
	items?: PlacedOrder[];
	modal: {
		observer: any;
		onClose: () => void;
		open: boolean;
	};
	mutate: any;
};

export type FormFields = {
	demoDuration: string;
	emailAddress: Item[];
	objective: string;
	projectId: string;
	site: string;
};

type Item = {
	key: string;
	label: string;
	value: string;
};

const Label = (label: string) => (
	<Form.Label className="mb-2">{label}</Form.Label>
);

const SectionTitle = ({title}: {title: string}) => (
	<>
		<h4>{title}</h4>
		<hr className="mb-3" />
	</>
);

const CreateTrialModalForm: React.FC<CreateTrialModalFormProps> = ({
	items,
	modal,
	mutate,
}) => {
	const {properties} = useMarketplaceContext();
	const {ssaAccount} = useSSADashboardOutlet();

	const [order, setOrder] = useState<any>();
	const [product, setProduct] = useState<DeliveryProduct | null>(null);
	const [submitSuccessful, setSubmittingSuccessful] = useState(false);

	useEffect(() => {
		async function fetchProduct() {
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
		}
		fetchProduct();
	}, [properties]);

	const productPurchase = useMemo(() => {
		if (!ssaAccount || !product) {
			return null;
		}

		return new ProductPurchaseSSATrial(ssaAccount, product);
	}, [ssaAccount, product]);

	const {
		clearErrors,
		formState: {errors, isSubmitting},
		handleSubmit,
		register,
		setError,
		setValue,
		watch,
	} = useForm<FormFields>({
		defaultValues: {
			demoDuration: '',
			emailAddress: [],
			objective: '',
			projectId: '',
			site: '',
		},
		resolver: zodResolver(zodSchema.ssaTrialForm),
	});

	const emails = watch('emailAddress');
	const objective = watch('objective');
	const projectId = watch('projectId');

	const isTestTrial = objective === 'Test';

	useEffect(() => {
		if (isTestTrial) {
			setValue('demoDuration', '1');
			clearErrors('demoDuration');
		}
	}, [isTestTrial, setValue, clearErrors]);

	const onSubmit = useCallback(
		async (data: FormFields) => {
			try {
				await trialOAuth2.checkDomainAvailability(data.projectId);
			}
			catch (error: any) {
				console.error(error.message);

				if (error.status === 409) {
					setError('projectId', {
						message: 'Project ID already exists',
					});
				}
				else {
					Liferay.Util.openToast({
						message: i18n.translate('an-unexpected-error-occurred'),
						type: 'danger',
					});
				}

				return;
			}

			const emails: string[] = [
				Liferay.ThemeDisplay.getUserEmailAddress(),
			];

			data.emailAddress.forEach((email: any) => emails.push(email.value));

			const trialSettings = {
				consoleInviteEmailAddresses: emails,
				duration: data.demoDuration,
				projectId: data.projectId,
			};

			try {
				const order = await productPurchase?.createOrder({
					customFields: {
						[OrderCustomFields.TRIAL_SETTINGS]:
							JSON.stringify(trialSettings),
					},
				} as Cart);

				if (!order) {
					return;
				}

				mutate(
					(orders: APIResponse<PlacedOrder>) => ({
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

				Liferay.Util.openToast({
					message: 'Trial is being provisioned.',
					title: i18n.translate('success'),
					type: 'success',
				});

				setOrder(order);
				setSubmittingSuccessful(true);

				Liferay.Util.openToast({
					message: 'Trial is being provisioned.',
					title: i18n.translate('success'),
					type: 'success',
				});

				setOrder(order);

				setSubmittingSuccessful(true);
			}
			catch (error) {
				console.error(error);

				Liferay.Util.openToast({
					message: i18n.translate('an-unexpected-error-occurred'),
					type: 'danger',
				});

				modal.onClose();
			}
		},
		[modal, mutate, productPurchase, setError]
	);

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
					<Loading className="mb-3" />

					<p className="mt-8 text-center">
						{i18n.translate(
							'the-installation-process-is-ongoing-and-may-take-some-time-navigating-to-other-sections-will-not-cancel-the-process'
						)}
					</p>
				</div>
				<hr className="mt-4" />
				<div className="d-flex justify-content-end">
					<Button displayType="secondary" onClick={modal.onClose}>
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
			<ClayForm.Group>
				<div className="mb-3 pr-2 w-100">
					<SectionTitle title={i18n.translate('main')} />

					{Label(i18n.translate('project-id'))}

					<ClayInput.Group
						className={classNames({
							'has-error': errors.projectId,
						})}
					>
						<ClayInput.GroupItem prepend>
							<ClayInput
								{...register('projectId')}
								className="custom-input mb-0"
								maxLength={25}
								required
								type="text"
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem append shrink>
							<ClayInput.GroupText>
								.saas.demo.lxc.liferay.com
							</ClayInput.GroupText>
						</ClayInput.GroupItem>
					</ClayInput.Group>
					{errors.projectId && (
						<p className="field-base-feedback text-danger">
							{errors.projectId?.message}
						</p>
					)}

					<small className="mt-0 text-black-50">
						{`${projectId?.length}/25`}
					</small>

					<div className="mb-3 mt-2 pr-2 w-100">
						{Label(i18n.translate('solution'))}

						<Input
							disabled
							name="site"
							placeholder={i18n.translate('blank-site')}
						/>
					</div>
				</div>

				<div className="mb-3">
					<SectionTitle title="Usage" />

					<div className="d-flex">
						<div className="pr-2 w-100">
							{Label(i18n.translate('objective'))}

							<Select
								{...register('objective')}
								defaultOptionLabel="Select an option"
								errors={errors}
								name="objective"
								options={[
									{
										key: 'Test',
										name: 'Test',
									},
									{
										key: 'Trial',
										name: 'Trial',
									},
								]}
							/>
						</div>

						<div className="pr-2 w-100">
							{Label(i18n.translate('duration-days'))}
							<Input
								{...register('demoDuration')}
								disabled={isTestTrial}
								errorMessage={errors.demoDuration?.message}
								max={60}
								min={1}
								type="number"
							/>
						</div>
					</div>
				</div>

				<div className="mb-3 pr-2 w-100">
					<SectionTitle title={i18n.translate('additional-admin')} />

					{Label(i18n.translate('email-address'))}

					<MultiSelect
						className="bg-white marketplace-form-select"
						id="allowed-email-domains"
						items={emails}
						onItemsChange={(values: Item[]) => {
							setValue('emailAddress', values);
						}}
					/>
					{errors.emailAddress && (
						<p className="text-danger">
							{errors.emailAddress?.message}
						</p>
					)}
				</div>
			</ClayForm.Group>

			<hr />

			<div className="d-flex justify-content-end">
				<Button
					className="mr-2"
					disabled={isSubmitting}
					displayType="secondary"
					onClick={modal.onClose}
				>
					{i18n.translate('cancel')}
				</Button>
				<Button
					disabled={isSubmitting}
					displayType="primary"
					onClick={handleSubmit(onSubmit)}
				>
					<div className="align-items-center d-flex">
						{isSubmitting && (
							<ClayLoadingIndicator className="mr-3 my-0" />
						)}
						{i18n.translate('create')}
					</div>
				</Button>
			</div>
		</Modal>
	);
};

export default CreateTrialModalForm;
