/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Text} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import {FormikHelpers} from 'formik';
import React, {useRef, useState} from 'react';
import {ConnectedProps, connect, useStore} from 'react-redux';
import {addAlert} from '~/shared/actions/alerts';
import {updateProject} from '~/shared/actions/projects';
import * as API from '~/shared/api';
import Loading, {Align} from '~/shared/components/Loading';
import Form, {
	validateMaxLength,
	validateMinLength,
	validatePattern,
} from '~/shared/components/form';
import Modal from '~/shared/components/modal';
import {compose, withHistory} from '~/shared/hoc';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import {useRequest} from '~/shared/hooks/useRequest';
import {Alert} from '~/shared/types';
import Constants from '~/shared/util/constants';
import {
	validateEmailDomain,
	validateEmailDomainArr,
} from '~/shared/util/email-validators';
import {sub} from '~/shared/util/lang';
import {sequence} from '~/shared/util/promise';
import {Routes, toRoute} from '~/shared/util/router';

const {faroURL} = Constants;

const connector = connect(null, {
	addAlert,
	updateProject,
});

type PropsFromRedux = ConnectedProps<typeof connector>;

interface IConfigureWorkspaceProps extends PropsFromRedux {
	emailAddressDomains: string[];
	emailAddressDomainsLoading: boolean;
	groupId: string;
	history: {
		replace: (value: any) => void;
	};
	onClose: () => void;
	onNext?: (increment?: number) => void;
}

const ConfigureWorkspaceWithEmailAddressDomains: React.FC<
	IConfigureWorkspaceProps
