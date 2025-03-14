/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayTimePicker from '@clayui/time-picker';
import {Input as TimeInput} from '@clayui/time-picker/lib';
import classNames from 'classnames';
import {useField} from 'formik';
import {
	required as requiredVaidation,
	validate,
} from '~/utils/validations.form';

import './TimePicker.css';

import getIconSpriteMap from '~/utils/getIconSpriteMap';

import Badge from '../Badge';

interface IProps {
	badgeClassName?: string;
	className?: string;
	groupStyle?: string;
	helper?: string;
	id?: string;
	label?: string;
	name: string;
	onBlur?: () => void;
	onChange?: (date: TimeInput) => void;
	required?: boolean;
	validations?: ((value: any) => string | undefined)[];
}

const TimePicker: React.FC<IProps> = ({
	badgeClassName,
	className,
	groupStyle,
	helper,
	id,
	label,
	name,
	onBlur,
	onChange,
	required,
	validations = [],
}) => {
	if (required) {
		validations = validations
			? [...validations, (value: string) => requiredVaidation(value)]
			: [(value: string) => requiredVaidation(value)];
	}

	const [field, meta, helpers] = useField<TimeInput>({
		className,
		id,
		name,
		required,
		validate: (value) =>
			validate(validations, `${value?.hours}:${value?.minutes}`),
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

	const handleChange = (value: TimeInput) => {
		helpers.setValue(value);

		if (onChange) {
			onChange(value);
		}
	};
	const defaultValue: TimeInput = {
		hours: '--',
		minutes: '--',
	};

	return (
		<ClayForm.Group
			className={classNames('w-100', getStyleStatus(), groupStyle)}
		>
			<label className="date-picker">
				{label}

				{required && (
					<span className="inline-item-after reference-mark text-warning">
						<ClayIcon symbol="asterisk" />
					</span>
				)}

				<ClayTimePicker
					onBlur={handleBlur}
					onChange={handleChange}
					spritemap={getIconSpriteMap()}
					use12Hours={false}
					value={field.value || defaultValue}
				/>
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

export default TimePicker;
