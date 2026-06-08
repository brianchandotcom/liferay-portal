/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import ClayPanel from '@clayui/panel';
import getCN from 'classnames';
import React from 'react';

interface ICardProps extends React.HTMLAttributes<HTMLElement> {
	innerPadding?: boolean;
	title: string;
}

const Card: React.FC<ICardProps> & {
	SubHeader: typeof SubHeader;
} = ({children, innerPadding = true, title}) => (
	<ClayPanel className="mb-4" displayType="secondary">
		<ClayPanel.Header>
			<h2 className="mb-0 p-2 text-6 text-dark">{title}</h2>
		</ClayPanel.Header>

		<ClayPanel.Body
			className={getCN({
				'p-0': !innerPadding,
				'p-4': innerPadding,
			})}
		>
			{children}
		</ClayPanel.Body>
	</ClayPanel>
);

interface ISubHeaderProps {
	title: string;
}

export const SubHeader = function SubHeader({title}: ISubHeaderProps) {
	return (
		<div className="mb-4">
			<Text color="secondary" size={3} weight="semi-bold">
				{title.toUpperCase()}
			</Text>
			<hr className="my-2" />
		</div>
	);
};

Card.SubHeader = SubHeader;

export {Card};
