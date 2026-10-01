import ClayDropdown, {Align} from '@clayui/drop-down';
import getCN from 'classnames';
import Header from './Header';
import React, {useEffect, useRef, useState} from 'react';
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
	trigger,
}) => {
	const [active, setActive] = useState(false);

	const triggerElementRef = useRef<HTMLElement | null>(null);

	const handleActiveChange = (value: boolean) => {
		if (value) {
			triggerElementRef.current = document.activeElement as HTMLElement;
		}

		setActive(value);
	};

	useEffect(() => {
		if (onActiveChange) {
			onActiveChange(active);
		}

		const triggerElement = triggerElementRef.current;

		if (active || !triggerElement) {
			return;
		}

		triggerElementRef.current = null;

		setTimeout(() => {
			const {activeElement} = document;

			if (
				triggerElement.isConnected &&
				(!activeElement ||
					activeElement === document.body ||
					activeElement.closest('.base-dropdown-menu-root'))
			) {
				triggerElement.focus();
			}
		});
	}, [active]);

	return (
		<ClayDropdown
			active={active}
			alignmentPosition={alignmentPosition}
			menuElementAttrs={{
				className: getCN('base-dropdown-menu-root', className),
			}}
			onActiveChange={handleActiveChange}
			trigger={trigger}
		>
			{children({active, setActive})}
		</ClayDropdown>
	);
};

export default Object.assign(BaseDropdown, {
	Header,
	SearchableList,
});
