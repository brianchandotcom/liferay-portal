/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayMultiSelect from '@clayui/multi-select';
import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';
import {useField, useFormikContext} from 'formik';
import {useEffect} from 'react';
import i18n from '~/common/I18n';
import {Badge} from '../..';
import {validateEmailsArray} from '../../../utils/validations.form';

const MultiSelect = ({
	groupStyle,
	items,
	label,
	metaErrorCallback,
	onChange,
	sourceItems,
	validations,
	values,
	...props
}) => {
	const formik = useFormikContext();

	const validateMultiSelect = () => {
		const unfilledField = validations
			.map((validation) => validation(values))
			.filter((error) => !!error);

		const emailErrors = validateEmailsArray(
			values.map((item) => item?.email || item?.label),
			sourceItems
		);

		return unfilledField.length ? unfilledField[0] : emailErrors;
	};

	const [field, meta] = useField({
		...props,
		validate: validateMultiSelect,
	});

	useEffect(() => {
		formik.setFieldValue(props.name, values);
		formik.validateField(props.name);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values]);

	useEffect(() => {
		metaErrorCallback(meta.error);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [meta.error]);

	const requiredMultiSelect = (value) => {
		if (!value) {
			return i18n.sub(
				'one-or-more-contacts-are-required-please-select-a-contact-for-x',
				[label]
			);
		}
	};

	if (props.required) {
		validations = validations
			? [...validations, () => requiredMultiSelect(values.length)]
			: [() => requiredMultiSelect(values.length)];
	}

	return (
		<div className="multi-select-container">
			<ClayForm.Group
				className={classNames('w-100', {
					groupStyle,
					'has-error': meta.touched && meta.error,
					'has-success': meta.touched && !meta.error,
				})}
			>
				<label className="ml-0">
					{`${label} `}

					{props.required && (
						<span className="inline-item-after reference-mark text-warning">
							<ClayIcon symbol="asterisk" />
						</span>
					)}
				</label>

				<ClayMultiSelect
					{...field}
					{...props}
					items={items}
					onChange={(event) => onChange(event?.target?.value)}
					sourceItems={sourceItems}
					value={items?.value}
				>
					{(item, index) => (
						<ClayMultiSelect.Item
							key={index}
							textValue={item?.label}
						>
							<div className="autofit-row autofit-row-center">
								<div className="autofit-col mr-3">
									<ClaySticker
										className="sticker-user-icon"
										size="sm"
									>
										<ClayIcon symbol="user" />
									</ClaySticker>
								</div>

								<div className="autofit-col">
									<strong>{item?.label}</strong>

									<span>{item?.email}</span>
								</div>
							</div>
						</ClayMultiSelect.Item>
					)}
				</ClayMultiSelect>

				{(typeof meta.error === 'string' ||
					meta.error instanceof String) &&
					meta.touched && (
						<Badge>
							<span className="pl-1">{meta.error}</span>
						</Badge>
					)}
			</ClayForm.Group>
		</div>
	);
};

export default MultiSelect;
