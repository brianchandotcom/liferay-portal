import getCN from 'classnames';
import React, {useEffect, useRef} from 'react';

interface ICheckboxProps extends React.HTMLAttributes<HTMLInputElement> {
	checked?: boolean;
	disabled?: boolean;
	displayInline?: boolean;
	indeterminate?: boolean;
	label?: React.ReactNode;
	name?: string;
}

const Checkbox: React.FC<ICheckboxProps> = ({
	checked,
	className,
	disabled,
	displayInline,
	indeterminate,
	label,
	name,
	onChange,
	...otherProps
}) => {
	const _checkboxRef = useRef<HTMLInputElement>(null);

	useEffect(() => {
		if (_checkboxRef.current) {
			_checkboxRef.current.indeterminate = !!indeterminate;
		}
	}, [indeterminate]);

	const classes = getCN('custom-control', 'custom-checkbox', className, {
		['custom-control-inline']: displayInline,
	});

	const handleEventPropagation = (event: React.MouseEvent) => {
		event.stopPropagation();
	};

	return (

		// This wrapper only stops click propagation (to prevent row
		// selection); it is intentionally not an interactive control.

		<div className={classes} onClick={handleEventPropagation}>
			<label>
				<input
					{...otherProps}
					checked={checked}
					className="custom-control-input"
					disabled={disabled}
					name={name}
					onChange={onChange}
					ref={_checkboxRef}
					type="checkbox"
					value={String(!!checked)}
				/>

				<span className="custom-control-label">
					{label && (
						<span className="custom-control-label-text">
							{label}
						</span>
					)}
				</span>
			</label>
		</div>
	);
};

export default Checkbox;
