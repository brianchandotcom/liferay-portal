/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayCard from '@clayui/card';
import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import {memo} from 'react';
import i18n from '../../../../../../../../../../../common/I18n';
import getKebabCase from '../../../../../../../../../../../common/utils/getKebabCase';
import getStyleFromTitle from './utils/getStyleFromTitle';

const SLACard = ({active, endDate, label, last, startDate, title, unique}) => {
	const displayDate = `${startDate} - ${endDate}`;
	const currentStyle = getStyleFromTitle(title);

	return (
		<div
			className={classNames(
				'align-items-center d-flex',
				!unique && {
					active,
					'cp-sla-card mt-3': true,
					last,
				}
			)}
		>
			<ClayCard
				className={classNames(
					'm-0 p-3 rounded-lg border',
					currentStyle.cardStyle
				)}
			>
				<ClayCard.Row className="align-items-center d-flex justify-content-between">
					<h5 className={classNames('mb-0', currentStyle.titleStyle)}>
						{i18n.translate(getKebabCase(title))}
					</h5>

					<div>
						<ClayCard.Caption>
							<ClayLabel
								className={classNames(
									'mr-0 p-0 text-small-caps',
									currentStyle.labelStyle
								)}
								displayType="secundary"
							>
								{i18n
									.translate(getKebabCase(label))
									.toUpperCase()}
							</ClayLabel>
						</ClayCard.Caption>
					</div>
				</ClayCard.Row>

				<ClayCard.Description
					className={classNames(currentStyle.dateStyle)}
					displayType="text"
					truncate={false}
				>
					{displayDate}
				</ClayCard.Description>
			</ClayCard>
		</div>
	);
};

export default memo(SLACard);
