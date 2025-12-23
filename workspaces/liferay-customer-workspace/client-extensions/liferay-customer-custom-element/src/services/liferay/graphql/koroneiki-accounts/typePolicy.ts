/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SLA_STATUS_TYPES, SLA_TYPES} from '~/utils/constants';

import concatPageSizePagination from '../common/utils/concatPageSizePagination';

export const koroneikiAccountsTypePolicy = {
	C_KoroneikiAccount: {
		fields: {
			hasPrioritySLA: {
				read(
					_: any,
					{readField}: {readField: (arg: string) => any}
				): boolean {
					const slaCurrent = readField('slaCurrent');

					return (
						slaCurrent &&
						(slaCurrent.includes(SLA_TYPES.global) ||
							slaCurrent.includes(SLA_TYPES.gold) ||
							slaCurrent.includes(SLA_TYPES.platinum) ||
							slaCurrent.includes(SLA_TYPES.premier) ||
							slaCurrent.includes(SLA_TYPES.standard) ||
							slaCurrent.includes(SLA_TYPES.strategic))
					);
				},
			},
			status: {
				read(
					_: any,
					{readField}: {readField: (arg: string) => any}
				): string {
					if (
						readField('partnershipCurrent') ||
						readField('slaCurrent')
					) {
						return SLA_STATUS_TYPES.active;
					}

					if (
						readField('partnershipFuture') ||
						readField('slaFuture')
					) {
						return SLA_STATUS_TYPES.future;
					}

					return SLA_STATUS_TYPES.expired;
				},
			},
		},
		keyFields: ['externalReferenceCode'],
	},
	C_KoroneikiAccountPage: {
		fields: {
			items: {
				...concatPageSizePagination(true),
			},
		},
	},
};

export const koroneikiAccountsQueryTypePolicy = {
	koroneikiAccounts: {
		keyArgs: ['filter'],
	},
};
