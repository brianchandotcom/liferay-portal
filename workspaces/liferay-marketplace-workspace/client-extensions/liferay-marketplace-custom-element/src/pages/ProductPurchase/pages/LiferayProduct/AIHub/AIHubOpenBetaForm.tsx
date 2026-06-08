/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {zodResolver} from '@hookform/resolvers/zod';
import {useEffect} from 'react';
import {useForm} from 'react-hook-form';

import {Input} from '../../../../../components/Input/Input';
import ProductPurchase from '../../../../../components/ProductPurchase';
import Select from '../../../../../components/Select/Select';
import useCommerceRegions from '../../../../../hooks/useCommerceRegions';
import i18n from '../../../../../i18n';
import {Liferay} from '../../../../../liferay/liferay';
import zodSchema, {z} from '../../../../../schema/zod';
import {phones} from '../../../../../utils/phones';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';

import './AIHubForm.scss';

import '../index.scss';

const setValuesOptions = {
	shouldDirty: true,
	shouldValidate: true,
};

const AIHubOpenBetaForm = () => {
	const {
		actions: {nextStep, previousStep},
		form,
		product,
		productPurchaseCart,
		selectedAccount,
		setForm,
		skuRef,
	} = useProductPurchaseOutletContext();

	const {
		formState: {errors, isValid},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<z.infer<typeof zodSchema.aiHubOpenBetaForm>>({
		defaultValues: {
			administratorEmailAddress:
				(form?.administratorEmailAddress as string) ||
				Liferay.ThemeDisplay.getUserEmailAddress(),
			aiHubAccountName: (form?.aiHubAccountName as string) || '',
			businessEmailAddress:
				(form?.businessEmailAddress as string) ||
				Liferay.ThemeDisplay.getUserEmailAddress(),
			companyName: (form?.companyName as string) || '',
			country: (form?.country as string) || '',
			extension: (form?.extension as string) || '',
			fullName:
				(form?.fullName as string) ||
				Liferay.ThemeDisplay.getUserName(),
			intlCode: (form?.intlCode as {code: string; flag: string}) || {
				code: '+1',
				flag: 'en-us',
			},
			jobTitle: (form?.jobTitle as string) || '',
			phoneNumber: (form?.phoneNumber as string) || '',
		},
		mode: 'all',
		reValidateMode: 'onChange',
		resolver: zodResolver(zodSchema.aiHubOpenBetaForm),
	});

	const watchedValues = watch();

	const {intlCode} = watchedValues;

	const {data: regionsResponse} = useCommerceRegions();

	const countries = regionsResponse?.items ?? [];

	useEffect(() => {
		if (!selectedAccount) {
			previousStep();
		}
	}, [selectedAccount, previousStep]);

	const onSubmit = async (
		form: z.infer<typeof zodSchema.aiHubOpenBetaForm>
	) => {
		const sku = product.skus.find(
			({externalReferenceCode}) => externalReferenceCode === skuRef
		);

		const skuId = sku?.id;

		const existingItem = productPurchaseCart.cartItems.find(
			(item) => item?.skuId === skuId
		);

		if (!existingItem && skuId) {
			await productPurchaseCart.addCart(product.id, skuId);
		}

		setForm(form);

		nextStep();
	};

	return (
		<ProductPurchase.Shell
			className="liferay-ai-hub-form"
			title={i18n.translate('ai-hub-account-details')}
		>
			<p className="mb-6 text-black-50">
				{i18n.translate(
					'submit-your-request-to-join-the-beta-program-all-submissions-will-be-reviewed-and-youll-receive-an-email-with-the-outcome'
				)}
			</p>

			<p className="h4 mb-0">{i18n.translate('personal-information')}</p>

			<hr className="mb-5 mt-3" />

			<ClayForm.Group>
				<Input
					{...register('fullName')}
					className="w-100"
					errorMessage={errors.fullName?.message}
					label={i18n.translate('full-name')}
					placeholder={i18n.translate('enter-your-full-name')}
					required
				/>

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<Input
							{...register('businessEmailAddress')}
							className="w-100"
							errorMessage={errors.businessEmailAddress?.message}
							id="businessEmailAddress"
							label={i18n.translate('business-email-address')}
							required
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem
						style={{position: 'relative', top: '-2px'}}
					>
						<Select
							className="custom-input"
							{...register('country')}
							label={i18n.translate('country')}
							name="country"
							options={countries.map((country) => ({
								key: country.title_i18n?.en_US,
								name: country.title_i18n?.en_US,
							}))}
							required
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<Input
							{...register('jobTitle')}
							className="w-100"
							errorMessage={errors.jobTitle?.message}
							id="jobTitle"
							label={i18n.translate('job-title')}
							placeholder={i18n.translate('enter-your-job-title')}
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem>
						<Input
							{...register('companyName')}
							className="w-100"
							errorMessage={errors.companyName?.message}
							id="companyName"
							label={i18n.translate('company-name')}
							placeholder={i18n.translate(
								'enter-your-company-name'
							)}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>

				<p className="h4">{i18n.translate('phone')}</p>

				<ClayForm.Group>
					<div className="d-flex justify-content-between purchased-solutions-phone">
						<div className="col-3 p-0">
							<ClayDropDown
								closeOnClick
								tabIndex={0}
								trigger={
									<div className="align-items-center custom-input custom-select d-flex form-control p-2 rounded-xs">
										<ClayIcon
											className="mr-2"
											symbol={intlCode?.flag as string}
										/>

										{intlCode?.code}
									</div>
								}
							>
								<ClayDropDown.ItemList items={phones as any}>
									{(item) => {
										const phone = item as any;

										return (
											<ClayDropDown.Item
												onClick={() => {
													setValue(
														'intlCode',
														{
															code: phone.code,
															flag: phone.flag,
														},
														setValuesOptions
													);
												}}
											>
												<ClayIcon
													className="mr-2"
													symbol={phone.flag}
												/>

												{phone.code}
											</ClayDropDown.Item>
										);
									}}
								</ClayDropDown.ItemList>
							</ClayDropDown>

							<div className="form-feedback-group">
								<div className="form-text">
									{i18n.translate('intl-code')}
								</div>
							</div>
						</div>

						<div className="col-6">
							<Input
								{...register('phoneNumber')}
								className="w-100"
								helpMessage={i18n.translate('phone-number')}
								id="phoneNumber"
								placeholder="___–___–____"
							/>
						</div>

						<div className="col-3 p-0">
							<Input
								{...register('extension')}
								className="text-nowrap w-100"
								helpMessage={`${i18n.translate('extension')} (optional)`}
								id="extension"
								placeholder="Enter +ext"
							/>
						</div>
					</div>
				</ClayForm.Group>

				<p className="h4">{i18n.translate('ai-hub-information')}</p>

				<hr className="mb-5 mt-3" />
				<ClayInput.Group>
					<ClayInput.GroupItem>
						<Input
							{...register('aiHubAccountName')}
							className="w-100"
							errorMessage={errors.aiHubAccountName?.message}
							id="aiHubAccountName"
							label={i18n.translate('ai-hub-account-name')}
							placeholder={i18n.translate('account-name')}
							required
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem>
						<Input
							{...register('administratorEmailAddress')}
							className="w-100"
							errorMessage={
								errors.administratorEmailAddress?.message
							}
							helpMessage={i18n.translate(
								'this-is-the-email-address-that-will-receive-the-ai-hub-account-management-invite'
							)}
							id="administratorEmailAddress"
							label={i18n.translate('administration-email')}
							placeholder={i18n.translate('email-address')}
							required
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>
			</ClayForm.Group>

			<ClayButton
				className="w-100"
				disabled={!isValid}
				onClick={handleSubmit(onSubmit)}
			>
				<div className="align-items-center d-flex justify-content-center">
					<span>{i18n.translate('continue')}</span>
				</div>
			</ClayButton>
		</ProductPurchase.Shell>
	);
};

export default AIHubOpenBetaForm;
