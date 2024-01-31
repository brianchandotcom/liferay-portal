/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import bodyParser from 'body-parser';
import cors from 'cors';
import express from 'express';

import Services from './services/services.js';
import config from './util/configTreePath.js';
import {corsWithReady, liferayJWT,} from './util/liferay-oauth2-resource-server.js';
import {getConfigByKey} from "./util/config-util.js";
import {serviceConfigKeys} from "./util/constants.js";


const serverPort = getConfigByKey(serviceConfigKeys.SERVER_PORT);

const app = express();

app.use(bodyParser.json());

app.use(corsWithReady);

app.use(cors());

app.use(express.json());

app.use(liferayJWT);

app.use('/jobs', Services);

app.get(getConfigByKey(serviceConfigKeys.READY_PATH), (req, res) => {

	res.send({groups: ['liveness', 'readiness'], status: 'UP'});

});

app.listen(serverPort, () => {

	// eslint-disable-next-line no-console
	console.log(`config: ${JSON.stringify(config, null, '\t')}`);

	// eslint-disable-next-line no-console
	console.log(`App listening on ${serverPort}`);
});

export default app;
