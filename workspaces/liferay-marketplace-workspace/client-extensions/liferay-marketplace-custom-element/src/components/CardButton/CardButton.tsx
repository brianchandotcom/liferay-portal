/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {MouseEvent, ReactNode} from 'react';

import './CardButton.scss';

import ClayIcon from '@clayui/icon';

import {GetAppStepTypes} from '../../pages/GetApp/enums/GetAppStepTypes';

export function CardButton({
	description,
	disabled,
	icon = '',
	iconRight,
	onClick,
	selected,
	step,
	title,
}: {
	description: string;
	disabled?: boolean;
	icon?: ReactNode;
	iconRight?: boolean;
	onClick: (event: MouseEvent) => void;
	selected: boolean;
	step?: GetAppStepTypes;
	title: string;
}) {
	return (
		<div
			className={classNames('card-button d-flex', {
				'card-button--disabled': disabled,
				'card-button--selected': selected,
			})}
			onClick={disabled ? () => {} : onClick}
		>
			{step === GetAppStepTypes.PAYMENT ? (
				<ClayIcon
					aria-label="trial"
					className="card-button-icon"
					symbol={icon as string}
				/>
			) : (
				!iconRight &&
				(icon ? (
					icon
				) : (
					<ClayIcon
						aria-label="sites-icon"
						className="card-button-icon"
						symbol="sites"
					/>
				))
			)}

			<div className="card-button-info">
				<div className="card-button-title">
					<div
						className={classNames('card-button-text', {
							'icon-right': iconRight,
						})}
					>
						{title}
						{step !== GetAppStepTypes.PAYMENT && iconRight && icon}
					</div>

					<small
						className={classNames({
							'card-button-description':
								step === GetAppStepTypes.PAYMENT,
							'card-button-description-paid':
								step === GetAppStepTypes.LICENSES,
						})}
					>
						{description}
					</small>
				</div>
			</div>
		</div>
	);
}

export default CardButton;
