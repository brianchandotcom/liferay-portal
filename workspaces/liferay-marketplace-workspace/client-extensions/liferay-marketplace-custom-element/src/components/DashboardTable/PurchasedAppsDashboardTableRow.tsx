/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';

import './PurchasedAppsDashboardTableRow.scss';

import DropDown from '@clayui/drop-down/lib/DropDown';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {useNavigate} from 'react-router-dom';

import {OrderStatus} from '../../enums/OrderStatus';
import {OrderType} from '../../enums/OrderType';
import i18n from '../../i18n';
import {PurchasedAppProps} from '../../pages/PurchasedAppsDashboard/PurchasedAppsDashboardOutlet';
import {showAppImage} from '../../utils/util';
import LabelStatus from '../LabelStatus/LabelStatus';

interface PurchasedAppsDashboardTableRowProps {
	item: PurchasedAppProps;
}

export function PurchasedAppsDashboardTableRow({
	item,
}: PurchasedAppsDashboardTableRowProps) {
	const {
		name,
		orderId,
		orderTypeExternalReferenceCode,
		project,
		provisioning,
		provisioningLabel,
		purchasedBy,
		purchasedDate,
		thumbnail,
		type,
		version,
		virtualURL,
	} = item;

	const navigate = useNavigate();

	const orderStatusIsNotCompleted =
		provisioningLabel !== OrderStatus.COMPLETED;

	return (
		<ClayTable.Row
			className="dashboard-table-row"
			onClick={() => {
				navigate(`/order/${orderId}`);
			}}
		>
			<ClayTable.Cell>
				<div className="dashboard-table-row-name-container">
					<div>
						<img
							alt="App Image"
							className="dashboard-table-row-name-logo"
							src={showAppImage(thumbnail)}
						/>
					</div>

					<div>
						<span className="dashboard-table-row-name-text">
							{name}
						</span>

						{version ? (
							<>
								<br></br>
								<span className="dashboard-table-row-name-version">
									{version}
								</span>
							</>
						) : (
							<></>
						)}
					</div>
				</div>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<div className="dashboard-table-row-purchased-container">
					<span className="dashboard-table-row-text">
						{purchasedBy}
					</span>
				</div>

				<span className="dashboard-table-row-purchased-date">
					{purchasedDate}
				</span>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<div className="dashboard-table-row-type">
					<span>{type}</span>
				</div>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<div className="dashboard-table-row-order-id-container">
					<span className="dashboard-table-row-order-id-text">
						{orderId}
					</span>
				</div>
			</ClayTable.Cell>

			{project ? (
				<ClayTable.Cell>
					<span className="dashboard-table-row-text">{project}</span>
				</ClayTable.Cell>
			) : (
				<></>
			)}

			<ClayTable.Cell>
				<div className="dashboard-table-row-provisioning-container">
					<LabelStatus
						provisioning={provisioning}
						provisioningLabel={provisioningLabel}
					/>
				</div>
			</ClayTable.Cell>

			<ClayTable.Cell onClick={(event) => event.stopPropagation()}>
				<DropDown
					trigger={
						<ClayButton displayType="secondary">
							{i18n.translate('manage')}
							<ClayIcon symbol="caret-bottom" />
						</ClayButton>
					}
				>
					<DropDown.ItemList>
						{orderTypeExternalReferenceCode === OrderType.DXP && (
							<ClayTooltipProvider>
								<DropDown.Item
									data-tooltip-align="left"
									disabled={orderStatusIsNotCompleted}
									onClick={() =>
										navigate(
											`/order/${orderId}/create-license`
										)
									}
									title={
										orderStatusIsNotCompleted
											? i18n.translate(
													'the-order-must-be-completed-before-licensing-this-app.'
											  )
											: undefined
									}
								>
									{i18n.translate('create-license-key')}
								</DropDown.Item>
							</ClayTooltipProvider>
						)}
						<DropDown.Item
							onClick={() => {
								navigate(`/order/${orderId}/licenses`);
							}}
						>
							Manage License Key(s)
						</DropDown.Item>

						<DropDown.Item
							onClick={() => {
								window.location.href =
									'https://console.marketplacedemo.liferay.sh/projects';
							}}
						>
							{i18n.translate('access-console')}
						</DropDown.Item>

						{orderTypeExternalReferenceCode === OrderType.DXP && (
							<ClayTooltipProvider>
								<DropDown.Item
									data-tooltip-align="left"
									disabled={orderStatusIsNotCompleted}
									onClick={() => {
										window.location.href = virtualURL;
									}}
									title={
										orderStatusIsNotCompleted
											? i18n.translate(
													'this-order-must-be-completed-before-downloading-this-app.'
											  )
											: undefined
									}
								>
									{i18n.translate('download-app')}
								</DropDown.Item>
							</ClayTooltipProvider>
						)}
					</DropDown.ItemList>
				</DropDown>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
}
