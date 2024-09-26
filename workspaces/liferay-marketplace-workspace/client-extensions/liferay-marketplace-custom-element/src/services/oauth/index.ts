/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Analytics from './Analytics';
import Console from './Console';
import Koroneiki from './Koroneiki';
import Provisioning from './Provisioning';
import Trial from './Trial';

const oAuthServices = {
	Analytics,
	Console,
	Koroneiki,
	Provisioning,
	Trial,
};

export default oAuthServices;
