/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {RemoteData} from './records';

/**
 * Creates a reducer that updates the error and loading states of the
 * {@link RemoteData} that the id in the action points to.
 * @param {Object} options - The options for the next state.
 * @param {boolean} options.error - If the entity has an error.
 * @param {boolean} options.loading - If the entity is still loading.
 * @returns {Function}
 */
export const setState = function setState({error, loading}) {
	return (state, action) =>
		state.update(action.payload.id, (item = new RemoteData()) =>
			item.merge({error, loading})
		);
};

/**
 * A reducer that handles the "loading" case.
 * @param {Object} state - The current state.
 * @param {Object} action - The action.
 * @returns {Object} - The next state.
 */
export const handleLoading = setState({error: false, loading: true});

/**
 * A reducer that handles the "error" case.
 * @param {Object} state - The current state.
 * @param {Object} action - The action.
 * @returns {Object} - The next state.
 */
export const handleError = setState({error: true, loading: false});
