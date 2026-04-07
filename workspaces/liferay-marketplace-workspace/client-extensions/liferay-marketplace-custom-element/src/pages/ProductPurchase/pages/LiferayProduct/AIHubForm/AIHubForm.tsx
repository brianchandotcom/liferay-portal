/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {zodResolver} from '@hookform/resolvers/zod';
import classNames from 'classnames';
import {useState} from 'react';
import {useForm} from 'react-hook-form';

import {RequiredMask} from '../../../../../components/FieldBase';
import {Input} from '../../../../../components/Input/Input';
import ProductPurchase from '../../../../../components/ProductPurchase';
import Select from '../../../../../components/Select/Select';
import useCommerceRegions from '../../../../../hooks/useCommerceRegions';
import i18n from '../../../../../i18n';
import {Liferay} from '../../../../../liferay/liferay';
import zodSchema, {z} from '../../../../../schema/zod';
import {productAgreements} from '../../../../../utils/agreements';
import {phones} from '../../../../../utils/phones';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import {ProductPurchaseAIHub} from '../../../services/ProductPurchaseAIHub';
import {PURPOSE_OPTIONS} from '../ActivationKeyForm/constants';

import './AIHubForm.scss';

import '../index.scss';

const setValuesOptions = {
	shouldDirty: true,
	shouldValidate: true,
};

const AIHubForm = () => {
	const {
		formState: {errors, isValid},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<z.infer<typeof zodSchema.aiHubForm>>({
		defaultValues: {
			administratorEmailAddress:
				Liferay.ThemeDisplay.getUserEmailAddress(),
			aiHubAccountName: '',
			businessEmailAddress: Liferay.ThemeDisplay.getUserEmailAddress(),
			companyName: '',
			country: '',
			extension: '',
			fullName: Liferay.ThemeDisplay.getUserName(),
			intlCode: {code: '+1', flag: 'en-us'},
			jobTitle: '',
			phoneNumber: '',
			purpose: '',
			termsAndConditions: false,
			userAgreement: false,
		},
		mode: 'all',
		reValidateMode: 'onChange',
		resolver: zodResolver(zodSchema.aiHubForm),
	});

	const watchedValues = watch();

	const {intlCode, purpose, termsAndConditions, userAgreement} =
		watchedValues;

	const [active, setActive] = useState(false);
	const [currentPhonesFlags, setCurrentPhonesFlags] = useState(intlCode);
	const [loading, setLoading] = useState(false);
	const {data: regionsResponse} = useCommerceRegions();
	const {handlePurchase, product, selectedAccount} =
		useProductPurchaseOutletContext();

	const countries = regionsResponse?.items ?? [];

	const onSubmit = async (form: z.infer<typeof zodSchema.aiHubForm>) => {
		setLoading(true);

		try {
			const productPurchase = new ProductPurchaseAIHub(
				selectedAccount,
				product
			);

			productPurchase.setForm(form);

			await handlePurchase(productPurchase);
		}
		catch (error) {
			console.error(error);
		}

		setLoading(false);
	};

	return (
		<ProductPurchase.Shell
			className="liferay-ai-hub-form"
			title={i18n.translate('request-access-to-ai-hub-private-beta')}
		>
			<p className="mb-6 text-black-50">
				{i18n.translate(
					'submit-your-request-to-join-the-beta-program-all-submissions-will-be-reviewed-and-youll-receive-an-email-with-the-outcome'
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

				<p className="h4">
					{i18n.translate('purpose')} <RequiredMask />
				</p>

				<ClayDropDown
					active={active}
					alignmentPosition={Align.BottomLeft}
					className="w-100"
					menuElementAttrs={{className: 'dropdown-menu-purpose'}}
					onActiveChange={setActive}
					trigger={
						<ClayButton
							className="align-items-center d-flex justify-content-between liferay-ai-hub-form-select-input rounded-lg w-100"
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
					<ClayDropDown.ItemList>
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

				<p className="h4 mt-6">
					{i18n.translate('ai-hub-information')}
				</p>

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

				<p className="liferay-ai-hub-form-aggreements-text">
					<span>Please read</span>

					<span className="ml-1">
						carefully before accessing or in any way using the AI
						Hub Private Beta experience.
					</span>
				</p>

				<div className="d-flex flex-row mb-3 text-justify">
					<ClayCheckbox
						checked={termsAndConditions}
						className="liferay-ai-hub-form-fail"
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
						I signify my assent to and acceptance of
						<a
							className="mx-1"
							href={productAgreements.links.aiHub.agreement}
							target="_blank"
						>
							this agreement
						</a>
						and acknowledge that I have read and you understand the
						terms. If I am an individual acting on behalf of an
						entity, I represent that I have the authority to enter
						into this agreement on behalf of that entity.
						<RequiredMask />
					</label>
				</div>

				<div className="d-flex flex-row text-justify">
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
						{i18n.translate(
							'i-agree-to-the-processing-of-my-personal-data-for-the-purpose-of-evaluating-my-beta-access-request-in-accordance-with-liferay’s-privacy-policy'
						)}
						<RequiredMask />
					</label>
				</div>
			</ClayForm.Group>

			<p className="liferay-ai-hub-form-aggreements-text text-justify">
				<span>
					You can stop receiving marketing emails by clicking the
					unsubscribe link in each email or withdraw your consent at
					any time by either using opt-out functionality accessible
					through the messages you receive or via email to
				</span>

				<a className="ml-1" href="mailto:dataprotection@liferay.com">
					dataprotection@liferay.com
				</a>

				<span className="ml-1">See</span>

				<a
					className="ml-1"
					href={productAgreements.links.eula}
					target="_blank"
				>
					privacy policy for details.
				</a>

				<span className="ml-1">
					carefully before accessing or in any way using the AI Hub
					Private Beta experience.
				</span>
			</p>

			<ClayButton
				className="w-100"
				disabled={loading || !isValid}
				onClick={handleSubmit(onSubmit)}
			>
				<div className="align-items-center d-flex justify-content-center">
					<span>{i18n.translate('send-request')}</span>

					<span className="ml-3">
						{loading && <ClayLoadingIndicator />}
					</span>
				</div>
			</ClayButton>
		</ProductPurchase.Shell>
	);
};

export default AIHubForm;
