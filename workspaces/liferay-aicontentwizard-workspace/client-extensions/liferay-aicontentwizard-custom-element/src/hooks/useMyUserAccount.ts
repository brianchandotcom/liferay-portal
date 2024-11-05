/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {Liferay} from '../services/liferay';

const useMyUserAccount = () =>
	useSWR('/my-user-account', () =>
		Liferay.Util.fetch('/o/headless-admin-user/v1.0/my-user-account').then(
			(response) => response.json()
		)
	);

export {useMyUserAccount};
