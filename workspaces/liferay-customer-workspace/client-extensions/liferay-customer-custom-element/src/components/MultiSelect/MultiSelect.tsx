/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Badge} from '..';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayMultiSelect from '@clayui/multi-select';
import {InternalDispatch} from '@clayui/shared';
import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';
import {FieldHookConfig, useField, useFormikContext} from 'formik';
import {useEffect} from 'react';
import i18n from '~/utils/I18n';
import {validateEmailsArray} from '~/utils/validations.form';

import './MultiSelect.css';

interface IItem {
	email?: string;
	label?: string;
	value: string;
}

interface IProps
	extends React.ComponentPropsWithoutRef<typeof ClayMultiSelect> {
	filteredSourceItems?: IItem[] | undefined;
	groupStyle?: string;
	items?: IItem[] | undefined;
	label?: string | undefined;
	metaErrorCallback?: (error: string | undefined) => void;
	name?: string;
	onChange?: InternalDispatch<string> | undefined;
	required?: boolean;
	sourceItems?: {email: string}[] | undefined;
	validations?: Function[] | undefined;
	values?: IItem[] | undefined;
}

const MultiSelect = ({
	filteredSourceItems,
	groupStyle,
	items,
	label,
	name,
	metaErrorCallback,
	onChange,
	required = false,
	sourceItems,
	validations = [],
	values,
}: IProps) => {
	const formik = useFormikContext();

	const validateMultiSelect = () => {
		const unfilledField = validations
			.map((validation) => validation(values))
			.filter((error) => !!error);

		const emailErrors = validateEmailsArray(
			values?.map((item) => item?.email || item?.label) as string[],
			sourceItems || []
		);

		return unfilledField.length ? unfilledField[0] : emailErrors;
	};

	const [field, meta] = useField({
		name,
		label,
		validate: validateMultiSelect,
		values,
	} as unknown as FieldHookConfig<IItem[]>);

	useEffect(() => {
		formik.setFieldValue(field?.name, values);
		formik.validateField(field?.name);
	}, [field?.name, formik, values]);

	useEffect(() => {
		if (metaErrorCallback) {
			metaErrorCallback(meta.error);
		}

	}, [meta.error, metaErrorCallback]);

	const requiredMultiSelect = (value: number) => {
		if (!value) {
			return i18n.sub(
				'one-or-more-contacts-are-required-please-select-a-contact-for-x',
				[label as string]
			);
		}

		return undefined;
	};

	if (required) {
		validations = validations
			? [
					...validations,
					(value: string) => requiredMultiSelect(value?.length),
				]
			: [(value: string) => requiredMultiSelect(value?.length)];
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

					{required && (
						<span className="inline-item-after reference-mark text-warning">
							<ClayIcon symbol="asterisk" />
						</span>
					)}
				</label>

				<ClayMultiSelect
					{...field}
					items={items}
					onChange={(event: any) => onChange?.(event?.target?.value)}
					sourceItems={filteredSourceItems}
					value={items?.map((item) => String(item?.value)) as unknown as string}
				>
					{(item, index) => (
						<ClayMultiSelect.Item
							key={index}
							onPointerEnterCapture={() => {}}
							onPointerLeaveCapture={() => {}}
							placeholder={item?.label}
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

				{meta?.touched && meta?.error && (
					<Badge>
						<span className="pl-1">{meta.error}</span>
					</Badge>
				)}
			</ClayForm.Group>
		</div>
	);
};

export default MultiSelect;
