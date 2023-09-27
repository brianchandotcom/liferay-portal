/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
