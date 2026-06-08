/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';
import {COMPOSITION_FRAGMENT} from '~/shared/queries/fragments';
import {CompositionTypes} from '~/shared/util/constants';

const INTERESTS_ID_MAP = {
	[CompositionTypes.AccountInterests]: 'accountId',
	[CompositionTypes.SegmentInterests]: 'individualSegmentId',
};

const InterestsQuery = function InterestsQuery(queryName) {
	return gql`
        query Interests(
            $active: Boolean!
            $channelId: String
            $id: String!
            $keywords: String
            $size: Int!
            $sort: Sort!
            $start: Int!
        ) {
            ${queryName}(
                active: $active
                channelId: $channelId
                ${INTERESTS_ID_MAP[queryName]}: $id
                keywords: $keywords
                size: $size
                sort: $sort
                start: $start
            ) {
                ...compositionFragment
            }
        }

        ${COMPOSITION_FRAGMENT}
    `;
};

export default InterestsQuery;
