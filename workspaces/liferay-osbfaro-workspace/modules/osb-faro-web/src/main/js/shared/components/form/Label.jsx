/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React from 'react';
import InfoPopover from '~/shared/components/InfoPopover';
import omitDefinedProps from '~/shared/util/omitDefinedProps';

const labelPropTypes = {
	className: PropTypes.string,
	popover: PropTypes.object,
	required: PropTypes.bool,
};

export default class Label extends React.Component {
	static defaultProps = {
		required: false,
	};

	static propTypes = labelPropTypes;

	render() {
		const {children, className, popover, required, ...otherProps} =
			this.props;

		return (
			<label
				{...omitDefinedProps(otherProps, labelPropTypes)}
				className={getCN(
					'form-control-label',
					'label-root',
					className,
					{
						required,
					}
				)}
			>
				<span className="content-container">{children}</span>
				{popover && (
					<InfoPopover
						content={popover.content}
						title={popover.title}
					/>
				)}
			</label>
		);
	}
}
