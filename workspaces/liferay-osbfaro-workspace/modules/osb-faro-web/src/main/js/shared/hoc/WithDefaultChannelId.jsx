import withAction from './WithAction';
import {fetchDefaultChannelId} from '../actions/preferences';
import {PreferencesScopes} from 'shared/util/constants';
import {RemoteData} from '../util/records';

export default withAction(
	({groupId}) => fetchDefaultChannelId(groupId),

	state =>
		state.getIn(
			['preferences', PreferencesScopes.User, 'defaultChannelId'],
			new RemoteData()
		),
	{propName: 'defaultChannelId'}
);
