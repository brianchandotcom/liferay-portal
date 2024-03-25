/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import {ClayTooltipProvider} from '@clayui/tooltip';

type TableKebabButtonProps<T = any> = {
	disabled?: boolean;
	onEdit?: () => void;
	onViewDetails: (row: T) => void;
};

const TableKebabButton: React.FC<TableKebabButtonProps> = ({
	disabled = false,
	onEdit,
	onViewDetails,
}) => (
	<ClayTooltipProvider>
		<ClayDropDown
			alignmentPosition={Align.BottomRight}
			trigger={
				<ClayButtonWithIcon
					aria-label="Menu"
					displayType={null}
					symbol="ellipsis-v"
					title="Menu"
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
	</ClayTooltipProvider>
);

export default TableKebabButton;
