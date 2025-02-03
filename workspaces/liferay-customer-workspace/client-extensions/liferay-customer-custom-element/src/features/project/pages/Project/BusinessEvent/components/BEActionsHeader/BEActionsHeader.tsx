/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';

import './BEActionsHeader.css';

import i18n from '~/utils/I18n';

import BEFilter from './components/BEFilter';
import BESearch from './components/BESearch';

interface IPropsHeader {
	hasAllEventsPermissions: Boolean;
}

const BEActionsHeader = ({hasAllEventsPermissions}: IPropsHeader) => {
	return (
		<div className="d-flex flex-column mt-4">
			<div className="be-table-header d-flex justify-content-between p-3">
				<div className="d-flex">
					<BESearch />

					<BEFilter />
				</div>

				{hasAllEventsPermissions && (
					<Button className="be-create-event">
						{i18n.translate('create-event')}
					</Button>
				)}
			</div>
		</div>
	);
};

export default BEActionsHeader;
