import React, {ReactNode, ForwardedRef} from 'react';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';

type ButtonWithIconProps = {
	children?: ReactNode;
	className?: string;
	symbol: string;
} & React.ComponentProps<typeof ClayButton>;

const ButtonWithIcon = React.forwardRef<HTMLButtonElement, ButtonWithIconProps>(
	(
		{children, className, symbol = 'plus', ...props},
		ref: ForwardedRef<HTMLButtonElement>
	) => {
		return (
			<ClayButton className={className} ref={ref} {...props}>
				<ClayIcon className="mr-2" symbol={symbol} />

				{children}
			</ClayButton>
		);
	}
);

export default ButtonWithIcon;
