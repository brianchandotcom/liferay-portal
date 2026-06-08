/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {toPairs} from 'lodash';
import {PropTypes} from 'prop-types';
import React from 'react';
import Label from '~/shared/components/Label';

export default class WillBeRemovedCell extends React.Component {
	static propTypes = {
		data: PropTypes.shape({
			dataSourceIndividualPKs: PropTypes.array,
		}),
	};

	render() {
		const {
			data: {dataSourceIndividualPKs},
		} = this.props;

		return (
			<td
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				{toPairs(dataSourceIndividualPKs).length === 1 && (
					<Label display="danger" size="lg" uppercase>
						{Liferay.Language.get('will-be-removed')}
					</Label>
				)}
			</td>
		);
	}
}
