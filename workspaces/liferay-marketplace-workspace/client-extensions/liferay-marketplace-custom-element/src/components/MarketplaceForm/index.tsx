/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormHTMLAttributes, ReactNode} from 'react';
import {FormProvider} from 'react-hook-form';

import {Input} from './Input';
import {Label} from './Label';

type FormProps = {
	children: ReactNode;
	formProviderProps: any;
} & FormHTMLAttributes<HTMLFormElement>;

type FormChildrens = {
	Input: typeof Input;
	Label: typeof Label;
};

const Form: React.FC<FormProps> & FormChildrens = ({
	children,
	formProviderProps,
	...formProps
}) => (
	<FormProvider {...formProviderProps}>
		<form className="my-3 space-y-5" {...formProps}>
			{children}
		</form>
	</FormProvider>
);

Form.Input = Input;
Form.Label = Label;

export default Form;
