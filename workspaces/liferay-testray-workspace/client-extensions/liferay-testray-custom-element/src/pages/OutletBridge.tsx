/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Outlet, useOutletContext} from 'react-router-dom';

/**
 * @description this is a bridge, to connect the parent outlet with his child
 * @returns Outlet
 */
const OutletBridge = () => {
	const outletContext = useOutletContext();

	return <Outlet context={outletContext} />;
};

export default OutletBridge;
