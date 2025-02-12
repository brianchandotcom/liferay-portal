/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button} from '@clayui/core';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {useState} from 'react';
import i18n from '~/utils/I18n';

interface IProps {
	onSelect: (key: string) => void;
	projectCategoryItems: ProjectCategoryItem[];
	selectedProjectCategoryKey: string;
}

interface ProjectCategoryItem {
	key: string;
	label: string;
}

const ProjectCategoryDropdown: React.FC<IProps> = ({
	onSelect,
	projectCategoryItems,
	selectedProjectCategoryKey,
}) => {
	const [active, setActive] = useState(false);

	return (
		<div className="align-items-center d-flex mb-4 ml-4 mt-2">
			<div className="font-weight-bold pr-1 text-paragraph-sm">
				{`${i18n.translate('filter-by-my-role')}:`}
			</div>

			<ClayDropDown
				active={active}
				closeOnClickOutside
				menuWidth="shrink"
				onActiveChange={setActive}
				trigger={
					<Button
						aria-label={
							projectCategoryItems.find(
								({key}) => key === selectedProjectCategoryKey
							)?.label ?? ''
						}
						borderless
						className="align-items-center d-flex px-2"
						size="sm"
					>
						{
							projectCategoryItems.find(
								({key}) => key === selectedProjectCategoryKey
							)?.label
						}

						<span className="inline-item-after">
							<ClayIcon symbol="caret-bottom" />
						</span>
					</Button>
				}
			>
				{projectCategoryItems?.map((item, index) => (
					<ClayDropDown.Item
						className="pr-6"
						disabled={item.key === selectedProjectCategoryKey}
						key={`${index}-${index}`}
						onClick={() => {
							onSelect(item.key);
							setActive(false);
						}}
						symbolRight={
							item.key === selectedProjectCategoryKey
								? 'check'
								: undefined
						}
					>
						{item.label}
					</ClayDropDown.Item>
				))}
			</ClayDropDown>
		</div>
	);
};

export default ProjectCategoryDropdown;
