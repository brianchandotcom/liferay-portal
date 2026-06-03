import getCN from 'classnames';
import React from 'react';

const Row: React.FC<React.HTMLAttributes<HTMLDivElement>> = ({
	children,
	className
}) => (
	<div
		className={getCN(
			'row-root align-items-center d-flex justify-content-between',
			className
		)}
	>
		{children}
	</div>
);

export default Row;
