/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Option, Picker, useModal} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import React, {useState} from 'react';

import EurFlag from '../../../../../../assets/icons/eur_flag.svg';
import {
	NewAppTypes,
	useNewAppContext,
} from '../../../../../../context/NewAppContext';
import {LicenseTier} from '../../../../../../enums/licenseTier';
import {currenciesCode} from '../../../../../../utils/currencies';
import {LicensePrice} from '../../../Apps/AppCreationFlow/AppContext/AppManageState';

import './LicensePrices.scss';

import ClayForm from '@clayui/form';

import Modal from '../../../../../../components/Modal';
import LicensePricePanel from '../../components/LicensePricePanel/LicensePricePanel';

const LicensePrices = () => {
	const [
		{
			build: {cloudCompatible},
			licensing: {prices},
		},
		dispatch,
	] = useNewAppContext();

	const defaultCurrency = 'USD';

	const [activeCurrencies, setActiveCurrencies] = useState([defaultCurrency]);
	const [selectedNewCurrency, setSelectedNewCurrency] = useState('');

	const {observer, onClose, onOpenChange, open} = useModal({
		onClose: () => {
			setSelectedNewCurrency('');
		},
	});

	const handleAddPriceTier = (licenseTier: LicenseTier, currency: string) => {
		dispatch({
			payload: {
				currency,
				licenseTier,
			},
			type: NewAppTypes.SET_LICENSING_ADD_PRICE,
		});
	};

	const handleDeletePriceTier = (
		licenseTier: LicenseTier,
		key: number,
		currency: string,
		deleteCurrency?: boolean
	) => {
		if (deleteCurrency) {
			setActiveCurrencies((prev) =>
				prev.filter((currencyCode) => currencyCode !== currency)
			);
		}

		dispatch({
			payload: {
				currency,
				deleteCurrency,
				key,
				licenseTier,
			},
			type: NewAppTypes.SET_LICENSING_DELETE_PRICE,
		});
	};

	const handleEditPriceTier = (
		licenseTier: LicenseTier,
		index: number,
		price: LicensePrice,
		currency: string
	) => {
		dispatch({
			payload: {
				currency,
				index,
				licenseTier,
				price: price.value,
				quantity: price.key,
			},
			type: NewAppTypes.SET_LICENSING_UPDATE_PRICES,
		});
	};

	const handleAddCurrency = () => {
		if (
			selectedNewCurrency &&
			!activeCurrencies.includes(selectedNewCurrency)
		) {
			setActiveCurrencies((prev) => [...prev, selectedNewCurrency]);

			dispatch({
				payload: {
					currency: selectedNewCurrency,
					licenseTier: LicenseTier.STANDARD,
				},
				type: NewAppTypes.SET_LICENSING_ADD_PRICE,
			});

			onClose();

			setSelectedNewCurrency('');
		}
	};

	const renderCurrencyFlag = (code: string, flag: string) => {
		if (code === 'EUR') {
			return (
				<img
					alt="EUR Flag"
					className="currency-selector-icon ml-2"
					src={EurFlag}
				/>
			);
		}

		return (
			<ClayIcon
				className="currency-selector-icon ml-2"
				symbol={flag || 'en-us'}
			/>
		);
	};

	const CurrencyTrigger = React.forwardRef(({children, ...props}, ref) => {
		const selected = currenciesCode.find(
			(item) => item.code === selectedNewCurrency
		);

		return (
			<div
				ref={ref}
				{...props}
				className="form-control form-control-select"
				tabIndex={0}
			>
				{children || 'Choose a option'}
				{selected && (
					<span className="ml-2">
						{renderCurrencyFlag(selected.code, selected.flag)}
					</span>
				)}
			</div>
		);
	});

	return (
		<div className="informing-licensing-terms-page-container">
			<div className="p-4">
				{activeCurrencies.map((currencyCode) => {
					if (!prices[currencyCode]) {
						return null;
					}

					return (
						<div key={currencyCode}>
							<LicensePricePanel
								cloudCompatible={cloudCompatible}
								currencyCode={currencyCode}
								handleAddPriceTier={handleAddPriceTier}
								handleDeletePriceTier={handleDeletePriceTier}
								handleEditPriceTier={handleEditPriceTier}
								prices={prices[currencyCode] || {}}
							/>
						</div>
					);
				})}

				<ClayButton
					className="add-currency-button w-100"
					onClick={() => onOpenChange(true)}
				>
					+ Add Currency
				</ClayButton>
			</div>

			{open && (
				<Modal
					className="currency-selector-modal"
					last={
						<ClayButton.Group spaced>
							<ClayButton
								displayType="secondary"
								onClick={onClose}
							>
								Cancel
							</ClayButton>
							<ClayButton
								disabled={!selectedNewCurrency}
								onClick={handleAddCurrency}
							>
								Confirm
							</ClayButton>
						</ClayButton.Group>
					}
					observer={observer}
					size="md"
					subtitle="Choose one of the following currencies"
					title="Select Desired Currency"
					visible
				>
					<div className="currency-selector-container">
						<ClayForm.Group>
							<label
								htmlFor="currency-picker"
								id="currency-picker-label"
							>
								Choose Currency
							</label>
							<Picker
								as={CurrencyTrigger}
								id="currency-picker"
								items={currenciesCode}
								onSelectionChange={(key) =>
									setSelectedNewCurrency(key)
								}
								width={200}
							>
								{(item) => (
									<Option
										disabled={activeCurrencies.includes(
											item.code
										)}
										key={item.code}
										textValue={item.code}
									>
										<span className="align-items-center d-flex">
											<span>{item.code}</span>
											{renderCurrencyFlag(
												item.code,
												item.flag
											)}
										</span>
									</Option>
								)}
							</Picker>
						</ClayForm.Group>
					</div>
				</Modal>
			)}
		</div>
	);
};

export default LicensePrices;
