/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {ErrorMessage} from 'formik';
import React from 'react';

const HelpBlock: React.FC<React.ComponentProps<typeof ErrorMessage>> = ({
	className,
	...otherProps
}) => {
	const classes = getCN('help-block', 'form-feedback-item', className);

	return (
		<ErrorMessage
			{...otherProps}
			render={(message) => <div className={classes}>{message}</div>}
		/>
	);
};

export default HelpBlock;
