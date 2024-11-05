/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import AIWizardContentOAuth2 from '../services/oauth/AIWizardContentOAuth2';

const useAIWizardContentOAuth2 = () => {
	const aiWizardContentOAuth2 = useMemo(
		() => new AIWizardContentOAuth2(),
		[]
	);

	return aiWizardContentOAuth2;
};

export default useAIWizardContentOAuth2;
