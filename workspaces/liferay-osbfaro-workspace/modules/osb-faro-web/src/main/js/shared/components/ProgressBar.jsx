/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React from 'react';

class ProgressBar extends React.Component {
	static defaultProps = {
		complete: false,
		error: false,
		value: 0,
	};

	static propTypes = {
		complete: PropTypes.bool,
		error: PropTypes.bool,
		value: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
	};

	renderProgressInfo() {
		const {complete, error, value} = this.props;

		if (complete && !error) {
			return (
				<div className="progress-group-feedback">
					<ClayIcon className="icon-root" symbol="check-circle" />
				</div>
			);
		}
		else if (error) {
			return (
				<div className="progress-group-feedback">
					<ClayIcon className="icon-root" symbol="times-circle" />
				</div>
			);
		}
		else {
			return (
				((complete && error) || (!complete && !error)) &&
				`${Math.ceil(value)}%`
			);
		}
	}

	render() {
		const {className, complete, error, value} = this.props;

		const classes = getCN('progress-bar-root', 'progress-group', {
			'progress-danger': error,
			'progress-success': complete,
		});

		const width = complete ? '100%' : `${value}%`;

		return (
			<div className={getCN(classes, className)}>
				<div className="progress">
					<div
						className="progress-bar"
						role="progressbar"
						style={{width}}
					/>
				</div>

				<div className="progress-group-addon">
					{this.renderProgressInfo()}
				</div>
			</div>
		);
	}
}

export default ProgressBar;
