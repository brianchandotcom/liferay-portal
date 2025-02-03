/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';

import './BESearch.css';

import ClayIcon from '@clayui/icon';
import i18n from '~/utils/I18n';

const BESearch = () => {
	return (
		<div className="flex-grow-1 mr-3 position-relative">
			<ClayInput
				className="be-search-bar border border-brand-primary-lighten-5 font-weight-semi-bold shadow-lg"
				placeholder={i18n.translate('search-event-name')}
				type="text"
			/>

			<ClayIcon
				className="be-search-icon position-absolute text-brand-primary"
				symbol="search"
			/>
		</div>
	);
};

export default BESearch;
