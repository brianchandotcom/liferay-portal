/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import autobind from 'autobind-decorator';
import {Stack} from 'immutable';
import {noop} from 'lodash';
import {PropTypes} from 'prop-types';
import React from 'react';
import {addContext} from '~/shared/util/clay';
import omitDefinedProps from '~/shared/util/omitDefinedProps';

import Input from './Input';

export const CONTEXT = 'search-input';

const searchInputPropTypes = {
	disabled: PropTypes.bool,
	onChange: PropTypes.func,
	onSubmit: PropTypes.func,
	placeholder: PropTypes.string,
	value: PropTypes.string,
};

export default class SearchInput extends React.Component {
	static childContextTypes = {
		clay: PropTypes.instanceOf(Stack),
	};

	static defaultProps = {
		disabled: false,
		onSubmit: noop,
		placeholder: Liferay.Language.get('search'),
		value: '',
	};

	static propTypes = searchInputPropTypes;

	constructor(props) {
		super(props);

		this._inputRef = React.createRef();
	}

	getChildContext() {
		return addContext(this, CONTEXT);
	}

	@autobind
	handleClearSearch() {
		const {onChange, onSubmit} = this.props;

		if (onChange) {
			onChange('');
		}

		if (onSubmit) {
			onSubmit('');
		}

		this._inputRef.current._elementRef.current.focus();
	}

	@autobind
	handleChange(event) {
		const {onChange} = this.props;

		if (onChange) {
			onChange(event.target.value);
		}
	}

	@autobind
	handleKeyDown(event) {
		if (event.key === 'Enter') {
			event.preventDefault();

			this.props.onSubmit(
				this._inputRef.current._elementRef.current.value
			);
		}
	}

	@autobind
	handleSubmit() {
		this.props.onSubmit(this._inputRef.current._elementRef.current.value);
	}

	render() {
		const {className, disabled, placeholder, value, ...otherProps} =
			this.props;

		return (
			<Input.Group className={className}>
				<Input.GroupItem>
					<Input
						{...omitDefinedProps(otherProps, searchInputPropTypes)}
						disabled={disabled}
						inset="after"
						onChange={this.handleChange}
						onKeyDown={this.handleKeyDown}
						placeholder={placeholder}
						ref={this._inputRef}
						value={value}
					/>

					<Input.Inset position="after">
						<ClayButton
							aria-label={
								value
									? Liferay.Language.get('clear')
									: Liferay.Language.get('search')
							}
							className="button-root"
							disabled={disabled}
							displayType="unstyled"
							onClick={
								value
									? this.handleClearSearch
									: this.handleSubmit
							}
						>
							{value ? (
								<ClayIcon
									className="icon-root"
									symbol="times"
								/>
							) : (
								<ClayIcon
									className="icon-root"
									symbol="search"
								/>
							)}
						</ClayButton>
					</Input.Inset>
				</Input.GroupItem>
			</Input.Group>
		);
	}
}
