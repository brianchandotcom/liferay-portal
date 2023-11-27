/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import {ClayTooltipProvider} from '@clayui/tooltip';

type TableActionsProps = {
	isDisabled: boolean;
	onDeactivate: () => void;
	onDownload: () => void;
	onView: () => void;
	tooltip: string;
};

const TableActions: React.FC<TableActionsProps> = ({
	isDisabled,
	onDeactivate,
	onDownload,
	onView,
	tooltip,
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
			<ClayDropDown.ItemList className="license-table-actions">
				<ClayDropDown.Item onClick={onView}>
					View License Details
				</ClayDropDown.Item>
				<ClayDropDown.Item
					data-title-set-as-html
					data-tooltip-align="left"
					disabled={isDisabled}
					onClick={onDownload}
					title={tooltip}
				>
					Download License Key
				</ClayDropDown.Item>
				<ClayDropDown.Item
					className="deactivate-license-key text-danger"
					onClick={onDeactivate}
				>
					Deactivate License Key
				</ClayDropDown.Item>
			</ClayDropDown.ItemList>
		</ClayDropDown>
	</ClayTooltipProvider>
);

export default TableActions;
