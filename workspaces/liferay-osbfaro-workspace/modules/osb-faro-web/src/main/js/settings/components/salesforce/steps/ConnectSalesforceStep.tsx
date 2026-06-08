/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import {Text} from '@clayui/core';
import ClayForm from '@clayui/form';
import React from 'react';
import {useHistory} from 'react-router-dom';
import {WizardPageButtonGroup} from '~/settings/components/base-page/WizardPageButtonGroup';
import {updateSearchParams} from '~/settings/components/base-page/utis';
import {ConnectSalesforceAuth} from '~/settings/components/salesforce/ConnectSalesforceAuth';
import {modalTypes} from '~/shared/actions/modals';
import {disconnect} from '~/shared/api/data-source';
import {Alert, Modal} from '~/shared/types';
import {DataSourceStatuses} from '~/shared/util/constants';
import {Routes, toRoute} from '~/shared/util/router';

import {useWizardPage} from '../../base-page/WizardPageContext';

interface IConnectSalesforceStepProps {
	addAlert: Alert.AddAlert;
	close: Modal.close;
	groupId: string;
	onNext: () => void;
	open: Modal.open;
}

const ConnectSalesforceStep = ({
	addAlert,
	close,
	groupId,
	onNext,
	open,
}: IConnectSalesforceStepProps) => {
	const history = useHistory();
	const {dataSource, refetchDataSource} = useWizardPage();

	if (!dataSource) {
		return (
			<ConnectSalesforceAuth
				addAlert={addAlert}
				buttonProps={{block: true}}
				onCancel={() => {
					history.push(
						toRoute(Routes.SETTINGS_DATA_SOURCE_LIST, {
							groupId,
						})
					);
				}}
				onSubmit={(dataSource) => {
					updateSearchParams(history, 'dataSourceId', dataSource.id);

					onNext();
				}}
			/>
		);
	}

	if (dataSource.get('status') === DataSourceStatuses.Active) {
		return (
			<ClayForm
				onSubmit={(event) => {
					event.preventDefault();

					onNext();
				}}
			>
				<ClayAlert
					displayType="success"
					title={Liferay.Language.get('success')}
				>
					{Liferay.Language.get(
						'connection-established-successfully'
					)}
				</ClayAlert>

				<ConnectSalesforceAuth
					addAlert={addAlert}
					buttonProps={{block: true}}
					dataSource={dataSource}
					disabled
					onSubmit={onNext}
				/>

				<WizardPageButtonGroup
					nextButtonLabel={Liferay.Language.get('continue')}
					onCancel={() => {
						open(modalTypes.CONFIRMATION_MODAL, {
							message: (
								<Text as="p" size={4}>
									{Liferay.Language.get(
										'this-action-will-stop-syncing-data-from-salesforce-to-this-analytics-cloud-workspace.-the-data-that-was-already-synced-will-remain-available-in-the-properties-the-data-source-was-connected-to.-are-you-sure-you-want-to-continue'
									)}
								</Text>
							),
							modalVariant: 'modal-warning',
							onClose: close,
							onSubmit: async () => {
								try {
									await disconnect({
										groupId,
										id: dataSource.id,
									});

									refetchDataSource(dataSource.id || '');

									addAlert({
										alertType: Alert.Types.Success,
										message: Liferay.Language.get(
											'data-source-disconnected'
										),
									});

									close();
								}
								catch (error) {
									addAlert({
										alertType: Alert.Types.Error,
										message: Liferay.Language.get(
											'there-was-an-error-processing-your-request.-try-again.-if-the-problem-persists-please-contact-support'
										),
									});
								}
							},
							submitButtonDisplay: 'warning',
							submitMessage: Liferay.Language.get('disconnect'),
							title: Liferay.Language.get(
								'disconnect-data-source'
							),
							titleIcon: 'warning-full',
						});
					}}
					prevButtonLabel={Liferay.Language.get(
						'disconnect-data-source'
					)}
				/>
			</ClayForm>
		);
	}

	return (
		<ConnectSalesforceAuth
			addAlert={addAlert}
			buttonProps={{block: true}}
			dataSource={dataSource}
			onCancel={() => {
				history.push(
					toRoute(Routes.SETTINGS_DATA_SOURCE_LIST, {
						groupId,
					})
				);
			}}
			onSubmit={onNext}
		/>
	);
};

export {ConnectSalesforceStep};
