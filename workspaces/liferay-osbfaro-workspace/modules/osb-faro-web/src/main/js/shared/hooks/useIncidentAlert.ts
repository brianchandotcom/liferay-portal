import {fetchPreferences} from 'shared/api/preferences';
import {useEffect, useState} from 'react';

export const useIncidentAlert = () => {
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
