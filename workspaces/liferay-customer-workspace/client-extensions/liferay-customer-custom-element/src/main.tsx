/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {ClayIconSpriteContext} from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React from 'react';
import {Root, createRoot} from 'react-dom/client';
import {SWRConfig} from 'swr';

import './common/styles/global.scss';
import {AppPropertiesContext} from './common/contexts/AppPropertiesContext';
import useApollo from './common/hooks/useApollo';
import useGlobalNetworkIndicator from './common/hooks/useGlobalNetworkIndicator';
import {Liferay} from './common/services/liferay';
import getIconSpriteMap from './common/utils/getIconSpriteMap';
import swrCacheProvider from './common/utils/swrCacheProvider';
import AttachmentFileUploader from './routes/attachment-uploader';
import CustomerPortal from './routes/customer-portal';
import Home from './routes/home';
import Onboarding from './routes/onboarding';
import SecurityVulnerabilities from './routes/security-vulnerabilities';

const ELEMENT_ID = 'liferay-customer-custom-element';

const AppRoutes = {
	attachmentFileUploader: AttachmentFileUploader,
	home: Home,
	onboarding: Onboarding,
	portal: CustomerPortal,
	securityVulnerabilities: SecurityVulnerabilities,
};

type Properties = {
	accountSettingsURL: string | null;
	articleAccountSupportURL: string | null;
	articleDeactivateKey: string | null;
	articleDeployingActivationKeysURL: string | null;
	articleGettingStartedWithLiferayEnterpriseSearchURL: string | null;
	articleNotifiedWhenMyActivationKeyIsAboutToExpireURL: string | null;
	articleWhatIsMyInstanceSizingValueURL: string | null;
	featureFlags?: string[];
	importDate?: Date | null;
	submitSupportTicketURL: string | null;
	theOverviewPageURL: string | null;
};

type APIs = {
	gravatarAPI: string | null;
	provisioningServerAPI: string | null;
};

type CustomerPortalAppProps = {
	apis: APIs;
	route: string;
} & Properties;

const CustomerPortalApp: React.FC<CustomerPortalAppProps> = ({
	apis,
	route,
	...properties
}) => {
	const {client, networkStatus} = useApollo(apis.provisioningServerAPI);

	useGlobalNetworkIndicator(networkStatus);

	if (!client) {
		return <ClayLoadingIndicator />;
	}

	const AppRouteComponent = (AppRoutes as any)[route];

	return (
		<ApolloProvider client={client}>
			<AppPropertiesContext.Provider
				value={
					{
						...properties,
						...apis,
						client,
					} as any
				}
			>
				{properties.featureFlags?.includes('LPS-192494')}

				<AppRouteComponent />
			</AppPropertiesContext.Provider>
		</ApolloProvider>
	);
};

class CustomerPortalWebComponent extends HTMLElement {
	private root: Root | undefined;

	connectedCallback() {
		const properties = {
			accountSettingsURL: super.getAttribute('account-settings-url'),
			articleAccountSupportURL: super.getAttribute(
				'article-account-support-url'
			),
			articleDeactivateKey: super.getAttribute(
				'article-deactivate-key-url'
			),
			articleDeployingActivationKeysURL: super.getAttribute(
				'article-deploying-activation-keys-url'
			),
			articleGettingStartedWithLiferayEnterpriseSearchURL:
				super.getAttribute(
					'article-getting-started-with-liferay-enterprise-search-url'
				),
			articleNotifiedWhenMyActivationKeyIsAboutToExpireURL:
				super.getAttribute(
					'article-notified-when-my-activation-key-is-about-to-expire-url'
				),
			articleWhatIsMyInstanceSizingValueURL: super.getAttribute(
				'article-what-is-my-instance-sizing-value-url'
			),
			featureFlags: (super.getAttribute('feature-flags') ?? '')
				.split(',')
				.map((featureflag) => featureflag.trim()),
			importDate: super.getAttribute('import-date')
				? new Date(super.getAttribute('import-date') as string)
				: undefined,
			submitSupportTicketURL: super.getAttribute(
				'submit-support-ticket-url'
			),
			theOverviewPageURL: super.getAttribute(
				'about-the-overview-page-url'
			),
		};

		if (
			!properties.featureFlags.includes('LPS-153478') &&
			(Liferay.FeatureFlags as any)['LPS-153478']
		) {
			properties.featureFlags.push('LPS-153478');
		}

		const apis = {
			gravatarAPI: super.getAttribute('gravatar-api'),
			provisioningServerAPI: super.getAttribute(
				'provisioning-server-api'
			),
		};

		if (!this.root) {
			this.root = createRoot(this);

			this.root.render(
				<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
					<SWRConfig
						value={{
							provider: swrCacheProvider,
							revalidateOnFocus: false,
						}}
					>
						<CustomerPortalApp
							{...properties}
							apis={apis}
							route={super.getAttribute('route') as string}
						/>
					</SWRConfig>
				</ClayIconSpriteContext.Provider>
			);
		}
	}
}

if (!customElements.get(ELEMENT_ID)) {
	customElements.define(ELEMENT_ID, CustomerPortalWebComponent);
}
