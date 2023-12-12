/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import TestrayError from '~/TestrayError';
import Rest from '~/core/Rest';
import SearchBuilder from '~/core/SearchBuilder';
import i18n from '~/i18n';
import yupSchema from '~/schema/yup';

import {testrayComponentImpl} from './TestrayComponent';
import {APIResponse, TestrayTeam} from './types';

type Team = typeof yupSchema.team.__outputType;

class TestrayTeamImpl extends Rest<Team, TestrayTeam> {
	constructor() {
		super({
			adapter: ({name, projectId: r_projectToTeams_c_projectId}) => ({
				name,
				r_projectToTeams_c_projectId,
			}),
			nestedFields: 'project',
			transformData: (team) => ({
				...team,
				project: team?.r_projectToTeams_c_project,
			}),
			uri: 'teams',
		});
	}

	protected async validate(team: Team, id?: number): Promise<void> {
		const searchBuilder = new SearchBuilder();

		if (id) {
			searchBuilder.ne('id', id).and();
		}

		const filter = searchBuilder
			.eq('name', team.name)
			.and()
			.eq('projectId', team.projectId as string)
			.build();

		const response = await this.fetcher<APIResponse<TestrayTeam>>(
			`/teams?filter=${filter}`
		);

		if (response?.items?.length) {
			throw new TestrayError(
				i18n.sub('the-x-name-already-exists', 'team')
			);
		}
	}

	protected async beforeCreate(team: Team) {
		await this.validate(team);
	}

	protected async beforeUpdate(id: number, team: Team): Promise<void> {
		await this.validate(team, id);
	}

	protected async beforeRemove(id: number): Promise<void> {
		const response = await testrayComponentImpl.getComponentsByTeamId(id);

		if (response?.totalCount) {
			throw new TestrayError(
				i18n.translate(
					'the-team-cannot-be-deleted-because-it-has-associated-components'
				)
			);
		}
	}
}

export const testrayTeamImpl = new TestrayTeamImpl();
