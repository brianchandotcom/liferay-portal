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
