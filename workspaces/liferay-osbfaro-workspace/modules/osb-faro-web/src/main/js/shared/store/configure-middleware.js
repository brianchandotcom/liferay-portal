/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {applyMiddleware} from 'redux';
import thunk from 'redux-thunk';
import api from '~/shared/middleware/api';
import normalizer from '~/shared/middleware/normalizer';

const middleware = applyMiddleware(api, normalizer, thunk);

export default middleware;
