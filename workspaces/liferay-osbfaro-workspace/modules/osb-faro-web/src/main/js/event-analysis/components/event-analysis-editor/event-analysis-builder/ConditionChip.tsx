import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import getCN from 'classnames';
import React from 'react';
import {sub} from 'shared/util/lang';
import {Text} from '@clayui/core';

interface IConditionChipProps
	extends Omit<React.ButtonHTMLAttributes<HTMLButtonElement>, 'children'> {
	dragState?: string;
	handle?: React.ReactNode;
	icon: string;
	label: React.ReactNode;
	name: string;
	onRemove: () => void;
	overline?: React.ReactNode;
}

const ConditionChip = React.forwardRef<HTMLDivElement, IConditionChipProps>(
	(
		{
			className,
			dragState,
			handle,
			icon,
			label,
			name,
			onRemove,
			overline,
			...otherProps
		},
		ref
	) => (
		<div
			className={getCN(
				'align-items-center condition-chip d-flex rounded-lg',
				{
					'condition-chip-draggable': !!handle,
					[`condition-chip-${dragState}`]: dragState,
				}
			)}
			ref={ref}
		>
			{handle}

			<ClayButton
				{...otherProps}
				className={getCN(
					'align-items-center condition-chip-content d-flex flex-grow-1 px-2 py-2 text-left text-wrap',
					className
				)}
				displayType="unstyled"
			>
				<ClaySticker
					className="condition-chip-sticker flex-shrink-0 mr-2"
					displayType="primary"
				>
					<ClayIcon symbol={icon} />
				</ClaySticker>

				<span className="condition-chip-text d-flex flex-column text-break">
					{overline && (
						<span className="d-block text-uppercase">
							<Text color="secondary" size={1} weight="semi-bold">
								{overline}
							</Text>
						</span>
					)}

					<span className="d-block">
						<Text size={3} weight="semi-bold">
							{label}
						</Text>
					</span>
				</span>
			</ClayButton>

			<ClayButtonWithIcon
				aria-label={
					sub(Liferay.Language.get('remove-x'), [name]) as string
				}
				className="condition-chip-remove flex-shrink-0 mr-1"
				displayType="unstyled"
				monospaced
				onClick={onRemove}
				size="sm"
				symbol="times-circle"
				title={sub(Liferay.Language.get('remove-x'), [name]) as string}
			/>
		</div>
	)
);

export default ConditionChip;
