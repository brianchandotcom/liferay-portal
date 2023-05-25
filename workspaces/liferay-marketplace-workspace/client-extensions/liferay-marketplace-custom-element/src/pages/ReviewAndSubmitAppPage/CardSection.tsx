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
import {ReactNode} from 'react';

import arrowDown from '../../assets/icons/arrow_down_icon.svg';
import {RequiredMask} from '../../components/FieldBase';
import {Tooltip} from '../../components/Tooltip/Tooltip';

import './CardSection.scss';

interface CardSectionProps {
	children: ReactNode;
	enableEdit?: boolean;
	localized?: boolean;
	required?: boolean;
	sectionName?: string;
}

export function CardSection({
	children,
	enableEdit = true,
	localized,
	required,
	sectionName,
}: CardSectionProps) {
	return (
		<div className="card-section-body-section">
			<div className="card-section-body-section-header">
				<span className="card-section-body-section-header-title">
					{sectionName}

					{required && <RequiredMask />}
				</span>

				<div className="card-section-body-section-header-actions">
					{localized && (
						<div className="field-base-localized-field">
							<ClayButton displayType={null}>
								English (US)
								<img
									className="arrow-down-icon"
									src={arrowDown}
								/>
							</ClayButton>

							<>
								&nbsp;
								<Tooltip tooltip="choose a language" />
							</>
						</div>
					)}

					{enableEdit && (
						<ClayButton className="edit-button" displayType={null}>
							Edit
						</ClayButton>
					)}
				</div>
			</div>

			{children}
		</div>
	);
}
