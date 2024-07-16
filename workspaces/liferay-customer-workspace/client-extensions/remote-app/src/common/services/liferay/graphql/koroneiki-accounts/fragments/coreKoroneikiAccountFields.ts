/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {gql} from '@apollo/client';

export const CORE_KORONEIKI_ACCOUNT_FIELDS = gql`
	fragment CoreKoroneikiAccountFields on C_KoroneikiAccount {
		accountKey
		acWorkspaceGroupId
		allowSelfProvisioning
		code
		dxpVersion
		externalReferenceCode
		hasSLAGoldPlatinum @client
		liferayContactEmailAddress
		liferayContactName
		liferayContactRole
		maxRequestors
		name
		partner
		region
		slaCurrent
		slaCurrentEndDate
		slaCurrentStartDate
		slaExpired
		slaExpiredEndDate
		slaExpiredStartDate
		slaFuture
		slaFutureEndDate
		slaFutureStartDate
		status @client
	}
`;
