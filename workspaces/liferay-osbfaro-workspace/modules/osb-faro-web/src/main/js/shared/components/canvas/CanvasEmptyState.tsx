import emptyStateReducedMotionURL from '../../../../images/states/empty_state_reduced_motion.svg';
import emptyStateURL from '../../../../images/states/empty_state.svg';
import getCN from 'classnames';
import React from 'react';
import {Heading, Text} from '@clayui/core';

export type DropState = 'dragging' | 'idle' | 'over';

export interface ICanvasEmptyStateProps
	extends React.HTMLAttributes<HTMLDivElement> {
	description: string;
	dropState?: DropState;
	title: string;
}

/**
 * Mirrors the markup of ClayEmptyState, whose title only accepts a string
 * rendered inside a span, so that the headline can be a Clay Heading. While an
 * item is dragged, the content is hidden rather than removed so that the drop
 * area keeps its height.
 */
const CanvasEmptyState: React.FC<ICanvasEmptyStateProps> = ({
	className,
	description,
	dropState = 'idle',
	title,
	...otherProps
}) => (
	<div
		className={getCN(
			'align-items-center canvas-empty-state d-flex justify-content-center rounded-lg',
			`canvas-empty-state-${dropState}`,
			{

				// The utility is important, so it would hide the drop color

				'bg-white': dropState !== 'over',
			},
			className
		)}
		{...otherProps}
	>
		<div
			className={getCN('c-empty-state c-empty-state-animation', {
				invisible: dropState !== 'idle',
			})}
		>
			<div className="c-empty-state-image">
				<div className="c-empty-state-aspect-ratio">
					<img
						alt=""
						className="aspect-ratio-item aspect-ratio-item-fluid d-none-c-prefers-reduced-motion"
						src={emptyStateURL}
					/>

					<img
						alt=""
						className="aspect-ratio-item aspect-ratio-item-fluid d-block-c-prefers-reduced-motion"
						src={emptyStateReducedMotionURL}
					/>
				</div>
			</div>

			<div className="c-empty-state-title text-dark">
				<Heading fontSize={6} level={3} weight="bold">
					{title}
				</Heading>
			</div>

			<div className="c-empty-state-text">
				<Text color="secondary">{description}</Text>
			</div>
		</div>
	</div>
);

export default CanvasEmptyState;
