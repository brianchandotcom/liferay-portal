/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {NewAppInitialState} from '../../../../../../context/NewAppContext';
import {LICENSING_OPTIONS} from '../../constants';
import {ProductPriceModel} from '../../../../../../enums/Product';
import {currenciesCodeObject} from '../../../../../../utils/currencies';

type SubmitLicensingListProps = {
	appData: NewAppInitialState;
};
const SubmitLicensingList = ({appData}: SubmitLicensingListProps) => {
	const licenseOption = LICENSING_OPTIONS.find(
		(licenseOption) => licenseOption.value === appData.licensing.licenseType
	);

	return (
		<>
			<div className="border p-4 rounded-lg">
				<div>
					{licenseOption && (
						<>
							<div className="align-items-center d-flex">
								<span
									className="mr-2"
									style={{
										fontSize: 18,
										fontWeight: 600,
									}}
								>
									{licenseOption?.title}
								</span>{' '}
								<ClayIcon
									style={{color: '#377CFF'}}
									symbol={licenseOption.icon}
								/>
							</div>

							<span style={{color: '#54555F', fontSize: 13}}>
								{licenseOption?.description}
							</span>
						</>
					)}
				</div>
			</div>
			{appData.pricing.priceModel !== ProductPriceModel.FREE && (
				<div className="border mt-4 p-4 rounded-lg">
					<div>
						{Object.keys(appData.licensing.prices).map((key) => (
							<div key={key}>
								<span>
									{key}
									{currenciesCodeObject[
										key as keyof typeof currenciesCodeObject
									].iconSrc ? (
										<img
											className="currency-selector-icon ml-2"
											src={
												currenciesCodeObject[
													key as keyof typeof currenciesCodeObject
												].iconSrc
											}
										/>
									) : (
										<ClayIcon
											className="currency-selector-icon ml-2"
											symbol={
												currenciesCodeObject[
													key as keyof typeof currenciesCodeObject
												].flag
											}
										/>
									)}
								</span>
								<div className="d-flex justify-content-between">
									{Object.entries(
										appData.licensing.prices[key]
									).map(([priceType, values], index) => (
										<div key={index}>
											<h5 className="licesing-price-type pt-2">
												{priceType} License price
											</h5>

											{Object.entries(values).map(
												([unit, price], index) => (
													<div
														className="licensing-unit-price"
														key={index}
													>
														Quantity: <b>{unit}</b>{' '}
														- Unit Price:{' '}
														<b>
															{
																currenciesCodeObject[
																	key as keyof typeof currenciesCodeObject
																].symbol
															}
															{price as number}
														</b>
													</div>
												)
											)}
										</div>
									))}
								</div>
								<hr />
							</div>
						))}
					</div>
				</div>
			)}
		</>
	);
};

export default SubmitLicensingList;
