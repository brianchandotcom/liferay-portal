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
import LicenseDetails from '../../../../CustomerDashboard/pages/Apps/App/Licenses/CreateLicense/LicenseDetails';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import {ProductPurchaseCMP} from '../../../services/ProductPurchaseCMP';

type LicenseKeyForm = z.infer<typeof zodSchema.generateLicenseKey>;

const ActivationKeyFormCMP = () => {
	const [loading, setLoading] = useState(false);
	const [termsAndConditions, setTermsAndConditions] = useState(false);

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
		mode: 'all',
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
					disabled: loading || !(isValid && termsAndConditions),
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
					This release of the Content Marketing Platform is a Beta.
					You acknowledge that Liferay is not obligated to provide
					support services while in Beta. For clarity, the
					Subscription benefits of any active Subscriptions you may
					have for Liferay Software shall not extend to cover the Beta
					release of the Content Marketing Platform and participation
					in the Beta does not grant access to any future GA version
					of the product
				</span>
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
					className={classNames(
						'activation-key-form-terms-and-conditions-text ml-2 pt-1',
						{
							'text-red': isValid && !termsAndConditions,
						}
					)}
					htmlFor="terms-and-conditions"
				>
					{i18n.translate(
						'i-have-read-and-agree-to-the-terms-and-conditions-above'
					)}

					<RequiredMask />
				</label>
			</div>
		</ProductPurchase.Shell>
	);
};

export default ActivationKeyFormCMP;
