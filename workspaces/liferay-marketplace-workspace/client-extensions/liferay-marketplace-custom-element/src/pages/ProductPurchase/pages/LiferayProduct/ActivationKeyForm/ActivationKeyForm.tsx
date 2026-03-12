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

import {Input} from '../../../../../components/Input/Input';
import ProductPurchase from '../../../../../components/ProductPurchase';
import i18n from '../../../../../i18n';
import zodSchema, {z} from '../../../../../schema/zod';
import {phones} from '../../../../../utils/phones';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import ProductPurchaseDXPTypeFree from '../../../services/ProductPurchaseDXPTypeFree';
import {PURPOSE_OPTIONS} from './constants';

import './ActivationKeyForm.scss';

const ActivationKeyForm = () => {
	const [active, setActive] = useState(false);
	const [loading, setLoading] = useState(false);

	const {handlePurchase, product, selectedAccount} =
		useProductPurchaseOutletContext();

	const {
		formState: {errors, isValid},
		handleSubmit,
		register,
		setValue,
		watch,
	} = useForm<z.infer<typeof zodSchema.activationKey>>({
		defaultValues: {
			businessEmail: '',
			companyName: '',
			country: '',
			domain: '',
			extension: '',
			fullname: '',
			intlCode: {code: '+1', flag: 'en-us'},
			jobTitle: '',
			notifyMeAboutProducts: false,
			phoneNumber: '',
			purpose: '',
			purposeOther: '',
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

			<p className="h3 mb-0">
				{i18n.translate('personal-information-purpose')}
			</p>

			<hr className="mb-5 mt-3" />

			<ClayForm.Group>
				<Input
					{...register('fullname')}
					className="w-100"
					errorMessage={errors.fullname?.message}
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

				<p className="h3">{i18n.translate('phone')}</p>

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

				<p className="h3">{i18n.translate('purpose')}</p>

				<ClayDropDown
					active={active}
					alignmentPosition={Align.BottomLeft}
					className="app-type-dropdown provide-app-build-page-cloud-compatible-container w-100"
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

									setValue('purpose', option.value);
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
						{...register('purposeOther')}
					/>
				)}

				<label className="align-items-center cursor-pointer d-flex flex-row font-weight-normal justify-content-between mt-2">
					<ClayCheckbox
						checked={notifyMeAboutProducts}
						onChange={(event) => {
							setValue(
								'notifyMeAboutProducts',
								event.target.checked
							);
						}}
					/>

					<p className="activation-key-form-notify-me-check-box mb-1 ml-2 w-100">
						{i18n.translate(
							'notify-me-about-products-services-and-events'
						)}
					</p>
				</label>

				<span>
					<p className="activation-key-form-purpose-helper-text mb-6">
						You can stop receiving marketing emails by clicking the
						unsubscribe link in each email or withdraw your consent
						at any time by either using opt-out functionality
						accessible through the messages you receive or via email
						to
						<a
							className="ml-1"
							href="https://www.liferay.com/privacy-policy"
						>
							dataprotection@liferay.com
						</a>
						. See{' '}
						<a href="https://www.liferay.com/privacy-policy">
							privacy policy
						</a>{' '}
						for details.
					</p>
				</span>

				<p className="h3">
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
						href="https://www.liferay.com/documents/d/guest/Liferay-EULA-2102602_GL"
					>
						https://www.liferay.com/documents/d/guest/Liferay-EULA-2102602_GL
					</a>

					<span className="ml-1">
						(these terms and the eula together form the
						&quot;agreement&quot;). Please read these terms and the
						Liferay End User License Agreement carefully before
						accessing, downloading, installing or in any way using
						the software. By clicking your assent or accessing,
						downloading, installing or in any way using the
						software, you signify your assent to and acceptance of
						the agreement and acknowledge that you have read and you
						understand terms of the agreement. If you are an
						individual acting on behalf of an entity, you represent
						that you have the authority to enter into this agreement
						on behalf of that entity. If you do not accept the terms
						of this agreement, then you must not access, download,
						install or in any way use the software. I have read and
						agree to all the terms and conditions below (check all
						boxes).
					</span>
				</p>

				<label className="cursor-pointer d-flex font-weight-normal w-100">
					<ClayCheckbox
						checked={termsAndConditions}
						className="activation-key-form-fail"
						onChange={(event) => {
							setValue(
								'termsAndConditions',
								event.target.checked
							);
						}}
						required
					/>

					<span className="activation-key-form-aggreements-check-box align-items-center d-flex justify-content-center mb-0 ml-2">
						<p
							className={classNames(
								'align-items-center d-flex justify-content-center mb-1',
								{
									'text-red':
										errors.termsAndConditions?.message,
								}
							)}
						>
							{i18n.translate(
								'i-have-read-and-agree-to-the-terms-and-conditions-above'
							)}
						</p>

						<span className="font-weight-bold text-red">*</span>
					</span>
				</label>

				<label className="cursor-pointer d-flex font-weight-normal">
					<ClayCheckbox
						checked={userAgreement}
						onChange={(event) => {
							setValue('userAgreement', event.target.checked);
						}}
						required
					/>

					<span className="activation-key-form-aggreements-check-box align-items-center d-flex justify-content-center mb-0 ml-2">
						<p
							className={classNames(
								'align-items-center d-flex justify-content-center mb-1',
								{
									'text-red': errors.userAgreement?.message,
								}
							)}
						>
							{i18n.translate(
								'i-have-read-and-agree-to-the-liferay-end-user-agreement'
							)}

							<a
								className="ml-1"
								onClick={(event) => event.stopPropagation()}
								rel="noopener noreferrer"
								target="_blank"
							>
								{i18n.translate('liferay-end-user-agreement')}
							</a>
						</p>
						<p className="align-items-center d-flex font-weight-bold justify-content-center mb-1 text-red">
							*
						</p>
					</span>
				</label>
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

export default ActivationKeyForm;
