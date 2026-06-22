import getCN from 'classnames';
import React from 'react';

const Item: React.FC<React.HTMLAttributes<HTMLElement>> = ({
	children,
	className
}) => <div className={getCN('kit-item-root', className)}>{children}</div>;

export default Item;
