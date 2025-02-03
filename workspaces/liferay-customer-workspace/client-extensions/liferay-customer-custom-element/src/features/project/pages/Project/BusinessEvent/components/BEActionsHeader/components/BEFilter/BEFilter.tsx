/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button as ClayButton} from '@clayui/core';
import ClayIcon from '@clayui/icon';

import './BEFilter.css';

import i18n from '~/utils/I18n';

const BEFilter = () => {
	return (
		<div>
			<ClayButton borderless className="text-neutral-10">
				<span className="inline-item inline-item-before">
					<ClayIcon symbol="filter" />
				</span>

				{i18n.translate('filters')}
			</ClayButton>
		</div>
	);
};

export default BEFilter;
