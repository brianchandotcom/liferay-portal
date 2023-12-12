/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import TestrayError from '../../TestrayError';
import Rest from '../../core/Rest';
import SearchBuilder from '../../core/SearchBuilder';
import i18n from '../../i18n';
import yupSchema from '../../schema/yup';
import {testrayCaseResultImpl} from './TestrayCaseResult';
import {APIResponse, TestrayCase} from './types';

type Case = typeof yupSchema.case.__outputType & {projectId: number};

class TestrayCaseImpl extends Rest<Case, TestrayCase> {
	constructor() {
		super({
			adapter: ({
				caseTypeId: r_caseTypeToCases_c_caseTypeId,
				componentId: r_componentToCases_c_componentId,
				description,
				descriptionType,
				estimatedDuration,
				name,
				priority,
				projectId: r_projectToCases_c_projectId,
				steps,
				stepsType,
			}) => ({
				description,
				descriptionType,
				estimatedDuration,
				name,
				priority,
				r_caseTypeToCases_c_caseTypeId,
				r_componentToCases_c_componentId,
				r_projectToCases_c_projectId,
				steps,
				stepsType,
			}),
			nestedFields: 'build.project,build.routine,caseType,component.team',
			transformData: (testrayCase) => ({
				...testrayCase,
				caseResults: testrayCase?.caseToCaseResult?.map((caseResult) =>
					testrayCaseResultImpl.transformData(caseResult)
				),
				caseType: testrayCase?.r_caseTypeToCases_c_caseType,
				component: testrayCase?.r_componentToCases_c_component
					? {
							...testrayCase?.r_componentToCases_c_component,
							team:
								testrayCase?.r_componentToCases_c_component
									?.r_teamToComponents_c_team,
					  }
					: undefined,
				project: testrayCase?.r_projectToCases_c_project,
			}),
			uri: 'cases',
		});
	}

	protected async validate(Case: Case, id?: number) {
		const searchBuilder = new SearchBuilder();

		if (id) {
			searchBuilder.ne('id', id).and();
		}

		const filters = searchBuilder
			.eq('name', Case.name)
			.and()
			.eq('projectId', Case.projectId)
			.build();

		const response = await this.fetcher<APIResponse<TestrayCase>>(
			`/cases?filter=${filters}`
		);

		if (response?.totalCount) {
			throw new TestrayError(
				i18n.sub('the-x-name-already-exists', 'case')
			);
		}
	}

	protected async beforeCreate(Case: Case): Promise<void> {
		await this.validate(Case);
	}

	protected async beforeUpdate(id: number, Case: Case): Promise<void> {
		await this.validate(Case, id);
	}
}

const testrayCaseImpl = new TestrayCaseImpl();

export {testrayCaseImpl};
