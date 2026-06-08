/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

export interface DataSourceData {
	data: {
		id: string;
		name: string;
		url: string;
	};
}

export interface DataSource {
	contactsSyncDetails: {selected: boolean};
	id: string;
	sitesSyncDetails: {selected: boolean};
}

export interface DataSourceSyncData {
	dataSources: DataSource[];
}

export default gql`
	query DataSource($size: Int, $sort: Sort, $type: String) {
		dataSources(size: $size, sort: $sort, type: $type) {
			contactsSyncDetails {
				selected
			}
			id
			name
			sitesSyncDetails {
				selected
			}
			url
		}
	}
`;
