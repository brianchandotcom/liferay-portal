/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMemo} from 'react';
import {Navigate, Outlet, useOutletContext} from 'react-router-dom';
import i18n from '~/utils/I18n';
import {useAppContext} from '~/features/project/context';

const ProductOutlet = ({product}) => {
	const {setHasSideMenu} = useOutletContext();
	const [{project, subscriptionGroups}] = useAppContext();

	const hasProduct = useMemo(
		() => !!subscriptionGroups?.find(({activationProductName, name}) => {
			if (name === product) {
				return true;
			}

			const activationProductNames = (activationProductName || '').split(',')
				.map(name => name.trim())
				.filter(name => name.length > 0);

			if (activationProductNames?.includes(product)) {
				return true;
			}

			return false;
		}),
		[product, subscriptionGroups]
	);

	if (!project || !subscriptionGroups) {
		return <> {i18n.translate('loading')}...</>;
	}

	if (!hasProduct) {
		return <Navigate replace={true} to={`/${project?.accountKey}`} />;
	}

	return (
		<Outlet
			context={{
				setHasSideMenu,
			}}
		/>
	);
};

export default ProductOutlet;
