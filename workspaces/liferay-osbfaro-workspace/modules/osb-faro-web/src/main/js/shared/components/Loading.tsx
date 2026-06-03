import ClayLoadingIndicator from '@clayui/loading-indicator';
import getCN from 'classnames';
import React from 'react';

export enum Align {
	Left = 'left',
	Right = 'right'
}

const inlineClassName = {
	[Align.Left]: 'mr-2',
	[Align.Right]: 'ml-2'
};

export interface ILoadingProps extends React.HTMLAttributes<HTMLElement> {
	center?: boolean;
	align?: Align;
	overlay?: boolean;
	spacer?: boolean;
}

const Loading: React.FC<ILoadingProps> = ({
	align,
	center = true,
	className,
	overlay = false,
	spacer = false
}) => {
	if (align) {
		return (
			<ClayLoadingIndicator
				className={getCN(
					className,
					'loading-root',
					'd-inline-block',
					inlineClassName[align]
				)}
			/>
		);
	}

	if (overlay) {
		return (
			<div className={getCN(className, 'loading-root', {overlay})}>
				<ClayLoadingIndicator
					className={getCN('loading-root', {
						center
					})}
				/>
			</div>
		);
	}

	if (spacer) {
		return (
			<ClayLoadingIndicator
				className={getCN(className, 'loading-root', {
					spacer
				})}
			/>
		);
	}

	return (
		<ClayLoadingIndicator
			className={getCN(className, 'loading-root', {
				center
			})}
		/>
	);
};

export default Loading;
