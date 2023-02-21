import { ClayInput } from '@clayui/form';
import { FieldBase } from '../FieldBase';

import './Input.scss';
import classNames from 'classnames';

interface InputProps
	extends React.InputHTMLAttributes<HTMLInputElement | HTMLTextAreaElement> {
	component?: 'input' | 'textarea';
	description?: string;
	helpMessage?: string;
	hideFeedback?: boolean;
	label?: string;
	localized?: boolean;
	required?: boolean;
	tooltip?: string;
	value?: string;
	type?: 'number' | 'textarea' | 'text' | 'date';
}

export function Input({
	className,
	component = 'input',
	helpMessage,
	hideFeedback,
	label,
	localized = false,
	placeholder,
	required,
	tooltip,
	onChange,
	value,
	type,
	...otherProps
}: InputProps) {
	return (
		<FieldBase
			className={className}
			helpMessage={helpMessage}
			hideFeedback={hideFeedback}
			label={label}
			localized={localized}
			required={required}
			tooltip={tooltip}
		>
			<ClayInput
				className='custom-input'
				component={component}
				placeholder={placeholder}
				onChange={onChange}
				type={type}
				value={value}
				{...otherProps}
			/>
		</FieldBase>
	);
}
