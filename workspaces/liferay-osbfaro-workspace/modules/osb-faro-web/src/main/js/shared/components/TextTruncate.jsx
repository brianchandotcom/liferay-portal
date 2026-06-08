/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import getCN from 'classnames';
import {truncate} from 'lodash';
import {PropTypes} from 'prop-types';
import React from 'react';
import debounce from '~/shared/util/debounce-decorator';

export default class TextTruncate extends React.Component {
	static defaultProps = {
		inline: false,
		title: '',
	};

	static propTypes = {
		className: PropTypes.string,
		inline: PropTypes.bool,
		maxCharLength: PropTypes.number,
		title: PropTypes.oneOfType([
			PropTypes.string,
			PropTypes.number,
			PropTypes.array,
		]),
	};

	state = {
		showTooltip: false,
	};

	constructor(props) {
		super(props);

		this._textRef = React.createRef();
	}

	componentDidMount() {
		this.updateTooltipVisibility();

		window.addEventListener('resize', this.updateTooltipVisibility);
	}

	componentWillUnmount() {
		this.updateTooltipVisibility.cancel();

		window.removeEventListener('resize', this.updateTooltipVisibility);
	}

	renderContent(className) {
		const {
			props: {children, maxCharLength, title},
			state: {showTooltip},
		} = this;

		const maybeTruncatedTitle = maxCharLength
			? truncate(title, {length: maxCharLength})
			: title;

		const overMaxCharLength = title.length > maybeTruncatedTitle.length;

		return (
			<span
				className={getCN('text-truncate', className)}
				data-tooltip={showTooltip || overMaxCharLength}
				ref={this._textRef}
				title={showTooltip || overMaxCharLength ? title : ''}
			>
				{children || maybeTruncatedTitle}
			</span>
		);
	}

	@debounce(250)
	@autobind
	updateTooltipVisibility() {
		const {offsetWidth, scrollWidth} = this._textRef.current;

		this.setState({
			showTooltip: scrollWidth > offsetWidth,
		});
	}

	render() {
		const {className, inline} = this.props;

		return inline ? (
			<span className={getCN('text-truncate-inline', className)}>
				{this.renderContent()}
			</span>
		) : (
			this.renderContent(className)
		);
	}
}
