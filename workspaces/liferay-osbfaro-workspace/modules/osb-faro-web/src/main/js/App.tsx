/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloProvider} from '@apollo/client';
import {ClayIconSpriteContext} from '@clayui/icon';
import {ClayLinkContext} from '@clayui/link';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {throttle} from 'lodash';
import React, {Suspense, lazy, useEffect, useState} from 'react';
import {Provider, useSelector} from 'react-redux';
import {
	BrowserRouter as Router,
	Link,
	Route,
	Switch,
	matchPath,
	useLocation,
} from 'react-router-dom';
import {close, modalTypes, open} from '~/shared/actions/modals';
import client from '~/shared/apollo/client';
import AlertFeed from '~/shared/components/AlertFeed';
import Loading from '~/shared/components/Loading';
import ModalRenderer from '~/shared/components/ModalRenderer';
import RouteNotFound from '~/shared/components/RouteNotFound';
import ChannelProvider from '~/shared/context/channel';
import {OnboardingContext} from '~/shared/context/onboarding';
import UnassignedSegmentsProvider from '~/shared/context/unassignedSegments';
import {useFetchCurrentUser} from '~/shared/hooks/useCurrentUser';
import ErrorPage from '~/shared/pages/ErrorPage';
import store from '~/shared/store';
import {saveState} from '~/shared/store/local-storage';
import {ENABLE_ADD_TRIAL_WORKSPACE} from '~/shared/util/constants';
import {Pendo} from '~/shared/util/pendo';
import {Project} from '~/shared/util/records';
import {Routes} from '~/shared/util/router';

import BundleRouter from './route-middleware/BundleRouter';

// Workspaces

const AddWorkspace = lazy(
	() =>
		import(

			/* webpackChunkName: "AddWorkspace" */ './shared/pages/AddWorkspace'
		)
);
const SelectWorkspaceAccount = lazy(
	() =>
		import(

			/* webpackChunkName: "SelectWorkspaceAccount" */ './shared/pages/SelectWorkspaceAccount'
		)
);
const Workspaces = lazy(
	() =>
		import(/* webpackChunkName: "Workspaces" */ './shared/pages/Workspaces')
);

// WorkspaceLayer

const WorkspaceLayer = lazy(
	() =>
		import(

			/* webpackChunkName: "WorkspaceLayer" */ './shared/components/WorkspaceLayer'
		)
);

// Other

const OAuthReceive = lazy(
	() =>
		import(

			/* webpackChunkName: "OAuthReceive" */ './settings/pages/OAuthReceive'
		)
);

const RoutesContainer = ({children}: {children: React.ReactNode}) => {
	const location = useLocation();

	const matchingPath = matchPath<any>(location.pathname, {
		path: Routes.WORKSPACE_WITH_ID,
	});

	const groupId = matchingPath?.params.groupId ?? '0';

	const project: Project = useSelector<any, any>((state) =>
		state.getIn(['projects', groupId, 'data'])
	);

	const {data: currentUser, loading} = useFetchCurrentUser(groupId);

	useEffect(() => {
		if (currentUser?.id && project?.corpProjectName) {
			const pendo = new Pendo();

			pendo.initialize({currentUser, project});
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [currentUser?.id, project?.corpProjectName]);

	if (loading) {
		return <Loading />;
	}

	if ((location?.state as any)?.notFoundError) {
		return <ErrorPage />;
	}

	return children as React.ReactElement;
};

const App = () => {
	const [onboardingTriggered, setOnboardingTriggered] = useState(false);

	useEffect(() => {
		store.subscribe(throttle(() => saveState(store.getState()), 1000));
	}, []);

	const handleUserConfirmation = (
		message: string,
		callback: (confirmed: boolean) => void
	) => {
		store.dispatch(
			open(modalTypes.CONFIRMATION_MODAL, {
				cancelMessage: Liferay.Language.get('stay-on-page'),
				message,
				modalVariant: 'modal-warning',
				onClose: () => {
					callback(false);

					store.dispatch(close());
				},
				onSubmit: () => {
					callback(true);
				},
				submitButtonDisplay: 'warning',
				submitMessage: Liferay.Language.get('leave-page'),
				title: Liferay.Language.get('unsaved-changes'),
				titleIcon: 'warning-full',
			})
		);
	};

	return (
		<ApolloProvider client={client}>
			<Provider store={store}>
				<ClayIconSpriteContext.Provider value="/o/osb-faro-web/dist/sprite.svg">
					<ClayLinkContext.Provider
						value={({
							children,
							externalLink = false,
							href,
							...otherProps
						}: {
							children?: React.ReactNode;
							externalLink?: boolean;
							href?: string;
						}) => {
							if (href?.startsWith('http') || externalLink) {
								return (
									<a {...otherProps} href={href}>
										{children}
									</a>
								);
							}

							return (
								<Link {...otherProps} to={href || ''}>
									{children}
								</Link>
							);
						}}
					>
						<UnassignedSegmentsProvider>
							<OnboardingContext.Provider
								value={{
									onboardingTriggered,
									setOnboardingTriggered: () =>
										setOnboardingTriggered(true),
								}}
							>
								<ChannelProvider>
									<ClayTooltipProvider>
										<div>
											<Router
												getUserConfirmation={
													handleUserConfirmation
												}
											>
												<RoutesContainer>
													<AlertFeed />

													<ModalRenderer />

													<Suspense
														fallback={<Loading />}
													>
														<Switch>
															<BundleRouter
																data={
																	Workspaces
																}
																exact
																path={
																	Routes.BASE
																}
															/>

															<BundleRouter
																data={
																	Workspaces
																}
																exact
																path={
																	Routes.WORKSPACES
																}
															/>

															<BundleRouter
																data={
																	SelectWorkspaceAccount
																}
																exact
																path={
																	Routes.WORKSPACE_ADD
																}
															/>

															{ENABLE_ADD_TRIAL_WORKSPACE && (
																<BundleRouter
																	data={
																		AddWorkspace
																	}
																	exact
																	path={
																		Routes.WORKSPACE_ADD_TRIAL
																	}
																/>
															)}

															<BundleRouter
																data={
																	AddWorkspace
																}
																exact
																path={
																	Routes.WORKSPACE_ADD_WITH_CORP_PROJECT_UUID
																}
															/>

															<BundleRouter
																data={
																	SelectWorkspaceAccount
																}
																exact
																path={
																	Routes.WORKSPACE_SELECT_ACCOUNT
																}
															/>

															<BundleRouter
																data={
																	OAuthReceive
																}
																exact
																path={
																	Routes.OAUTH_RECEIVE
																}
															/>

															<Route
																component={
																	Loading
																}
																path={
																	Routes.LOADING
																}
															/>

															<WorkspaceLayer />

															<RouteNotFound />
														</Switch>
													</Suspense>
												</RoutesContainer>
											</Router>
										</div>
									</ClayTooltipProvider>
								</ChannelProvider>
							</OnboardingContext.Provider>
						</UnassignedSegmentsProvider>
					</ClayLinkContext.Provider>
				</ClayIconSpriteContext.Provider>
			</Provider>
		</ApolloProvider>
	);
};

export default App;
