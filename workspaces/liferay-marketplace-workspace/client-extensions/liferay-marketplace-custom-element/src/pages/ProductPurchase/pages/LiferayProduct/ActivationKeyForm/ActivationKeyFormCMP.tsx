/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import classNames from 'classnames';
import {useState} from 'react';
import {useForm} from 'react-hook-form';
import {useNavigate} from 'react-router-dom';

import {RequiredMask} from '../../../../../components/FieldBase';
import ProductPurchase from '../../../../../components/ProductPurchase';
import i18n from '../../../../../i18n';
import zodSchema, {z, zodResolver} from '../../../../../schema/zod';
import {productAgreements} from '../../../../../utils/agreements';
import LicenseDetails from '../../../../CustomerDashboard/pages/Apps/App/Licenses/CreateLicense/LicenseDetails';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import {ProductPurchaseCMP} from '../../../services/ProductPurchaseCMP';

type LicenseKeyForm = z.infer<typeof zodSchema.generateLicenseKey>;

const ActivationKeyFormCMP = () => {
	const [loading, setLoading] = useState(false);
	const [termsAndConditions, setTermsAndConditions] = useState(false);
	const [userAgreement, setUserAgreement] = useState(false);

	const navigate = useNavigate();

	const {handlePurchase, product, selectedAccount} =
		useProductPurchaseOutletContext();

	const {
		formState: {errors, isValid},
		handleSubmit,
		register,
	} = useForm<LicenseKeyForm>({
		defaultValues: {
			description: '',
			hostname: '',
			ipAddress: '',
			macAddress: '',
			subscription: undefined,
		},
		resolver: zodResolver(zodSchema.generateLicenseKey),
	});

	const onSubmit = (data: LicenseKeyForm) => {
		setLoading(true);

		const productPurchase = new ProductPurchaseCMP(
			selectedAccount,
			product
		);

		productPurchase.setForm(data);

		handlePurchase(productPurchase)
			.catch(console.error)
			.finally(() => setLoading(false));
	};

	const inputProps = {
		errors,
		register,
		required: true,
	};

	return (
		<ProductPurchase.Shell
			className="activation-key-form"
			footerProps={{
				backButtonProps: {
					onClick: () => navigate('/'),
				},
				continueButtonProps: {
					children: 'Try Beta',
					disabled:
						loading ||
						!(isValid && termsAndConditions && userAgreement),
					onClick: handleSubmit((data) => onSubmit(data)),
				},
			}}
			title={i18n.translate('activation-key-creation')}
		>
			<p className="mb-6 text-black-50">
				{i18n.translate(
					'to-generate-your-unique-activation-key-file-and-access-the-download-please-complete-your-profile-details-below-tell-us-a-bit-about-your-intended-use-to-help-us-support-your-experience'
				)}
			</p>

			<LicenseDetails inputProps={inputProps as any} />

			<p className="activation-key-form-aggreements-text">
				<span>
					Your use of Liferay DXP is subject to these terms and the
					Liferay End User License Agreement set forth at
				</span>

				<a
					className="ml-1"
					href={productAgreements.links.eula}
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
					onChange={() => setTermsAndConditions(!termsAndConditions)}
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
					onChange={() => setUserAgreement(!userAgreement)}
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
		</ProductPurchase.Shell>
	);
};

export default ActivationKeyFormCMP;
