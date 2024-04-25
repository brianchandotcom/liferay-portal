/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CheckboxCard} from '../../../../../../../components/CheckboxCard/CheckboxCard';

type OfferingTypeCheckboxProps = {
	handleSelectCheckbox: (label: string) => void;
	offeringTypes: OfferingType[];
	selectedValue: string[];
};

export default function OfferingTypeCheckbox({
	handleSelectCheckbox,
	offeringTypes,
	selectedValue,
}: OfferingTypeCheckboxProps) {
	return (
		<>
			{offeringTypes?.map((type) => (
				<CheckboxCard
					checked={selectedValue.includes(type?.label)}
					description={type.description}
					disabled={type.disabled}
					key={type.label}
					label={type.label}
					onChange={handleSelectCheckbox}
				/>
			))}
		</>
	);
}
