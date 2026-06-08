/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloError} from '@apollo/client';
import React from 'react';
import StatesRenderer from '~/shared/components/states-renderer/StatesRenderer';

import ErrorDisplay from '../ErrorDisplay';

interface IAudienceReportStateRendererProps {
	children?: React.ReactNode;
	error: ApolloError;
	loading: boolean;
}

const AudienceReportStateRenderer: React.FC<
	IAudienceReportStateRendererProps
> = ({children, error, loading}) => (
	<StatesRenderer empty={false} error={!!error} loading={loading}>
		<StatesRenderer.Loading />
		<StatesRenderer.Error apolloError={error}>
			<ErrorDisplay />
		</StatesRenderer.Error>
		<StatesRenderer.Success>{children}</StatesRenderer.Success>
	</StatesRenderer>
);

export default AudienceReportStateRenderer;
