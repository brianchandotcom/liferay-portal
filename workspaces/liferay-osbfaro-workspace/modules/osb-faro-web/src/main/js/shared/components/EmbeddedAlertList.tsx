import Alert, {ALERT_CONFIG_MAP, AlertTypes} from 'shared/components/Alert';
import getCN from 'classnames';
import React from 'react';

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
	className
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
