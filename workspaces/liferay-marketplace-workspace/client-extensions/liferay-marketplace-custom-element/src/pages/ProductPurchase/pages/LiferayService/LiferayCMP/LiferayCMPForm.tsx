/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import classNames from 'classnames';
import {useState} from 'react';
import {useForm} from 'react-hook-form';
import {useNavigate} from 'react-router-dom';

import ProductPurchase from '../../../../../components/ProductPurchase';
import i18n from '../../../../../i18n';
import zodSchema, {z, zodResolver} from '../../../../../schema/zod';
import LicenseDetails from '../../../../CustomerDashboard/pages/Apps/App/Licenses/CreateLicense/LicenseDetails';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import {ProductPurchaseCMP} from '../../../services/ProductPurchaseCMP';

const LiferayCMPForm = () => {
	const navigate = useNavigate();

	const {handlePurchase, product, selectedAccount} =
		useProductPurchaseOutletContext();

	const {
		formState: {errors, isValid},
		handleSubmit,
		register,
	} = useForm<z.infer<typeof zodSchema.generateLicenseKey>>({
		defaultValues: {
			description: '',
			hostname: '',
			ipAddress: '',
			macAddress: '',
			subscription: undefined,
		},
		resolver: zodResolver(zodSchema.generateLicenseKey),
	});

	const [termsAndConditions, setTermsAndConditions] = useState(false);
	const [userAgreement, setUserAgreement] = useState(false);
	const [loading, setLoading] = useState(false);

	const onSubmit = async (data: any) => {
		setLoading(true);

		try {
			const productPurchaseCMP = new ProductPurchaseCMP(
				selectedAccount,
				product
			);
			productPurchaseCMP.setForm(data);
			await handlePurchase(productPurchaseCMP);
		}
		catch (error) {
			console.log(error);
		}
		finally {
			setLoading(false);
		}
	};

	const inputProps = {
		errors,
		register,
		required: true,
	};

	return (
		<ProductPurchase.Shell
			footerProps={{
				continueButtonProps: {
					disabled:
						loading ||
						!(isValid && termsAndConditions && userAgreement),
					onClick: handleSubmit((data) => onSubmit(data)),
				},
				backButtonProps: {
					onClick: () => navigate('/'),
				},
			}}
			title={i18n.translate('activation-key-creation')}
		>
			<div className="dxp-free-form">
				<p className="mb-6 text-black-50">
					{i18n.translate(
						'to-generate-your-unique-activation-key-file-and-access-the-download-please-complete-your-profile-details-below-tell-us-a-bit-about-your-intended-use-to-help-us-support-your-experience'
					)}
				</p>

				<LicenseDetails inputProps={inputProps as any} />

				<p className="dxp-free-form-aggreements-text">
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
						"agreement"). Please read these terms and the Liferay
						End User License Agreement carefully before accessing,
						downloading, installing or in any way using the
						software. By clicking your assent or accessing,
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

				<ClayForm.Group>
					<ClayInput.GroupItem className="w-100">
						<label
							className="d-flex font-weight-normal w-100"
							style={{cursor: 'pointer'}}
						>
							<ClayCheckbox
								checked={termsAndConditions}
								className="dxp-free-form-fail"
								onChange={(e) => {
									setTermsAndConditions(!termsAndConditions);
								}}
								required
							/>
							<span className="align-items-center d-flex dxp-free-form-aggreements-check-box justify-content-center mb-0 ml-2">
								<p
									className={classNames(
										'align-items-center d-flex justify-content-center mb-1',
										{
											'text-red':
												isValid === true &&
												termsAndConditions === false,
										}
									)}
								>
									{i18n.translate(
										'i-have-read-and-agree-to-the-terms-and-conditions-above'
									)}
								</p>
								<p className="align-items-center d-flex font-weight-bold justify-content-center mb-1 text-red">
									*
								</p>
							</span>
						</label>
						<label
							className="d-flex font-weight-normal"
							style={{cursor: 'pointer'}}
						>
							<ClayCheckbox
								checked={userAgreement}
								onChange={(e) => {
									setUserAgreement(!userAgreement);
								}}
								required
							/>
							<span className="align-items-center d-flex dxp-free-form-aggreements-check-box justify-content-center mb-0 ml-2">
								<p
									className={classNames(
										'align-items-center d-flex justify-content-center mb-1',
										{
											'text-red':
												isValid === true &&
												userAgreement === false,
										}
									)}
								>
									{i18n.translate(
										'i-have-read-and-agree-to-the-liferay-end-user-agreement'
									)}

									<a
										className="ml-1"
										href="seu-link-aqui"
										onClick={(e) => e.stopPropagation()}
										rel="noopener noreferrer"
										target="_blank"
									>
										{i18n.translate(
											'liferay-end-user-agreement'
										)}
									</a>
								</p>
								<p className="align-items-center d-flex font-weight-bold justify-content-center mb-1 text-red">
									*
								</p>
							</span>
						</label>
					</ClayInput.GroupItem>
				</ClayForm.Group>
			</div>
		</ProductPurchase.Shell>
	);
};

export default LiferayCMPForm;
