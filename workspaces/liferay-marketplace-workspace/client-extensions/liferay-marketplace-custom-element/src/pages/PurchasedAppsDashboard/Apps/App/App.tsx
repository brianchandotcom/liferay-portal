/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect} from 'react';
import {useParams} from 'react-router-dom';
import useSWR from 'swr';

import {useAppContext} from '../../../../manage-app-state/AppManageState';
import {TYPES} from '../../../../manage-app-state/actionTypes';
import HeadlessCommerceAdminCatalogImpl from '../../../../services/rest/HeadlessCommerceAdminCatalog';
import {ReviewAndSubmitAppPage} from '../../../ReviewAndSubmitAppPage/ReviewAndSubmitAppPage';

import './App.scss';

const App = () => {
	const [, dispatch] = useAppContext();
	const {appId: productId} = useParams();

	const {data = []} = useSWR(`/apps/app/${productId}`, () =>
		Promise.all([
			HeadlessCommerceAdminCatalogImpl.getProduct(productId as string),
			HeadlessCommerceAdminCatalogImpl.getProductSpecifications(
				productId as string
			),
		])
	);

	const [selectedApp] = data ?? [];

	useEffect(() => {
		if (!selectedApp) {
			return;
		}

		dispatch({
			payload: {
				value: {
					appERC: selectedApp.externalReferenceCode,
					appProductId: selectedApp.productId,
				},
			},
			type: TYPES.SUBMIT_APP_PROFILE,
		});
	}, [
		dispatch,
		selectedApp,
		selectedApp?.externalReferenceCode,
		selectedApp?.productId,
	]);

	if (!selectedApp) {
		return null;
	}

	return (
		<div className="app-details-page-container">
			<div>
				<ReviewAndSubmitAppPage
					onClickBack={() => {}}
					onClickContinue={() => {}}
					productERC={selectedApp.externalReferenceCode}
					productId={selectedApp.productId}
					readonly
				/>
			</div>
		</div>
	);
};

export default App;
