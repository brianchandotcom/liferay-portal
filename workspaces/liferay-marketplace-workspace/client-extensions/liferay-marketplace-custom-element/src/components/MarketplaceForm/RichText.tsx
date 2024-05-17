/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {useState} from 'react';
import ReactQuill from 'react-quill';

import {removeHTMLTags} from '../../utils/string';

type RichTextProps = {
	maxLength?: number;
	onChange: (value: any) => void;
	placeholder?: string;
	value: string;
};

const VALIDATE_TIMEOUT = 1000;

const RichTextEditor: React.FC<RichTextProps> = ({
	maxLength,
	onChange,
	placeholder,
	value = '',
}) => {
	const [validate, setValidate] = useState(false);

	function handleKeyDown(event: any) {
		setValidate(false);

		const text = removeHTMLTags(value);

		if (event.key === 'Backspace') {
			return setTimeout(() => {
				setValidate(false);
			}, VALIDATE_TIMEOUT);
		}

		if (text.length === maxLength) {
			setValidate(true);
		}

		setTimeout(() => {
			setValidate(false);
		}, VALIDATE_TIMEOUT);
	}

	return (
		<>
			<ReactQuill
				{...(maxLength && {onKeyDown: handleKeyDown})}
				className={classNames({
					'mb-1': maxLength,
				})}
				onChange={(event) => onChange(event)}
				placeholder={placeholder}
				readOnly={validate}
				value={value}
			/>

			{maxLength && (
				<small className="text-black-50">
					{`${
						value ? removeHTMLTags(value).length : 0
					}/ ${maxLength}`}
				</small>
			)}
		</>
	);
};

export default RichTextEditor;
