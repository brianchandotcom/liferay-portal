/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Form, Formik, FormikConfig, FormikValues} from 'formik';
import React from 'react';

import Array from './components/Array';
import Field from './components/Field';
import PRMFormikPageProps from './interfaces/prmFormikPageProps';

const PRMFormik = <T extends FormikValues>({
	children,
	...props
}: FormikConfig<T>) => {
	const currentChild = React.Children.only(children) as React.ReactElement<
		PRMFormikPageProps
	>;

	return (
		<Formik
			{...props}
			validationSchema={currentChild.props.validationSchema}
		>
			<Form>{currentChild}</Form>
		</Formik>
	);
};

PRMFormik.Array = Array;
PRMFormik.Field = Field;

export default PRMFormik;
