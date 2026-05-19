/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';
import classNames from 'classnames';
import {Controller, useForm} from 'react-hook-form';
import useSWR from 'swr';

import {RequiredMask} from '../../../../components/FieldBase';
import {Input} from '../../../../components/Input/Input';
import Loading from '../../../../components/Loading';
import ProductPurchase from '../../../../components/ProductPurchase';
import Select from '../../../../components/Select/Select';
import useListTypeDefinition from '../../../../hooks/useListTypeDefinition';
import i18n from '../../../../i18n';
import {Liferay} from '../../../../liferay/liferay';
import zodSchema, {z, zodResolver} from '../../../../schema/zod';
import analyticsOAuth2 from '../../../../services/oauth/Analytics';
import {useProductPurchaseOutletContext} from '../../ProductPurchaseOutlet';
import ProductPurchaseDSR from '../../services/ProductPurchaseDSR';

type DSRForm = z.infer<typeof zodSchema.dsrLicenseKey>;

const EULA_URL = 'https://www.liferay.com/legal';

const DSRLicenseKeyForm = () => {
	const {
		actions: {previousStep},
		handlePurchase,

		product,
		productPurchaseCart,
		selectedAccount,
		setForm,
	} = useProductPurchaseOutletContext();

	const {data: acRegionsResponse, isLoading: isLoadingRegions} =
		useListTypeDefinition('AC-REGIONS');

	const acRegions = acRegionsResponse?.listTypeEntries ?? [];

	const accountKey = selectedAccount.externalReferenceCode;

	const {
		data: analyticsPlan,
		error,
		isLoading,
	} = useSWR(
		accountKey ? `/project-corp-project-uuid/${accountKey}` : null,
		async () => {
			return analyticsOAuth2.getProjectCorpProjectUuid(accountKey);
		},
		{
			shouldRetryOnError: false,
		}
	);

	const hasAnalyticsCloud = !!analyticsPlan && !error;

	const {
		control,
		formState: {errors, isValid},
		handleSubmit,
		register,
	} = useForm<DSRForm>({
		defaultValues: {
			acceptTermsAndConditions: false,
			dataCenterLocation: '',
			hostname: '',
			ipAddress: '',
			macAddress: '',
			workspaceName: '',
			workspaceOwnerEmail: Liferay.ThemeDisplay.getUserEmailAddress(),
		},
		mode: 'all',
		resolver: zodResolver(
			hasAnalyticsCloud
				? zodSchema.dsrLicenseKeyServerOnly
				: zodSchema.dsrLicenseKey
		),
	});

	const onSubmit = async (data: DSRForm) => {
		setForm((current) => ({...current, ...data}));

		const productPurchase = new ProductPurchaseDSR(
			selectedAccount,
			product
		);

		productPurchase.setForm(data);

		productPurchase.setHasAnalyticsCloud(hasAnalyticsCloud);

		await handlePurchase(
			productPurchase,
			productPurchaseCart.cart,
			undefined
		);
	};

	if (isLoadingRegions || isLoading) {
		return <Loading />;
	}

	return (
		<ProductPurchase.Shell
			className="activation-key-form"
			footerProps={{
				backButtonProps: {onClick: previousStep},
				continueButtonProps: {
					children: i18n.translate('try-beta'),
					disabled: !isValid,
					onClick: handleSubmit(onSubmit),
				},
			}}
			title={i18n.translate('activation-key-creation')}
		>
			<p className="mb-6 text-black-50">
				{i18n.translate(
					'to-generate-your-unique-activation-key-file-please-provide-the-technical-specifications-required-below-these-details-are-used-exclusively-to-configure-the-software-for-your-environment-and-ensure-hardware-compatibility'
				)}
			</p>

			{!hasAnalyticsCloud && (
				<>
					<div className="h4">
						{i18n.translate('environment-details')}
					</div>

					<hr className="mt-2" />

					<Input
						{...register('workspaceName')}
						errorMessage={errors.workspaceName?.message}
						label={i18n.translate('workspace-name')}
						required
					/>

					<Input
						{...register('workspaceOwnerEmail')}
						errorMessage={errors.workspaceOwnerEmail?.message}
						label={i18n.translate('workspace-owner-email')}
						required
					/>

					<Select
						className="custom-input"
						{...register('dataCenterLocation')}
						boldLabel
						helpText={i18n.translate(
							'select-a-server-to-store-your-data-this-could-have-implications-to-your-organizations-policy-on-user-data-storage'
						)}
						label={i18n.translate('data-center-location')}
						options={acRegions.map(
							({externalReferenceCode, name}) => ({
								key: externalReferenceCode,
								name,
							})
						)}
						required
					/>
				</>
			)}

			<p
				className={classNames('h4', {
					'mt-7': !hasAnalyticsCloud,
				})}
			>
				{i18n.translate('activation-key-server-details')}
			</p>

			<small>
				{i18n.translate(
					'please-complete-at-least-one-of-the-following-fields-to-proceed'
				)}
			</small>

			<hr className="mt-2" />

			<Input
				{...register('hostname')}
				errorMessage={errors.hostname?.message}
				helpMessage={i18n.translate('input-one-host-name-per-instance')}
				label={i18n.translate('host-name')}
				placeholder="HOST-DSR-01"
			/>

			<Input
				{...register('ipAddress')}
				component="textarea"
				errorMessage={errors.ipAddress?.message}
				helpMessage={i18n.translate(
					'add-one-ip-address-per-line-ipv-six-addresses-are-not-supported'
				)}
				label={i18n.translate('ip-addresses')}
				placeholder={'1.1.1.1\n2.2.2.2'}
			/>

			<Input
				{...register('macAddress')}
				component="textarea"
				errorMessage={errors.macAddress?.message}
				helpMessage={i18n.translate('add-one-mac-address-per-line')}
				label={i18n.translate('mac-addresses')}
				placeholder={'XX-XX-XX-XX-XX-XX\nXX-XX-XX-XX-XX-XX'}
			/>

			<p className="activation-key-form-aggreements-text mt-4">
				{i18n.translate('liferay-dsr-eula-disclaimer-prefix')}{' '}
				<a href={EULA_URL} rel="noreferrer" target="_blank">
					Legal | Liferay
				</a>{' '}
				{i18n.translate('liferay-dsr-eula-disclaimer-suffix')}
			</p>

			<div className="align-items-center d-flex flex-row">
				<Controller
					control={control}
					name="acceptTermsAndConditions"
					render={({field: {onChange, value}}) => (
						<ClayCheckbox
							checked={!!value}
							id="accept-terms-and-conditions"
							onChange={(event) => onChange(event.target.checked)}
						/>
					)}
				/>

				<label
					className={classNames('mb-1 ml-2', {
						'text-red': !!errors.acceptTermsAndConditions,
					})}
					htmlFor="accept-terms-and-conditions"
					style={{fontSize: 12, fontWeight: 400}}
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

export default DSRLicenseKeyForm;