> = ({
	addAlert,
	emailAddressDomains: initialEmailAddressDomains,
	groupId,
	history,
	onClose,
	onNext,
	updateProject,
}) => {
	const store = useStore();
	const project = store.getState().getIn(['projects', groupId, 'data']);
	const currentUser = useCurrentUser();
	const disabled = !currentUser.isAdmin();
	const formRef = useRef<any>(null);
	const [emailAddressDomains, setEmailAddressDomains] = useState(
		initialEmailAddressDomains
	);

	type ConfigureWorkspaceFormValues = {
		emailAddressDomains: string[];
		friendlyURL: string;
	};

	const handleSubmit = (
		values: ConfigureWorkspaceFormValues,
		{
			resetForm,
			setFieldError,
			setSubmitting,
		}: FormikHelpers<ConfigureWorkspaceFormValues>
	): void => {
		const {initialValues} = formRef.current;
		const {friendlyURL: initialFriendlyURL} = initialValues;

		(
			updateProject({
				emailAddressDomains: values.emailAddressDomains,
				friendlyURL: values.friendlyURL,
				groupId,
				incidentReportEmailAddresses:
					project.incidentReportEmailAddresses,
				name: project.name,
				timeZoneId: project.timeZoneId,
			} as any) as unknown as Promise<void>
		)
			.then(() => {
				setSubmitting(false);

				if (initialFriendlyURL === values.friendlyURL) {
					resetForm({values});
				}

				if (values.friendlyURL !== groupId) {
					history.replace(
						toRoute(Routes.SETTINGS_WORKSPACE, {
							groupId: values.friendlyURL || project.groupId,
						})
					);
				}

				addAlert({
					alertType: Alert.Types.Success,
					message: Liferay.Language.get('workspace-settings-saved'),
				});

				onNext?.();
			})
			.catch(({field, message}: {field?: string; message: string}) => {
				setSubmitting(false);

				if (field) {
					setFieldError(field, message);
				}
				else {
					addAlert({
						alertType: Alert.Types.Error,
						message: Liferay.Language.get('unknown-error'),
						timeout: false,
					});
				}
			});
	};

	return (
		<>
			<Form
				enableReinitialize
				initialValues={{
					emailAddressDomains: initialEmailAddressDomains || [],
					friendlyURL: project?.friendlyURL?.replace('/', '') || '',
				}}
				innerRef={formRef as any}
				onSubmit={handleSubmit}
			>
				{({dirty, errors, handleSubmit, isSubmitting, isValid}) => (
					<Form.Form
						onSubmit={(event: React.FormEvent<HTMLFormElement>) => {
							event.preventDefault();

							if (!dirty || !isValid) {
								onNext?.();

								return;
							}

							handleSubmit(event);
						}}
					>
						<Modal.Header onClose={onClose} />

						<Modal.Body>
							<div className="text-center">
								<ClayIcon
									className="icon-root"
									style={{fontSize: '10rem'}}
									symbol="analytics_onboarding_welcome"
								/>
							</div>

							<div className="mb-4 text-center">
								<Text size={10} weight="bold">
									{Liferay.Language.get(
										'configure-your-workspace-access'
									)}
								</Text>
							</div>

							<Form.Group>
								<div>
									<Text size={6} weight="semi-bold">
										{Liferay.Language.get(
											'set-a-friendly-workspace-url'
										)}
									</Text>
								</div>

								<Text color="secondary" size={3}>
									{Liferay.Language.get(
										'define-a-friendly-url-that-others-can-use-to-access-and-share-this-workspace.-this-value-cannot-be-changed-after-it-is-set'
									)}
								</Text>

								<div className="mb-1">
									<Text color="secondary" size={3}>
										{sub(
											Liferay.Language.get('e.g.-x'),
											[
												<React.Fragment key="WORKSPACE_URL">
													<span>{faroURL}</span>
													<strong>
														/workspace-name
													</strong>
												</React.Fragment>,
											],
											false
										)}
									</Text>
								</div>

								<Form.Input
									data-testid="friendly-url-input"
									disabled={
										disabled ||
										(project && project.friendlyURL)
									}
									name="friendlyURL"
									text={{
										content: '/',
										position: 'prepend',
									}}
									validate={sequence([
										validateMinLength(2),
										validateMaxLength(255),
										validatePattern(
											/^(?=.*[a-z])[a-z0-9._-]+$/,
											sub(
												Liferay.Language.get(
													'workspace-url-must-only-contain-x-and-at-least-one-letter'
												),
												["a-z, 0-9, '.', '_', '-'"]
											) as string
										),
									])}
								/>
							</Form.Group>

							<Form.Group className="mb-0">
								<div>
									<Text size={6} weight="semi-bold">
										{Liferay.Language.get(
											'allowed-email-domains'
										)}
									</Text>
								</div>

								<Text color="secondary" size={3}>
									{Liferay.Language.get(
										'define-which-email-domains-can-request-access-to-this-workspace'
									)}
								</Text>

								<div className="mb-1">
									<Text color="secondary" size={3}>
										{sub(
											Liferay.Language.get('e.g.-x'),
											[
												<React.Fragment key="EMAIL_DOMAIN">
													<span>user.name@</span>
													<strong>
														company-domain.com
													</strong>
												</React.Fragment>,
											],
											false
										)}
									</Text>
								</div>

								<Form.InputList
									disabled={disabled}
									errorMessage={Liferay.Language.get(
										'please-enter-the-domain-in-this-format-domain-com'
									)}
									name="emailAddressDomains"
									onChangeInputList={setEmailAddressDomains}
									text={{
										content: '@',
										position: 'prepend',
									}}
									validate={(items: string[]) =>
										validateEmailDomainArr(
											items,
											emailAddressDomains
										)
									}
									validationFn={validateEmailDomain}
								/>
							</Form.Group>
						</Modal.Body>

						<Modal.Footer>
							<ClayButton
								className="button-root ml-2"
								disabled={
									isSubmitting ||
									!!errors.emailAddressDomains ||
									!!errors.friendlyURL
								}
								displayType="primary"
								type="submit"
							>
								{isSubmitting && <Loading align={Align.Left} />}

								{Liferay.Language.get('next')}
							</ClayButton>
						</Modal.Footer>
					</Form.Form>
				)}
			</Form>
		</>
	);
};

const ConfigureWorkspace: React.FC<IConfigureWorkspaceProps> = (props) => {
	const {data, loading} = useRequest({
		dataSourceFn: API.projects.fetchEmailAddressDomains,
		variables: {
			groupId: props.groupId,
		},
	});

	if (loading) {
		return <Loading spacer />;
	}

	return (
		<ConfigureWorkspaceWithEmailAddressDomains
			{...props}
			emailAddressDomains={data}
		/>
	);
};

export default compose(connector, withHistory)(ConfigureWorkspace) as any;
