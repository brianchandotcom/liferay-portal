/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ReactNode} from 'react';

import {AppProps} from '../../components/DashboardTable/DashboardTable';
import {Header} from '../../components/Header/Header';

import './DashboardPage.scss';

export interface DashboardListItems {
	itemIcon: string;
	itemName: string;
	itemSelected: boolean;
	itemTitle: string;
	items?: AppProps[];
}

interface DashBoardPageProps {
	buttonMessage?: string | ReactNode;
	children: ReactNode;
	messages: {
		description: string;
		title: string;
	};
	onButtonClick?: () => void;
}

export function DashboardPage({
	buttonMessage,
	children,
	messages,
	onButtonClick,
}: DashBoardPageProps) {
	return (
		<div className="dashboard-page-container">
			<div className="dashboard-page-body-container">
				<div>
					<div className="dashboard-page-body-header-container">
						<Header
							description={messages.description}
							title={messages.title}
						/>

						{buttonMessage && (
							<ClayButton onClick={onButtonClick}>
								{buttonMessage}
							</ClayButton>
						)}
					</div>

					{children}
				</div>
			</div>
		</div>
	);
}
