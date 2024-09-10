import {Section} from '../../../../../../components/Section/Section';
import {
	NewAppTypes,
	useNewAppContext,
} from '../../../../../../context/NewAppContext';
import {LicenseTier} from '../../../../../../enums/licenseTier';
import {TYPES} from '../../../Apps/AppCreationFlow/AppContext/actionTypes';
import {LicensePrice} from '../../../Apps/AppCreationFlow/AppContext/AppManageState';
import IconButton from '../../../Apps/AppCreationFlow/InformLicensingTermsPage/components/IconButton';
import LicensePriceCard from '../../../Apps/AppCreationFlow/InformLicensingTermsPage/components/LicensePriceCard';

const LicensePrices = () => {
	const [
		{
			licensing: {
				prices: {developer: developerPrices, standard: standardPrices},
			},
			build: {cloudCompatible},
		},
		dispatch,
	] = useNewAppContext();

	const handleAddPriceTier = (licenseTier: LicenseTier) =>
		dispatch({
			payload: {licenseTier},
			type: NewAppTypes.SET_LICENSING_ADD_PRICE,
		});

	const handleDeletePriceTier = (licenseTier: LicenseTier, key: number) =>
		dispatch({
			payload: {
				key,
				licenseTier,
			},
			type: NewAppTypes.SET_LICENSING_DELETE_PRICE,
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
			type: NewAppTypes.SET_LICENSING_UPDATE_PRICES,
		});

	return (
		<div className="informing-licensing-terms-page-container">
			<Section
				className="mb-6"
				label="Standard License prices"
				required
				tooltip="Standard licenses cover the following DXP environments: production, non-production (UAT) and backup (DR) for both standalone and virtual cluster servers."
				tooltipText="More Info"
			>
				<LicensePriceCard
					licensePrices={standardPrices}
					onAdd={() => handleAddPriceTier(LicenseTier.STANDARD)}
					onChange={(index: number, price: LicensePrice) => {
						handleEditPriceTier(LicenseTier.STANDARD, index, price);
					}}
					onDelete={(key: number) =>
						handleDeletePriceTier(LicenseTier.STANDARD, key)
					}
				/>
			</Section>

			{!cloudCompatible && (
				<Section
					label="Developer License prices"
					tooltip="Developer licenses are limited to 5 unique addresses and should not be used for full scale production deployments."
					tooltipText="More Info"
				>
					{developerPrices.length ? (
						<LicensePriceCard
							licensePrices={developerPrices}
							onAdd={() =>
								handleAddPriceTier(LicenseTier.DEVELOPER)
							}
							onChange={(index: number, price: LicensePrice) =>
								handleEditPriceTier(
									LicenseTier.DEVELOPER,
									index,
									price
								)
							}
							onDelete={(key: number) =>
								handleDeletePriceTier(
									LicenseTier.DEVELOPER,
									key
								)
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
			)}
		</div>
	);
};

export default LicensePrices;
