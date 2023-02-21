import {ClayInput} from '@clayui/form';

import {FieldBase} from '../FieldBase';

import './Input.scss';

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
	type?: 'number' | 'textarea' | 'text' | 'date';
	value?: string;
}

export function Input({
	className,
	component = 'input',
	helpMessage,
	hideFeedback,
	label,
	localized = false,
	onChange,
	placeholder,
	required,
	tooltip,
	type,
	value,
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
				className="custom-input"
				component={component}
				onChange={onChange}
				placeholder={placeholder}
				type={type}
				value={value}
				{...otherProps}
			/>
		</FieldBase>
	);
}
