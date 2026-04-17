/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useEffect, useState} from 'react';
import {Navigate, Outlet, useParams} from 'react-router-dom';
import {useAppPropertiesContext} from '~/contexts/AppPropertiesContext';
import {Liferay} from '~/services/liferay';
import {getBusinessEventByIdLegacy} from '~/services/liferay/api';
import {getBusinessEventById} from '~/services/liferay/rest/jira/Jira';
import i18n from '~/utils/I18n';

interface BusinessEventOutletProps {
	skip: boolean;
}

const BusinessEventOutlet: React.FC<BusinessEventOutletProps> = ({skip}) => {
	const {featureFlags} = useAppPropertiesContext();
	const isJiraBackend = featureFlags.includes('LRSD-11821');
	const {accountKey, id} = useParams();
	const [isValidBusinessEvent, setIsValidBusinessEvent] = useState<
		boolean | null
	>(null);
	const [isLoading, setIsLoading] = useState(true);

	useEffect(() => {
		const validateBusinessEvent = async () => {
			if (skip) {
				return;
			}

			if (!id || !accountKey) {
				setIsValidBusinessEvent(false);
				setIsLoading(false);

				return;
			}

			try {
				if (isJiraBackend) {
					await getBusinessEventById(accountKey, id);
				}
				else {
					await getBusinessEventByIdLegacy(id);
				}

				setIsValidBusinessEvent(true);
			}
			catch (error) {
				console.error('Error fetching business event:', error);

				Liferay.Util.openToast({
					message: i18n.translate('an-unexpected-error-occurred'),
					type: 'danger',
				});

				setIsValidBusinessEvent(false);
			}
			finally {
				setIsLoading(false);
			}
		};

		validateBusinessEvent();
	}, [id, accountKey, isJiraBackend, skip]);

	if (isLoading || skip) {
		return (
			<div className="mx-auto">
				<ClayLoadingIndicator size="sm" />
			</div>
		);
	}

	if (!isValidBusinessEvent && !skip) {
		return <Navigate replace to={`/${accountKey}/business-events`} />;
	}

	return <Outlet />;
};

export default BusinessEventOutlet;
