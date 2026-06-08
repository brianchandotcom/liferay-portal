/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {times} from 'lodash';
import React from 'react';
import Growth from '~/segment/components/Growth';
import {ONE_DAY} from '~/shared/util/constants';
import {Segment} from '~/shared/util/records';
import {mockSegment} from '~/test/data';

function getRandomInt(max) {
	return Math.floor(Math.random() * Math.floor(max * 100));
}

const points = times(90, (i) => ({
	modifiedDate: Date.now() - ONE_DAY * i,
	value: getRandomInt(i),
})).reverse();

export default class SegmentGrowthKit extends React.Component {
	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<Growth
					data={points}
					groupId="35205"
					id="AWOKZWGtYGgetnXCCc8L"
					segment={new Segment(mockSegment())}
				/>
			</div>
		);
	}
}
