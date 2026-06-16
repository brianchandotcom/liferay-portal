import getCN from 'classnames';
import React from 'react';

interface IRowProps extends React.HTMLAttributes<HTMLElement> {
	flex?: Boolean;
}

const Row: React.FC<IRowProps> = ({
	children,
	className,
	flex = false,
	...otherProps
}) => (
	<div
		{...otherProps}
		className={getCN('kit-row-root', className, {
			['d-flex flex-wrap']: flex
		})}
	>
		{children}
	</div>
);

export default Row;
