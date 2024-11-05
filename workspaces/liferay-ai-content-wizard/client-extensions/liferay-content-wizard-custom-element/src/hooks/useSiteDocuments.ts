/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {Liferay} from '../services/liferay';

const useSiteDocuments = () => {
	return useSWR('/o/graphql/documents', async () => {
		const response = await Liferay.Util.fetch('/o/graphql', {
			body: JSON.stringify({
				query: `query Documents {
                documents(siteKey: "${Liferay.ThemeDisplay.getScopeGroupId()}", flatten: true) {
                    items {
                    contentUrl
                    fileName
                    folder {
                        id
                        name
                        }
                    id
                    }
                    totalCount
                }
                }`,
			}),
			headers: {
				'Content-Type': 'application/json',
			},
			method: 'POST',
		});

		return response.json();
	});
};

export default useSiteDocuments;
