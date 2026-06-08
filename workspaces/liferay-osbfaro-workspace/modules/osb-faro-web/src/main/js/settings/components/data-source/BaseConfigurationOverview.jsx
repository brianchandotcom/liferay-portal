/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {get} from 'lodash';
import {PropTypes} from 'prop-types';
import React from 'react';
import BaseConfigurationItem, {
	getStatusMessage,
} from '~/settings/components/data-source/BaseConfigurationItem';
import Sheet from '~/shared/components/Sheet';
import {
	DataSourceProgressStatuses,
	DataSourceStatuses,
} from '~/shared/util/constants';
import omitDefinedProps from '~/shared/util/omitDefinedProps';

const ConfigurationItem = ({
	buttonParams,
	configuration,
	description,
	disabled = false,
	href,
	progress,
	status,
	title,
}) => {
	const current = get(progress, 'processedOperations', 0);
	const total = get(progress, 'totalOperations', 0);

	const configured = Boolean(
		configuration && status === DataSourceStatuses.Active
	);

	const inProgress = [
		DataSourceProgressStatuses.InProgress,
		DataSourceProgressStatuses.Started,
	].includes(get(progress, 'status'));

	return (
		<BaseConfigurationItem
			buttonParams={{
				disabled,
				href,
				...buttonParams,
			}}
			completion={current / total}
			description={description}
			showBar={inProgress}
			statusMessage={getStatusMessage({
				configured,
				current,
				dateRecorded: get(progress, 'dateRecorded'),
				total,
			})}
			title={title}
		/>
	);
};

const baseConfigurationOverviewPropTypes = {
	configurationItems: PropTypes.array,
};

export default class BaseConfigurationOverview extends React.Component {
	static defaultProps = {
		configurationItems: [],
	};

	static propTypes = baseConfigurationOverviewPropTypes;

	render() {
		const {className, configurationItems, ...otherProps} = this.props;

		return (
			<div className={getCN('configuration-overview-root', className)}>
				<Sheet.Subtitle>
					{Liferay.Language.get('data-configuration')}
				</Sheet.Subtitle>
				{configurationItems.map((item, i) => (
					<ConfigurationItem
						{...omitDefinedProps(
							otherProps,
							baseConfigurationOverviewPropTypes
						)}
						{...item}
						key={i}
					/>
				))}
			</div>
		);
	}
}
