/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayMultiSelect from '@clayui/multi-select';
import {zodResolver} from '@hookform/resolvers/zod';
import classNames from 'classnames';
import {useMemo, useState} from 'react';
import {useForm} from 'react-hook-form';
import {z} from 'zod';

import HelpPopover from '../../../../components/HelpPopover';
import {Input} from '../../../../components/Input/Input';
import Loading from '../../../../components/Loading';
import ProductPurchase from '../../../../components/ProductPurchase';
import Select from '../../../../components/Select/Select';
import useListTypeDefinition from '../../../../hooks/useListTypeDefinition';
import i18n from '../../../../i18n';
import {Liferay} from '../../../../liferay/liferay';
import zodSchema from '../../../../schema/zod';
import {useProductPurchaseOutletContext} from '../../ProductPurchaseOutlet';

type MultiSelectValue = {
	key: string;
	label: string;
	value: string;
};

function ContactSalesLink() {
	return (
		<a
			href="https://www.liferay.com/contact-sales"
			rel="noopener noreferrer"
			target="_blank"
		>
			Sales Department
		</a>
	);
}

function SupportLink(props: {children: React.ReactNode}) {
	return (
		<a
			href="https://support.liferay.com"
			referrerPolicy="no-referrer"
			target="_blank"
		>
			{props.children}
		</a>
	);
}

const paragraphProps = {
	align: 'left',
};

const emptyStateMessages = {
	UNABLE_TO_PROVISION: {
		description: (
			<>
				<p {...paragraphProps} className="px-5">
					Analytics Cloud requires an active Liferay Enterprise
					subscription and we could not find any associated with your
					account.
				</p>

				<p>
					To resolve this, please follow the steps below that apply to
					your situation:
				</p>

				<ul {...paragraphProps}>
					<li>
						If your company has an active DXP subscription, please
						contact your Account Administrator and ask them to add
						your user to the company account.
					</li>

					<li>
						If you do not yet have an active enterprise
						subscription, please contact our <ContactSalesLink /> to
						get started.
					</li>

					<li>
						If neither of the above situations applies to you, or
						you are experiencing a technical issue not covered here,
						please contact our{' '}
						<SupportLink>Support Team</SupportLink> for assistance.
					</li>
				</ul>
			</>
		),
		title: 'We could not find an Enterprise Subscription related to your Account',
	},
	WORKSPACE_ALREADY_EXISTS: {
		description: (
			<>
				Your account is already linked to an existing Analytics Cloud
				workspace. If you cannot access your workspace please contact{' '}
				<SupportLink>support</SupportLink>.
			</>
		),
		title: 'This account already has an active\n Analytics Cloud workspace',
	},
};

const CDPProvisioning = () => {
	const [incidentReportContactsText, setIncidentReportContactsText] =
		useState('');

	const {
		actions: {nextStep},
		product,
		productPurchaseCart,
		selectedAccount,
		setForm,
	} = useProductPurchaseOutletContext();

	const {data: acRegionsResponse, isLoading} =
		useListTypeDefinition('AC-REGIONS');

	const acRegions = acRegionsResponse?.listTypeEntries ?? [];

	const accountKey = selectedAccount.externalReferenceCode;

	const {formState, handleSubmit, register, setValue, watch} = useForm<
		z.infer<typeof zodSchema.cdpProvisioning>
	>({
		defaultValues: {
			_refAllowedEmailDomains: [],
			_refIncidentReportContacts: [],
			acceptTerms: false,
			allowedEmailDomains: [],
			dataCenterLocation: acRegions[0]?.externalReferenceCode,
			incidentReportContacts: [],
			workspaceOwnerEmail: Liferay.ThemeDisplay.getUserEmailAddress(),
		},
		mode: 'all',
		resolver: zodResolver(zodSchema.cdpProvisioning),
	});

	const _refIncidentReportContacts = watch('_refIncidentReportContacts');

	const onSubmit = async (
		form: z.infer<typeof zodSchema.cdpProvisioning>
	) => {
		setForm(form);
		productPurchaseCart.addCart(Number(product.id), product.skus[0].id);
		nextStep();
	};

	if (isLoading || !accountKey) {
		return <Loading />;
	}

	return (
		<ProductPurchase.Shell
			footerProps={{
				backButtonProps: {className: 'd-none'},
				continueButtonProps: {
					children: i18n.translate('continue'),
					disabled: !formState.isValid,
					onClick: handleSubmit(onSubmit),
					type: 'submit',
				},
			}}
			title="Provisioning"
		>
			<h3 className="sheet-subtitle">General</h3>

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

			<Select
				{...register('dataCenterLocation')}
				boldLabel
				helpText={`Select a server to store your data. This could have implications to your organization's policy on user data storage.`}
				label="Data Center Location"
				options={acRegions.map(({externalReferenceCode, name}) => ({
					key: externalReferenceCode,
					name,
				}))}
				required
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
							className="required"
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

			<ClayForm.Group
				className={classNames('mt-4', {
					'has-error': formState.errors.acceptTerms?.message,
				})}
			>
				<ClayCheckbox
					{...({} as any)}
					{...register('acceptTerms')}
					id="accept-terms"
					label="I agree"
				/>

				<ClayForm.FeedbackItem>
					{formState.errors.acceptTerms?.message}
				</ClayForm.FeedbackItem>

				<label className="font-weight-normal" htmlFor="accept-terms">
					By selecting &quot;I Agree&quot;, you agree to our{' '}
					<a
						href="https://www.liferay.com/legal/marketplace-terms-of-service"
						target="_blank"
					>
						Terms and Conditions
					</a>{' '}
					including our{' '}
					<a
						href="https://www.liferay.com/privacy-policy"
						target="_blank"
					>
						Privacy Policy.
					</a>
				</label>
			</ClayForm.Group>
		</ProductPurchase.Shell>
	);
};

export default CDPProvisioning;
