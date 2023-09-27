/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';

import {FieldBase} from '../FieldBase';

import './Section.scss';

interface SectionProps {
	children: ReactNode;
	className?: string;
	description?: string;
	disabled?: boolean;
	label?: string;
	required?: boolean;
	tooltip?: string;
	tooltipText?: string;
}

export function Section({
	children,
	className,
	description,
	disabled = false,
	label,
	required = false,
	tooltip,
	tooltipText,
}: SectionProps) {
	return (
		<FieldBase
			className={className}
			description={description}
			label={label}
			required={required}
			tooltip={tooltip}
			tooltipText={tooltipText}
		>
			{!disabled && <div className="section-divider"></div>}

			{children}
		</FieldBase>
	);
}
