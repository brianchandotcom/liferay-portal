/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Entity} from './types';

export interface EntityDisplay {
	icon: string;
	label: string;
}

const ENTITY_DISPLAY: Record<Entity, EntityDisplay> = {
	[Entity.Accounts]: {
		icon: 'briefcase',
		label: Liferay.Language.get('accounts'),
	},
	[Entity.Events]: {
		icon: 'click',
		label: Liferay.Language.get('events'),
	},
	[Entity.Individuals]: {
		icon: 'user',
		label: Liferay.Language.get('individuals'),
	},
	[Entity.Sites]: {
		icon: 'sites',
		label: Liferay.Language.get('sites'),
	},
	[Entity.Users]: {
		icon: 'users',
		label: Liferay.Language.get('users'),
	},
};

export function getEntityDisplay(entity: Entity): EntityDisplay {
	return ENTITY_DISPLAY[entity];
}
