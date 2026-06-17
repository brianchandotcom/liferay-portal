import {ConnectorStatus} from './types';
import {DataSource} from 'shared/util/records';
import {getConnectorStatus} from './getConnectorStatus';

export interface ConnectorStatusDisplay {
	display: 'secondary' | 'success' | 'warning';
	label: string;
}

const STATUS_DISPLAY: Record<ConnectorStatus, ConnectorStatusDisplay> = {
	[ConnectorStatus.Active]: {
		display: 'success',
		label: Liferay.Language.get('active'),
	},
	[ConnectorStatus.Disconnected]: {
		display: 'secondary',
		label: Liferay.Language.get('disconnected'),
	},
	[ConnectorStatus.Inactive]: {
		display: 'warning',
		label: Liferay.Language.get('inactive'),
	},
};

export function getConnectorStatusDisplay(
	dataSource?: DataSource
): ConnectorStatusDisplay {
	return STATUS_DISPLAY[getConnectorStatus(dataSource)];
}
