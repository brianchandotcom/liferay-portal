/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './CheckboxDataCard.scss';
import {Checkbox} from '../Checkbox/Checkbox';
import {Tooltip} from '../Tooltip/Tooltip';

interface CheckboxItem {
	checked: boolean;
	description?: string;
	label?: string;
	name: string;
}

export interface BaseCheckboxDataCard {
	checkboxItems: CheckboxItem[];
	icon: string;
	name: string;
	title: string;
	tooltip: string;
	tooltipText: string;
}
interface CheckboxDataCardProps extends BaseCheckboxDataCard {
	onChange: (cardName: string, selectedCheckboxName: string) => void;
}

export function CheckboxDataCard({
	checkboxItems,
	icon,
	name,
	onChange,
	title,
	tooltip,
	tooltipText,
}: CheckboxDataCardProps) {
	return (
		<div className="checkbox-data-card-container">
			<div className="checkbox-data-card-left-info">
				<div className="checkbox-data-card-left-info-group">
					<span className="checkbox-data-card-left-info-title">
						{title}
					</span>

					<img
						alt="Person Fill"
						className="checkbox-data-card-left-info-icon"
						src={icon}
					/>
				</div>

				<Tooltip tooltip={tooltip} tooltipText={tooltipText} />
			</div>

			<div className="checkbox-data-card-checkbox-list-container">
				{checkboxItems.map((checkboxItem) => (
					<Checkbox
						checked={checkboxItem.checked}
						description={checkboxItem.description}
						key={checkboxItem.name}
						label={checkboxItem.label}
						onChange={() => onChange(name, checkboxItem.name)}
					/>
				))}
			</div>
		</div>
	);
}
