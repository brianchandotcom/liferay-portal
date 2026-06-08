/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import Label from '@clayui/label';
import ClayList from '@clayui/list';
import ClaySticker from '@clayui/sticker';
import React from 'react';
import {sub} from '~/shared/util/lang';

import {getEntityDisplay} from './getEntityDisplay';
import {ConnectorEntityDescriptor, ConnectorStatus} from './types';

interface IConnectorEntitiesProps {
	connectorStatus?: ConnectorStatus;
	entities: ConnectorEntityDescriptor[];
	syncedCounts: {[entity: string]: number | undefined};
}

const ConnectorEntities: React.FC<IConnectorEntitiesProps> = ({
	connectorStatus,
	entities,
	syncedCounts,
}) => (
	<ClayList className="mb-0">
		{entities.map(({entity}) => {
			const {icon, label} = getEntityDisplay(entity);
			const count = syncedCounts[entity];
			const configured =
				connectorStatus !== ConnectorStatus.Disconnected &&
				typeof count === 'number' &&
				count > 0;

			return (
				<ClayList.Item flex key={entity}>
					<ClayList.ItemField>
						<ClaySticker displayType="unstyled">
							<ClayIcon
								className="text-secondary"
								symbol={icon}
							/>
						</ClaySticker>
					</ClayList.ItemField>

					<ClayList.ItemField expand>
						<ClayList.ItemTitle>{label}</ClayList.ItemTitle>

						{typeof count === 'number' && count >= 0 && (
							<ClayList.ItemText>
								{sub(Liferay.Language.get('x-items-synced'), [
									count,
								])}
							</ClayList.ItemText>
						)}
					</ClayList.ItemField>

					<ClayList.ItemField className="justify-content-center">
						<Label
							displayType={configured ? 'success' : 'secondary'}
						>
							{Liferay.Language.get(
								configured ? 'configured' : 'unconfigured'
							)}
						</Label>
					</ClayList.ItemField>
				</ClayList.Item>
			);
		})}
	</ClayList>
);

export default ConnectorEntities;
