/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';
import Alert, {ALERT_CONFIG_MAP, AlertTypes} from '~/shared/components/Alert';

export interface IEmbeddedAlertListProps
	extends React.HTMLAttributes<HTMLDivElement> {
	alerts: {
		alertType?: AlertTypes;
		customComponent?: typeof Alert;
		iconSymbol?: string;
		id?: string;
		message?: React.ReactNode;
		onClose?: (id: string) => void;
		stripe?: boolean;
		title?: string;
		type?: AlertTypes;
	}[];
}

const EmbeddedAlertList: React.FC<IEmbeddedAlertListProps> = ({
	alerts = [],
	className,
}) => (
	<div className={getCN('embedded-alert-list-root', className)}>
		{alerts.map(
			({alertType, customComponent, message, ...otherParams}, i) => {
				if (customComponent) {
					const CustomAlert = customComponent;

					return <CustomAlert key={i} />;
				}

				const alertConfig = alertType
					? ALERT_CONFIG_MAP[alertType]
					: {};

				return (
					<Alert {...alertConfig} key={i} {...otherParams}>
						{message}
					</Alert>
				);
			}
		)}
	</div>
);

export default EmbeddedAlertList;
