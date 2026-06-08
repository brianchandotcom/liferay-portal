/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React from 'react';
import MetricBar from '~/shared/components/MetricBar';
import {formatUTCDateFromUnix} from '~/shared/util/date';
import {sub} from '~/shared/util/lang';
import {getFinitePercent} from '~/shared/util/numbers';

export function getStatusMessage({configured, current, dateRecorded, total}) {
	if (configured) {
		if (current < total) {
			return sub(Liferay.Language.get('syncing-x-percent-completed'), [
				getFinitePercent(current, total, 2),
			]);
		}
		else if (dateRecorded) {
			return `${sub(Liferay.Language.get('last-sync-x'), [
				formatUTCDateFromUnix(dateRecorded, 'l - LT'),
			])} GMT`;
		}
	}

	return '';
}

export default class BaseConfigurationItem extends React.Component {
	static defaultProps = {
		buttonParams: {},
		completion: 0,
		description: '',
		showBar: false,
		title: '',
	};

	static propTypes = {
		buttonParams: PropTypes.object,
		completion: PropTypes.number,
		description: PropTypes.string,
		showBar: PropTypes.bool,
		statusMessage: PropTypes.string,
		title: PropTypes.string,
	};

	render() {
		const {
			buttonParams: {
				disabled,
				href,
				label = Liferay.Language.get('configure'),
				...otherButtonParams
			},
			completion,
			description,
			showBar,
			statusMessage,
			title,
		} = this.props;

		return (
			<div
				className={`base-configuration-item-root${
					this.props.className ? ` ${this.props.className}` : ''
				}`}
			>
				<div className="actions">
					<ClayLink
						{...otherButtonParams}
						{...(!disabled && {href})}
						block
						button
						className={getCN('button-root', {
							'link-disabled': disabled,
						})}
						displayType="secondary"
					>
						{label}
					</ClayLink>

					{showBar && isFinite(completion) && completion < 1 && (
						<MetricBar
							display="primary"
							percent={completion}
							size="xs"
						/>
					)}
				</div>

				<div className="info">
					<div className="title">
						<b>{title}</b>
					</div>

					<div className="text-secondary">
						<div className="description">{description}</div>

						{statusMessage && (
							<div className="status">{statusMessage}</div>
						)}
					</div>
				</div>
			</div>
		);
	}
}
