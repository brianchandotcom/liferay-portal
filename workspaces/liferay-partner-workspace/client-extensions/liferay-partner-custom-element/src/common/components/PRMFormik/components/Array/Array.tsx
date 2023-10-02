/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ArrayHelpers, FieldArray} from 'formik';
import React from 'react';

interface IProps {
	component: JSX.Element;
	name: string;
}

const Array = ({component, name, ...props}: IProps & any) => (
	<FieldArray name={name}>
		{(arrayHelpers: ArrayHelpers) =>
			React.createElement(component, {arrayHelpers, ...props})
		}
	</FieldArray>
);

export default Array;
