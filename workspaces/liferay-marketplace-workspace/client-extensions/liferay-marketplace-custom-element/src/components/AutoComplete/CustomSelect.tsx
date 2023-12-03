/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
