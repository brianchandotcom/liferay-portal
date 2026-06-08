/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import React from 'react';
import SearchInput from '~/shared/components/SearchInput';

import Row from '../components/Row';

class SearchInputKit extends React.Component {
	@autobind
	handleSubmit(val) {
		alert(val);
	}

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<SearchInput onSubmit={this.handleSubmit} />
				</Row>
			</div>
		);
	}
}

export default SearchInputKit;
