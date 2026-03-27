/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown, { Align } from '@clayui/drop-down';
import ClayForm, { ClayCheckbox, ClayInput } from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import { zodResolver } from '@hookform/resolvers/zod';
import classNames from 'classnames';
import { useState } from 'react';
import { useForm } from 'react-hook-form';

import { RequiredMask } from '../../../../../components/FieldBase';
import { Input } from '../../../../../components/Input/Input';
import ProductPurchase from '../../../../../components/ProductPurchase';
import i18n from '../../../../../i18n';
import { Liferay } from '../../../../../liferay/liferay';
import zodSchema, { z } from '../../../../../schema/zod';
import { productAgreements } from '../../../../../utils/agreements';
import { phones } from '../../../../../utils/phones';
import { useProductPurchaseOutletContext } from '../../../ProductPurchaseOutlet';
import ProductPurchaseDXPTypeFree from '../../../services/ProductPurchaseDXPTypeFree';
import { PURPOSE_OPTIONS } from './constants';

import './ActivationKeyForm.scss';

const setValuesOptions = {
	shouldDirty: true,
	shouldValidate: true,
};

const ActivationKeyFormDXP = () => {
	const [active, setActive] = useState(false);
	const [loading, setLoading] = useState(false);

	const { handlePurchase, product, selectedAccount } =
		useProductPurchaseOutletContext();

	const {
		formState: { errors, isValid },
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<z.infer<typeof zodSchema.activationKey>>({
		defaultValues: {
			businessEmail: Liferay.ThemeDisplay.getUserEmailAddress(),
			companyName: '',
			country: '',
			domain: '',
			extension: '',
			fullName: Liferay.ThemeDisplay.getUserName(),
			intlCode: { code: '+1', flag: 'en-us' },
			jobTitle: '',
			notifyMeAboutProducts: false,
			phoneNumber: '',
			purpose: '',
			purposeDescription: '',
			termsAndConditions: false,
			userAgreement: false,
		},
		mode: 'all',
		reValidateMode: 'onChange',
		resolver: zodResolver(zodSchema.activationKey),
	});

	const {
		intlCode,
		notifyMeAboutProducts,
		purpose,
		termsAndConditions,
		userAgreement,
	} = watch();

	const [currentPhonesFlags, setCurrentPhonesFlags] = useState(intlCode);

	const onSubmit = async (data: z.infer<typeof zodSchema.activationKey>) => {
		setLoading(true);

		const productPurchase = new ProductPurchaseDXPTypeFree(
			selectedAccount,
			product
		);

		productPurchase.setForm(data);

		await handlePurchase(productPurchase);

		setLoading(false);
	};

	return (
		<ProductPurchase.Shell
			className="activation-key-form"
			title={i18n.translate('activation-key-creation')}
		>
			<p className="mb-6 text-black-50">
				{i18n.translate(
					'to-generate-your-unique-activation-key-file-and-access-the-download-please-complete-your-profile-details-below-tell-us-a-bit-about-your-intended-use-to-help-us-support-your-experience'
				)}
			</p>

			<p className="h4 mb-0">
				{i18n.translate('personal-information-purpose')}
			</p>

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
							{...register('businessEmail')}
							className="w-100"
							errorMessage={errors.businessEmail?.message}
							id="businessEmail"
							label={i18n.translate('business-email')}
							placeholder={i18n.translate(
								'enter-your-business-email'
							)}
							required
						/>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem>
						<Input
							{...register('country')}
							className="w-100"
							errorMessage={errors.country?.message}
							id="country"
							label={i18n.translate('country')}
							placeholder={i18n.translate('enter-your-country')}
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
											symbol={
												currentPhonesFlags?.flag as string
											}
										/>

										{currentPhonesFlags?.code}
									</div>
								}
							>
								<ClayDropDown.ItemList items={phones as any}>
									{(item) => {
										const phone = item as any;

										return (
											<ClayDropDown.Item
												onClick={() => {
													setCurrentPhonesFlags({
														code: phone.code,
														flag: phone.flag,
													});

													setValue('intlCode', {
														code: phone.code,
														flag: phone.flag,
													});
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

				<p className="h4">
					{i18n.translate('purpose')} <RequiredMask />{' '}
				</p>

				<ClayDropDown
					active={active}
					alignmentPosition={Align.BottomLeft}
					menuElementAttrs={{ className: 'dropdown-menu-purpose' }}
					onActiveChange={setActive}
					trigger={
						<ClayButton
							className="activation-key-form-select-input align-items-center app-type-dropdown d-flex justify-content-between rounded-lg w-100"
							displayType="secondary"
							onClick={() => setActive(!active)}
						>
							<div className="align-items-center d-flex justify-content-between w-100">
								<span>
									{
										PURPOSE_OPTIONS.find(
											(item) => item.value === purpose
										)?.title
									}
								</span>

								<ClayIcon symbol="caret-bottom" />
							</div>
						</ClayButton>
					}
				>
					<ClayDropDown.ItemList className="app-type-list-unstyled">
						{PURPOSE_OPTIONS.map((option, index) => (
							<ClayDropDown.Item
								className="d-flex flex-column"
								key={index}
								onClick={() => {
									setActive(false);

									setValue(
										'purpose',
										option.value,
										setValuesOptions
									);
								}}
							>
								<strong>{option.title}</strong>
								<span>{option.subtitle}</span>
							</ClayDropDown.Item>
						))}
					</ClayDropDown.ItemList>
				</ClayDropDown>

				{purpose === 'other' && (
					<textarea
						className="activation-key-form-textarea custom-input mt-5 rounded-lg w-100"
						{...register('purposeDescription')}
					/>
				)}

				<div className="align-items-center d-flex mt-2">
					<ClayCheckbox
						checked={notifyMeAboutProducts}
						id="notifyMeAboutProducts"
						onChange={(event) => {
							setValue(
								'notifyMeAboutProducts',
								event.target.checked
							);
						}}
					/>

					<label
						className="activation-key-form-label-check-box cursor-pointer font-weight-normal ml-2"
						htmlFor="notifyMeAboutProducts"
					>
						{i18n.translate(
							'notify-me-about-products-services-and-events'
						)}
					</label>
				</div>

				<span>
					<p className="activation-key-form-purpose-helper-text mb-6">
						You can stop receiving marketing emails by clicking the
						unsubscribe link in each email or withdraw your consent
						at any time by either using opt-out functionality
						accessible through the messages you receive or via email
						to
						<a
							className="ml-1"
							href="mailto:dataprotection@liferay.com"
						>
							dataprotection@liferay.com
						</a>
						. See{' '}
						<a
							href="https://www.liferay.com/privacy-policy"
							rel="noopener noreferrer"
							target="_blank"
						>
							privacy policy
						</a>{' '}
						for details.
					</p>
				</span>

				<p className="h4">
					{i18n.translate('activation-key-server-details')}
				</p>

				<hr className="mb-5 mt-3" />

				<ClayInput.Group>
					<ClayInput.GroupItem>
						<Input
							{...register('domain')}
							className="w-100"
							errorMessage={errors.domain?.message}
							helpMessage={i18n.translate(
								'input-one-domain-name-per-instance'
							)}
							label={i18n.translate('domain')}
							placeholder={i18n.translate('enter-domain-here')}
							required
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>

				<p className="activation-key-form-aggreements-text">
					<span>
						Your use of Liferay DXP is subject to these terms and
						the Liferay End User License Agreement set forth at
					</span>

					<a
						className="ml-1"
						href={productAgreements.links.eula}
						rel="noopener noreferrer"
						target="_blank"
					>
						{productAgreements.links.eula}
					</a>

					<span className="ml-1">{productAgreements.agreement}</span>
				</p>

				<div className="d-flex flex-row">
					<ClayCheckbox
						checked={termsAndConditions}
						className="activation-key-form-fail"
						id="terms-and-conditions"
						onChange={(event) => {
							setValue(
								'termsAndConditions',
								event.target.checked,
								setValuesOptions
							);
						}}
						required
					/>

					<label
						className={classNames('font-weight-normal px-1', {
							'text-red': isValid && !termsAndConditions,
						})}
						htmlFor="terms-and-conditions"
					>
						{i18n.translate(
							'i-have-read-and-agree-to-the-terms-and-conditions-above'
						)}

						<RequiredMask />
					</label>
				</div>

				<div className="d-flex flex-row">
					<ClayCheckbox
						checked={userAgreement}
						id="user-agreement"
						onChange={(event) => {
							setValue(
								'userAgreement',
								event.target.checked,
								setValuesOptions
							);
						}}
						required
					/>

					<label
						className={classNames('font-weight-normal px-1', {
							'text-red': isValid && !userAgreement,
						})}
						htmlFor="user-agreement"
					>
						I have read and agree to the{' '}
						<a
							href={productAgreements.links.userAgreement}
							onClick={(event) => event.stopPropagation()}
							rel="noopener noreferrer"
							target="_blank"
						>
							{i18n.translate('liferay-end-user-agreement')}
						</a>
						<RequiredMask />
					</label>
				</div>
			</ClayForm.Group>

			<ClayButton
				className="w-100"
				disabled={loading || !isValid}
				onClick={handleSubmit(onSubmit)}
			>
				<div className="align-items-center d-flex justify-content-center">
					<span>{i18n.translate('get-activation-key')}</span>

					<span className="ml-3">
						{loading && <ClayLoadingIndicator />}
					</span>
				</div>
			</ClayButton>
		</ProductPurchase.Shell>
	);
};

export default ActivationKeyFormDXP;
