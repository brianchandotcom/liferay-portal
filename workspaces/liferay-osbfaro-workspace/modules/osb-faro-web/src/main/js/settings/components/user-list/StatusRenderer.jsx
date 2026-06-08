/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PropTypes} from 'prop-types';
import React from 'react';
import Label from '~/shared/components/Label';
import {UserStatuses} from '~/shared/util/constants';

const STATUS_LANG_MAP = {
	[UserStatuses.Approved]: Liferay.Language.get('approved'),
	[UserStatuses.Pending]: Liferay.Language.get('pending'),
};

class StatusRenderer extends React.Component {
	static defaultProps = {
		data: {},
	};

	static propTypes = {
		data: PropTypes.object,
	};

	getDisplayType(status) {
		switch (status) {
			case UserStatuses.Approved:
				return 'success';
			case UserStatuses.Pending:
				return 'warning';
			default:
				return '';
		}
	}

	render() {
		const {
			className,
			data: {status},
		} = this.props;

		const displayType = this.getDisplayType(status);

		return (
			<td className={className}>
				<Label display={displayType}>{STATUS_LANG_MAP[status]}</Label>
			</td>
		);
	}
}

export default StatusRenderer;
