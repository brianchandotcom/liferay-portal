/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import Avatar from '~/shared/components/Avatar';
import Sticker from '~/shared/components/Sticker';
import {mockIndividual} from '~/test/data';

import Item from '../components/Item';
import Row from '../components/Row';

class AvatarKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Row>
					<Item>
						<Avatar
							entity={mockIndividual(0, {
								image: 'http://i.imgur.com/G5pfP.jpg',
							})}
						/>
					</Item>

					<Item>
						<Avatar
							entity={mockIndividual(0, {
								familyName: undefined,
							})}
						/>
					</Item>

					<Item>
						<Avatar entity={mockIndividual()} />
					</Item>
				</Row>

				<Row>
					{Sticker.DISPLAYS.map((item, index) => (
						<Item key={index}>
							<Avatar entity={mockIndividual(index)} />
						</Item>
					))}
				</Row>

				<Row>
					{Sticker.SIZES.map((size, index) => (
						<Item key={index}>
							<Avatar entity={mockIndividual()} size={size} />
						</Item>
					))}
				</Row>
			</div>
		);
	}
}

export default AvatarKit;
