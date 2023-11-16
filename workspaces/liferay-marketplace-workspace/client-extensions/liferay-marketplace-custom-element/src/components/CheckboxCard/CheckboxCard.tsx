/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayCheckbox} from '@clayui/form';

import './CheckboxCard.scss';

import classNames from 'classnames';

import {Tooltip} from '../Tooltip/Tooltip';

interface CheckboxProps {
	checked: boolean;
	description: string;
	label: string;
	onChange: (label: string) => void;
	tooltip?: string;
}

export function CheckboxCard({
	checked,
	description,
	label,
	onChange,
	tooltip,
}: CheckboxProps) {
	return (
		<div
			className={classNames('checkbox-container d-flex p-3 rounded', {
				'checkbox-container-checked': checked,
			})}
		>
			<ClayCheckbox checked={checked} onChange={() => onChange(label)} />
			<div className="mx-2">
				<h4>{label}</h4>
				<p className="checkbox-container-description">{description}</p>
			</div>

			<div className="radio-card-title-tooltip">
				<Tooltip tooltip={tooltip} />
			</div>
		</div>
	);
}
