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

	const fallbackElementRef = useRef<HTMLElement | null>(null);
	const triggerElementRef = useRef<HTMLElement | null>(null);

	const handleActiveChange = (value: boolean) => {
		if (value) {
			triggerElementRef.current = document.activeElement as HTMLElement;

			fallbackElementRef.current =
				triggerElementRef.current?.parentElement?.closest<HTMLElement>(
					'[tabindex="-1"]'
				) ?? null;
		}

		setActive(value);
	};

	const restoreFocus = () => {
		const fallbackElement = fallbackElementRef.current;
		const triggerElement = triggerElementRef.current;

		fallbackElementRef.current = null;
		triggerElementRef.current = null;

		if (!triggerElement) {
			return;
		}

		setTimeout(() => {
			const {activeElement} = document;

			if (
				activeElement &&
				activeElement !== document.body &&
				!activeElement.closest('.base-dropdown-menu-root')
			) {
				return;
			}

			if (triggerElement.isConnected) {
				triggerElement.focus();
			}
			else {
				fallbackElement?.focus();
			}
		});
	};

	useEffect(() => {
		if (onActiveChange) {
			onActiveChange(active);
		}

		if (!active) {
			restoreFocus();
		}
	}, [active]);

	useEffect(() => restoreFocus, []);

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
