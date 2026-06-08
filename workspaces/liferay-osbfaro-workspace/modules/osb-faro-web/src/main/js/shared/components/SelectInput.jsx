/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import {PropTypes} from 'prop-types';
import React from 'react';
import omitDefinedProps from '~/shared/util/omitDefinedProps';

import BaseSelect from './BaseSelect';

class SelectInput extends React.Component {
	static defaultProps = {
		placeholder: '',
	};

	static propTypes = {
		placeholder: PropTypes.string,
		selectedItem: PropTypes.any,
	};

	state = {
		query: '',
	};

	_baseSelectRef = React.createRef();

	focus() {
		this._baseSelectRef.current.focus();
	}

	@autobind
	handleInputValueChange(query) {
		this.setState({
			query,
		});
	}

	render() {
		const {
			props: {placeholder, selectedItem, ...otherProps},
			state: {query},
		} = this;

		return (
			<BaseSelect
				{...omitDefinedProps(otherProps, SelectInput.propTypes)}
				emptyInputOnInactive
				inputValue={query}
				onInputValueChange={this.handleInputValueChange}
				placeholder={selectedItem ? '' : placeholder}
				ref={this._baseSelectRef}
				selectedItem={selectedItem}
			/>
		);
	}
}

export default SelectInput;
