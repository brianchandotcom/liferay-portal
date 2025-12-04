/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Component, ComponentProps, Fragment} from 'react';
import {HashRouter} from 'react-router-dom';

import {Breadcrumbs} from '../components/Breadcrumb/Breadcrumb';
import ErrorBoundary from '../components/ErrorBoundary';
import Providers from '../providers';
import {MarketplaceProperties} from '../utils/attributes';

/**
 * @description due the lazy rendering, the context needs to be initialized
 * after the lazy component is loaded, this needs to be imported
 * to each lazy rendered component
 * @param WrappedComponent
 */

export default function withProviders<T extends object>(
	WrappedComponent: React.ComponentType<T>,
	properties?: {
		breadcrumbProps?: ComponentProps<typeof Breadcrumbs>;
		withBreadcrumbs?: boolean;
		withErrorBoundary?: boolean;
		withHashRouter?: boolean;
	}
) {
	return class extends Component<T & {properties: MarketplaceProperties}> {
		render() {
			const {withBreadcrumbs, withHashRouter} = properties ?? {};
			const HashRouterWrapper = withHashRouter ? HashRouter : Fragment;

			const Wrapper = properties?.withErrorBoundary
				? ErrorBoundary
				: Fragment;

			return (
				<Wrapper>
					<Providers properties={this.props.properties}>
						<HashRouterWrapper>
							{withBreadcrumbs && withHashRouter && (
								<Breadcrumbs {...properties?.breadcrumbProps} />
							)}

							<WrappedComponent {...this.props} />
						</HashRouterWrapper>
					</Providers>
				</Wrapper>
			);
		}
	};
}
