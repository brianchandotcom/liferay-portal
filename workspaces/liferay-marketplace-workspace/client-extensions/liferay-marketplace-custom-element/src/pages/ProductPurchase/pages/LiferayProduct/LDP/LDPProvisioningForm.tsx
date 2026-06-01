/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import {zodResolver} from '@hookform/resolvers/zod';
import classNames from 'classnames';
import {useState} from 'react';
import {useForm} from 'react-hook-form';
import {Navigate} from 'react-router-dom';
import {z} from 'zod';

import HelpPopover from '../../../../../components/HelpPopover';
import {Input} from '../../../../../components/Input/Input';
import Loading from '../../../../../components/Loading';
import ProductPurchase from '../../../../../components/ProductPurchase';
import i18n from '../../../../../i18n';
import {Liferay} from '../../../../../liferay/liferay';
import zodSchema from '../../../../../schema/zod';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import ProductPurchaseLDP from '../../../services/ProductPurchaseLDP';

type MultiSelectValue = {
	key: string;
	label: string;
	value: string;
};

const LDPProvisioning = () => {
	const [incidentReportContactsText, setIncidentReportContactsText] =
		useState('');

	const {
		actions: {previousStep},
		handlePurchase,
		product,
		selectedAccount,
		setForm,
	} = useProductPurchaseOutletContext();

	const accountKey = selectedAccount.externalReferenceCode;

	const {formState, handleSubmit, register, setValue, watch} = useForm<
		z.infer<typeof zodSchema.ldpProvisioning>
	>({
		defaultValues: {
			_refAllowedEmailDomains: [],
			_refIncidentReportContacts: [],
			agreementAcceptance: false,
			allowedEmailDomains: [],
			dataCenterLocation: 'INTERNAL',
			dataProcessingConsent: false,
			friendlyWorkspaceURL: '',
			incidentReportContacts: [],
			timezone: 'UTC',
			workspaceOwnerEmail: Liferay.ThemeDisplay.getUserEmailAddress(),
		},
		mode: 'all',
		resolver: zodResolver(zodSchema.ldpProvisioning),
	});

	const _refIncidentReportContacts = watch('_refIncidentReportContacts');

	const onSubmit = async (
		form: z.infer<typeof zodSchema.ldpProvisioning>
	) => {
		setForm(form);

		const productPurchase = new ProductPurchaseLDP(
			selectedAccount,
			product
		);

		productPurchase.setForm(form);

		await handlePurchase(productPurchase);
	};

	if (!selectedAccount) {
		return <Navigate to="/" />;
	}

	if (!accountKey) {
		return <Loading />;
	}

	return (
		<ProductPurchase.Shell
			footerProps={{
				backButtonProps: {onClick: previousStep},
				continueButtonProps: {
					children: i18n.translate('try-beta'),
					disabled: !formState.isValid,
					onClick: handleSubmit(onSubmit),
					type: 'submit',
				},
			}}
			title="Provisioning"
		>
			<h3 className="sheet-subtitle">General</h3>

			<ClayAlert displayType="info" title="Data Center Location:">
				Our LDP private beta is hosted in Oregon (USA). This could have
				implications to your organization&apos;s policy on user data
				storage.
			</ClayAlert>

			<Input
				{...register('workspaceName')}
				errorMessage={formState.errors?.workspaceName?.message}
				label="Workspace Name"
				required
			/>

			<Input
				{...register('workspaceOwnerEmail')}
				errorMessage={formState.errors?.workspaceOwnerEmail?.message}
				label="Workspace Owner Email"
				required
			/>

			<Input
				{...register('friendlyWorkspaceURL')}
				description={
					<div className="d-flex flex-column">
						<span>
							You can only set your friendly workspace URL once.
						</span>

						<span className="gray my-1">
							https://analytics-internal.liferay.com/workspace
						</span>
					</div>
				}
				errorMessage={formState.errors?.friendlyWorkspaceURL?.message}
				label="Set a Friendly Workspace URL"
				prependGroupItemSymbol="/"
			/>

			<h3 className="mt-4 sheet-subtitle">Security</h3>

			<ClayForm.Group
				className={classNames('mt-4', {
					'has-error':
						formState.errors.incidentReportContacts?.length ||
						formState.errors.incidentReportContacts?.message,
				})}
			>
				<div className="d-flex flex-column">
					<div>
						<label
							className="mr-1 required"
							htmlFor="incident-report-contacts"
						>
							Add Incident Report Contacts{' '}
						</label>

						<HelpPopover header="Incident Report Contact">
							<span>
								This person will be contacted in the event of:
							</span>

							<ul>
								<li>Services interruptions;</li>
								<li>Security incidents;</li>
								<li>
									Other urgent service updates that require
									action.
								</li>
							</ul>
						</HelpPopover>
					</div>

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
					{Array.isArray(formState.errors.incidentReportContacts)
						? formState.errors.incidentReportContacts?.[0]?.message
						: formState.errors.incidentReportContacts?.message}
				</ClayForm.FeedbackItem>
			</ClayForm.Group>

			<div className="mt-4 text-1">
				<p>
					Please read{' '}
					<a
						href="https://www.liferay.com/legal/marketplace-terms-of-service"
						rel="noopener noreferrer"
						target="_blank"
					>
						this agreement
					</a>{' '}
					carefully before accessing or in any way using the AI Hub
					private beta experience.
				</p>

				<ClayForm.Group
					className={classNames({
						'has-error':
							formState.errors.agreementAcceptance?.message,
					})}
				>
					<ClayCheckbox
						{...({} as any)}
						{...register('agreementAcceptance')}
						id="agreement-acceptance"
						label={
							<span className="text-2">
								I signify my assent to and acceptance of this
								agreement and acknowledge that I have read and
								understand the terms. If I am an individual
								acting on behalf of an entity, I represent that
								I have the authority to enter into this
								agreement on behalf of that entity.
								<span className="text-danger"> *</span>
							</span>
						}
					/>

					<ClayForm.FeedbackItem>
						{formState.errors.agreementAcceptance?.message}
					</ClayForm.FeedbackItem>
				</ClayForm.Group>

				<ClayForm.Group
					className={classNames({
						'has-error':
							formState.errors.dataProcessingConsent?.message,
					})}
				>
					<ClayCheckbox
						{...({} as any)}
						{...register('dataProcessingConsent')}
						id="data-processing-consent"
						label={
							<span className="text-2">
								I agree to the processing of my personal data
								for the purpose of evaluating my beta access
								request, in accordance with{' '}
								<a
									href="https://www.liferay.com/privacy-policy"
									rel="noopener noreferrer"
									target="_blank"
								>
									Liferay&apos;s Privacy Policy
								</a>
								.<span className="text-danger"> *</span>
							</span>
						}
					/>

					<ClayForm.FeedbackItem>
						{formState.errors.dataProcessingConsent?.message}
					</ClayForm.FeedbackItem>
				</ClayForm.Group>

				<small className="text-2">
					You can stop receiving marketing emails by clicking the
					unsubscribe link in each email or withdraw your consent at
					any time by either using opt-out functionality accessible
					through the messages you receive or via email to{' '}
					<a href="mailto:dataprotection@liferay.com">
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
				</small>
			</div>
		</ProductPurchase.Shell>
	);
};

export default LDPProvisioning;
