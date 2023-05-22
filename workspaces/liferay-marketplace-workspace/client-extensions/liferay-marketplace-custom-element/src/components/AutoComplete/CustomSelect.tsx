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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

import './CustomSelect.scss';

export const CustomSelect = React.forwardRef<
	HTMLDivElement,
	ICustomSelectProps
>(
	(
		{contentRight, disabled, onClick, placeholder, value, ...otherProps},
		forwardRef
	) => {
		return (
			<div className={classNames({'custom-select__wrapper': disabled})}>
				<div
					{...otherProps}
					className={classNames(
						'custom-select__content form-control',
						{
							'custom-select__content--disabled form-control': disabled,
						}
					)}
					onClick={disabled ? undefined : onClick}
					ref={forwardRef}
					tabIndex={0}
				>
					<span>{value || placeholder}</span>

					<div>
						{contentRight}

						<a
							className={classNames('custom-select__icon', {
								'custom-select__icon--disabled': disabled,
							})}
						>
							<ClayIcon symbol="caret-double" />
						</a>
					</div>
				</div>
			</div>
		);
	}
);

interface ICustomSelectProps extends React.HTMLAttributes<HTMLDivElement> {
	contentRight?: React.ReactNode;
	disabled?: boolean;
	value?: string;
}
