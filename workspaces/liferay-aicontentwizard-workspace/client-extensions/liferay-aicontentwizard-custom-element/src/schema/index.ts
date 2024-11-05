/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {z} from 'zod';

export const contentWizardSettings = z.object({
	active: z.boolean(),
	apiKey: z.string().min(3),
	description: z.string(),
	id: z.number().optional(),
	imageModel: z.string().min(1),
	model: z.string().min(1),
	provider: z.string().min(1),
});
