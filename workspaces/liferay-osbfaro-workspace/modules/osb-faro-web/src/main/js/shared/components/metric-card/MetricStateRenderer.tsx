/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloError} from '@apollo/client';
import React from 'react';
import StatesRenderer from '~/shared/components/states-renderer/StatesRenderer';

import ErrorDisplay from '../ErrorDisplay';

interface IMetricStateRendererProps {
	children?: React.ReactNode;
	error?: ApolloError;
	loading: boolean;
	spacer?: boolean;
}

const MetricStateRenderer: React.FC<IMetricStateRendererProps> = ({
	children,
	error,
	loading,
	spacer = false,
}) => (
	<StatesRenderer empty={false} error={!!error} loading={loading}>
		<StatesRenderer.Loading spacer={spacer} />
		<StatesRenderer.Error apolloError={error}>
			<ErrorDisplay />
		</StatesRenderer.Error>
		<StatesRenderer.Success>{children}</StatesRenderer.Success>
	</StatesRenderer>
);

export default MetricStateRenderer;
