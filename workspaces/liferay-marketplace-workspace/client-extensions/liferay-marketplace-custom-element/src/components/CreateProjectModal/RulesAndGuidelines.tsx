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

import ClayAlert from '@clayui/alert';
import ClayLink from '@clayui/link';

function getClayLink(href: string, text: string) {
	return (
		<ClayLink
			decoration="underline"
			href={href}
			style={{color: '#6F3000', cursor: 'pointer'}}
			target="_blank"
			weight="semi-bold"
		>
			{text}
		</ClayLink>
	);
}

export function RulesAndGuidelines() {
	return (
		<>
			<div className="create-project-modal-rules-container">
				<strong className="create-project-modal-rules-text-bold">
					This service has the following rules and guidelines:
				</strong>

				<span className="create-project-modal-rules-text">
					· Projects are automatically managed from creation to
					deletion and cleanup. <br />
					· Projects are only available for creation by approved
					publishers in the Marketplace. <br />
					· Accounts take approximately 90-180 minutes to be
					provisioned. You will be notified by email with details on
					how to access it. <br />
					· Each publisher account is allowed to have a maximum of two
					projects simultaneously. <br />· If you need more than two,
					please contact us at <a>marketplace-admin@liferay.com</a>
				</span>
			</div>

			<ClayAlert
				displayType="warning"
				style={{
					border: 'none',
				}}
				title="Terms & Conditions for Customer Data Upload:"
			>
				If a project is shared with a customer, they must be explicitly
				informed not to upload their data. If a customer needs to upload
				data, they are required to sign an Order Form and agree to the
				Liferay Cloud{' '}
				{getClayLink(
					'https://web.liferay.com/legal/doc/app4/1301911_NA?_ga=2.203072209.1745225301.1613503864-1493387283.1612984613',
					'Appendix 4'
				)}{' '}
				Terms and Conditions which includes an agreement to the{' '}
				{getClayLink(
					'https://web.liferay.com/legal/doc/DPA/1001810',
					'Data Processing Addendum.'
				)}{' '}
				Please reach out to your Account Executive and request them to
				create and have the customer sign a zero price Order Form.
			</ClayAlert>
		</>
	);
}
