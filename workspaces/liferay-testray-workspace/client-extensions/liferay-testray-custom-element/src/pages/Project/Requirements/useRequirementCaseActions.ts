/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TestrayRequirement} from '../../../services/rest';
import useCaseRequirementActions from '../Cases/useCaseRequirementActions';

const useRequirementCaseActions = (testrayRequirement: TestrayRequirement) => {
	return useCaseRequirementActions({requirementId: testrayRequirement.id});
};

export default useRequirementCaseActions;
