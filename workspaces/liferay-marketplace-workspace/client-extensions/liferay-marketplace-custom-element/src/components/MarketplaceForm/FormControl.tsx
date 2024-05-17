/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HTMLAttributes} from 'react';

type FormControlProps = HTMLAttributes<HTMLDivElement>;

const FormControl: React.FC<FormControlProps> = ({children, ...otherProps}) => {
	return <div {...otherProps}>{children}</div>;
};

export default FormControl;
