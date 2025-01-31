/* eslint-disable no-unused-vars */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {
	Outlet,
	useMatch,
	useNavigate,
	useOutletContext,
	useResolvedPath,
} from 'react-router-dom';
import i18n from '~/utils/I18n';
import getKebabCase from '~/utils/getKebabCase';
import {useCustomerPortal} from '~/features/project/context';

const ActivationOutlet = () => {
	const [{subscriptionGroups}] = useCustomerPortal();
	const {setHasSideMenu} = useOutletContext();

	const isCurrentActivationRoute = !!useMatch({
		path: useResolvedPath('').pathname,
	});
	const navigate = useNavigate();

	useEffect(() => {
		setHasSideMenu(true);
	}, [setHasSideMenu]);

	useEffect(() => {
		if (subscriptionGroups?.length && isCurrentActivationRoute) {
			const redirectPage = getKebabCase(subscriptionGroups[0].name);

			navigate(redirectPage);
		}
	}, [isCurrentActivationRoute, navigate, subscriptionGroups]);

	if (!subscriptionGroups) {
		return <> {i18n.translate('loading')}...</>;
	}

	return (
		<Outlet
			context={{
				setHasSideMenu,
			}}
		/>
	);
};

export default ActivationOutlet;
