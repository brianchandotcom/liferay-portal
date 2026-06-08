/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

let responseData;

function setResponseData(data) {
	responseData = data;
}

function clearResponseData() {
	responseData = undefined;
}

const sendRequest = jest.fn(() => Promise.resolve(responseData));

sendRequest.setResponseData = setResponseData;
sendRequest.clearResponseData = clearResponseData;

export default sendRequest;
