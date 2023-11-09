/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';

type TableActionsProps = {
	onDeactivate: () => void;
	onDownload: () => void;
	onView: () => void;
};

const TableActions: React.FC<TableActionsProps> = ({
	onDeactivate,
	onDownload,
	onView,
}) => (
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
			<ClayDropDown.Item onClick={onDownload}>
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
);

export default TableActions;
