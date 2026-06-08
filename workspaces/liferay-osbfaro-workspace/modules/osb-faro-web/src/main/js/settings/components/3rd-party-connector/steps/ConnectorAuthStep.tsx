/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {useHistory} from 'react-router-dom';
import {updateSearchParams} from '~/settings/components/base-page/utis';
import {Alert} from '~/shared/types';
import {Routes, toRoute} from '~/shared/util/router';

import {useWizardPage} from '../../base-page/WizardPageContext';
import ConnectorAuth from '../ConnectorAuth';
import {ConnectorConfig} from '../types';

interface IConnectorAuthStepProps {
	addAlert: Alert.AddAlert;
	config: ConnectorConfig;
	groupId: string;
	onNext: () => void;
}

const ConnectorAuthStep = ({
	addAlert,
	config,
	groupId,
	onNext,
}: IConnectorAuthStepProps) => {
	const {dataSource} = useWizardPage();
	const history = useHistory();

	const handleCancel = () => {
		history.push(
			toRoute(Routes.SETTINGS_DATA_SOURCE_LIST, {
				groupId,
			})
		);
	};

	if (!dataSource) {
		return (
			<ConnectorAuth
				addAlert={addAlert}
				buttonProps={{block: true}}
				config={config}
				groupId={groupId}
				onCancel={handleCancel}
				onSubmit={(createdDataSource) => {
					updateSearchParams(
						history,
						'dataSourceId',
						createdDataSource.id
					);
					onNext();
				}}
			/>
		);
	}

	return (
		<ConnectorAuth
			addAlert={addAlert}
			buttonProps={{block: true}}
			config={config}
			dataSource={dataSource}
			groupId={groupId}
			onCancel={handleCancel}
			onSubmit={onNext}
		/>
	);
};

export {ConnectorAuthStep};
