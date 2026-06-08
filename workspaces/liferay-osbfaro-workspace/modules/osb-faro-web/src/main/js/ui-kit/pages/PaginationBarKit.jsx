/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import PaginationBar from '~/shared/components/PaginationBar';

import Row from '../components/Row';

class PaginationBarKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<PaginationBar
						href={window.location.pathname}
						page={2}
						selectedDelta={10}
						totalItems={200}
					/>
				</Row>

				<Row>
					<PaginationBar
						href={window.location.pathname}
						page={10}
						selectedDelta={30}
						size="sm"
						totalItems={400}
					/>
				</Row>

				<Row>
					<PaginationBar
						href={window.location.pathname}
						page={19}
						selectedDelta={5}
						size="lg"
						totalItems={200}
					/>
				</Row>
			</div>
		);
	}
}

export default PaginationBarKit;
