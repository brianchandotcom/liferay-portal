/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import i18n from '../../../../common/I18n';
import DeveloperKeysInputs from './Inputs';

const DeveloperKeysLayouts = ({children}) => {
	return (
		<div>
			<h4 className="m-0 py-3">{i18n.translate('developer-keys')}</h4>

			{children}
		</div>
	);
};

DeveloperKeysLayouts.Inputs = DeveloperKeysInputs;

export default DeveloperKeysLayouts;
