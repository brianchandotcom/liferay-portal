/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';

import './LicensePriceCard.scss';

import classNames from 'classnames';
import {useState} from 'react';

import {FieldBase} from '../../../../../../../../components/FieldBase';
import {LicensePrice} from '../../../AppContext/AppManageState';
import CurrencySelector from '../CurrencySelector';
import IconButton from '../IconButton';

type LicensePriceCardProps = {
	licensePrices: LicensePrice[];
	onAdd: () => void;
	onChange: (index: number, price: LicensePrice) => void;
	onDelete: (key: number) => void;
};

const LicensePriceCard: React.FC<LicensePriceCardProps> = ({
	licensePrices,
	onAdd,
	onChange,
	onDelete,
}) => {
	const [, setSelectedCurrency] = useState('USD');

	const handleCurrencySelection = (currency: string) => {
		setSelectedCurrency(currency);
	};

	return (
		<ClayForm.Group className="d-flex flex-column license-card-container p-4">
			<div className="row">
				<FieldBase
					className="col-3"
					label="Quantity"
					labelClassName="teste"
					tooltip="By adding quantities to price tiers, you can offer quantity discounts. For example, adding a quantity of 3 would allow you to offer a discount unit price for 3 or more licenses."
				/>

				<FieldBase
					className="col-6 p-0"
					label="Unit Price"
					labelClassName="teste"
					tooltip="Adding a unit price sets the amount you want to charge for each individual license when the set quantity is chosen."
				/>

				<FieldBase
					className="col-3"
					label={
						<CurrencySelector onChange={handleCurrencySelection} />
					}
					labelClassName="teste"
					tooltip="Quantity info"
				/>
			</div>

			{licensePrices.map((tierPrice, index) => (
				<div className="align-items-center mb-4 row" key={index}>
					<ClayInput.Group className="col-11 p-0">
						<ClayInput.GroupItem className="col-3">
							<ClayInput
								className={classNames(
									'license-card-input py-5',
									{
										'bg-white': index,
										'disabled': !index,
									}
								)}
								disabled={!index}
								onChange={(event) => {
									if (index) {
										onChange(index, {
											key: Number(event.target.value),
											value: tierPrice.value,
										});
									}
								}}
								placeholder="1"
								type="number"
								value={tierPrice.key}
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem className="col-9 m-0">
							<ClayInput
								className="bg-white license-card-input py-5 text-right"
								onChange={(event) => {
									const regExp = /^[0-9.,]*$/;

									if (regExp.test(event.target.value)) {
										onChange(index, {
											key: tierPrice.key,
											value: Number(event.target.value),
										});
									}
								}}
								placeholder="$0,00"
								type="text"
								value={tierPrice.value || ''}
							/>
						</ClayInput.GroupItem>
					</ClayInput.Group>

					{!!index && (
						<ClayButtonWithIcon
							aria-label="Delete"
							displayType={null}
							onClick={() => onDelete(tierPrice.key)}
							symbol="trash"
							title="Delete"
						/>
					)}
				</div>
			))}

			<IconButton
				className="icon-button py-3 w-100"
				displayType={null}
				onClick={onAdd}
			>
				Add Price Tier
			</IconButton>
		</ClayForm.Group>
	);
};

export default LicensePriceCard;
