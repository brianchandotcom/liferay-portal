/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import CardTabs, {CardTabSizes} from '~/shared/components/CardTabs';

interface IHeaderProps {
	activeTabId: string;
	tabs: {
		onClick: () => void;
		tabId: string;
		title: string;
	}[];
	title: string;
}

const Header: React.FC<IHeaderProps> = ({activeTabId, tabs, title}) => (
	<div className="event-analysis-dropdown-header">
		<div className="event-analysis-dropdown-header-title">{title}</div>

		<CardTabs
			activeTabId={activeTabId}
			className="event-type-selector"
			size={CardTabSizes.Small}
			tabs={tabs}
		/>
	</div>
);

export default Header;
