/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';

import Jethr0Input from '../../components/Jethr0Input/Jethr0Input';
import {getJobParameterValue, getUpdatedJobParameters} from '../../objects/jobs/JobUtil';

function Jethr0JobParameterFields({
	jobDefinitionParameters,
	jobParameters,
	setJobParameters,
}) {

	if (!jobDefinitionParameters || !jobParameters) {
		return <></>;
	}

	return jobDefinitionParameters.map((jobDefinitionParameter) => {
		return (
			<ClayForm.Group
				key={jobDefinitionParameter.key}
			>
				<label htmlFor={jobDefinitionParameter.key}>
					{jobDefinitionParameter.label}
				</label>

				<Jethr0Input
					id={jobDefinitionParameter.key}
					onChange={(event) => {
						setJobParameters(
							getUpdatedJobParameters({
								jobParameters,
								key: jobDefinitionParameter.key,
								value: event.target.value,
							})
						);
					}}
					placeholder={
						jobDefinitionParameter.valueDescription
					}
					type="text"
					value={getJobParameterValue({
						jobParameters,
						key: jobDefinitionParameter.key,
					})}
				/>
			</ClayForm.Group>
		);
	});
}

export default Jethr0JobParameterFields;
