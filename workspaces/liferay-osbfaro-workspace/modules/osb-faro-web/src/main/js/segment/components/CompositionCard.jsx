/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PropTypes} from 'prop-types';
import React from 'react';
import Card from '~/shared/components/Card';
import CompositionChart from '~/shared/components/CompositionChart';
import {ReportContainer} from '~/shared/components/download-report/DownloadPDFReport';

export default class CompositionCard extends React.Component {
	static propTypes = {
		activeIndividualCount: PropTypes.number,
		individualCount: PropTypes.number,
		knownIndividualCount: PropTypes.number,
	};

	render() {
		const {activeIndividualCount, individualCount, knownIndividualCount} =
			this.props;

		return (
			<Card
				className="composition-card-root"
				reportContainer={ReportContainer.SegmentCompositionCard}
			>
				<Card.Header>
					<Card.Title>
						{Liferay.Language.get('segment-composition')}
					</Card.Title>
				</Card.Header>

				<Card.Body>
					<CompositionChart
						innerData={{
							label: Liferay.Language.get('known-members'),
							value: knownIndividualCount,
						}}
						outerData={{
							label: Liferay.Language.get('active-last-30-days'),
							value: activeIndividualCount,
						}}
						total={individualCount}
					/>
				</Card.Body>
			</Card>
		);
	}
}
