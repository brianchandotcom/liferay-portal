/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button, DropDown} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import {useState} from 'react';
import i18n from '~/common/I18n';
import {Skeleton} from '~/common/components';

const ProjectCategoryDropdown = ({
	loading,
	onSelect,
	projectCategoryItems,
	selectedProjectCategoryKey,
}) => {
	const [active, setActive] = useState(false);

	return (
		<div className="align-items-center d-flex mb-4 ml-4 mt-2">
			<div className="font-weight-bold pr-1 text-paragraph-sm">
				{loading ? (
					<Skeleton height={18} width={40} />
				) : (
					`${i18n.translate('filter-by-my-role')}:`
				)}
			</div>

			<DropDown
				active={active}
				closeOnClickOutside
				menuWidth="shrink"
				onActiveChange={setActive}
				trigger={
					<Button
						borderless
						className="align-items-center d-flex px-2"
						disabled={loading}
						size="sm"
					>
						{loading ? (
							<Skeleton height={18} width={46} />
						) : (
							projectCategoryItems.find(
								({key}) => key === selectedProjectCategoryKey
							)?.label
						)}

						<span className="inline-item-after">
							<ClayIcon symbol="caret-bottom" />
						</span>
					</Button>
				}
			>
				{projectCategoryItems?.map((item, index) => (
					<DropDown.Item
						className="pr-6"
						disabled={item.key === selectedProjectCategoryKey}
						key={`${index}-${index}`}
						onClick={() => {
							onSelect(item.key);
							setActive(false);
						}}
						symbolRight={
							item.key === selectedProjectCategoryKey && 'check'
						}
					>
						{item.label}
					</DropDown.Item>
				))}
			</DropDown>
		</div>
	);
};

export default ProjectCategoryDropdown;
