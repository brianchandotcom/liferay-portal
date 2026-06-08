/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {useParams} from 'react-router-dom';
import {getConnectorConfig} from '~/settings/components/3rd-party-connector/registry';
import {ConnectorAuthStep} from '~/settings/components/3rd-party-connector/steps/ConnectorAuthStep';
import {ConnectorConfig} from '~/settings/components/3rd-party-connector/types';
import WizardPage, {Step} from '~/settings/components/base-page/WizardPage';
import {AssignIndividualsDataToPropertiesStep} from '~/settings/components/salesforce/steps/AssignIndividualsDataToChannelsStep';
import {updateConnector} from '~/shared/api/connector';
import RouteNotFound from '~/shared/components/RouteNotFound';
import {Alert} from '~/shared/types';

const buildSteps = (config: ConnectorConfig): Step[] => [
	{
		content: (props: any) => (
			<ConnectorAuthStep {...props} config={config} />
		),
		description: config.languages.connectDescription,
		title: config.languages.connectTitle,
	},
	{
		content: (props: any) => (
			<AssignIndividualsDataToPropertiesStep
				{...props}
				onSubmit={() => {
					props.addAlert({
						alertType: Alert.Types.Success,
						message: Liferay.Language.get(
							'the-data-source-setup-has-finished'
						),
					});
				}}
				updateDataSourceFn={(params: {[key: string]: any}) =>
					updateConnector(
						config.slug,
						params as Parameters<typeof updateConnector>[1]
					)
				}
			/>
		),
		description: Liferay.Language.get(
			'properties-allow-you-to-aggregate-data-on-your-users,-sites-and-dxp-commerce-channels.-individuals-data-will-be-available-in-any-property-they-are-assigned-to'
		),
		title: Liferay.Language.get('assign-individuals-data-to-properties'),
	},
];

const ConnectConnector = () => {
	const {id = ''} = useParams<{id: string}>();

	const config = getConnectorConfig(id);

	if (!config) {
		return <RouteNotFound />;
	}

	return <WizardPage steps={buildSteps(config)} />;
};

export default ConnectConnector;
