/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';
import BaseInterestDetails from '~/contacts/pages/BaseInterestDetails';
import {Segment} from '~/shared/util/records';
import {Routes, SEGMENTS} from '~/shared/util/router';

export default class InterestDetails extends React.Component {
	static propTypes = {
		segment: PropTypes.instanceOf(Segment).isRequired,
	};

	render() {
		const {segment, ...otherProps} = this.props;

		return (
			<BaseInterestDetails
				{...otherProps}
				entity={segment}
				interestDetailsRoute={Routes.CONTACTS_SEGMENT_INTEREST_DETAILS}
				type={SEGMENTS}
			/>
		);
	}
}
