/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './CustomerGate.scss';

import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import {useState} from 'react';

import magnifyingGlass from '../../assets/images/magnifying_glass.svg';
import menu from '../../assets/images/menu.svg';
import {GateCard} from '../../components/Card/GateCard';
import {Header} from '../../components/Header/Header';
import {useMarketplaceContext} from '../../context/MarketplaceContext';
import withProviders from '../../hoc/withProviders';
import i18n from '../../i18n';
import CreateCustomerAccountForm from './CustomerForm';

type Steps = {
	page: 'onboarding' | 'customerGateForm';
};

function CustomerGate() {
	const [step, setStep] = useState<Steps>({page: 'onboarding'});
	const {myUserAccount} = useMarketplaceContext();

	if (step.page === 'onboarding') {
		return (
			<div className="customer-gate-page-container">
				<div className="customer-gate-page-body">
					<Header
						description={i18n.translate(
							'we-are-happy-to-have-you-interested-in-the-liferay-marketplace-at-the-moment-we-are-working-on-enhancing-the-experience-for-our-customers-in-the-marketplace-and-access-is-invite-only-if-you-are-an-existing-liferay-customer-please-keep-an-eye-out-for-an-announcement-related-to-the-new-marketplace-in-the-coming-months'
						)}
						title={i18n.translate(
							'becoming-a-liferay-marketplace-customer'
						)}
					/>

					<GateCard
						description={i18n.translate(
							'explore-over-800-apps-available-in-the-liferay-marketplace-from-a-variety-of-publishers-apps-allow-you-to-accelerate-your-liferay-development-get-to-market-faster'
						)}
						image={{
							description: 'Magnifying Glass',
							svg: magnifyingGlass,
						}}
						title={i18n.translate('discover-and-customize')}
					/>

					<GateCard
						description={i18n.translate(
							'manage-all-your-app-purchases-and-subscriptions-in-one-place-read-other-users-reviews-get-notifications-when-updates-are-available-and-get-the-most-out-of-our-apps-catalog'
						)}
						image={{
							description: 'Menu ',
							svg: menu,
						}}
						title={i18n.translate(
							'manage-all-your-apps-in-one-place'
						)}
					/>

					<hr className="customer-gate-page-divider" />

					<div className="customer-gate-page-button-container">
						<ClayButton
							className="customer-gate-page-button"
							onClick={() => setStep({page: 'customerGateForm'})}
						>
							{i18n.translate('get-started')}
						</ClayButton>

						<div>
							<ClayLink
								className="customer-gate-page-link"
								href="/c/portal/login"
							>
								{i18n.translate(
									'learn-more-about-becoming-a-liferay-customer'
								)}
							</ClayLink>
						</div>
					</div>
				</div>
			</div>
		);
	}

	return <CreateCustomerAccountForm setStep={setStep} user={myUserAccount} />;
}

export default withProviders(CustomerGate);
