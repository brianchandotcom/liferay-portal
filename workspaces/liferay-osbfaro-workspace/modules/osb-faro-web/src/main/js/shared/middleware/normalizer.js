/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {normalize} from 'normalizr';

const Normalizer = function Normalizer() {
	return (next) => (action) => {
		const {meta, payload, type} = action;

		if (payload && meta && meta.schema) {
			const {entities, result} = normalize(payload, meta.schema);

			action = {
				meta,
				payload: {
					entities,
					result,
				},
				type,
			};
		}

		return next(action);
	};
};

export default Normalizer;
