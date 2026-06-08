/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import Breadcrumbs from '~/shared/components/Breadcrumbs';

import Row from '../components/Row';

const ITEMS = ['one', 'two', 'three', 'four', 'five', 'six', 'seven'];

class BreadcrumbsKit extends React.Component {
	state = {
		activeHash: 'one',
	};

	constructor(props) {
		super(props);

		window.addEventListener('hashchange', () => {
			this.setState({
				activeHash: window.location.hash.substring(1),
			});
		});
	}

	handleClick(label) {
		alert(`you clicked ${label}`);
	}

	render() {
		const {activeHash} = this.state;

		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<Breadcrumbs
						items={ITEMS.map((item) => ({
							active: activeHash === item,
							href: `#${item}`,
							label: item,
						}))}
					/>
				</Row>

				<Row>
					<Breadcrumbs
						items={ITEMS.map((item) => ({
							active: activeHash === item,
							id: item,
							label: item,
						}))}
						onClick={this.handleClick}
					/>
				</Row>

				<Row>
					<Breadcrumbs
						bufferSize={0}
						items={ITEMS.map((item) => ({
							active: activeHash === item,
							id: item,
							label: item,
						}))}
						onClick={this.handleClick}
					/>
				</Row>
			</div>
		);
	}
}

export default BreadcrumbsKit;
