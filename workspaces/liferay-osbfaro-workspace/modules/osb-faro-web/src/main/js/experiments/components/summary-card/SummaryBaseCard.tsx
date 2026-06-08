/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import React from 'react';
import Card from '~/shared/components/Card';

import {SummaryTitle} from './SummaryTitle';

interface SummaryBaseCardIProps extends React.HTMLAttributes<HTMLElement> {
	status?: string;
}

const Body: React.FC<React.HTMLAttributes<HTMLElement>> = ({children}) => (
	<Card.Body>{children}</Card.Body>
);

const Footer: React.FC<React.HTMLAttributes<HTMLElement>> = ({children}) => (
	<Card.Footer>{children}</Card.Footer>
);

const Header = ({
	Description,
	title,
}: {
	Description?: React.ComponentType;
	title: React.ReactNode;
}) => (
	<Card.Header>
		<SummaryTitle className="mb-2" label={title} />

		{Description && (
			<span className="font-size-sm font-weight-normal">
				<Description />
			</span>
		)}
	</Card.Header>
);

export const SummaryBaseCard: React.FC<SummaryBaseCardIProps> & {
	Body: typeof Body;
	Footer: typeof Footer;
	Header: typeof Header;
} = function SummaryBaseCard({children, status}) {
	return (
		<Card
			className={getCN(
				'analytics-summary-card analytics-summary-card-status',
				{
					[` analytics-summary-card-status-${status}`]: status,
				}
			)}
		>
			{children}
		</Card>
	);
};

SummaryBaseCard.Body = Body;
SummaryBaseCard.Footer = Footer;
SummaryBaseCard.Header = Header;
