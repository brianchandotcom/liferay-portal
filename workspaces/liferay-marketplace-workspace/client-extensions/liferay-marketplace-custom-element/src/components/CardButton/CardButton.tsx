/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {MouseEvent} from 'react';

import arrowLeft from '../../assets/icons/guide_icon.svg';

import './CardButton.scss';

export function CardButton({
	description,
	disabled,
	onClick,
	selected,
	title,
}: {
	description: string;
	disabled: boolean;
	onClick: (event: MouseEvent) => void;
	selected: boolean;
	title: string;
}) {
	return (
		<div
			className={classNames('card-button', {
				'card-button--disabled': disabled,
				'card-button--selected': selected,
			})}
			onClick={onClick}
		>
			<img alt="trial" className="card-button-icon" src={arrowLeft} />

			<div className="card-button-info">
				<div className="card-button-title">
					<div className="card-button-text">{title}</div>

					<div className="card-button-description">{description}</div>
				</div>
			</div>
		</div>
	);
}
