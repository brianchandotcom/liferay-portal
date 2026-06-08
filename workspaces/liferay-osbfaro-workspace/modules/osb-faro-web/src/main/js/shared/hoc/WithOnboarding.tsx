/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {graphql} from '@apollo/client/react/hoc';
import {isArray} from 'lodash';
import React, {useContext, useEffect} from 'react';
import {connect} from 'react-redux';
import {compose} from 'redux';
import {close, modalTypes, open} from '~/shared/actions/modals';
import {OnboardingContext} from '~/shared/context/onboarding';
import {useCurrentUser} from '~/shared/hooks/useCurrentUser';
import SitesDashboardQuery from '~/shared/queries/SitesDashboardQuery';
import {User} from '~/shared/util/records';

const withOnboarding = (
	WrappedComponent: React.ComponentType<{
		currentUser: User;
		groupId: string;
	}>
) =>
	compose<any>(
		connect(null, {close, open}),
		graphql(SitesDashboardQuery, {options: {variables: {type: null}}})
	)(
		({
			close,
			data,
			groupId,
			open,
			...otherProps
		}: {
			close: () => void;
			data: {dataSources?: unknown; loading: boolean};
			groupId: string;
			open: (
				type: string,
				props: {[key: string]: any},
				options?: {closeOnBlur?: boolean}
			) => void;
			[key: string]: any;
		}) => {
			const {onboardingTriggered, setOnboardingTriggered} =
				useContext(OnboardingContext);
			const currentUser = useCurrentUser();

			useEffect(() => {
				const {dataSources, loading} = data;

				if (!onboardingTriggered && currentUser.isAdmin()) {
					const triggerCondition =
						!loading && isArray(dataSources) && !dataSources.length;

					if (triggerCondition) {
						open(modalTypes.ONBOARDING_MODAL, {
							groupId,
							onClose: close,
						});
						setOnboardingTriggered();
					}
				}

				// eslint-disable-next-line react-hooks/exhaustive-deps
			}, [data]);

			return (
				<WrappedComponent
					currentUser={currentUser}
					groupId={groupId}
					{...otherProps}
				/>
			);
		}
	);

export default withOnboarding;
