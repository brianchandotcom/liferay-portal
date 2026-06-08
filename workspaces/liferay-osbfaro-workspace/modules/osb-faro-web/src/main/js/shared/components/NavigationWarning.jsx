/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {Prompt} from 'react-router';

const NavigationWarning = ({
	message = Liferay.Language.get(
		'you-have-unsaved-changes-that-will-be-discarded-by-navigating-away-from-this-page.-do-you-want-to-leave-and-discard-your-changes'
	),
	...otherProps
}) => (
	<Prompt
		message={(nextLocation) =>
			nextLocation.pathname === window.location.pathname || message
		}
		{...otherProps}
	/>
);

export default NavigationWarning;
