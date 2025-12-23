/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {SLA_TYPES} from '~/utils/constants';

export default function getStyleFromTitle(title) {
	return {
		cardStyle: {
			'bg-brand-secondary-lighten-6 border-brand-secondary-lighten-4':
				title === SLA_TYPES.gold ||
				title === SLA_TYPES.standard,
			'bg-neutral-0 border-brand-primary-darken-2 ':
				title === SLA_TYPES.limited,
			'bg-neutral-0 border-neutral-2 ':
				title === SLA_TYPES.global ||
				title === SLA_TYPES.platinum ||
				title === SLA_TYPES.premier ||
				title === SLA_TYPES.strategic,
			'border-brand-primary-lighten-3 bg-brand-primary-lighten-5': title === SLA_TYPES.premium,
		},
		dateStyle: {
			'text-brand-primary-darken-2': title === SLA_TYPES.limited,
			'text-brand-primary-lighten-1': title === SLA_TYPES.premium,
			'text-brand-secondary-darken-3':
				title === SLA_TYPES.gold ||
				title === SLA_TYPES.standard,
			'text-neutral-6':
				title === SLA_TYPES.global ||
				title === SLA_TYPES.platinum ||
				title === SLA_TYPES.premier ||
				title === SLA_TYPES.strategic,
			'text-center':
			    title === SLA_TYPES.premier ||
			    title === SLA_TYPES.standard ||
				title === SLA_TYPES.strategic,
		},
		labelStyle: {
			'label-borderless-dark text-neutral-7':
				title === SLA_TYPES.global ||
				title === SLA_TYPES.platinum ||
				title === SLA_TYPES.premier ||
				title === SLA_TYPES.strategic,
			'label-borderless-primary text-brand-primary-darken-2':
				title === SLA_TYPES.limited,
			'label-borderless-secondary text-brand-primary-lighten-1': title === SLA_TYPES.premium,
			'label-borderless-secondary text-brand-secondary-darken-3':
				title === SLA_TYPES.gold ||
				title === SLA_TYPES.standard,
		},
		titleStyle: {
			'pr-2':
			    title === SLA_TYPES.premier ||
				title === SLA_TYPES.standard ||
				title === SLA_TYPES.strategic,
			'text-brand-primary-darken-2': title === SLA_TYPES.limited,
			'text-brand-primary-lighten-1':	title === SLA_TYPES.premium,
			'text-brand-secondary-darken-3': 
				title === SLA_TYPES.gold ||
				title === SLA_TYPES.standard,
			'text-neutral-7':
				title === SLA_TYPES.global ||
				title === SLA_TYPES.platinum ||
				title === SLA_TYPES.premier ||
				title === SLA_TYPES.strategic,
		},
	};
}
