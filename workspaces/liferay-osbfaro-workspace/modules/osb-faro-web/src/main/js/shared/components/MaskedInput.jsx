/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import {isFunction} from 'lodash';
import {PropTypes} from 'prop-types';
import React from 'react';
import {createTextMaskInputElement} from 'text-mask-core';
import Input from '~/shared/components/Input';
import omitDefinedProps from '~/shared/util/omitDefinedProps';

const maskedInputPropTypes = {
	guide: PropTypes.bool,
	keepCharPositions: PropTypes.bool,
	mask: PropTypes.oneOfType([
		PropTypes.array,
		PropTypes.func,
		PropTypes.bool,
		PropTypes.shape({
			mask: PropTypes.oneOfType([PropTypes.array, PropTypes.func]),
			pipe: PropTypes.func,
		}),
	]).isRequired,
	onChange: PropTypes.func,
	pipe: PropTypes.func,
	placeholderChar: PropTypes.string,
	showMask: PropTypes.bool,
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};

class MaskedInput extends React.Component {
	static defaultProps = {
		guide: true,
		keepCharPositions: false,
		placeholderChar: '_',
		showMask: false,
	};

	static propTypes = maskedInputPropTypes;

	constructor(props) {
		super(props);

		this._inputRef = React.createRef();
	}

	componentDidMount() {
		this.textMaskInputElement_ = createTextMaskInputElement({
			inputElement: this._inputRef.current._elementRef.current,
			...this.props,
		});
	}

	componentDidUpdate(firstRender) {
		const {value} = this.props;

		if (!firstRender && this.textMaskInputElement_ && value) {
			this.textMaskInputElement_.update(value);
		}
	}

	@autobind
	handleChange(event) {
		this.textMaskInputElement_.update(event.target.value);

		if (isFunction(this.props.onChange)) {
			this.props.onChange(event);
		}
	}

	render() {
		const {className, value, ...otherProps} = this.props;

		return (
			<Input
				{...omitDefinedProps(otherProps, maskedInputPropTypes)}
				className={className}
				onChange={this.handleChange}
				ref={this._inputRef}
				value={value}
			/>
		);
	}
}

export default MaskedInput;
