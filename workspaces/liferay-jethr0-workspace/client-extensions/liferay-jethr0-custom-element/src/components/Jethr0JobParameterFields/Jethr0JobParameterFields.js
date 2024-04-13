/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';

import Jethr0Input from '../../components/Jethr0Input/Jethr0Input';
import {
	getJobParameter,
	getUpdatedJobParameters,
} from '../../objects/jobs/JobUtil';

function Jethr0JobParameterFields({
	jobDefinitionParameters,
	jobParameters,
	setJobParameters,
}) {
	if (!jobDefinitionParameters || !jobParameters) {
		return <></>;
	}

	return jobDefinitionParameters.map((jobDefinitionParameter) => {
		const key = jobDefinitionParameter.key;

		const jobParameter = getJobParameter({jobParameters, key});

		let disabled = false;

		if (jobParameter?.routineField) {
			disabled = true;
		}

		return (
			<ClayForm.Group key={key}>
				<label htmlFor={key}>{jobDefinitionParameter.label}</label>

				<Jethr0Input
					disabled={disabled}
					id={key}
					onChange={(event) => {
						setJobParameters(
							getUpdatedJobParameters({
								jobParameters,
								key,
								value: event.target.value,
							})
						);
					}}
					placeholder={jobDefinitionParameter.valueDescription}
					type="text"
					value={jobParameter?.value}
				/>
			</ClayForm.Group>
		);
	});
}

export default Jethr0JobParameterFields;
