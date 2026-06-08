/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {withRouter} from 'react-router';

const WithHistory = function WithHistory(WrappedComponent) {
	return withRouter(({history, ...otherProps}) => (
		<WrappedComponent history={history} {...otherProps} />
	));
};

/**
 * Adds history prop from WithRouter.
 * @param {function} WrappedComponent
 * @returns {function} - The WrappedComponent with the history prop.
 */
export default WithHistory;
