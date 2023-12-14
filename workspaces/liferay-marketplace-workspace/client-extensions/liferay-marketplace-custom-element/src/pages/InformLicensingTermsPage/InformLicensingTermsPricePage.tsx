/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Header} from '../../components/Header/Header';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {Section} from '../../components/Section/Section';
import {
	LicensePrice,
	PriceEntry,
	useAppContext,
} from '../../manage-app-state/AppManageState';

import './InformLicensingTermsPage.scss';
import {LicenseTier} from '../../enums/licenseTier';
import {TYPES} from '../../manage-app-state/actionTypes';
import {
	getPriceListByCatalogName,
	getPriceListIdPriceEntries,
	getProductById,
	getProductIdSkusPage,
	patchPriceEntry,
	patchSKUById,
	postPriceEntryIdTierPrice,
} from '../../utils/api';
import {getSkuPrice} from '../../utils/util';
import IconButton from './components/IconButton/IconButton';
import LicensePriceCard from './components/LicensePriceCard';
interface InformLicensingTermsPricePageProps {
	onClickBack: () => void;
	onClickContinue: () => void;
}

export function InformLicensingTermsPricePage({
	onClickBack,
	onClickContinue,
}: InformLicensingTermsPricePageProps) {
	const [{appLicensePrice, appProductId}, dispatch] = useAppContext();

	const handleAddPriceTier = (licenseTier: LicenseTier) =>
		dispatch({
			payload: {licenseTier},
			type: TYPES.ADD_APP_LICENSE_PRICE,
		});

	const handleDeletePriceTier = (licenseTier: LicenseTier, key: number) =>
		dispatch({
			payload: {
				key,
				licenseTier,
			},
			type: TYPES.DELETE_APP_LICENSE_PRICE,
		});

	const handleEditPriceTier = (
		licenseTier: LicenseTier,
		index: number,
		price: LicensePrice
	) =>
		dispatch({
			payload: {
				index,
				licenseTier,
				price,
			},
			type: TYPES.UPDATE_APP_LICENSE_PRICES,
		});

	const processTier = async (priceEntry: PriceEntry) => {
		const skuName = priceEntry?.sku?.name;

		const keyAppLicensePrice = skuName.endsWith('d')
			? 'developer'
			: 'standard';

		const tiers = appLicensePrice[keyAppLicensePrice];

		for (const {key, value} of tiers || []) {
			const tierPrice = {
				minimumQuantity: key,
				price: value,
				priceEntryId: priceEntry?.priceEntryId,
			};

			await postPriceEntryIdTierPrice(
				priceEntry?.priceEntryId,
				tierPrice
			);
		}
	};

	const handlePostPriceEntryIdTierPrice = async (sku: SKU) => {
		const product = await getProductById({
			nestedFields: 'catalog',
			productId: appProductId,
		});

		const priceList = await getPriceListByCatalogName(
			product?.catalog?.name
		);

		const priceEntries = await getPriceListIdPriceEntries(
			priceList?.items[0]?.id
		);

		for (const priceEntry of priceEntries?.items || []) {
			if (priceEntry?.sku?.id !== sku?.id) {
				continue;
			}

			if (!priceEntry?.bulkPricing || priceEntry?.hasTierPrice) {
				await patchPriceEntry(
					{
						bulkPricing: true,
						hasTierPrice: false,
					},
					priceEntry?.priceEntryId
				);
			}

			if (sku?.sku !== 'TRIAL' || sku?.sku.endsWith('ts')) {
				await processTier(priceEntry);
			}
		}
	};

	const submitLicensePrice = async () => {
		const skusJSON = await getProductIdSkusPage(appProductId);

		for (const sku of skusJSON?.items) {
			await handlePostPriceEntryIdTierPrice(sku);
			await patchSKUById(sku?.id, {
				...sku,
				price: getSkuPrice(appLicensePrice, sku),
			});
		}
	};

	return (
		<div className="informing-licensing-terms-page-container">
			<Header
				description="Define the licensing approach for your app. This will impact users' licensing renew experience."
				title="Select licensing terms"
			/>

			<Section
				className="mb-6"
				label="Standard License prices"
				required
				tooltip="Standard licenses cover the following DXP environments: production, non-production (UAT) and backup (DR) for both standalone and virtual cluster servers."
				tooltipText="More Info"
			>
				<LicensePriceCard
					licensePrices={appLicensePrice.standard}
					onAdd={() => handleAddPriceTier(LicenseTier.STANDARD)}
					onChange={(index: number, price: LicensePrice) => {
						handleEditPriceTier(LicenseTier.STANDARD, index, price);
					}}
					onDelete={(key: number) =>
						handleDeletePriceTier(LicenseTier.STANDARD, key)
					}
				/>
			</Section>

			<Section
				label="Developer License prices"
				tooltip="Developer licenses are limited to 5 unique addresses and should not be used for full scale production deployments."
				tooltipText="More Info"
			>
				{appLicensePrice.developer.length ? (
					<LicensePriceCard
						licensePrices={appLicensePrice.developer}
						onAdd={() => handleAddPriceTier(LicenseTier.DEVELOPER)}
						onChange={(index: number, price: LicensePrice) =>
							handleEditPriceTier(
								LicenseTier.DEVELOPER,
								index,
								price
							)
						}
						onDelete={(key: number) =>
							handleDeletePriceTier(LicenseTier.DEVELOPER, key)
						}
					/>
				) : (
					<IconButton
						className="icon-button py-3 w-100"
						onClick={() =>
							handleAddPriceTier(LicenseTier.DEVELOPER)
						}
					>
						Add Developer Licenses
					</IconButton>
				)}
			</Section>

			<NewAppPageFooterButtons
				disableContinueButton={!appLicensePrice}
				onClickBack={() => onClickBack()}
				onClickContinue={async () => {
					await submitLicensePrice();

					onClickContinue();
				}}
				showBackButton
			/>
		</div>
	);
}
