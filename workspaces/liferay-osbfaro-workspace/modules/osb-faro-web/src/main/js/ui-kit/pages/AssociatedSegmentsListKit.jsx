/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import AssociatedSegmentsList from '~/contacts/components/AssociatedSegmentsList';
import * as data from '~/test/data';

const dataSourceFn = () =>
	Promise.resolve(
		data.mockSearch(data.mockSegment, 20, [
			null,
			{
				count: 123,
				dateCreated: data.getTimestamp(-2),
				individualAddedDate: data.getTimestamp(-1),
			},
		])
	);

export default class AssociatedSegmentsListKit extends React.Component {
	render() {
		return (
			<div>
				<AssociatedSegmentsList
					dataSourceFn={dataSourceFn}
					groupId="23"
					id="test"
					total={20}
				/>
			</div>
		);
	}
}
