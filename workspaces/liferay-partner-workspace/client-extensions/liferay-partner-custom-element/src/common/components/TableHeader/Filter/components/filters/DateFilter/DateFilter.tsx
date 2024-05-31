/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {useEffect, useState} from 'react';

interface IProps {
	children?: JSX.Element | JSX.Element[];
	clearInputs?: {dates: {endDate: string; startDate: string}};
	dateFilters: (dates: {endDate: string; startDate: string}) => void;
	filterDescription?: string;
	initialDates?: {endDate: string; startDate: string};
	years?: {end: string; start: string};
}

const DateFilter = ({
	children,
	clearInputs,
	dateFilters,
	filterDescription,
	initialDates,
	years,
}: IProps) => {
	const [startActivityDate, setStartActivityDate] = useState(
		initialDates?.startDate ? initialDates?.startDate : ''
	);
	const [endActivityDate, setEndActivityDate] = useState(
		initialDates?.endDate ? initialDates?.endDate : ''
	);

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
			<div className="font-weight-semi-bold pb-3 text-paragraph">
				{filterDescription}
				On Or After
				<ClayInput
					id="basicInputText"
					max={years?.end}
					min={years?.start}
					onChange={(event) => {
						setStartActivityDate(event.target.value);
					}}
					type="date"
					value={startActivityDate}
				/>
			</div>

			<div className="font-weight-semi-bold pb-3 text-paragraph">
				{filterDescription}
				On Or Before
				<ClayInput
					id="basicInputText"
					max={years?.end}
					min={years?.start}
					onChange={(event) => {
						setEndActivityDate(event.target.value);
					}}
					type="date"
					value={endActivityDate}
				/>
			</div>

			{children}

			<div>
				<ClayButton
					className="w-100"
					onClick={() => {
						dateFilters({
							endDate: endActivityDate,
							startDate: startActivityDate,
						});
					}}
					small={true}
				>
					Apply
				</ClayButton>
			</div>
		</div>
	);
};
export default DateFilter;
