/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Outlet, useParams} from 'react-router-dom';

import './Provisioning.scss';
import {PageRenderer} from '../../../../../../components/Page';
import useAccounts, {
	useAccount,
} from '../../../../../../hooks/data/useAccounts';
import useGetProductByOrderId from '../../../../../../hooks/useGetProductByOrderId';
import {isCloudProduct} from '../../../../../../utils/productUtils';
import useGetResourceInfo from '../../../../../GetApp/hooks/useGetResourceInfo';

type Requirements = {
	cpu: number;
	ram: number;
};

const InstallCloudAppOutlet = () => {
	const {orderId} = useParams();

	const {data: selectedAccount, error, isLoading} = useAccount();
	const accountsSearch = useAccounts();
	const orderInfo = useGetProductByOrderId(orderId as string);

	const isCloudApp = isCloudProduct(orderInfo.data?.product);

	const resourceResponse = useGetResourceInfo({
		product: orderInfo.data?.product,
		selectedProject: undefined,
		shouldFetch: isCloudApp,
	});

	const getProductRequirements = (product: DeliveryProduct) => {
		const requirements = {
			cpu: 0,
			ram: 0,
		};

		for (const requirement of ['ram', 'cpu']) {
			const currentSpecification = product?.productSpecifications.find(
				(specification) =>
					specification.specificationKey === requirement
			);

			(requirements as any)[requirement] = currentSpecification?.value;
		}

		return requirements;
	};

	return (
		<PageRenderer error={error} isLoading={isLoading}>
			<div className="provisioning">
				<Outlet
					context={{
						accountsSearch,
						orderInfo,
						productRequirements: getProductRequirements(
							orderInfo.data?.product as DeliveryProduct
						),
						resourceResponse,
						selectedAccount,
					}}
				/>
			</div>
		</PageRenderer>
	);
};

export type InstallCloudAppOutlet = {
	accountsSearch: ReturnType<typeof useAccounts>;
	orderInfo: ReturnType<typeof useGetProductByOrderId>;
	productRequirements: Requirements;
	resourceResponse: ReturnType<typeof useGetResourceInfo>;
	selectedAccount: ReturnType<typeof useAccount>;
};
export default InstallCloudAppOutlet;
