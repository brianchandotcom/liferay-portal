/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {FormikHelpers} from 'formik';
import React from 'react';
import {ConnectedProps, connect} from 'react-redux';
import {addAlert} from '~/shared/actions/alerts';
import * as API from '~/shared/api';
import Loading, {Align} from '~/shared/components/Loading';
import Form, {
	validateMaxLength,
	validateRequired,
} from '~/shared/components/form';
import Modal from '~/shared/components/modal';
import {Alert} from '~/shared/types';
import {sequence} from '~/shared/util/promise';

import {IHelpWidgetScreenProps} from './types';

const connector = connect(null, {addAlert});

type PropsFromRedux = ConnectedProps<typeof connector>;

type ReportIssueFormValues = {
	description: string;
	issueTitle: string;
};

const ReportIssue: React.FC<IHelpWidgetScreenProps & PropsFromRedux> = ({
	addAlert,
	groupId,
	onClose,
	onNext,
}) => {
	const onSubmit = (
		{description, issueTitle}: ReportIssueFormValues,
		{setSubmitting}: FormikHelpers<ReportIssueFormValues>
	) => {
		API.issue
			.create({
				currentUrl: window.location.href,
				description,
				groupId: groupId ?? '',
				title: issueTitle,
			})
			.then(() => {
				setSubmitting(false);

				onNext?.();
			})
			.catch(() => {
				addAlert({
					alertType: Alert.Types.Error,
					message: Liferay.Language.get(
						'there-was-an-error-processing-your-request.-please-try-again'
					),
				});

				setSubmitting(false);
			});
	};

	return (
		<>
			<Modal.Header
				onClose={onClose}
				title={Liferay.Language.get('report-an-issue')}
			/>

			<Form
				initialValues={{description: '', issueTitle: ''}}
				onSubmit={onSubmit}
			>
				{({handleSubmit, isSubmitting, isValid}) => (
					<Form.Form onSubmit={handleSubmit}>
						<Modal.Body>
							<Form.Group>
								<Form.GroupItem className="mb-4">
									<Form.Input
										label={Liferay.Language.get(
											'issue-title'
										)}
										name="issueTitle"
										required
										validate={sequence([
											validateRequired,
											validateMaxLength(150),
										])}
									/>
								</Form.GroupItem>

								<Form.GroupItem>
									<Form.Input
										label={Liferay.Language.get(
											'description'
										)}
										name="description"
										required
										type="textarea"
										validate={validateRequired}
									/>
								</Form.GroupItem>

								<Form.GroupItem className="text-secondary">
									{Liferay.Language.get(
										'please-include-as-many-details-as-possible'
									)}
								</Form.GroupItem>
							</Form.Group>
						</Modal.Body>

						<Modal.Footer>
							<ClayButton
								className="button-root"
								displayType="secondary"
								onClick={onClose}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								className="button-root"
								disabled={!isValid}
								displayType="primary"
								type="submit"
							>
								{isSubmitting && <Loading align={Align.Left} />}

								{Liferay.Language.get('submit')}
							</ClayButton>
						</Modal.Footer>
					</Form.Form>
				)}
			</Form>
		</>
	);
};

export default connector(ReportIssue);
