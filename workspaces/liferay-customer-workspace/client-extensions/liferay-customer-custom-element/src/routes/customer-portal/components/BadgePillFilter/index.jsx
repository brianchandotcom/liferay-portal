/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
import {Button} from '../../../../common/components';

const BadgePillFilter = ({filterName, filterValue, onClick}) => (
	<div>
		<div className="align-items-center badge badge-light badge-pill bg-white border border-secondary pl-2 text-neutral-8 text-paragraph-sm">
			<p className="font-weight-semi-bold mx-1 my-0">
				{filterName}

				{':'}
			</p>

			<p className="font-weight-normal m-0 pr-1"> {filterValue}</p>

			<Button
				appendIcon="times-small"
				aria-label="close"
				className="align-self-start mr-1"
				displayType="unstyled"
				onClick={onClick}
			/>
		</div>
	</div>
);

export default BadgePillFilter;
