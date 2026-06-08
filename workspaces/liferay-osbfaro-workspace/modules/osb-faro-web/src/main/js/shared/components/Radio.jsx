/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React from 'react';
import omitDefinedProps from '~/shared/util/omitDefinedProps';

export default class Radio extends React.Component {
	static defaultProps = {
		displayInline: false,
	};

	static propTypes = {
		checked: PropTypes.bool,
		displayInline: PropTypes.bool,
		label: PropTypes.node,
	};

	render() {
		const {checked, className, displayInline, label, ...otherProps} =
			this.props;

		const classes = getCN('custom-control', 'custom-radio', className, {
			['custom-control-inline']: displayInline,
		});

		return (
			<div className={classes}>
				<label>
					<input
						{...omitDefinedProps(otherProps, Radio.propTypes)}
						checked={checked}
						className="custom-control-input"
						type="radio"
					/>

					<span className="custom-control-label">
						{label && (
							<span className="custom-control-label-text">
								{label}
							</span>
						)}
					</span>
				</label>
			</div>
		);
	}
}
