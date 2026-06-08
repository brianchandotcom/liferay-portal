/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React from 'react';
import Avatar from '~/shared/components/Avatar';
import Card from '~/shared/components/Card';
import {formatDateToTimeZone} from '~/shared/util/date';
import {sub} from '~/shared/util/lang';
import {Individual} from '~/shared/util/records';
import {Routes, toRoute} from '~/shared/util/router';

export default class DetailsCard extends React.PureComponent {
	static defaultProps = {
		entity: new Individual(),
	};

	static propTypes = {
		channelId: PropTypes.string,
		entity: PropTypes.instanceOf(Individual),
		groupId: PropTypes.string.isRequired,
		timeZoneId: PropTypes.string.isRequired,
	};

	render() {
		const {channelId, className, entity, groupId, timeZoneId} = this.props;

		const individual = entity.toJS();

		const {
			firstActivityDate,
			id,
			name,
			properties: {email, jobTitle},
		} = individual;

		return (
			<Card className={getCN('individual-details-card-root', className)}>
				<Card.Body>
					<Avatar circle entity={individual} size="xl" />

					{name && <div className="h4">{name}</div>}

					{jobTitle && <div className="job-title">{jobTitle}</div>}

					{email && <div className="email">{email}</div>}

					{firstActivityDate && (
						<div className="first-seen">
							{sub(Liferay.Language.get('first-seen-x'), [
								formatDateToTimeZone(
									firstActivityDate,
									'LL',
									timeZoneId
								),
							])}
						</div>
					)}
				</Card.Body>

				<Card.Footer>
					<ClayLink
						borderless
						button
						className="button-root"
						displayType="secondary"
						href={toRoute(Routes.CONTACTS_INDIVIDUAL_DETAILS, {
							channelId,
							groupId,
							id,
						})}
						small
					>
						{Liferay.Language.get('view-all-details')}

						<ClayIcon
							className="icon-root ml-2"
							symbol="angle-right-small"
						/>
					</ClayLink>
				</Card.Footer>
			</Card>
		);
	}
}
