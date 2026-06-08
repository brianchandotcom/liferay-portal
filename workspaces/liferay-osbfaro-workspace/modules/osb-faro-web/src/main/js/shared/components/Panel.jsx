/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import autobind from 'autobind-decorator';
import {PropTypes} from 'prop-types';
import React from 'react';
import {onEnter} from '~/shared/util/key-constants';

class Panel extends React.Component {
	static defaultProps = {
		expandable: false,
		initialExpanded: false,
	};

	static propTypes = {
		content: PropTypes.node,
		expandable: PropTypes.bool,
		initialExpanded: PropTypes.bool,
		label: PropTypes.oneOfType([PropTypes.func, PropTypes.object]),
		title: PropTypes.node,
	};

	state = {
		expanded: false,
	};

	constructor(props) {
		super(props);

		this.state = {
			...this.state,
			expanded: this.props.initialExpanded,
		};
	}

	@autobind
	handleClick() {
		this.toggleExpand();
	}

	@autobind
	@onEnter
	handleKeyPress() {
		this.toggleExpand();
	}

	toggleExpand() {
		this.setState({
			expanded: !this.state.expanded,
		});
	}

	render() {
		const {
			props: {children, expandable, label, title},
			state: {expanded},
		} = this;

		return (
			<div
				className={`panel-root${
					this.props.className ? ` ${this.props.className}` : ''
				}`}
			>
				{title && (
					<div
						className="panel-header"
						onClick={this.handleClick}
						onKeyPress={this.handleKeyPress}
						role="button"
						tabIndex="0"
					>
						{expandable && (
							<div className="trigger">
								<ClayIcon
									className="icon-root"
									symbol={
										expanded ? 'angle-up' : 'angle-down'
									}
								/>
							</div>
						)}

						<div className="panel-title">{title}</div>

						{label && (
							<div className="panel-title-label">{label}</div>
						)}
					</div>
				)}

				{(expanded || !expandable) && children && (
					<div className="panel-body">{children}</div>
				)}
			</div>
		);
	}
}

export default Panel;
