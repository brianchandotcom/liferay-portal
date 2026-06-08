/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';
import Nav from '~/shared/components/Nav';

import Row from '../components/Row';

const items = ['Foo', 'Bar', 'Baz'];

class NavKit extends React.Component {
	state = {
		activeHash: '0',
	};

	componentDidMount() {
		window.addEventListener('hashchange', () => {
			this.setState({
				activeHash: window.location.hash.substring(1),
			});
		});
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
					<Nav>
						{items.map((item, index) => (
							<Nav.Item
								active={index == activeHash}
								href={`#${index}`}
								key={index}
							>
								{item}
							</Nav.Item>
						))}
					</Nav>
				</Row>

				<Row>
					<Nav display="stacked">
						{items.map((item, index) => (
							<Nav.Item
								active={index == activeHash}
								href={`#${index}`}
								key={index}
							>
								<h3>{`Anchor: ${item}`}</h3>

								{item}
							</Nav.Item>
						))}
					</Nav>
				</Row>

				<Row>
					<Nav display="stacked">
						{items.map((item, index) => (
							<Nav.Item key={index}>
								<ClayButton
									active={index == activeHash}
									className="button-root nav-link nav-link-monospaced"
								>
									<h3>{`Button: ${item}`}</h3>

									{item}
								</ClayButton>
							</Nav.Item>
						))}
					</Nav>
				</Row>

				<Row>
					<Nav display="pills">
						{items.map((item, index) => (
							<Nav.Item
								active={index == activeHash}
								href={`#${index}`}
								key={index}
							>
								{item}
							</Nav.Item>
						))}
					</Nav>
				</Row>

				<Row>
					<Nav display="tabs">
						{items.map((item, index) => (
							<Nav.Item
								active={index == activeHash}
								href={`#${index}`}
								key={index}
							>
								{item}
							</Nav.Item>
						))}
					</Nav>
				</Row>

				<Row>
					<Nav display="underline">
						{items.map((item, index) => (
							<Nav.Item
								active={index == activeHash}
								href={`#${index}`}
								key={index}
							>
								{item}
							</Nav.Item>
						))}
					</Nav>
				</Row>
			</div>
		);
	}
}

export default NavKit;
