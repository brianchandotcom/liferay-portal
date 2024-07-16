/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import {SLA_LABELS} from '../utils/constants/slaLabels';
import getSLACard from '../utils/getSLACard';

export default function useSLACards(koroneikiAccount) {
	return useMemo(() => {
		if (koroneikiAccount) {
			const {
				slaCurrent,
				slaCurrentEndDate,
				slaCurrentStartDate,
				slaExpired,
				slaExpiredEndDate,
				slaExpiredStartDate,
				slaFuture,
				slaFutureEndDate,
				slaFutureStartDate,
			} = koroneikiAccount;

			const slaCards = [];

			if (slaCurrent) {
				slaCards.push(
					getSLACard(
						slaCurrent === slaFuture
							? slaFutureEndDate
							: slaCurrentEndDate,
						slaCurrent === slaExpired
							? slaExpiredStartDate
							: slaCurrentStartDate,
						slaCurrent,
						SLA_LABELS.current
					)
				);
			}

			if (!!slaFuture && slaFuture !== slaCurrent) {
				slaCards.push(
					getSLACard(
						slaFutureEndDate,
						slaFutureStartDate,
						slaFuture,
						SLA_LABELS.future
					)
				);
			}

			if (!!slaExpired && slaExpired !== slaCurrent) {
				slaCards.push(
					getSLACard(
						slaExpiredEndDate,
						slaExpiredStartDate,
						slaExpired,
						SLA_LABELS.expired
					)
				);
			}

			return slaCards;
		}
	}, [koroneikiAccount]);
}
