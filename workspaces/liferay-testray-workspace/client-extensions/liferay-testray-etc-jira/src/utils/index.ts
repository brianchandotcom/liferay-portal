/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Response} from 'express';

const getUserId = (request: any) => {
	if (request.query?.['userId']) {
		return request.query['userId'];
	}

	if (request?.params) {
		return request?.params['liferay-user-id'];
	}

	return request?.headers.get('liferay-user-id');
};

const getHttpContext = ({
	query,
	request,
}: {
	query: {[key: string]: string};
	request: Request;
}) => {
	const userId = getUserId({...request, query});

	const data = {
		authorization: request.headers.get('authorization'),
		userId,
	};

	return data;
};

const runAsyncAction = (callback: () => void, response?: Response) => {
	setTimeout(async () => {
		try {
			await callback();
		}
		catch (error) {
			console.error({error});
		}
	}, 1000);

	if (response) {
		response.send('ok');
	}
};

export {getHttpContext, runAsyncAction};
