/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useModal} from '@clayui/core';

import {
	LicensingPrices,
	useNewAppContext,
} from '../../../../../../context/NewAppContext';
import i18n from '../../../../../../i18n';
import {currenciesCode} from '../../../../../../utils/currencies';
import LicensePricePanel from '../../components/LicensePricePanel';
import CurrencyModal from './components/CurrencyModal';

import './LicensePrices.scss';

const LicensePrices = () => {
	const [
		{
			licensing: {prices},
		},
	] = useNewAppContext();

	const modal = useModal();

	return (
		<div>
			{Object.entries(prices).map(([currencyCode, tierPrices], index) => (
				<LicensePricePanel
					currencyCode={currencyCode}
					key={index}
					tierPrices={tierPrices as unknown as LicensingPrices}
				/>
			))}

			{currenciesCode.length > Object.keys(prices).length && (
				<ClayButton
					className="add-currency-button btn-primary w-100"
					onClick={() => modal.onOpenChange(true)}
				>
					{i18n.translate('add-currency')}
				</ClayButton>
			)}

			{modal.open && <CurrencyModal {...modal} />}
		</div>
	);
};

export default LicensePrices;
