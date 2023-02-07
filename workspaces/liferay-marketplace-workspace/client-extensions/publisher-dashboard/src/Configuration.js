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

const ConfigurationLocal = {
	apiServer: '',
	imageURLPrefix: '',
};

const ConfigurationCloud = {
	apiServer: '',
	imageURLPrefix: '',
};

export const ConfigurationProd = {
	apiServer: '',
	imageURLPrefix: '',
};

let Configuration = ConfigurationCloud;

if (
	(window && window.location.hostname === 'localhost') ||
	window.location.hostname === '0.0.0.0'
) {
	Configuration = ConfigurationLocal;
} else if (window && window.location.hostname === 'marketplace.liferay.com') {
	Configuration = ConfigurationProd;
}

export default Configuration;
