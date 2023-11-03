/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import solutionsIcon from '../../../../assets/icons/bookmarks_icon.svg';
import {DashboardEmptyTable} from '../../../../components/DashboardTable/DashboardEmptyTable';
import i18n from '../../../../i18n';

const Licenses = () => {
	return (
		<DashboardEmptyTable
			button
			buttonName={i18n.translate('create-license-key')}
			description1={i18n.translate(
				'create-new-licenses-and-they-will-show-up-here'
			)}
			icon={solutionsIcon}
			title={i18n.translate('no-licenses-yet')}
		/>
	);
};

export default Licenses;
