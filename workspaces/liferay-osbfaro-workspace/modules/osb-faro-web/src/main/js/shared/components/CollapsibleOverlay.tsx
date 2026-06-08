/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import React from 'react';

interface ICollapsibleOverlayProps extends React.HTMLFactory<HTMLElement> {
	children?: React.ReactNode;
	onClose: (event: React.MouseEvent<HTMLButtonElement>) => void;
	title: string;
	visible: boolean;
}

const CollapsibleOverlay: React.FC<ICollapsibleOverlayProps> = ({
	children,
	onClose,
	title = '',
	visible = false,
}) => (
	<div
		className={getCN('collapsible-overlay-root', {
			hidden: !visible,
		})}
		hidden={!visible}
	>
		<div className="content-wrapper">
			<div className="header">
				<h3>{title}</h3>

				<ClayButton
					aria-label={Liferay.Language.get('close')}
					className="button-root"
					displayType="unstyled"
					onClick={onClose}
				>
					<ClayIcon className="icon-root" symbol="times" />
				</ClayButton>
			</div>

			{children}
		</div>
	</div>
);

export default CollapsibleOverlay;
