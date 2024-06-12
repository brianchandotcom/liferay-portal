/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Rest from '~/core/Rest';

import yupSchema from '../../schema/yup';
import {TestrayRequirement} from './types';

type Requirement = typeof yupSchema.requirement.__outputType & {
	components: string;
	projectId: number;
};

class TestrayRequirementsImpl extends Rest<Requirement, TestrayRequirement> {
	constructor() {
		super({
			adapter: ({
				componentId: r_componentToRequirements_c_componentId,
				components,
				description,
				descriptionType,
				key,
				linkTitle,
				linkURL,
				projectId: r_projectToRequirements_c_projectId,
				summary,
			}) => ({
				components,
				description,
				descriptionType,
				key,
				linkTitle,
				linkURL,
				r_componentToRequirements_c_componentId,
				r_projectToRequirements_c_projectId,
				summary,
			}),
			nestedFields:
				'component,team,componentToRequirements.teamToComponents',
			transformData: (testrayRequirement) => ({
				...testrayRequirement,
				component:
					testrayRequirement.r_componentToRequirements_c_component
						? {
								...testrayRequirement.r_componentToRequirements_c_component,
								team: testrayRequirement
									.r_componentToRequirements_c_component
									.r_teamToComponents_c_team,
							}
						: undefined,
			}),
			uri: 'requirements',
		});
	}
}

const testrayRequirementsImpl = new TestrayRequirementsImpl();

export {testrayRequirementsImpl};
