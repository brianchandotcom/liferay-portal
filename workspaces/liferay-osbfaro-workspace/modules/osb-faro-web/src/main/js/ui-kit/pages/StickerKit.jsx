/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import Sticker from '~/shared/components/Sticker';

import Row from '../components/Row';

class StickerKit extends React.Component {
	render() {
		return (
			<div>
				<div className="h4">Displays</div>

				<Row>
					{Sticker.DISPLAYS.map((display, index) => (
						<div className="mr-1" key={index}>
							<Sticker display={display} symbol="bolt" />
						</div>
					))}
				</Row>

				<div className="h4">Sizes</div>

				<Row className="align-items-center">
					{Sticker.SIZES.map((size, index) => (
						<div className="mr-1" key={index}>
							<Sticker
								display="primary"
								size={size}
								symbol="bolt"
							/>
						</div>
					))}
				</Row>
			</div>
		);
	}
}

export default StickerKit;
