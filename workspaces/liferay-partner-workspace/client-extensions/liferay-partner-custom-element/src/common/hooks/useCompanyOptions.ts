/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useState} from 'react';

import AccountEntry from '../interfaces/accountEntry';
import Currency from '../interfaces/currency';
import LiferayAccountBrief from '../interfaces/liferayAccountBrief';
import LiferayPicklist from '../interfaces/liferayPicklist';
import PartnerLevel from '../interfaces/partnerLevel';
import {LiferayAPIs} from '../services/liferay/common/enums/apis';
import LiferayItems from '../services/liferay/common/interfaces/liferayItems';
import useGet from '../services/liferay/object/useGet';
import isObjectEmpty from '../utils/isObjectEmpty';

export default function useCompanyOptions(
	handleSelected: (
		partnerCountries: LiferayPicklist[],
		company: LiferayAccountBrief,
		currency: LiferayPicklist,
		currencyExchangeRate: number,
		claimPercent: number
	) => void,
	companyOptions?: React.OptionHTMLAttributes<HTMLOptionElement>[],
	currencyOptions?: React.OptionHTMLAttributes<HTMLOptionElement>[],
	currentCurrency?: LiferayPicklist,
	currentCurrencyExchangeRate?: number,
	countryOptions?: React.OptionHTMLAttributes<HTMLOptionElement>[],
	currentCountry?: LiferayPicklist[],
	currentCompany?: LiferayAccountBrief
) {
	const [selectedAccountBrief, setSelectedAccountBrief] = useState<
		LiferayAccountBrief | undefined
	>(currentCompany);

	const {data: account} = useGet<AccountEntry>(
		selectedAccountBrief?.externalReferenceCode &&
			`/o/${LiferayAPIs.HEADERLESS_ADMIN_USER}/accounts/by-external-reference-code/${selectedAccountBrief.externalReferenceCode}`
	);

	const {data: datedConversionRates} = useGet<LiferayItems<Currency[]>>(
		account?.currency &&
			`/o/c/datedconversionratesfs?filter=isoCode eq '${
				account.currency
			}' and nextStartDate gt ${new Date().toISOString().split('T')[0]}`
	);

	const {data: partnerLevel} = useGet<PartnerLevel>(
		account?.r_prtLvlToAcc_c_partnerLevelERC &&
			`/o/${LiferayAPIs.OBJECT}/partnerlevels/by-external-reference-code/${account.r_prtLvlToAcc_c_partnerLevelERC}`
	);

	const formatPartnerCountries = useCallback(
		(
			company?: AccountEntry,
			countriesList?: React.OptionHTMLAttributes<HTMLOptionElement>[]
		) => {
			if (!isObjectEmpty(company) && !!countriesList?.length) {
				const splitCountries = company?.partnerCountries?.split(', ');

				const countryItems =
					splitCountries?.map((country) =>
						countriesList.find(
							(options) => options.value === country
						)
					) || [];

				return countryItems.map(
					(country) =>
						({
							key: country?.value,
							name: country?.label,
						}) as LiferayPicklist
				);
			}

			return [];
		},
		[]
	);

	const currencyPicklist =
		account &&
		currencyOptions &&
		currencyOptions.find((options) => options.value === account.currency);

	const datedConversionRate =
		account &&
		datedConversionRates &&
		datedConversionRates.items.find(
			(currency) => currency.isoCode.key === account.currency
		);

	if (!companyOptions && account) {
		companyOptions = [
			{
				defaultValue: account.id,
				label: account.name,
				value: account.externalReferenceCode,
			},
		];
	}

	useEffect(() => {
		if (!isObjectEmpty(selectedAccountBrief) && selectedAccountBrief) {
			handleSelected(
				currentCountry
					? currentCountry
					: formatPartnerCountries(account, countryOptions),
				selectedAccountBrief,
				currentCurrency && !isObjectEmpty(currentCurrency)
					? currentCurrency
					: (currencyPicklist && {
							key: currencyPicklist.value as string,
							name: currencyPicklist.label as string,
						}) ||
							{},
				currentCurrencyExchangeRate
					? currentCurrencyExchangeRate
					: (datedConversionRate &&
							datedConversionRate.conversionRate) ||
							0,
				partnerLevel?.claimPercent || 0.5
			);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [
		account?.externalReferenceCode,
		currencyPicklist,
		currentCountry,
		currentCurrency,
		currentCurrencyExchangeRate,
		datedConversionRate,
		formatPartnerCountries,
		handleSelected,
		partnerLevel?.claimPercent,
		selectedAccountBrief,
	]);

	const onCompanySelected = (event: React.ChangeEvent<HTMLInputElement>) => {
		const optionSelected = companyOptions?.find(
			(options) => options.value === event.target.value
		);

		setSelectedAccountBrief({
			externalReferenceCode: optionSelected?.value as string,
			id: optionSelected?.defaultValue as number,
			name: optionSelected?.label as string,
		});
	};

	return {
		companyOptions,
		onCompanySelected,
	};
}
