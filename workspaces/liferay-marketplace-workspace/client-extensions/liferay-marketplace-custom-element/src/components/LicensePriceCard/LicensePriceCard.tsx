/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
