/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import LiferayAccountBrief from '../interfaces/liferayAccountBrief';
import LiferayPicklist from '../interfaces/liferayPicklist';
import useGetAccountByERC from '../services/liferay/accounts/useGetAccountByERC';
import useGetPartnerLevel from '../services/liferay/object/partner-level/useGetPartnerLevel';
import isObjectEmpty from '../utils/isObjectEmpty';

export default function useCompanyOptions(
	handleSelected: (
		partnerCountry: LiferayPicklist,
		company: LiferayAccountBrief,
		currency: LiferayPicklist,
		claimPercent: number
	) => void,
	companyOptions?: React.OptionHTMLAttributes<HTMLOptionElement>[],
	currencyOptions?: React.OptionHTMLAttributes<HTMLOptionElement>[],
	currentCurrency?: LiferayPicklist,
	countryOptions?: React.OptionHTMLAttributes<HTMLOptionElement>[],
	currentCountry?: LiferayPicklist,
	currentCompany?: LiferayAccountBrief
) {
	const [selectedAccountBrief, setSelectedAccountBrief] = useState<
		LiferayAccountBrief | undefined
	>(currentCompany);

	const {data: account} = useGetAccountByERC(
		selectedAccountBrief?.externalReferenceCode
	);

	const {data: partnerLevel} = useGetPartnerLevel(
		account?.r_prtLvlToAcc_c_partnerLevelERC
	);

	const currencyPicklist =
		account &&
		currencyOptions &&
		currencyOptions.find((options) => options.value === account.currency);

	const countryPicklist =
		account &&
		countryOptions &&
		countryOptions.find(
			(options) => options.value === account.partnerCountry
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
					: (countryPicklist && {
							key: countryPicklist.value as string,
							name: countryPicklist.label as string,
					  }) ||
							{},
				selectedAccountBrief,
				currentCurrency && !isObjectEmpty(currentCurrency)
					? currentCurrency
					: (currencyPicklist && {
							key: currencyPicklist.value as string,
							name: currencyPicklist.label as string,
					  }) ||
							{},
				partnerLevel?.claimPercent || 0
			);
		}
	}, [
		account?.externalReferenceCode,
		countryPicklist,
		currencyPicklist,
		currentCountry,
		currentCurrency,
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
