/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';

import MarketplaceSpringBootOAuth2 from '../services/oauth/MarketplaceSpringBootOAuth2';

const useMarketplaceSpringBootOAuth2 = () => {
	const marketplaceSpringBootOAuth2 = useMemo(
		() => new MarketplaceSpringBootOAuth2(),
		[]
	);

	return marketplaceSpringBootOAuth2;
};

export default useMarketplaceSpringBootOAuth2;
