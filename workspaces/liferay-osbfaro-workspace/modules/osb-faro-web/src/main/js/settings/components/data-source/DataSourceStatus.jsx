/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PropTypes} from 'prop-types';
import React from 'react';
import Label from '~/shared/components/Label';
import Panel from '~/shared/components/Panel';

export default class DataSourceStatus extends React.Component {
	static propTypes = {
		display: PropTypes.string.isRequired,
		label: PropTypes.string.isRequired,
		message: PropTypes.oneOfType([PropTypes.array, PropTypes.string])
			.isRequired,
	};

	render() {
		const {display, label, message} = this.props;

		return (
			<Panel
				className={`data-source-status-root${
					this.props.className ? ` ${this.props.className}` : ''
				}`}
				label={
					<Label display={display} size="lg" uppercase>
						{label}
					</Label>
				}
				title={
					<div>{Liferay.Language.get('current-status-colon')}</div>
				}
			>
				{message}
			</Panel>
		);
	}
}
