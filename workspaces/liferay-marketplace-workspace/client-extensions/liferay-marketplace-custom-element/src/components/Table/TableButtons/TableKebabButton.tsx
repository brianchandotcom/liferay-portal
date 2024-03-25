/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';

type TableKebabButtonProps<T = any> = {
	disabled?: boolean;
	onEdit?: () => void;
	onViewDetails: (row: T) => void;
};

const TableKebabButton: React.FC<TableKebabButtonProps> = ({
	disabled = true,
	onEdit,
	onViewDetails,
}) => (
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
			<ClayDropDown.Item onClick={onViewDetails}>
				View Details
			</ClayDropDown.Item>

			<ClayDropDown.Item disabled={disabled} onClick={onEdit}>
				Edit
			</ClayDropDown.Item>
		</ClayDropDown.ItemList>
	</ClayDropDown>
);

export default TableKebabButton;
