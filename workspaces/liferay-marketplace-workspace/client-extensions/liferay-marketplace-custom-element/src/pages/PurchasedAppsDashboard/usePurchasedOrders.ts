/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import useSWR from 'swr';

import {getPlacedOrders} from '../../utils/api';

type Props = {
	accountId: number;
	channelId: number | string;
	orderTypeExternalReferenceCodes: string[];
	page: number;
	pageSize: number;
};

const usePurchasedOrders = ({
	accountId,
	channelId,
	orderTypeExternalReferenceCodes,
	page,
	pageSize,
}: Props) => {
	const key = `/placed-orders/${accountId}/${channelId}/${page}/${pageSize}`;

	const swr = useSWR(
		`/placed-orders/${accountId}/${channelId}/${page}/${pageSize}`,
		async () => {
			const placedOrders = await getPlacedOrders(
				accountId,
				channelId,
				page,
				pageSize
			);

			return {
				...placedOrders,
				items: placedOrders.items.filter(
					({orderTypeExternalReferenceCode}) =>
						orderTypeExternalReferenceCodes.length
							? orderTypeExternalReferenceCodes.includes(
									orderTypeExternalReferenceCode
							  )
							: true
				),
			};
		}
	);

	return {key, ...swr};
};

export {usePurchasedOrders};
