import {HTMLAttributes} from 'react';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

import './AlertBox.scss';

export default function AlertBox({
	className,
	...props
}: HTMLAttributes<HTMLDivElement>) {
	return (
		<div
			{...props}
			className={classNames('alert-box-container', className)}
		>
			<ClayIcon
				fontSize={32}
				color="#B95000"
				symbol="warning-full
"
			/>
		</div>
	);
}
