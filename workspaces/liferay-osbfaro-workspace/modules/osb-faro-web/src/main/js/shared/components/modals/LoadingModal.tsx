/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import React from 'react';
import Loading from '~/shared/components/Loading';
import Modal from '~/shared/components/modal';

interface ILoadingModalProps extends React.HTMLAttributes<HTMLDivElement> {
	icon?: string;
	message?: string;
	title?: string;
}

const LoadingModal: React.FC<ILoadingModalProps> = ({
	className,
	icon,
	message = Liferay.Language.get('loading'),
	title,
}) => (
	<Modal className={getCN('loading-modal-root', className)} size="sm">
		{title && <h1 className="title">{title}</h1>}

		<div className="icon-container">
			{icon ? (
				<ClayIcon className="icon-root icon-size-xl" symbol={icon} />
			) : (
				<Loading />
			)}
		</div>

		{message && <p className="message">{message}</p>}
	</Modal>
);

export default LoadingModal;
