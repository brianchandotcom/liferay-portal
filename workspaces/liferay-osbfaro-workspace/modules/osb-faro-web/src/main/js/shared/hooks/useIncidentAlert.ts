/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';
import {fetchPreferences} from '~/shared/api/preferences';

export const useIncidentAlert = function useIncidentAlert() {
	const [data, setData] = useState({incidentAlertEnabled: false});
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		const fetchData = async () => {
			try {
				const response = await fetchPreferences();

				setData(response.preferences || {incidentAlertEnabled: false});
			}
			catch (error) {
				throw new Error(
					`Failed to fetch incident alert status: ${error}`
				);
			}
			finally {
				setLoading(false);
			}
		};

		fetchData();
	}, []);

	return {
		data,
		loading,
		onClose: () => setData({incidentAlertEnabled: false}),
	};
};
