import {fetchDefaultChannelId} from 'shared/actions/preferences';
import {PreferencesScopes} from 'shared/util/constants';
import {RemoteData} from 'shared/util/records';
import {RootState} from 'shared/store';
import {useDispatch, useSelector} from 'react-redux';
import {useEffect} from 'react';

export const useDefaultChannelId = ({groupId}: {groupId: string}) => {
	const dispatch = useDispatch();

	useEffect(() => {
		Promise.resolve(dispatch(fetchDefaultChannelId(groupId) as any)).catch(
			() => {}
		);
	}, [dispatch, groupId]);

	const remoteData = useSelector(
		(state: RootState) =>
			state.getIn([
				'preferences',
				PreferencesScopes.User,
				'defaultChannelId',
			]) || new RemoteData()
	);

	const {data, error, loading} = remoteData.toObject();

	return {
		defaultChannelId: data,
		error: !!error,
		loading: !data && loading,
	};
};
