import {Map} from 'immutable';
import {useSelector} from 'react-redux';

type RootState = Map<string, any>;

export const useSubscriptionName = ({
	groupId
}: {
	groupId: string;
}): string | null =>
	useSelector((state: RootState) =>
		state.getIn(
			['projects', groupId, 'data', 'faroSubscription', 'name'],
			null
		)
	);
