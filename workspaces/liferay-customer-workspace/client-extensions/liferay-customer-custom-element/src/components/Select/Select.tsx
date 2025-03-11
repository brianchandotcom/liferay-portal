/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Badge} from '..';
import ClayForm, {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useField} from 'formik';
import {
	required as requiredVaidation,
	validate,
} from '~/utils/validations.form';

import './Select.css';

import PopoverIconButton from '~/features/project/components/PopoverIconButton';
import i18n from '~/utils/I18n';
import {IOption} from '~/utils/types';

interface IProps {
	badgeClassName?: string;
	className?: string;
	groupStyle?: string;
	helper?: string;
	id?: string;
	label: string;
	link?: string;
	linkText?: string;
	name: string;
	onBlur?: () => void;
	onChange?: (value: string) => void;
	options: IOption[];
	required?: boolean;
	showPopover?: boolean;
	text?: string;
	validations?: Function[];
}

const Select: React.FC<IProps> = ({
	badgeClassName,
	className,
	groupStyle,
	helper,
	id,
	label,
	link,
	linkText,
	name,
	onChange,
	onBlur,
	options,
	showPopover,
	required,
	text,
	validations = [],
}) => {
	if (required) {
		validations = validations
			? [...validations, (value: string) => requiredVaidation(value)]
			: [(value: string) => requiredVaidation(value)];
	}

	const [field, meta, helpers] = useField({
		className,
		id,
		name,
		required,
		validate: (value) => validate(validations, value),
	});

	const getStyleStatus = () => {
		if (meta.touched) {
			return meta.error ? 'has-error' : 'has-success';
		}

		return;
	};

	const handleBlur = () => {
		helpers.setTouched(true);

		if (onBlur) {
			onBlur();
		}
	};

	const handleChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
		const value = event.target.value;

		helpers.setValue(value);

		if (onChange) {
			onChange(value);
		}
	};

	return (
		<ClayForm.Group
			className={classNames('w-100', getStyleStatus(), groupStyle)}
		>
			<label>
				{label}

				{required && (
					<span className="inline-item-after reference-mark text-warning">
						<ClayIcon symbol="asterisk" />
					</span>
				)}

				{showPopover && (
					<PopoverIconButton
						alignPosition="top"
						formatedHTML={i18n.sub(text || '', [
							'<a href="' +
								link +
								'" target="_blank">' +
								i18n.translate(linkText || '') +
								'</a>',
						])}
						iconSize="xs"
						isSubscriptionCard
					/>
				)}

				<div className="position-relative">
					<ClayIcon className="select-icon" symbol="caret-bottom" />

					<ClaySelect
						aria-label={label}
						id={id}
						name={name}
						onBlur={handleBlur}
						onChange={handleChange}
						value={field.value}
					>
						{options.map(({disabled, label, value}, index) => (
							<ClaySelect.Option
								disabled={disabled}
								key={`${value}-${index}`}
								label={label}
								value={value}
							/>
						))}
					</ClaySelect>
				</div>
			</label>

			{meta.touched && meta.error && required && (
				<Badge badgeClassName={badgeClassName}>
					<span className="pl-1">{meta.error}</span>
				</Badge>
			)}

			{helper && (
				<div className="ml-3 pl-3 text-neutral-6 text-paragraph-sm">
					{helper}
				</div>
			)}
		</ClayForm.Group>
	);
};

export default Select;
