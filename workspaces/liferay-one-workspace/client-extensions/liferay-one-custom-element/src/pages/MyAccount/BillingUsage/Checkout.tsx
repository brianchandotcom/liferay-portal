/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useProperties} from '../../../context/PropertiesContext';

export default function Checkout() {
	const {accountId} = useProperties();

	return <div>{accountId}</div>;
}
