/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
