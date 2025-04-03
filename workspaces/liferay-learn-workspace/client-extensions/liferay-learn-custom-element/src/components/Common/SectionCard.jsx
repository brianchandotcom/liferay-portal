/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {getPersonasLabel, getPersonasTooltip, truncateText} from '../../utils/util';
import React from 'react';
import './SectionCard.scss';

const SectionCard = ({
	description,
	expertise,
	index,
	link,
	personas,
	title,
}) => {
	return (
		<ClayLayout.Col className="course-layout-col d-flex" key={index} lg={4} md={6} sm={12} xl={4} xs={12}>
			<a className="section-card-link" href={link} >
				<div className="d-flex section-card-container">
					<div className="card-content d-flex flex-column justify-content-between">
						<h4 className="title">{title}</h4>

						<div className="description">
							{truncateText(description, 150)}
						</div>

						<div className="card-tags-container d-flex learn-education">
							<ClayTooltipProvider>
								<div
									className="card-tag card-tag__persona"
									data-tool-tip-align="top"
									title={getPersonasTooltip(personas)}
								>
									<p></p>
									{getPersonasLabel(personas)}
								</div>
							</ClayTooltipProvider>
							<div
								className={`card-tag card-tag__expertise-${expertise.toLowerCase()}`}
							>
								<p>{expertise}</p>
							</div>
						</div>
					</div>
				</div>
			</a>
		</ClayLayout.Col>
	);
};

export default SectionCard;
