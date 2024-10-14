/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import {Fragment} from 'react';
import {IconBreadcrumbs} from '~/common/icons';
import {Liferay} from '~/common/services/liferay';

import ProjectNameTruncate from './ProjectNameTruncate';

type DropdownItemsProps = {
	koroneikiAccounts: any[];
	selectedKoroneikiAccount: any;
};

const getHref = (accountKey: string) => {
	const hashLocation = window.location.hash.replace(
		/[A-Z]+-\d+/g,
		accountKey
	);

	return `${Liferay.ThemeDisplay.getLayoutURL()}/${hashLocation}`;
};

const DropdownItems: React.FC<DropdownItemsProps> = ({
	koroneikiAccounts,
	selectedKoroneikiAccount,
}) => {
	return (
		<>
			{koroneikiAccounts?.map((koroneikiAccount, index) => {
				const isSelected =
					koroneikiAccount?.accountKey ===
					selectedKoroneikiAccount?.accountKey;

				return (
					<Fragment key={index}>
						<ClayDropDown.Item
							active={isSelected}
							className="align-items-center cp-breadcrumbs-dropdown-item d-flex font-weight-semi-bold pl-3 pr-5 text-paragraph"
							href={
								!isSelected
									? getHref(koroneikiAccount?.accountKey)
									: ''
							}
							symbolRight={isSelected ? 'check' : ''}
						>
							<div>
								<IconBreadcrumbs height={25} width={25} />
							</div>

							<div
								className="pl-2"
								title={koroneikiAccount?.name}
							>
								<ProjectNameTruncate>
									{koroneikiAccount?.name}
								</ProjectNameTruncate>
							</div>
						</ClayDropDown.Item>
					</Fragment>
				);
			})}
		</>
	);
};

export default DropdownItems;
