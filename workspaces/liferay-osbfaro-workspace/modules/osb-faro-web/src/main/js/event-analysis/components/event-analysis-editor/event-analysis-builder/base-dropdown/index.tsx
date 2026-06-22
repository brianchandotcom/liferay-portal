import ClayDropdown, {Align} from '@clayui/drop-down';
import getCN from 'classnames';
import Header from './Header';
import React, {useEffect, useState} from 'react';
import SearchableList from './SearchableList';

interface IBaseDropdownProps {
	alignmentPosition?: (typeof Align)[keyof typeof Align];
	children: (bag: {
		active: boolean;
		setActive: (v: boolean) => void;
	}) => React.ReactNode;
	className?: string;
	trigger: React.ReactElement;
	onActiveChange?: (active: boolean) => void;
}

const BaseDropdown: React.FC<IBaseDropdownProps> = ({
	alignmentPosition = Align.RightTop,
	children,
	className,
	onActiveChange,
	trigger
}) => {
	const [active, setActive] = useState(false);

	useEffect(() => {
		if (onActiveChange) {
			onActiveChange(active);
		}
	}, [active]);

	return (
		<ClayDropdown
			active={active}
			alignmentPosition={alignmentPosition}
			menuElementAttrs={{
				className: getCN('base-dropdown-menu-root', className)
			}}
			onActiveChange={setActive}
			trigger={trigger}
		>
			{children({active, setActive})}
		</ClayDropdown>
	);
};

export default Object.assign(BaseDropdown, {
	Header,
	SearchableList
});
