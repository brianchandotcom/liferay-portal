/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {HashRouter, Route, Routes} from 'react-router-dom';

import './index.scss';

import ClayLoadingIndicator from '@clayui/loading-indicator';

import {useMarketplaceContext} from '../../context/MarketplaceContext';
import withProviders from '../../hoc/withProviders';
import {useDeliveryProduct} from '../../hooks/data/useProduct';
import GetSolutionOutlet from './GetSolutionOutlet';
import SolutionAccount from './pages/SolutionAccount';
import SolutionFinish from './pages/SolutionFinish';
import SolutionForm from './pages/SolutionForm';

const queryString = window.location.search;

const GetSolutionRouter = () => {
	const {properties} = useMarketplaceContext();

	const {data: product, isLoading} = useDeliveryProduct(
		properties.trialProductId ||
			((new URLSearchParams(queryString).get(
				'productId'
			) as unknown) as string)
	);

	if (isLoading) {
		return <ClayLoadingIndicator />;
	}

	return (
		<HashRouter>
			<Routes>
				<Route
					element={
						<GetSolutionOutlet
							product={product as DeliveryProduct}
						/>
					}
				>
					<Route element={<SolutionAccount />} index />
					<Route element={<SolutionForm />} path="form" />
				</Route>

				<Route
					element={
						<SolutionFinish product={product as DeliveryProduct} />
					}
					path="finish"
				/>
			</Routes>
		</HashRouter>
	);
};

export default withProviders(GetSolutionRouter);
