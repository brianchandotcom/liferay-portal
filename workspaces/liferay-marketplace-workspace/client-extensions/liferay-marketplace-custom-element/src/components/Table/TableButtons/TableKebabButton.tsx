/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';

type TableKebabButtonProps = {
	items: {
		disabled?: boolean;
		label?: string;
		onClick?: () => void;
	}[];
};

const TableKebabButton: React.FC<TableKebabButtonProps> = ({items}) => (
	<ClayDropDown
		trigger={
			<ClayButtonWithIcon
				aria-label="Kebab Button"
				displayType={null}
				symbol="ellipsis-v"
				title="Kebab Button"
			/>
		}
	>
		<ClayDropDown.ItemList>
			{items.map((item, index: number) => {
				return (
					<ClayDropDown.Item
						disabled={item?.disabled}
						key={index}
						onClick={item?.onClick}
					>
						{item?.label}
					</ClayDropDown.Item>
				);
			})}
		</ClayDropDown.ItemList>
	</ClayDropDown>
);

export default TableKebabButton;
