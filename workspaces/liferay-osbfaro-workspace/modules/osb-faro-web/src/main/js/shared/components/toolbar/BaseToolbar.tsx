import ClayToolbar from '@clayui/toolbar';
import getCN from 'classnames';
import React from 'react';

interface IBaseToolbarProps {
	children?: React.ReactNode;
	className?: string;
}

const BaseToolbar: React.FC<IBaseToolbarProps> & {
	Item: typeof ClayToolbar.Item;
	Section: typeof ClayToolbar.Section;
} = ({children, className}) => (
	<ClayToolbar
		className={getCN(
			'align-items-center bg-white sticky-top toolbar-root',
			className
		)}
	>
		<ClayToolbar.Nav className="align-items-center mx-3">
			{children}
		</ClayToolbar.Nav>
	</ClayToolbar>
);

BaseToolbar.Item = ClayToolbar.Item;
BaseToolbar.Section = ClayToolbar.Section;

export default BaseToolbar;
