import Body from './Body';
import Footer from './Footer';
import getCN from 'classnames';
import Header from './Header';
import React from 'react';

const SIZES = ['sm', 'lg', 'xl', 'xxl'];

export type Size = 'sm' | 'lg' | 'xl' | 'xxl';

type Type = 'danger' | 'info' | 'success' | 'warning';

interface IModalProps extends React.HTMLAttributes<HTMLDivElement> {
	size?: Size;
	type?: Type;
}

const Modal: React.FC<IModalProps> = ({children, className, size, type}) => (
	<div
		aria-modal
		className={getCN('modal-dialog', className, {
			[`modal-${size}`]: size,
			[`modal-${type}`]: type
		})}
		role='dialog'
	>
		<div className='modal-content'>{children}</div>
	</div>
);

export default Object.assign(Modal, {
	Body,
	Footer,
	Header,
	SIZES
});
