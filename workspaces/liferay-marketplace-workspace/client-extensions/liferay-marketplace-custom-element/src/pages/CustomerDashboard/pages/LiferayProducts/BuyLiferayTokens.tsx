/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {useOutletContext} from 'react-router-dom';

import {getSiteURL} from '../../../../utils/site';

export default function BuyLiferayTokens() {
	const data = useOutletContext<any>();

	const productId = data?.product?.productId;

	useEffect(() => {
		if (!productId) {
			return;
		}

		window.location.href = `${getSiteURL()}/product-purchase?productId=${productId}&aiHubTokens#/`;
	}, [productId]);

	return null;
}
