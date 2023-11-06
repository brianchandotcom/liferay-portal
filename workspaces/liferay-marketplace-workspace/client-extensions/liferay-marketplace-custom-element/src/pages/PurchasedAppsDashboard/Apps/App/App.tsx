/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useParams} from 'react-router-dom';

import {ReviewAndSubmitAppPage} from '../../../ReviewAndSubmitAppPage/ReviewAndSubmitAppPage';

import './App.scss';
import useGetProductByOrderId from '../../../../hooks/useGetProductByOrderId';

const App = () => {
	const {orderId} = useParams();

	const {data} = useGetProductByOrderId(orderId);
	const product = data?.product;

	return (
		<div className="app-details-page-container">
			<div>
				<ReviewAndSubmitAppPage
					onClickBack={() => {}}
					onClickContinue={() => {}}
					productERC={product?.externalReferenceCode}
					productId={product?.productId}
					readonly
				/>
			</div>
		</div>
	);
};

export default App;
