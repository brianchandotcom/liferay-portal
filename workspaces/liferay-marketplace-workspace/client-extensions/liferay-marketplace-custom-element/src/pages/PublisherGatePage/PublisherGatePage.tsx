/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';

import cash from '../../assets/images/cash.svg';
import cloudUpload from '../../assets/images/cloud_upload.svg';
import {GateCard} from '../../components/Card/GateCard';
import {Header} from '../../components/Header/Header';

import './PublisherGatePage.scss';

import ClayLink from '@clayui/link';

export function PublisherGatePage() {
	return (
		<div className="publisher-gate-page-container">
			<div className="publisher-gate-page-body">
				<Header
					description="We are happy to have you interested in the Liferay Marketplace. At the moment, we are working on enhancing the experience for our publishers in the Marketplace and access is invite only. If you are an existing Liferay developer or partner, please keep an eye out for an announcement related to the new Marketplace in the coming months!"
					title="Becoming a Liferay Marketplace Publisher"
				/>

				<GateCard
					description="The Liferay Marketplace is the premier place for Liferay customers to find pre-built, pre-approved app extensions to quickly extend the Liferay platform to new and legacy technologies."
					image={{
						description: 'Cloud Upload',
						svg: cloudUpload,
					}}
					label="Free"
					link={{
						href: '',
						label: 'Learn More',
					}}
					title="Publish Apps to the Liferay Marketplace"
				/>

				<GateCard
					description="The Liferay Marketplace gives you the opportunity to monetize your app or solutions from a single use case to many, while engaging with new customer opportunities and generating ongoing revenue."
					image={{
						description: 'Cash',
						svg: cash,
					}}
					link={{
						href: '',
						label: 'Learn More',
					}}
					title="Monetize Your Apps and Solutions"
				/>

				<hr className="publisher-gate-page-divider" />

				<div className="publisher-gate-page-button-container">
					<ClayButton
						className="publisher-gate-page-button"
						onClick={() => {
							window.location.href =
								'https://marketplace.liferay.com/';
						}}
					>
						Go Back to Marketplace
					</ClayButton>

					<ClayLink className="publisher-gate-page-link" href="">
						Learn More About Becoming a Liferay Publisher
					</ClayLink>
				</div>
			</div>
		</div>
	);
}
