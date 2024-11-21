/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode} from 'react';
import PopoverIconButton from '~/routes/customer-portal/components/PopoverIconButton';

import './CardContainer.css';

interface IProps {
	children?: ReactNode;
	className?: string;
	classNameCard?: string;
	infoButtonText?: string;
}

const CardContainer: React.FC<IProps> = ({
	children,
	className,
	classNameCard,
	infoButtonText,
}) => {
	return (
		<div className={`col-12 col-md-6 col-xl-4 mb-3 ${className}`}>
			<div
				className={`card-container px-3 py-4 position-relative rounded ${classNameCard}`}
			>
				{infoButtonText && (
					<div className="align-items-center d-flex info-button justify-content-center position-absolute">
						<PopoverIconButton
							iconSize="xs"
							isSubscriptionCard
							popoverText={infoButtonText}
						/>
					</div>
				)}

				{children}
			</div>
		</div>
	);
};

export default CardContainer;
