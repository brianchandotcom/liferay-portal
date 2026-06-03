import React from 'react';

interface ICellProps {
	children: React.ReactNode;
	className?: string;
	title?: boolean;
}

const Cell: React.FC<ICellProps> = ({children, className, title}) => (
	<td className={className}>
		{title ? (
			<div className='h4 table-title text-truncate'>{children}</div>
		) : (
			children
		)}
	</td>
);

export default Cell;
