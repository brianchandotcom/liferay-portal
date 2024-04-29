/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';

import './CurrencySelector.scss';

import ClayIcon from '@clayui/icon';
import {useState} from 'react';

type CurrencySelectorProps = {
	onChange: (currency: string) => void;
};

const CURRENCY_SELECTOR_OPTIONS = [
	{
		label: (
			<span className="align-items-center d-flex">
				<span className="mr-1">USD</span>
				<ClayIcon className="currency-selector-icon" symbol="en-us" />
			</span>
		),
		value: 'USD',
	},
];

const CurrencySelector: React.FC<CurrencySelectorProps> = ({onChange}) => {
	const [selectedOption, setSelectedOption] = useState(
		CURRENCY_SELECTOR_OPTIONS[0]
	);

	return (
		<ClayDropDown
			filterKey="name"
			trigger={
				<ClayButton
					className="align-items-center currency-selector d-flex p-0"
					displayType={null}
				>
					<div className="mr-1">{selectedOption.label}</div>
					<ClayIcon className="m-0" symbol="caret-bottom" />
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList>
				{CURRENCY_SELECTOR_OPTIONS.map((item) => (
					<ClayDropDown.Item
						key={item.value}
						onClick={() => {
							onChange(item.value);
							setSelectedOption(item);
						}}
					>
						{item.label}
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

export default CurrencySelector;
