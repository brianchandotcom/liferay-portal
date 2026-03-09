/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayCard from '@clayui/card';
import Icon from '@clayui/icon';
import classNames from 'classnames';
import {useState} from 'react';

export type ActivationKeyAlertProps = {
	children?: string;
	className?: string;
	onClose?: () => void;
	symbol?: string;
	title: string;
};

export default function ActivationKeyAlert({
	children,
	className,
	onClose,
	symbol,
	title,
}: ActivationKeyAlertProps) {
	const [visible, setVisible] = useState(true);

	if (!visible) {
		return null;
	}

	const handleClose = () => {
		setVisible(false);
		onClose?.();
	};

	return (
		<ClayCard
			className={classNames(
				'license-alert-card d-flex align-items-start',
				className
			)}
		>
			<div>
				<Icon className="license-alert-icon" symbol={symbol ?? ''} />
			</div>

			<div className="flex-grow-1">
				<p className="license-alert-title">{title}</p>
				<p className="license-alert-subtitle">{children}</p>
			</div>

			<ClayButton
				aria-label="Close"
				className="close"
				displayType="unstyled"
				onClick={handleClose}
			>
				<Icon className="close-license-alert-icon" symbol="times" />
			</ClayButton>
		</ClayCard>
	);
}
