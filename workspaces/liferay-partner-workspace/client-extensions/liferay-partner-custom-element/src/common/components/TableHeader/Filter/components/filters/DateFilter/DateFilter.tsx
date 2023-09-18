/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {useState} from 'react';

interface IProps {
	children?: JSX.Element | JSX.Element[];
	dateFilters: (dates: {endDate: string; startDate: string}) => void;
	filterDescription?: string;
}

const DateFilter = ({children, dateFilters, filterDescription}: IProps) => {
	const [startActivityDate, setStartActivityDate] = useState('');
	const [endActivityDate, setEndActivityDate] = useState('');

	return (
		<div className="p-3 w-100">
			<div className="font-weight-semi-bold pb-3 text-paragraph">
				{filterDescription}
				On Or After
				<ClayInput
					id="basicInputText"
					onChange={(event) => {
						setStartActivityDate(event.target.value);
					}}
					placeholder="mm-dd-yyyye"
					type="date"
					value={startActivityDate}
				/>
			</div>

			<div className="font-weight-semi-bold pb-3 text-paragraph">
				{filterDescription}
				On Or Before
				<ClayInput
					id="basicInputText"
					onChange={(event) => {
						setEndActivityDate(event.target.value);
					}}
					placeholder="mm-dd-yyyy"
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
