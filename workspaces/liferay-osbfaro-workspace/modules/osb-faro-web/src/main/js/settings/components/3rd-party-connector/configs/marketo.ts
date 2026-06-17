import {buildLanguages} from '../buildLanguages';
import {ConnectorConfig, Entity} from '../types';
import {DataSourceTypes} from 'shared/util/constants';
import {fetchConnectorEntityCount} from 'shared/api/connector';

const SLUG = 'marketo';

const displayName = Liferay.Language.get('marketo');

const marketoConfig: ConnectorConfig = {
	displayName,
	endpointPath: '/api/marketo_webhooks',
	entities: [
		{
			entity: Entity.Events,
			fetchCount: (params) =>
				fetchConnectorEntityCount(Entity.Events, params),
		},
	],
	languages: buildLanguages(displayName),
	requiresLDP: true,
	singleton: true,
	slug: SLUG,
	type: DataSourceTypes.Marketo,
};

export default marketoConfig;
