/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as constants from "constants";

import config from './configTreePath.js';

export function getConfigByKey(configKey) {

    configKey = configKey.toString().toUpperCase().replaceAll('.','_');

    if (process.env[configKey] && process.env[configKey].length > 1) {

        return process.env[configKey.toString()]

    }else{

        configKey = configKey.toString().toLowerCase().replaceAll('_','.').replaceAll('-','.');

        if (config[configKey]){

            return config[configKey];

        }else{

            throw new Error(`"Provided key '${configKey}' has not been found!"`);

        }
    }

}

export function getOAuthConfigByKey(erc,configKey) {

    configKey = `${erc}${configKey}`;

    if (process.env[configKey] && process.env[configKey].length > 1) {

        return process.env[configKey.toString().toUpperCase()]

    }else{

        configKey = configKey.toString().toLowerCase().replaceAll('_','.').replaceAll('-','.');

        if (config[configKey]){

            return config[configKey];

        }else{
            throw new Error(`"Provided key '${configKey}' has not been found!"`);
        }
    }

}

