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

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import classNames from 'classnames';
import {ReactNode} from 'react';

import arrowDown from '../assets/icons/arrow_down_icon.svg';
import asteriskIcon from '../assets/icons/asterisk_icon.svg';

import './FieldBase.scss';
import {Tooltip} from './Tooltip/Tooltip';

export function RequiredMask() {
	return (
		<>
			<span className="field-base-required-asterisk">
				<img
					className="field-base-required-asterisk-icon"
					src={asteriskIcon}
				/>
			</span>

			<span className="hide-accessible sr-only">Mandatory</span>
		</>
	);
}

interface FieldBaseProps {
	children?: ReactNode;
	className?: string;
	description?: string;
	disabled?: boolean;
	errorMessage?: string;
	helpMessage?: string;
	hideFeedback?: boolean;
	id?: string;
	label?: string;
	localized?: boolean;
	localizedTooltipText?: string;
	required?: boolean;
	tooltip?: string;
	tooltipText?: string;
	warningMessage?: string;
}

export function FieldBase({
	children,
	className,
	description,
	disabled,
	errorMessage,
	helpMessage,
	hideFeedback,
	id,
	label,
	localized,
	localizedTooltipText,
	required,
	tooltip,
	tooltipText,
	warningMessage,
}: FieldBaseProps) {
	return (
		<ClayForm.Group
			className={classNames(className, {
				'has-error': errorMessage,
				'has-warning': warningMessage && !errorMessage,
			})}
		>
			<div className="field-base-container">
				<div className="field-base-container_label">
					{label && (
						<label className={classNames({disabled})} htmlFor={id}>
							{label}

							{required && <RequiredMask />}
						</label>
					)}

					{tooltip && (
						<div className="field-base-tooltip">
							<Tooltip
								tooltip={tooltip}
								tooltipText={tooltipText}
							/>
						</div>
					)}
				</div>

				{localized && (
					<div className="field-base-localized-field">
						<ClayButton displayType={null}>
							English (US)
							<img className="arrow-down-icon" src={arrowDown} />
						</ClayButton>

						<>
							&nbsp;
							<Tooltip
								tooltip={
									localizedTooltipText ?? 'choose a language'
								}
								tooltipText={tooltipText}
							/>
						</>
					</div>
				)}
			</div>

			{description && (
				<span className="field-base-description">{description}</span>
			)}

			{children}

			{!hideFeedback && helpMessage && (
				<div className="field-base-feedback">{helpMessage}</div>
			)}
		</ClayForm.Group>
	);
}
