/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {REACT_APP_LIFERAY_API = window.location.origin} = process.env;

const API_BASE_URL = REACT_APP_LIFERAY_API;

export {API_BASE_URL};
