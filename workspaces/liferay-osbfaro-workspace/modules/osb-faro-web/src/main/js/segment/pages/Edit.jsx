/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {get} from 'lodash';
import {PropTypes} from 'prop-types';
import React from 'react';
import {optional} from '~/shared/hoc';
import {withSegment} from '~/shared/hoc/WithSegment';
import {SegmentTypes} from '~/shared/util/constants';
import omitDefinedProps from '~/shared/util/omitDefinedProps';
import {Segment} from '~/shared/util/records';

import DynamicSegment from './edit/Dynamic';

export class Edit extends React.Component {
	static defaultProps = {
		type: SegmentTypes.Batch,
	};

	static propTypes = {
		segment: PropTypes.instanceOf(Segment),
		type: PropTypes.oneOf([SegmentTypes.RealTime, SegmentTypes.Batch]),
	};

	render() {
		const {segment, type, ...otherProps} = this.props;

		const segmentType = get(segment, 'segmentType') || type;

		if (segmentType) {
			return (
				<DynamicSegment
					{...omitDefinedProps(otherProps, Edit.propTypes)}
					segment={segment}
					type={segmentType}
				/>
			);
		}
	}
}

export default optional(withSegment(true))(Edit);
