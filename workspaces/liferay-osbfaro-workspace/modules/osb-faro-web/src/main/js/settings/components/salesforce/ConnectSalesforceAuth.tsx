/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayButton from '@clayui/button';
import {Text} from '@clayui/core';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import Clipboard from 'clipboard';
import {FormikProps} from 'formik';
import React, {useEffect, useRef, useState} from 'react';
import {useParams} from 'react-router-dom';
import {createSalesforce, updateSalesforce} from '~/shared/api/data-source';
import Form from '~/shared/components/form';
import {Alert} from '~/shared/types';
import {DataSourceStatuses} from '~/shared/util/constants';
import {sub} from '~/shared/util/lang';
import {
	ERROR_TYPES,
	OAUTH_CALLBACK_URL,
	getOAuthWindowErrorMessage,
	getTempCredentials,
} from '~/shared/util/oauth';
import {sequence} from '~/shared/util/promise';
import {DataSource} from '~/shared/util/records';
import {Routes} from '~/shared/util/router';
import {
	validateRequired,
	validateSalesforceDomain,
} from '~/shared/util/validators';

/**
 * The salesforceAuthErrorMessage function aims to map all the errors returned
 * by Salesforce and display them in a more user-friendly way,
 * grouping them by message type.
 *
 * More about it here https://help.salesforce.com/s/articleView?id=xcloud.remoteaccess_oauth_flow_errors.htm
 */

function salesforceAuthErrorMessage(errMessage: string) {
	const findErrMessage = (value: string) => errMessage.includes(value);
	const errorGroups = [
		{
			matches: [
				'access_denied',
				'immediate_unsuccessful',
				'authorization_pending',
			],
			messageKey: Liferay.Language.get(
				'you-did-not-grant-access-to-the-application.-please-approve-the-request-and-try-again'
			),
		},
		{
			matches: [
				'inactive_user',
				'inactive_org',
				'invalid_app_access',
				'NO_ACCESS',
			],
			messageKey: Liferay.Language.get(
				'your-account-or-organization-is-not-eligible-for-sign-in.-contact-your-administrator-for-assistance'
			),
		},
		{
			matches: [
				'invalid_grant',
				'authentication failure',
				'rate_limit_exceeded',
			],
			messageKey: Liferay.Language.get(
				'we-could-not-verify-your-sign-in.-check-your-credentials-and-try-again'
			),
		},
		{
			matches: [
				'invalid_client',
				'invalid_client_id',
				'invalid_assertion_type',
				'unsupported_response_type',
			],
			messageKey: Liferay.Language.get(
				'the-application-credentials-or-configuration-are-invalid.-verify-the-client-settings-and-try-again'
			),
		},
	];

	for (const {matches, messageKey} of errorGroups) {
		if (matches.some(findErrMessage)) {
			return messageKey;
		}
	}

	return Liferay.Language.get(
		'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists,-please-contact-support'
	);
}

interface IConnectSalesforceAuthProps {
	addAlert: any;
	buttonProps?: {
		[key: string]: any;
	};
	dataSource?: DataSource;

	/**
	 * When disabled, the form renders with all inputs
	 * read-only and without any buttons.
	 */
	disabled?: boolean;
	onCancel?: () => void;
	onSubmit: (dataSource: DataSource) => void;
}

