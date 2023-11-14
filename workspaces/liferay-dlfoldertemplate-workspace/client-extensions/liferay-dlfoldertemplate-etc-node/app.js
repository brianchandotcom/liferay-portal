/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import express from 'express';
import {Server} from 'http';
import bodyParser from 'body-parser';
import cors from 'cors';
import Services from "./services/services.js";
import config from './util/configTreePath.js';
import {
	corsWithReady,
	liferayJWT,
} from './util/liferay-oauth2-resource-server.js';
import {logger} from './util/logger.js';
// Global Variables
const serverPort = config['server.port'];
const ServicesMainAddress = config['services.main.address'];

const app = express();
app.use(bodyParser.json());
app.use(corsWithReady);
app.use(cors());
app.use(express.json());
app.use(liferayJWT);
app.use('/jobs', Services);
app.get(config.readyPath, (req, res) => {
	res.send({"status":"UP","groups":["liveness","readiness"]});
});
app.listen(serverPort, (message) => {
	console.log(message);
	logger.log(`config: ${JSON.stringify(config, null, '\t')}`);
	// eslint-disable-next-line no-console
	console.log(`App listening on ${serverPort}`);
});
export default app;
