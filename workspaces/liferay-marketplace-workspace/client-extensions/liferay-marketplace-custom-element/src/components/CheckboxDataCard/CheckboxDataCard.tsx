/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
