/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {FieldProps} from 'formik';
import {isEmpty} from 'lodash';
import React, {useEffect, useState} from 'react';
import {IInputListProps as IInputListComponentProps} from '~/shared/components/InputList';

import Input from '../Input';
import InputList from '../InputList';
import HelpBlock from './HelpBlock';
import Label from './Label';

interface IInputListProps
	extends React.HTMLAttributes<HTMLElement>,
		FieldProps {
	contentAfter?: React.ReactNode;
	errorMessage: string;
	inline?: boolean;
	inset?: {
		content: React.ReactNode;
		position: 'after' | 'before';
	};
	label?: React.ReactNode;
	onChangeInputList: (value: string) => void;
	popover?: {
		content: React.ReactNode;
		title: React.ReactNode;
	};
	required?: boolean;
	secondaryInfo?: React.ReactNode;
	showHelpBlock?: boolean;
	text?: {
		content: React.ReactNode;
		position: 'append' | 'prepend';
	};
	validationFn: (value?: string) => boolean | void;
}

type RenderInputProps = {
	contentAfter?: React.ReactNode;
	inset?: {
		content: React.ReactNode;
		position: 'after' | 'before';
	};
	text?: {
		content: React.ReactNode;
		position: 'append' | 'prepend';
	};
} & IInputListComponentProps;

const renderInput: React.FC<RenderInputProps> = ({
	contentAfter,
	inset,
	text,
	...props
}) => {
	if (!isEmpty(text)) {
		if (text.position === 'prepend') {
			return (
				<Input.Group>
					<Input.GroupItem position="prepend" shrink>
						<Input.Text>{text.content}</Input.Text>
					</Input.GroupItem>

					<Input.GroupItem position="append">
						<InputList {...props} />
					</Input.GroupItem>
				</Input.Group>
			);
		}
		else {
			return (
				<Input.Group>
					<Input.GroupItem position="prepend">
						<InputList {...props} />
					</Input.GroupItem>

					<Input.GroupItem position="append" shrink>
						<Input.Text>{text.content}</Input.Text>
					</Input.GroupItem>
				</Input.Group>
			);
		}
	}
	else if (!isEmpty(inset)) {
		return (
			<Input.Group>
				<Input.GroupItem>
					<InputList {...props} />

					<Input.Inset position={inset.position}>
						{inset.content}
					</Input.Inset>
				</Input.GroupItem>
			</Input.Group>
		);
	}
	else if (contentAfter) {
		return (
			<Input.Group>
				<InputList {...props} />

				<Input.GroupItem shrink>{contentAfter}</Input.GroupItem>
			</Input.Group>
		);
	}
	else {
		return <InputList {...props} />;
	}
};

const FormInputList: React.FC<IInputListProps> = ({
	className,
	contentAfter,
	errorMessage,
	field: {name, value},
	form: {errors, setFieldError, setFieldTouched, setFieldValue, touched},
	inline,
	inset,
	label,
	onChangeInputList,
	popover,
	required,
	secondaryInfo,
	showHelpBlock = true,
	text,
	validationFn = () => true,
	...otherProps
}) => {
	const hasError = errors[name];
	const isTouched = touched[name];

	const [items, setItems] = useState(value || []);
	const [inputValue, setInputValue] = useState<string>();

	useEffect(() => {
		setFieldValue(name, items);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [items]);

	useEffect(() => {
		setItems(value);
	}, [value]);

	useEffect(() => {
		if (inputValue) {
			setFieldTouched(name);
		}
		else {
			setFieldError(name, '');
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [inputValue]);

	onChangeInputList(inputValue || '');

	const classes = getCN(className, {
		'form-inline-group': inline,
		'has-error': isTouched && hasError,
		'has-success': isTouched && !hasError,
	});

	const onValidationFail = () => setFieldError(name, errorMessage);

	return (
		<div className={classes}>
			{label && (
				<Label htmlFor={name} popover={popover} required={required}>
					{label}
				</Label>
			)}

			{secondaryInfo && (
				<Label className="font-weight-normal" htmlFor={name}>
					<p>{secondaryInfo}</p>
				</Label>
			)}

			<div className="input-group">
				{renderInput({
					contentAfter,
					inputValue: inputValue || '',
					inset,
					items,
					onInputChange: setInputValue,
					onItemsChange: setItems,
					onValidationFail,
					text,
					validateOnBlur: true,
					validationFn,
					...otherProps,
				})}
			</div>

			{showHelpBlock && <HelpBlock name={name} />}
		</div>
	);
};

export default FormInputList;
