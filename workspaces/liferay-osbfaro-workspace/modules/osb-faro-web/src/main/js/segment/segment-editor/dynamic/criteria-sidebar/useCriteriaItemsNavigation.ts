import React, {useEffect} from 'react';

const ITEM_SELECTOR = '.criteria-sidebar-item-root';

const getItems = (root: HTMLElement): HTMLElement[] =>
	Array.from(root.querySelectorAll<HTMLElement>(ITEM_SELECTOR));

const setTabStop = (items: HTMLElement[], target: HTMLElement) => {
	items.forEach((item) => {
		item.tabIndex = item === target ? 0 : -1;
	});
};

/**
 * Keeps a single tab stop across the criteria items, wherever they are
 * rendered inside the sidebar, and moves focus between them with the arrow,
 * Home, and End keys.
 */
export default function useCriteriaItemsNavigation(
	rootRef: React.RefObject<HTMLElement>
) {
	useEffect(() => {
		const root = rootRef.current;

		if (!root) {
			return;
		}

		const ensureTabStop = () => {
			const items = getItems(root);

			if (items.length && !items.some((item) => item.tabIndex === 0)) {
				items[0].tabIndex = 0;
			}
		};

		ensureTabStop();

		const observer = new MutationObserver(ensureTabStop);

		observer.observe(root, {childList: true, subtree: true});

		return () => observer.disconnect();
	}, [rootRef]);

	const handleFocus = (event: React.FocusEvent<HTMLElement>) => {
		const target = event.target as HTMLElement;

		if (rootRef.current && target.matches(ITEM_SELECTOR)) {
			setTabStop(getItems(rootRef.current), target);
		}
	};

	const handleKeyDown = (event: React.KeyboardEvent<HTMLElement>) => {
		const target = event.target as HTMLElement;

		if (!rootRef.current || !target.matches(ITEM_SELECTOR)) {
			return;
		}

		const items = getItems(rootRef.current);

		const index = items.indexOf(target);

		let nextIndex = null;

		if (event.key === 'ArrowDown') {
			nextIndex = Math.min(index + 1, items.length - 1);
		}
		else if (event.key === 'ArrowUp') {
			nextIndex = Math.max(index - 1, 0);
		}
		else if (event.key === 'End') {
			nextIndex = items.length - 1;
		}
		else if (event.key === 'Home') {
			nextIndex = 0;
		}

		if (nextIndex === null) {
			return;
		}

		event.preventDefault();

		setTabStop(items, items[nextIndex]);

		items[nextIndex].focus();
	};

	return {handleFocus, handleKeyDown};
}
