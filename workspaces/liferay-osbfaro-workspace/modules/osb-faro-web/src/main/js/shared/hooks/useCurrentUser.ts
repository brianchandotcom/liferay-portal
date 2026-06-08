/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {fetchCurrentUser} from '~/shared/actions/users';
import {User} from '~/shared/util/records';

/**
 * Get currentUser from redux store.
 */
export const useCurrentUser = function useCurrentUser(): User {
	const currentUserId = useSelector<any, any>((state) =>
		state.getIn(['currentUser', 'data'])
	);
	const data: User = useSelector<any, any>((state) =>
		state.getIn(['users', currentUserId, 'data'])
	);

	const newUser = new User({
		emailAddress: '',
		id: '',
		name: '',
		roleName: '',
		status: 1,
	});

	return data || newUser;
};

/**
 * Used only on the first time on App.tsx to fetch data from backend.
 * To get currentUser, you can use WithCurrentUser (for HOC) or useCurrentUser (for HOOK).
 */
export const useFetchCurrentUser = function useFetchCurrentUser(
	initialGroupId: string = '0'
) {
	const currentUser = useSelector<any, any>((state) =>
		state.get('currentUser')
	);
	const error = currentUser.get('error');
	const loading = currentUser.get('loading');

	const data = useCurrentUser();

	const dispatch = useDispatch();

	useEffect(() => {
		let groupId = '0';

		if (initialGroupId && initialGroupId !== 'add') {
			groupId = initialGroupId;
		}

		dispatch(fetchCurrentUser(groupId));

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [initialGroupId]);

	return {
		data,
		error,
		loading,
	};
};
