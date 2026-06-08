/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getVariables, safeResultToProps} from '~/shared/util/mappers';
import {getSafeTouchpoint} from '~/shared/util/util';

const assetTypeLabels = {
	blog: Liferay.Language.get('blogs'),
	custom: Liferay.Language.get('custom'),
	document: Liferay.Language.get('documents-and-media'),
	form: Liferay.Language.get('forms'),
	journal: Liferay.Language.get('web-content'),
};

const mapResultToProps = safeResultToProps(({assets}) => {
	const items = assets.map(
		({assetId, assetTitle, assetType, defaultMetric}) => ({
			assetId,
			assetType,
			interactions: defaultMetric.value,
			title: assetTitle || assetId,
			type: assetTypeLabels[assetType],
		})
	);

	return {items};
});

/**
 * Map Props to Options
 * @param {object} param0 props
 * @param {object} param1 context
 */
const mapPropsToOptions = ({
	filters,
	rangeSelectors,
	router: {params},
	touchpoint,
}) => {
	const {variables} = getVariables({filters, params, rangeSelectors});

	if (touchpoint) {
		return {
			variables: {
				...variables,
				touchpoint: getSafeTouchpoint(touchpoint),
			},
		};
	}

	return getVariables({
		filters,
		params,
		rangeSelectors,
	});
};

export {mapPropsToOptions, mapResultToProps};
