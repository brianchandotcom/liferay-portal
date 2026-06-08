/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {useParams} from 'react-router-dom';
import {getConnectorConfig} from '~/settings/components/3rd-party-connector/registry';
import RouteNotFound from '~/shared/components/RouteNotFound';
import {DataSourceTypes} from '~/shared/util/constants';

import ConnectConnector from './ConnectConnector';
import ConnectLiferayDXP from './ConnectLiferayDXP';
import ConnectSalesforce from './ConnectSalesforce';

const PAGE_MAP = {
	[DataSourceTypes.Liferay]: ConnectLiferayDXP,
	[DataSourceTypes.Salesforce]: ConnectSalesforce,
};

const DataSourceOnboarding = () => {
	const {id = ''} = useParams<{id: string}>();

	const normalizedId = id.toUpperCase();

	if (getConnectorConfig(normalizedId)) {
		return <ConnectConnector />;
	}

	const Component = (PAGE_MAP as {[key: string]: React.ComponentType})[
		normalizedId
	];

	if (Component) {
		return <Component />;
	}

	return <RouteNotFound />;
};

export default DataSourceOnboarding;
