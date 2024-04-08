/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import JobDefinitionParameter from './JobDefinitionParameter';

export async function getJobDefinitionParametersByJobDefinition({
	jobDefintionId,
	setJobDefinitionParameters,
}) {
	const response = await liferayRequest({
		urlPath:
			'/o/c/jobdefinitions/' +
			jobDefintionId +
			'/jobDefinitionsToJobDefinitionParameters',
	});

	const result = JSON.parse(await response.text());

	const jobDefinitionParameters = [];

	result.items.forEach((item) => {
		jobDefinitionParameters.push(new JobDefinitionParameter(item));
	});

	if (jobDefinitionParameters && setJobDefinitionParameters) {
		setJobDefinitionParameters(jobDefinitionParameters);
	}
}
