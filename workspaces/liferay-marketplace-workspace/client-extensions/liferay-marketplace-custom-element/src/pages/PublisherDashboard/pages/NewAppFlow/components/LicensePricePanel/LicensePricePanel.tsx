/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import Panel from '@clayui/panel';
import React from 'react';

import EurFlag from '../../../../../../assets/icons/eur_flag.svg';
import {Section} from '../../../../../../components/Section/Section';
import {LicenseTier} from '../../../../../../enums/licenseTier';
import {currenciesCode} from '../../../../../../utils/currencies';
import IconButton from '../IconButton';
import LicensePriceCard from '../LicensePriceCard';

type Props = {
	cloudCompatible: boolean;
	currencyCode: string;
	handleAddPriceTier: Function;
	handleDeletePriceTier: Function;
	handleEditPriceTier: Function;
	prices: {
		[licenseTier in LicenseTier]?: {[key: number]: number};
	};
};

const LicensePricePanel: React.FC<Props> = ({
	cloudCompatible,
	currencyCode,
	handleAddPriceTier,
	handleDeletePriceTier,
	handleEditPriceTier,
	prices,
}) => {
	const standardPrices = prices[LicenseTier.STANDARD] || {};
	const developerPrices = prices[LicenseTier.DEVELOPER] || {};

	const matchedCurrency = currenciesCode.find((c) => c.code === currencyCode);

	const icon =
		matchedCurrency?.code === 'EUR' ? (
			<img
				alt="EUR Flag"
				className="currency-selector-icon"
				src={EurFlag}
			/>
		) : (
			<ClayIcon
				className="currency-selector-icon"
				symbol={matchedCurrency?.flag || 'en-us'}
			/>
		);

	const shouldShowDeveloperCard = () => !!Object.keys(developerPrices).length;

	const handleDeleteAllPricesForCurrency = (currency: string) => {
		const tiers = [LicenseTier.STANDARD, LicenseTier.DEVELOPER];

		tiers.forEach((tier) => {
			const pricesByTier = prices[tier] || {};
			const keys = Object.keys(pricesByTier);

			keys.forEach((key) => {
				handleDeletePriceTier(tier, Number(key), currency, true);
			});
		});
	};

	return (
		<Panel
			collapsable
			defaultExpanded
			displayTitle={
				<div className="align-items-center d-flex justify-content-between w-100">
					<div className="align-items-center d-flex">
						<span className="mr-2">{currencyCode}</span>
						{icon}
					</div>

					{currencyCode !== 'USD' && (
						<ClayButtonWithIcon
							aria-label={`Delete all prices for ${currencyCode}`}
							className="h-auto ml-auto"
							displayType="unstyled"
							onClick={() =>
								handleDeleteAllPricesForCurrency(currencyCode)
							}
							symbol="trash"
							title="Delete all prices"
						/>
					)}
				</div>
			}
			displayType="secondary"
			showCollapseIcon={true}
		>
			<Panel.Body>
				<Section
					className="mb-6"
					label="Standard License prices"
					required
					tooltip="Standard licenses cover the following DXP environments: production, non-production (UAT) and backup (DR) for both standalone and virtual cluster servers."
					tooltipText="More Info"
				>
					<LicensePriceCard
						currency={currencyCode}
						licensePrices={standardPrices}
						licenseTier={LicenseTier.STANDARD}
						onAdd={(currency) =>
							handleAddPriceTier(LicenseTier.STANDARD, currency)
						}
						onChange={(index, price, currency) =>
							handleEditPriceTier(
								LicenseTier.STANDARD,
								index,
								price,
								currency
							)
						}
						onDelete={(key, currency) =>
							handleDeletePriceTier(
								LicenseTier.STANDARD,
								key,
								currency
							)
						}
					/>
				</Section>

				{!cloudCompatible && (
					<Section
						label="Developer License prices"
						tooltip="Developer licenses are limited to 5 unique addresses and should not be used for full scale production deployments."
						tooltipText="More Info"
					>
						{shouldShowDeveloperCard() ? (
							<LicensePriceCard
								currency={currencyCode}
								licensePrices={developerPrices}
								licenseTier={LicenseTier.DEVELOPER}
								onAdd={(currency) =>
									handleAddPriceTier(
										LicenseTier.DEVELOPER,
										currency
									)
								}
								onChange={(index, price, currency) =>
									handleEditPriceTier(
										LicenseTier.DEVELOPER,
										index,
										price,
										currency
									)
								}
								onDelete={(key, currency) =>
									handleDeletePriceTier(
										LicenseTier.DEVELOPER,
										key,
										currency
									)
								}
							/>
						) : (
							<IconButton
								className="license-icon-button py-3 w-100"
								displayType={null}
								onClick={() =>
									handleAddPriceTier(
										LicenseTier.DEVELOPER,
										currencyCode
									)
								}
							>
								Add Developer Licenses
							</IconButton>
						)}
					</Section>
				)}
			</Panel.Body>
		</Panel>
	);
};

export default LicensePricePanel;
