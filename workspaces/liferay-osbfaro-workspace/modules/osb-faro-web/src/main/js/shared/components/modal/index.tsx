/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';

import Body from './Body';
import Footer from './Footer';
import Header from './Header';

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
			[`modal-${type}`]: type,
		})}
		role="dialog"
	>
		<div className="modal-content">{children}</div>
	</div>
);

export default Object.assign(Modal, {
	Body,
	Footer,
	Header,
	SIZES,
});
