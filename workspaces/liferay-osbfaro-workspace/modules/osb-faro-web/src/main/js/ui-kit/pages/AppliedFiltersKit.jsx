/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import BasePage from '~/shared/components/base-page';
import AppliedFilters from '~/shared/components/filter/AppliedFilters';

import Row from '../components/Row';

export default class AppliedFiltersKit extends React.Component {
	render() {
		return (
			<div>
				<Row>
					<h3>AppliedFilters</h3>

					<BasePage.Context.Provider
						value={{
							filters: {},
							rangeKey: {
								defaultValue: '',
								lastValue: '',
							},
							router: {query: ''},
							sidebarId: null,
						}}
					>
						<AppliedFilters
							filters={{devices: ['smartphone', 'desktop']}}
						/>
					</BasePage.Context.Provider>
				</Row>
			</div>
		);
	}
}
