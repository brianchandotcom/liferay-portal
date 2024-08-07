/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {useEffect, useState} from 'react';

export interface Dates {
	endDate: string;
	startDate: string;
}

interface Years {
	endYear: string;
	startYear: string;
}

interface IProps {
	children?: JSX.Element | JSX.Element[];
	clearInputs?: {dates: Dates};
	closeFilterMenu: () => void;
	filterDescription?: string;
	initialValues?: Dates;
	updateFilter: (dates: Dates) => void;
	years?: Years;
}

const DateFilter = ({
	children,
	clearInputs,
	closeFilterMenu,
	filterDescription,
	initialValues,
	updateFilter,
	years,
}: IProps) => {
	const [startActivityDate, setStartActivityDate] = useState(
		initialValues?.startDate ? initialValues?.startDate : ''
	);
	const [endActivityDate, setEndActivityDate] = useState(
		initialValues?.endDate ? initialValues?.endDate : ''
	);

	const filterDescriptionFormated = filterDescription
		? filterDescription + ' '
		: '';

	useEffect(() => {
		if (
			!clearInputs?.dates?.startDate?.length &&
			!clearInputs?.dates?.endDate?.length
		) {
			setStartActivityDate('');
			setEndActivityDate('');
		}
	}, [clearInputs]);

	return (
		<div className="p-3 w-100">
			<div className="font-weight-semi-bold mb-3 text-paragraph">
				{filterDescriptionFormated}
				On Or After
				<ClayInput
					id="basicInputText"
					max={years?.endYear}
					min={years?.startYear}
					onChange={(event) => {
						setStartActivityDate(event.target.value);
					}}
					type="date"
					value={startActivityDate}
				/>
			</div>

			<div className="font-weight-semi-bold mb-3 text-paragraph">
				{filterDescriptionFormated}
				On Or Before
				<ClayInput
					id="basicInputText"
					max={years?.endYear}
					min={years?.startYear}
					onChange={(event) => {
						setEndActivityDate(event.target.value);
					}}
					type="date"
					value={endActivityDate}
				/>
			</div>

			{children}

			<Button
				className="w-100"
				onClick={() => {
					updateFilter({
						endDate: endActivityDate,
						startDate: startActivityDate,
					});

					closeFilterMenu();
				}}
				size="sm"
			>
				Apply
			</Button>
		</div>
	);
};
export default DateFilter;
