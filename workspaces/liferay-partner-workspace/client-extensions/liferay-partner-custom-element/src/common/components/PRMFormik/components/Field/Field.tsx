/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Field as FormikField, FieldProps} from 'formik';
import React from 'react';

interface IProps {
	component: JSX.Element;
	name: string;
}

const Field = ({component, name, ...props}: IProps & any) => (
	<FormikField name={name}>
		{({field, meta}: FieldProps) =>
			React.createElement(component, {field, meta, ...props})
		}
	</FormikField>
);

export default Field;
