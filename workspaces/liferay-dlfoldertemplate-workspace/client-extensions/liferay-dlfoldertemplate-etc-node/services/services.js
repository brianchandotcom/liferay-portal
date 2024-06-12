/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import express from 'express';

import {createFolders} from '../util/folder-structure.js';

const router = express.Router();

router.post(
	'/create/folder/direct/:templateId/:containerId/:rootName',
	async (request, response) => {
		try {
			const {containerId, rootName, templateId} = request.params;

			await createFolders(rootName, templateId, containerId);

			response.status(200).json({});
		}
		catch (error) {

			// eslint-disable-next-line no-console
			console.log(error.message);

			response.status(500).json({error: error.message});
		}
	}
);
export default router;
