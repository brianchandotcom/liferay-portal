/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Align} from '@clayui/drop-down';
import {useAtom} from 'jotai';
import {headerAtom} from '~/atoms';

import DropDown from '../../DropDown';
import HeaderBreadcrumbTrigger from './HeaderBreadcrumbTrigger';

const HeaderDropDown = () => {
	const [dropdown] = useAtom(headerAtom.dropdown);
	const [heading] = useAtom(headerAtom.heading);
	const [symbol] = useAtom(headerAtom.symbol);

	const breadcrumbTriggerProps = {
		symbol,
		title: heading[0]?.title,
	};

	return (
		<div className="align-items-center d-flex justify-content-center mx-3">
			{dropdown.length ? (
				<DropDown
					items={dropdown}
					position={Align.BottomLeft}
					trigger={
						<div>
							<HeaderBreadcrumbTrigger
								displayCarret
								{...breadcrumbTriggerProps}
							/>
						</div>
					}
				/>
			) : (
				<HeaderBreadcrumbTrigger {...breadcrumbTriggerProps} />
			)}
		</div>
	);
};

export default HeaderDropDown;
