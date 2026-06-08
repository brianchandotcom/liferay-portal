/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {AttributesProvider} from '~/event-analysis/components/event-analysis-editor/context/attributes';

import BaseEventAnalysisPage from '../components/BaseEventAnalysisPage';

const Create: React.FC<React.HTMLAttributes<HTMLElement>> = () => (
	<AttributesProvider>
		<BaseEventAnalysisPage />
	</AttributesProvider>
);

export default Create;
