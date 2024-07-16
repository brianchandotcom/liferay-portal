/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import BadgeFilter from '../../../ActivationKeysTable/components/BadgeFilter';
import Filter from '../../../ActivationKeysTable/components/Filter';

const DeactivationKeysTableHeader = ({
	activationKeysState,
	loading,
	filterState: [filters, setFilters],
}) => {
	const [activationKeys] = activationKeysState;

	return (
		<div className="bg-neutral-1 d-flex flex-column pb-1 pt-3 px-3 rounded">
			<div className="d-flex">
				<Filter
					activationKeys={activationKeys}
					filtersState={[filters, setFilters]}
				/>
			</div>

			<BadgeFilter
				activationKeysLength={activationKeys?.length}
				filtersState={[filters, setFilters]}
				loading={loading}
			/>
		</div>
	);
};

export default DeactivationKeysTableHeader;
