/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {MarketplaceSpringBootOAuth2} from './OAuth2Client';

class DXPOAuth2Client extends MarketplaceSpringBootOAuth2 {}

const dxpOAuth2Client = new DXPOAuth2Client('/dxp');

export default dxpOAuth2Client;
