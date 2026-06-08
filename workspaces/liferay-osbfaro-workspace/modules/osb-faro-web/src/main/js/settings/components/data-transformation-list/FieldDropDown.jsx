/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Map} from 'immutable';
import {PropTypes} from 'prop-types';
import React from 'react';
import SearchableSelect from '~/shared/components/SearchableSelect';
import omitDefinedProps from '~/shared/util/omitDefinedProps';

const fieldDropDownPropTypes = {
	dataIMap: PropTypes.instanceOf(Map).isRequired,
	name: PropTypes.string,
	onFieldPreviewModal: PropTypes.func,
	onSearchInput: PropTypes.func,
	onSearchSelect: PropTypes.func,
	placeholder: PropTypes.string,
	readOnly: PropTypes.bool,
	searchInputValue: PropTypes.string,
	searchItems: PropTypes.array,
	title: PropTypes.oneOfType([PropTypes.array, PropTypes.string]),
};

export default class FieldDropDown extends React.Component {
	static propTypes = fieldDropDownPropTypes;

	render() {
		const {
			className,
			dataIMap,
			name,
			onSearchInput,
			onSearchSelect,
			placeholder,
			readOnly,
			searchInputValue,
			searchItems,
			title,
			...otherProps
		} = this.props;

		const data = dataIMap.toJS();

		return (
			<div className={className}>
				{title && <label className="text-truncate">{title}</label>}
				<SearchableSelect
					{...omitDefinedProps(otherProps, fieldDropDownPropTypes)}
					buttonPlaceholder={(data && data.name) || placeholder}
					caretDouble
					inputPlaceholder={Liferay.Language.get('search')}
					inputValue={searchInputValue}
					items={searchItems}
					name={name}
					onSearchChange={onSearchInput}
					onSelect={onSearchSelect}
					readOnly={readOnly}
					selectedItem={data}
				/>
				<div className="example-value text-truncate">
					{data && data.value}
				</div>
			</div>
		);
	}
}
