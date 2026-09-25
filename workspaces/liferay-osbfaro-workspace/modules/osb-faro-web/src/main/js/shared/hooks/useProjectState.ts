import {fetchProject} from 'shared/actions/projects';
import {RemoteData} from 'shared/util/records';
import {RootState} from 'shared/store';
import {useDispatch, useSelector} from 'react-redux';
import {useEffect} from 'react';

export const useProjectState = ({groupId}: {groupId: string}) => {
	const dispatch = useDispatch();

	useEffect(() => {
		Promise.resolve(dispatch(fetchProject({groupId}) as any)).catch(
			() => {}
		);
	}, [dispatch, groupId]);

	const remoteData = useSelector(
		(state: RootState) =>
			state.getIn(['projects', groupId]) || new RemoteData()
	);

	const {data, error, errorStatus, loading} = remoteData.toObject();

	return {
		error: !!error,
		errorStatus,
		loading: !data && loading,
		project: data,
	};
};
