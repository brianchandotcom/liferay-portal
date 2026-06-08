/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import getCN from 'classnames';
import {PropTypes} from 'prop-types';
import React from 'react';
import ReactDOM from 'react-dom';
import {Link} from 'react-router-dom';
import Popover from '~/shared/components/Popover';
import {isEllipisActive} from '~/shared/util/util';

/**
 * Table Data
 * @class
 */
class TableData extends React.Component {
	static defaultProps = {
		emptyMessage: '',
		firstColumn: true,
		url: '',
	};

	static propTypes = {
		emptyMessage: PropTypes.string,
		firstColumn: PropTypes.bool,
		title: PropTypes.string,
		url: PropTypes.string,
	};

	state = {
		showPopover: false,
	};

	constructor(props) {
		super(props);

		this._titleRef = React.createRef();
	}

	/**
	 * Handle Mouse Over
	 * @param {object} event
	 */
	@autobind
	handleMouseOver(event) {
		this.setState({
			showPopover: isEllipisActive(event),
		});
	}

	/**
	 * Handle Mouse Out
	 */
	@autobind
	handleMouseOut() {
		this.setState({
			showPopover: false,
		});
	}

	renderEmptyMessage() {
		const {emptyMessage} = this.props;

		return <span className="mb-1 text-secondary">{emptyMessage}</span>;
	}

	renderTitle(title) {
		return (
			<span
				className="mb-1 text-truncate"
				onBlur={this.handleMouseOut}
				onFocus={this.handleMouseOver}
				onMouseOut={this.handleMouseOut}
				onMouseOver={this.handleMouseOver}
				ref={this._titleRef}
			>
				{title}
			</span>
		);
	}

	renderUrl({title, url}) {
		return (
			<Link
				onBlur={this.handleMouseOut}
				onFocus={this.handleMouseOver}
				onMouseOut={this.handleMouseOut}
				onMouseOver={this.handleMouseOver}
				to={url}
			>
				{this.renderTitle(title)}
			</Link>
		);
	}

	/**
	 * Lifecycle Render - ReactJS
	 */
	render() {
		const {showPopover = true} = this.state;
		const {className, emptyMessage, firstColumn, title, url} = this.props;

		const classes = getCN(
			'table-cell-expand',
			[{'table-first-column': firstColumn}],
			className
		);

		return (
			<td className={classes}>
				{url
					? this.renderUrl({title, url})
					: title
						? this.renderTitle(title)
						: emptyMessage
							? this.renderEmptyMessage()
							: null}

				{ReactDOM.createPortal(
					<Popover
						alignElement={this._titleRef.current}
						title={title}
						visible={showPopover}
					/>,
					document.querySelector('body.dxp')
				)}
			</td>
		);
	}
}

export default TableData;
