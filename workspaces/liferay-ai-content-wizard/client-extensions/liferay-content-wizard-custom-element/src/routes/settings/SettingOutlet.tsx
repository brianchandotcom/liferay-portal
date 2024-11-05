/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {Outlet, useParams} from 'react-router-dom';
import useSWR from 'swr';

import useAIWizardContentOAuth2 from '../../hooks/useAIWizardOAuth2';

export default function SettingOutlet() {
	const aiWizardOAuth2 = useAIWizardContentOAuth2();
	const {id} = useParams();

	const {
		data: setting,
		isLoading,
		mutate,
	} = useSWR(`/setting/${id}`, () => aiWizardOAuth2.getSetting(id as string));

	if (isLoading) {
		return <ClayLoadingIndicator />;
	}

	return <Outlet context={{mutate, setting}} />;
}
