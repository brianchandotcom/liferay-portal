/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';

import {FieldBase} from '../FieldBase';

import './LicensePriceCard.scss';
import unitedStatesIcon from '../../assets/icons/united_states_icon.svg';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {TYPES} from '../../manage-app-state/actionTypes';

export function LicensePriceCard() {
	const [{appLicensePrice}, dispatch] = useAppContext();

	return (
		<div className="license-card-container">
			<ClayForm.Group>
				<div className="license-card-quantity">
					<FieldBase
						label="Quantity"
						required
						tooltip="Quantity info"
					/>

					<ClayInput.Group>
						<ClayInput.GroupItem>
							<div className="license-card-input-title">
								<span>From</span>
							</div>

							<ClayInput disabled placeholder="1" type="text" />
						</ClayInput.GroupItem>

						<ClayInput.GroupItem>
							<div className="license-card-input-title">
								<span>To</span>
							</div>

							<ClayInput disabled placeholder="1" type="text" />
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</div>

				<div className="license-card-price">
					<FieldBase label="Price" required tooltip="Price info" />

					<ClayInput.Group>
						<ClayInput.GroupItem prepend shrink>
							<ClayInput.GroupText>
								<img
									className="license-card-price-icon"
									src={unitedStatesIcon}
								/>
								USD
							</ClayInput.GroupText>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem
							append
							className="license-card-price-currency-input"
						>
							<ClayInput
								onChange={({
									target,
								}: {
									target: {value: string};
								}) =>
									dispatch({
										payload: {
											value: target.value,
										},
										type: TYPES.UPDATE_APP_LICENSE_PRICE,
									})
								}
								placeholder="$100"
								type="text"
								value={appLicensePrice}
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem>
							<div className="license-card-input-title license-card-input-title-total">
								<span>Total</span>
							</div>

							<ClayInput
								placeholder="$100"
								type="text"
								value={appLicensePrice}
							/>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</div>
			</ClayForm.Group>
		</div>
	);
}
