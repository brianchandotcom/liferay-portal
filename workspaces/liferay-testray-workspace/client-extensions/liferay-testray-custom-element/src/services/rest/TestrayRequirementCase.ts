/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetcher from '../fetcher';

const nestedFieldsParam =
	'nestedFields=case.component,requirement.component.team&nestedFieldsDepth=3';

const requirementsCasesResource = `/requirementscaseses?${nestedFieldsParam}`;

const createRequirementCaseBatch = (
	requirements: {caseId: number; requirementId: number}[]
) => {
	if (requirements.length <= 20) {
		return Promise.allSettled(
			requirements.map(
				({
					caseId: r_caseToRequirementsCases_c_caseId,
					requirementId: r_requiremenToRequirementsCases_c_requirementId,
				}) =>
					fetcher.post('/requirementscaseses', {
						name:
							r_caseToRequirementsCases_c_caseId +
							r_requiremenToRequirementsCases_c_requirementId,
						r_caseToRequirementsCases_c_caseId,
						r_requiremenToRequirementsCases_c_requirementId,
					})
			)
		);
	}

	return fetcher.post(
		'/requirementscaseses/batch',
		requirements.map(
			({
				caseId: r_caseToRequirementsCases_c_caseId,
				requirementId: r_requiremenToRequirementsCases_c_requirementId,
			}: any) => ({
				name:
					r_caseToRequirementsCases_c_caseId +
					r_requiremenToRequirementsCases_c_requirementId,
				r_caseToRequirementsCases_c_caseId,
				r_requiremenToRequirementsCases_c_requirementId,
			})
		)
	);
};

const deleteRequirementCaseBatch = (requirements: any) => {
	const requirementsList = requirements.map(({id}: {id: number}) => id);

	if (requirements.length <= 20) {
		return Promise.allSettled(
			requirementsList.map((id: number) =>
				fetcher.delete(`/requirementscaseses/${id}`)
			)
		);
	}

	return fetcher.delete('/requirementscaseses/batch');
};

export {
	createRequirementCaseBatch,
	requirementsCasesResource,
	deleteRequirementCaseBatch,
};
