/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {useEffect, useState} from 'react';
import SearchBuilder from '~/common/core/SearchBuilder';
import {useAppPropertiesContext} from '../../../../../../common/contexts/AppPropertiesContext';
import {getCommerceOrderItems} from '../../../../../../common/services/liferay/graphql/queries';
import getActivationStatusDateRange from '../../../../../../common/utils/getActivationStatusDateRange';

export default function useActivationStatusDate(project) {
	const {client} = useAppPropertiesContext();
	const [activationStatusDate, setActivationStatusDate] = useState('');

	useEffect(() => {
		const fetchCommerceOrderItems = async () => {
			const {data} = await client.query({
				fetchPolicy: 'network-only',
				query: getCommerceOrderItems,
				variables: {
					filter: SearchBuilder.eq(
						'customFields/accountSubscriptionGroupERC',
						`${project.accountKey}_liferay-experience-cloud`
					),
				},
			});

			if (data) {
				const activationStatusDateRange = getActivationStatusDateRange(
					data?.orderItems?.items
				);
				setActivationStatusDate(activationStatusDateRange);
			}
		};

		fetchCommerceOrderItems();
	}, [client, project]);

	return {activationStatusDate};
}
