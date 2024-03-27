/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import liferayRequest from '../../services/liferayRequest';
import JobDefinitionParameter from './JobDefinitionParameter';

export function getJobDefinitionParametersByJobDefinition({
	jobDefintionId,
	setJobDefinitionParameters,
}) {
	liferayRequest({
		urlPath:
			'/o/c/jobdefinitions/' +
			jobDefintionId +
			'/jobDefinitionsToJobDefinitionParameters',
	})
		.then((request) => request.text())
		.then((result) => {
			const resultJSON = JSON.parse(result);

			const jobDefinitionParameters = [];

			resultJSON.items.forEach((item) => {
				jobDefinitionParameters.push(new JobDefinitionParameter(item));
			});

			if (jobDefinitionParameters && setJobDefinitionParameters) {
				setJobDefinitionParameters(jobDefinitionParameters);
			}
		})
		.catch((error) => {
			// eslint-disable-next-line no-console
			console.log(error);
		});
}
