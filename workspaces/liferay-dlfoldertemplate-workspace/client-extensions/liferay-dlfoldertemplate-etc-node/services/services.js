/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import express from 'express';

import {start, startCustom} from '../util/folder-structure.js';

const router = express.Router();
router.post('/hr/employee/folder', (req, res) => {
	const {userName} = req.body;
	const employeeId = userName;
	const templateId = templateId;
	const task = () => {
		start(employeeId, templateId);
	};
	setTimeout(async () => {
		await task();
	}, 3000);

	res.status(200).json(req.body.objectEntry);
});
router.post('/create/folder/:employeeId/:templateId', (req, res) => {
	const employeeId = req.params.employeeId;
	const templateId = req.params.templateId;
	const task = () => {
		start(employeeId, templateId);
	};
	setTimeout(async () => {
		await task();
	}, 3000);

	res.status(200).json({});
});
router.post(
	'/create/folder/direct/:templateId/:containerId/:rootName',
	(req, res) => {
		const containerId = req.params.containerId;
		const templateId = req.params.templateId;
		const rootName = req.params.rootName;
		const task = () => {
			startCustom(rootName, templateId, containerId);
		};
		setTimeout(async () => {
			await task();
		}, 3000);

		res.status(200).json({});
	}
);
export default router;
