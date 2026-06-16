import getCN from 'classnames';
import React from 'react';

interface IFooterProps extends React.HTMLAttributes<HTMLDivElement> {
	border?: boolean;
}

const Footer: React.FC<IFooterProps> = ({
	border = false,
	children,
	className
}) => (
	<div className={getCN('modal-footer', className, {border})}>{children}</div>
);

export default Footer;
