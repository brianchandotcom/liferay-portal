import {ITimeZone} from 'shared/util/records/TimeZone';
import {useParams} from 'react-router-dom';
import {useSelector} from 'react-redux';

export const useTimeZone = (initialGroupId?: string): ITimeZone => {
	const {groupId} = useParams();

	const value = useSelector<any, any>(state =>
		state.getIn(['projects', initialGroupId || groupId, 'data', 'timeZone'])
	);

	return value.toObject();
};
