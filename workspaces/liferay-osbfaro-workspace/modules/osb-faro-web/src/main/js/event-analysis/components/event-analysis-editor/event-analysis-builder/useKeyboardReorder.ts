import React, {useEffect, useRef, useState} from 'react';
import {HoverTypes} from './useSortableChip';
import {sub} from 'shared/util/lang';
import {useAnnounce} from './AnnounceContext';

type Movement = {from: number; to: number};

export interface IKeyboardReorderProps {
	handleProps: {
		onBlur: () => void;
		onKeyDown: (event: React.KeyboardEvent) => void;
		ref: (element: HTMLElement | null) => void;
	};
	indicator: HoverTypes | null;
	moving: boolean;
}

const useKeyboardReorder = ({
	count,
	getName,
	onMove,
}: {
	count: number;
	getName: (index: number) => string;
	onMove: (movement: Movement) => void;
}) => {
	const announce = useAnnounce();

	const [movement, setMovement] = useState<Movement | null>(null);

	const movementRef = useRef<Movement | null>(null);
	const handleElementsRef = useRef<(HTMLElement | null)[]>([]);
	const pendingFocusRef = useRef<number | null>(null);

	useEffect(() => {
		if (pendingFocusRef.current === null) {
			return;
		}

		handleElementsRef.current[pendingFocusRef.current]?.focus();

		pendingFocusRef.current = null;
	});

	const updateMovement = (newMovement: Movement | null) => {
		movementRef.current = newMovement;

		setMovement(newMovement);
	};

	const cancel = () => {
		const currentMovement = movementRef.current;

		if (!currentMovement) {
			return;
		}

		updateMovement(null);

		announce(
			sub(Liferay.Language.get('movement-of-x-was-cancelled'), [
				getName(currentMovement.from),
			]) as string
		);
	};

	const moveTo = (to: number) => {
		const currentMovement = movementRef.current!;

		const target = Math.min(Math.max(to, 0), count - 1);

		updateMovement({...currentMovement, to: target});

		announce(
			sub(Liferay.Language.get('x-of-x'), [target + 1, count]) as string
		);
	};

	const place = () => {
		const {from, to} = movementRef.current!;

		const name = getName(from);

		updateMovement(null);

		if (from !== to) {
			pendingFocusRef.current = to;

			onMove({from, to});
		}

		announce(
			sub(Liferay.Language.get('x-placed-on-x-of-x'), [
				name,
				to + 1,
				count,
			]) as string
		);
	};

	const handleKeyDown = (index: number) => (event: React.KeyboardEvent) => {
		const currentMovement = movementRef.current;

		if (!currentMovement) {
			if (event.key === 'Enter' || event.key === ' ') {
				event.preventDefault();

				updateMovement({from: index, to: index});

				announce(
					sub(
						Liferay.Language.get(
							'use-up-and-down-arrows-to-move-x-and-press-enter-to-place-it-in-desired-position'
						),
						[getName(index)]
					) as string
				);
			}

			return;
		}

		const keyActions: {[key: string]: () => void} = {
			' ': place,
			ArrowDown: () => moveTo(currentMovement.to + 1),
			ArrowUp: () => moveTo(currentMovement.to - 1),
			End: () => moveTo(count - 1),
			Enter: place,
			Escape: cancel,
			Home: () => moveTo(0),
		};

		const action = keyActions[event.key];

		if (action) {
			event.preventDefault();

			action();
		}
	};

	const getKeyboardProps = (index: number): IKeyboardReorderProps => {
		let indicator: HoverTypes | null = null;

		if (movement && movement.to === index && movement.from !== index) {
			indicator =
				movement.to < movement.from
					? HoverTypes.Top
					: HoverTypes.Bottom;
		}

		return {
			handleProps: {
				onBlur: cancel,
				onKeyDown: handleKeyDown(index),
				ref: (element) => {
					handleElementsRef.current[index] = element;
				},
			},
			indicator,
			moving: movement?.from === index,
		};
	};

	return getKeyboardProps;
};

export default useKeyboardReorder;
