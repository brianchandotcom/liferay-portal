/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import {useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';
import classNames from 'classnames';
import {zodResolver} from '@hookform/resolvers/zod';
import {z} from 'zod';
import {useNavigate, useOutletContext} from 'react-router-dom';

import Loading from '../../../../components/Loading';
import {useMarketplaceContext} from '../../../../context/MarketplaceContext';
import i18n from '../../../../i18n';
import {Liferay} from '../../../../liferay/liferay';
import zodSchema from '../../../../schema/zod';
import regions from '../../constants/regions';
import {Input} from '../../../../components/Input/Input';
import Select from '../../../../components/Select/Select';
import ProductPurchase from '../../../../components/ProductPurchase';
import ProductPurchaseAnalytics from '../../services/ProductPurchaseAnalytics';
import {ProductPurchaseOutletContext} from '../../ProductPurchaseOutlet';

type MultiSelectValue = {
	key: string;
	label: string;
	value: string;
};

const DATA_CENTER_OPTIONS = [
	{
		key: 'stg',
		name: 'STG',
	},
];

const countries = [...new Set([...regions.map(({country}) => country)])].sort();

const AnalyticsProvisioning = () => {
	const navigate = useNavigate();
	const {selectedAccount, product} =
		useOutletContext<ProductPurchaseOutletContext>();
	const {channel, properties} = useMarketplaceContext();
	const [allowedEmailDomainsText, setAllowedEmailDomainsText] = useState('');
	const [incidentReportContactsText, setIncidentReportContactsText] =
		useState('');

	const {formState, handleSubmit, register, setValue, watch} = useForm<
		z.infer<typeof zodSchema.analyticsProvisioning>
	>({
		defaultValues: {
			_refAllowedEmailDomains: [],
			_refIncidentReportContacts: [],
			allowedEmailDomains: [],
			incidentReportContacts: [],
			dataCenterLocation: DATA_CENTER_OPTIONS[0].key,
			workspaceOwnerEmail: Liferay.ThemeDisplay.getUserEmailAddress(),
		},
		mode: 'all',
		resolver: zodResolver(zodSchema.analyticsProvisioning),
	});

	const _refAllowedEmailDomains = watch('_refAllowedEmailDomains');
	const _refIncidentReportContacts = watch('_refIncidentReportContacts');
	const timezone = watch('timezone');

	const regionOptions = regions
		.filter((region) => region.country === timezone)
		.map((region) => ({
			key: region.timeZoneId,
			name: region.displayTimeZone,
		}));

	const onSubmit = async (
		form: z.infer<typeof zodSchema.analyticsProvisioning>
	) => {
		try {
			const productPurchase = new ProductPurchaseAnalytics(
				selectedAccount,
				channel,
				product
			);

			const order = await productPurchase.create(form);

			Liferay.Util.openToast({
				message: i18n.translate('your-request-completed-successfully'),
				type: 'success',
			});

			navigate(`/thank-you?orderId=${order.id}`);
		}
		catch (error) {
			console.error(error);

			Liferay.Util.openToast({
				message: i18n.translate('an-unexpected-error-occurred'),
				type: 'danger',
			});
		}
	};

	useEffect(() => {
		if (timezone && regionOptions.length) {
			setValue('region', regionOptions[0].key);
		}
	}, [timezone]);

	return (
		<ProductPurchase.Shell
			title="Create an Analytics Cloud Workspace"
			footerProps={{
				continueButtonProps: {
					onClick: handleSubmit(onSubmit),
					children: 'Finish Setup',
					disabled: formState.isSubmitting,
					type: 'submit',
				},
			}}
		>
			{formState.isSubmitting && (
				<Loading.FullScreen>
					Hang tight, <b>Analytics Cloud</b> workspace is being
					provisioned by <b>Marketplace</b>.
				</Loading.FullScreen>
			)}

			<h3 className="sheet-subtitle">General</h3>

			<Input
				{...register('workspaceName')}
				errorMessage={formState.errors.workspaceName?.message}
				label="Workspace Name"
				required
			/>

			<Input
				{...register('workspaceOwnerEmail')}
				disabled
				label="Workspace Owner Email"
			/>

			<Select
				{...register('dataCenterLocation')}
				boldLabel
				helpText={`Select a server to store your data. This could have implications to your organization's policy on user data storage.`}
				label="Data Center Location"
				options={DATA_CENTER_OPTIONS}
				required
			/>

			<div>
				<div className="d-flex flex-column">
					<label htmlFor="timezone">Timezone</label>
					<small>
						Select a timezone that will be used for all data
						reporting in your workspace.
					</small>
				</div>

				<div className="row">
					<div className="col-3">
						<Select
							{...register('timezone')}
							id="timezone"
							defaultValue="UTC"
							options={countries.map((country) => ({
								key: country,
								name: country,
							}))}
						/>
					</div>

					<div className="col-9">
						<Select
							{...register('region')}
							options={regionOptions}
						/>
					</div>
				</div>
			</div>

			<Input
				{...register('friendlyWorkspaceURL')}
				errorMessage={formState.errors.friendlyWorkspaceURL?.message}
				helpMessage={`You can only set your friendly workspace URL once. ${properties.analyticsCloudURL}/workspace`}
				label="Set a Friendly Workspace URL"
				prependGroupItemSymbol="/"
				required
			/>

			<ClayInput.Group
				className={classNames('mt-4', {
					'has-error': formState.errors.allowedEmailDomains?.message,
				})}
			>
				<div className="d-flex flex-column">
					<label htmlFor="allowed-email-domains">
						Allowed Email Domains
					</label>
					<small>
						Anyone with an email address at these domains can
						request access to your workspace.
					</small>
				</div>

				<ClayInput.Group>
					<ClayInput.GroupItem prepend shrink>
						<ClayInput.GroupText>@</ClayInput.GroupText>
					</ClayInput.GroupItem>

					<ClayInput.GroupItem prepend>
						<ClayMultiSelect
							id="allowed-email-domains"
							items={_refAllowedEmailDomains}
							onChange={setAllowedEmailDomainsText}
							onItemsChange={(values: MultiSelectValue[]) => {
								setValue('_refAllowedEmailDomains', values);

								setValue(
									'allowedEmailDomains',
									values.map(({value}) => value)
								);
							}}
							value={allowedEmailDomainsText}
						/>
					</ClayInput.GroupItem>
				</ClayInput.Group>

				<ClayForm.FeedbackItem>
					{formState.errors.allowedEmailDomains?.message}
				</ClayForm.FeedbackItem>
			</ClayInput.Group>

			<h3 className="mt-4 sheet-subtitle">Security</h3>

			<ClayForm.Group
				className={classNames('mt-4', {
					'has-error':
						formState.errors.incidentReportContacts?.message,
				})}
			>
				<div className="d-flex flex-column">
					<label htmlFor="incident-report-contacts">
						Add Incident Report Contacts
					</label>
					<small>
						Who should we contact in case of a security breach?
					</small>
				</div>

				<ClayMultiSelect
					id="incident-report-contacts"
					items={_refIncidentReportContacts}
					onChange={setIncidentReportContactsText}
					onItemsChange={(values: MultiSelectValue[]) => {
						setValue('_refIncidentReportContacts', values);

						setValue(
							'incidentReportContacts',
							values.map(({value}) => value)
						);
					}}
					value={incidentReportContactsText}
				/>

				<ClayForm.FeedbackItem>
					{formState.errors.incidentReportContacts?.message}
				</ClayForm.FeedbackItem>
			</ClayForm.Group>
		</ProductPurchase.Shell>
	);
};

export default AnalyticsProvisioning;
