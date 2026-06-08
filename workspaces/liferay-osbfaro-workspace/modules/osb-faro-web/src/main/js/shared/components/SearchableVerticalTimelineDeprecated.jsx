/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import autobind from 'autobind-decorator';
import {PropTypes} from 'prop-types';
import React from 'react';
import BaseResults from '~/shared/components/BaseResults';
import VerticalTimeline from '~/shared/components/VerticalTimelineDeprecated';
import {withStatefulPagination} from '~/shared/hoc';

export default class SearchableVerticalTimeline extends React.Component {
	static defaultProps = {
		initialExpanded: true,
		items: [],
		loading: false,
		nested: false,
	};

	static propTypes = {
		className: PropTypes.string,
		groupId: PropTypes.string,
		headerLabels: PropTypes.object,
		initialExpanded: PropTypes.bool,
		items: PropTypes.arrayOf(PropTypes.object),
		loading: PropTypes.bool,
		nested: PropTypes.bool,
		timeZoneId: PropTypes.string,
	};

	@autobind
	renderVerticalTimeline({items, loading}) {
		const {groupId, headerLabels, initialExpanded, nested, timeZoneId} =
			this.props;

		return (
			<VerticalTimeline
				groupId={groupId}
				headerLabels={headerLabels}
				initialExpanded={initialExpanded}
				items={items}
				loading={loading}
				nested={nested}
				timeZoneId={timeZoneId}
			/>
		);
	}

	render() {
		const {className} = this.props;

		return (
			<BaseResults
				{...this.props}
				className={className}
				resultsRenderer={this.renderVerticalTimeline}
			/>
		);
	}
}

SearchableVerticalTimeline.StatefulPagination = withStatefulPagination(
	SearchableVerticalTimeline
);
