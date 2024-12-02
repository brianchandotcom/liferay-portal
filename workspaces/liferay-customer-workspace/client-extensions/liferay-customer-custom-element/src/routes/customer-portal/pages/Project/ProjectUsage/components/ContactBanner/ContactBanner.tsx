/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import './ContactBanner.css';

import i18n from '~/common/I18n';
import BannerWaves from '~/common/icons/banner-waves';
import {useCustomerPortal} from '~/routes/customer-portal/context';

interface IProps {
	className?: string;
	description?: string;
	title: string;
}

const ContactBanner: React.FC<IProps> = ({className, description, title}) => {
	const [{project}] = useCustomerPortal();

	return (
		<div
			className={`contact-banner overflow-hidden position-relative p-5 ${className}`}
		>
			<div className="align-items-center justify-content-between m-0 row">
				<div className="col-8 title-container">
					<h2 className="mb-4">{title}</h2>

					<p className="m-0">{description}</p>
				</div>

				<div className="col-3 contact-container overflow-hidden p-3 rounded">
					<p className="mb-3 small">
						{i18n.translate('account-manager')}
					</p>

					{project?.liferayContactName && (
						<p className="m-0 manager-name">
							{project?.liferayContactName}
						</p>
					)}

					<p className="m-0">
						{project?.liferayContactEmailAddress ||
							'sales@liferay.com'}
					</p>
				</div>
			</div>

			<div className="align-items-end d-flex justify-content-end position-absolute second-gradient">
				<BannerWaves />
			</div>
		</div>
	);
};

export default ContactBanner;