const ConnectSalesforceAuth: React.FC<IConnectSalesforceAuthProps> = ({
	addAlert,
	buttonProps,
	dataSource,
	disabled,
	onCancel,
	onSubmit,
}) => {
	const {groupId = ''} = useParams<{groupId: string}>();

	const [isUrlCopied, setIsUrlCopied] = useState(false);
	const [copyTitle, setCopyTitle] = useState(
		Liferay.Language.get('click-to-copy')
	);
	const [inlineAlert, setInlineAlert] = useState<string | null>(null);
	const [showClientId, setShowClientId] = useState(false);
	const [showClientSecret, setShowClientSecret] = useState(false);

	const _formRef = useRef<FormikProps<any>>(null);

	useEffect(() => {
		const _clipboard = new Clipboard('[data-clipboard-text]');

		_clipboard.on('success', (event: {clearSelection: () => void}) => {
			setCopyTitle(Liferay.Language.get('copied'));

			addAlert({
				alertType: Alert.Types.Success,
				message: Liferay.Language.get('copied'),
			});

			setTimeout(() => {
				setCopyTitle(Liferay.Language.get('click-to-copy'));
				setIsUrlCopied(false);
			}, 3000);

			event.clearSelection();
		});

		return () => _clipboard.destroy();

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<Form
			initialValues={{
				clientId: dataSource?.credentials?.get('oAuthClientId'),
				clientSecret: dataSource?.credentials?.get('oAuthClientSecret'),
				salesForceDataSource: dataSource?.url,
			}}
			innerRef={_formRef}
			onSubmit={(values: any) => {
				const {setSubmitting} = _formRef.current!;

				const authWindow = open(
					Routes.LOADING,
					'authorizeWindow',
					'width=400,height=500'
				);

				return getTempCredentials({
					authWindow,
					baseUrl: values.salesForceDataSource,
					consumerKey: values.clientId,
					consumerSecret: values.clientSecret,
					groupId,
					type: 'salesforce',
				} as any)
					.then(async (tempCredentials) => {
						if (tempCredentials) {
							if (dataSource) {
								const updatedDataSource = {
									credentials: tempCredentials,
									groupId,
									id: dataSource.id,
									name: dataSource.name,
									status: DataSourceStatuses.Active,
									url: values.salesForceDataSource,
								} as any;

								updateSalesforce(updatedDataSource)
									.then(() => {
										addAlert({
											alertType: Alert.Types.Success,
											message: Liferay.Language.get(
												'connection-established-successfully'
											),
										});

										onSubmit(updatedDataSource);
									})
									.catch(() => {
										addAlert({
											alertType: Alert.Types.Error,
											message: Liferay.Language.get(
												'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists,-please-contact-support'
											),
										});
									})
									.finally(() => {
										setSubmitting(false);
									});
							}
							else {
								const dataSource = {
									accountsConfiguration: {
										enableAllAccounts: false,
									},
									channelsConfiguration: {
										channelIds: [],
										enableAllChannels: false,
									},
									contactsConfiguration: {
										enableAllContacts: false,
										enableAllLeads: false,
									},
									credentials: tempCredentials,
									groupId,
									name: Liferay.Language.get('salesforce'),
									status: DataSourceStatuses.Active,
									url: values.salesForceDataSource,
								} as any;

								createSalesforce(dataSource)
									.then((response) => {
										addAlert({
											alertType: Alert.Types.Success,
											message: Liferay.Language.get(
												'connection-established-successfully'
											),
										});

										onSubmit(response);
									})
									.catch(() => {
										addAlert({
											alertType: Alert.Types.Error,
											message: Liferay.Language.get(
												'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists,-please-contact-support'
											),
										});
									})
									.finally(() => {
										setSubmitting(false);
									});
							}
						}
					})
					.catch((error) => {
						addAlert({
							alertType: Alert.Types.Error,
							message:
								error.type ===
								ERROR_TYPES.AC_RECEIVE_CALLBACK_ERROR
									? salesforceAuthErrorMessage(error.message)
									: getOAuthWindowErrorMessage(error),
						});

						setSubmitting(false);
					});
			}}
		>
			{({handleSubmit, isSubmitting, isValid, values}) => (
				<Form.Form
					className="oauth-form-root"
					onSubmit={(event: React.FormEvent<HTMLFormElement>) => {
						if (!isValid) {
							if (
								!values.salesForceDataSource &&
								!values.clientId &&
								!values.clientSecret
							) {
								setInlineAlert(
									Liferay.Language.get(
										'please-enter-a-value-before-continuing'
									)
								);
							}
							else {
								setInlineAlert(null);
							}
						}

						handleSubmit(event);
					}}
				>
					{inlineAlert && (
						<ClayAlert
							displayType="danger"
							title={Liferay.Language.get('error')}
						>
							{inlineAlert}
						</ClayAlert>
					)}

					<ClayForm.Group
						className={getCN({
							'has-success': isUrlCopied,
						})}
					>
						<label htmlFor="callbackURL">
							<Text weight="semi-bold">
								{Liferay.Language.get('target-url')}
							</Text>

							<div>
								<Text
									color="secondary"
									size={3}
									weight="normal"
								>
									{sub(
										Liferay.Language.get(
											'this-is-analytics-cloud-callback-url-x-will-redirect-to-after-a-user-authorizes-the-connection'
										),
										[Liferay.Language.get('salesforce')]
									)}
								</Text>
							</div>
						</label>

						<ClayInput.Group>
							<ClayInput.GroupItem prepend>
								<ClayInput
									id="callbackURL"
									insetAfter
									name="callbackURL"
									readOnly={!isUrlCopied}
									type="text"
									value={OAUTH_CALLBACK_URL}
								/>
							</ClayInput.GroupItem>

							<ClayInput.GroupItem append shrink>
								<ClayButton
									aria-label={copyTitle}
									data-clipboard-text={OAUTH_CALLBACK_URL}
									displayType={
										isUrlCopied ? 'success' : 'secondary'
									}
									onClick={() => setIsUrlCopied(true)}
									outline
									title={copyTitle}
								>
									<ClayIcon
										symbol={isUrlCopied ? 'check' : 'copy'}
									/>
								</ClayButton>
							</ClayInput.GroupItem>
						</ClayInput.Group>
					</ClayForm.Group>

					<Form.Input
						className="mb-3"
						id="salesForceDataSource"
						label={Liferay.Language.get('salesforce-url')}
						name="salesForceDataSource"
						readOnly={disabled}
						required
						type="text"
						validate={sequence([
							(value: string) =>
								validateRequired(
									value,
									sub(
										Liferay.Language.get(
											'the-x-field-is-required'
										),
										[Liferay.Language.get('salesforce-url')]
									) as string
								),
							validateSalesforceDomain,
						])}
					/>

					<Form.Input
						className="mb-3"
						contentAfter={
							<ClayButton
								aria-label={
									showClientId
										? Liferay.Language.get('view')
										: Liferay.Language.get('hidden')
								}
								displayType="secondary"
								onClick={() => setShowClientId(!showClientId)}
							>
								<ClayIcon
									symbol={showClientId ? 'view' : 'hidden'}
								/>
							</ClayButton>
						}
						contentAfterEnableMagnet
						id="clientId"
						label={Liferay.Language.get('consumer-key-client-id')}
						name="clientId"
						readOnly={disabled}
						required
						type={showClientId ? 'text' : 'password'}
						validate={(value: string) =>
							validateRequired(
								value,
								sub(
									Liferay.Language.get(
										'the-x-field-is-required'
									),
									[
										Liferay.Language.get(
											'consumer-key-client-id'
										),
									]
								) as string
							)
						}
					/>

					<Form.Input
						className="mb-4"
						contentAfter={
							<ClayButton
								aria-label={
									showClientSecret
										? Liferay.Language.get('view')
										: Liferay.Language.get('hidden')
								}
								displayType="secondary"
								onClick={() =>
									setShowClientSecret(!showClientSecret)
								}
							>
								<ClayIcon
									symbol={
										showClientSecret ? 'view' : 'hidden'
									}
								/>
							</ClayButton>
						}
						contentAfterEnableMagnet
						id="clientSecret"
						label={Liferay.Language.get(
							'consumer-secret-client-secret'
						)}
						name="clientSecret"
						readOnly={disabled}
						required
						type={showClientSecret ? 'text' : 'password'}
						validate={(value: string) =>
							validateRequired(
								value,
								sub(
									Liferay.Language.get(
										'the-x-field-is-required'
									),
									[
										Liferay.Language.get(
											'consumer-secret-client-secret'
										),
									]
								) as string
							)
						}
					/>

					{!disabled && (
						<>
							<ClayButton
								{...buttonProps}
								disabled={isSubmitting}
								loading={isSubmitting}
								type="submit"
							>
								{Liferay.Language.get('connect')}
							</ClayButton>

							{onCancel && (
								<ClayButton
									block
									borderless
									displayType="secondary"
									onClick={onCancel}
								>
									{Liferay.Language.get('cancel')}
								</ClayButton>
							)}
						</>
					)}
				</Form.Form>
			)}
		</Form>
	);
};

export {ConnectSalesforceAuth};
