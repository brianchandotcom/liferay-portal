/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import TestrayIcons from '~/components/Icons/TestrayIcon';

type HeaderBreadcrumbTriggerProps = {
	displayCarret?: boolean;
	symbol: string;
	title: string;
};

const HeaderBreadcrumbTrigger: React.FC<HeaderBreadcrumbTriggerProps> = ({
	displayCarret,
	symbol,
	title,
}) => (
	<div className="align-items-center d-flex" title={title}>
		<TestrayIcons
			className="dropdown-poll-icon mr-2"
			fill="darkblue"
			size={35}
			symbol={symbol || 'polls'}
		/>

		{displayCarret && (
			<ClayIcon
				className={classNames('dropdown-arrow-icon')}
				color="darkblue"
				symbol="caret-bottom"
			/>
		)}
	</div>
);

export default HeaderBreadcrumbTrigger;
