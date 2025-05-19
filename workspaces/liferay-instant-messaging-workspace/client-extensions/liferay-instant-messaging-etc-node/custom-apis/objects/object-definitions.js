/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {Router} = require('express');

const {
	getObjectDefinitions,
} = require('../../services/liferay-interface/objects');

const router = Router();

router.get('/list', async (req, res) => {
	res.send(await getObjectDefinitions());
});

module.exports = router;
