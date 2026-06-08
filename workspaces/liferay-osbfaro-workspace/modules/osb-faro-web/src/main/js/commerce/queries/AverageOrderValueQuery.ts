/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';
import {Trend} from '~/commerce/utils/types';

export interface CommerceAverageOrderValueData {
	orderAverageCurrencyValues: {
		currencyCode: string;
		trend: Trend;
		value: string;
	}[];
}

export default gql`
	query CommerceAverageOrderValue(
		$channelId: String!
		$rangeEnd: String
		$rangeKey: Int
		$rangeStart: String
	) {
		orderAverageCurrencyValues(
			channelId: $channelId
			rangeEnd: $rangeEnd
			rangeKey: $rangeKey
			rangeStart: $rangeStart
		) {
			currencyCode
			trend {
				trendClassification
				percentage
			}
			value
		}
	}
`;
