/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import Pagination from '~/shared/components/Pagination';

import Row from '../components/Row';

class PaginationKit extends React.Component {
	state = {
		page: 1,
	};

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<Pagination
						href={window.location.pathname}
						page={1}
						total={10}
					/>
				</Row>

				<Row>
					<Pagination
						href={window.location.pathname}
						page={28}
						total={30}
					/>
				</Row>

				<Row>
					<Pagination
						href={window.location.pathname}
						page={10}
						total={100}
					/>
				</Row>
			</div>
		);
	}
}

export default PaginationKit;
