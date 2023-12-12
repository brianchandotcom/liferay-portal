/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import TestrayError from '../../TestrayError';
import Rest from '../../core/Rest';
import SearchBuilder from '../../core/SearchBuilder';
import i18n from '../../i18n';
import yupSchema from '../../schema/yup';
import {APIResponse, TestraySuite} from './types';

type Suite = typeof yupSchema.suite.__outputType & {projectId: number};

class TestraySuiteImpl extends Rest<Suite, TestraySuite> {
	constructor() {
		super({
			adapter: ({
				autoanalyze,
				caseParameters,
				description,
				id,
				name,
				projectId: r_projectToSuites_c_projectId,
				smartSuite,
			}) => ({
				autoanalyze,
				caseParameters,
				description,
				id,
				name,
				r_projectToSuites_c_projectId,
				smartSuite,
			}),
			uri: 'suites',
		});
	}
	protected async validate(suite: Suite, id?: number) {
		const searchBuilder = new SearchBuilder();

		if (id) {
			searchBuilder.ne('id', id).and();
		}

		const filters = searchBuilder
			.eq('name', suite.name)
			.and()
			.eq('projectId', suite.projectId)
			.build();

		const response = await this.fetcher<APIResponse<TestraySuite>>(
			`/suites?filter=${filters}`
		);

		if (response?.totalCount) {
			throw new TestrayError(
				i18n.sub('the-x-name-already-exists', 'suite')
			);
		}
	}
	protected async beforeCreate(suite: Suite): Promise<void> {
		await this.validate(suite);
	}

	protected async beforeUpdate(id: number, suite: Suite): Promise<void> {
		await this.validate(suite, id);
	}
}

const testraySuiteImpl = new TestraySuiteImpl();

export {testraySuiteImpl};
