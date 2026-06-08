/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React from 'react';
import Card from '~/shared/components/Card';

interface IGenericBarsCard {
	cardTitle: string;
}

export const GenericBarsCard = function GenericBarsCard({
	cardTitle,
}: IGenericBarsCard) {
	return (
		<ClayLayout.Col key={cardTitle} xl={4}>
			<Card className="mt-4">
				<Card.Header>
					<Card.Title>
						<h3>{cardTitle}</h3>
					</Card.Title>
				</Card.Header>
				<Card.Body>
					<div className="chart-bars">
						<div className="small-bar"></div>
						<div className="big-bar"></div>
					</div>
				</Card.Body>
			</Card>
		</ClayLayout.Col>
	);
};
