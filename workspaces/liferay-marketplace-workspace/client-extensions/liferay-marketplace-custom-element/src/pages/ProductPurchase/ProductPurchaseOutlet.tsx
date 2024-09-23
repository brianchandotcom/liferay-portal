/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Outlet, useLocation, useNavigate} from 'react-router-dom';

import ProductPurchase from '../../components/ProductPurchase';
import {SOLUTION_TYPES} from '../../enums/Product';
import useAccounts from './hooks/useAccounts';

type ProductPurchaseOutletProps = {
	product: DeliveryProduct;
	routes: any[];
	solutionTypeSpecificationValue: SOLUTION_TYPES;
};

export type ProductPurchaseOutletContext = {
	product: DeliveryProduct;
	solutionTypeSpecificationValue: SOLUTION_TYPES;
} & Omit<ReturnType<typeof useAccounts>, 'myUserAccount'>;

const ProductPurchaseOutlet: React.FC<ProductPurchaseOutletProps> = ({
	product,
	routes,
	solutionTypeSpecificationValue,
}) => {
	const {pathname} = useLocation();
	const {accounts, selectedAccount, setSelectedAccount} = useAccounts();
	const navigate = useNavigate();

	const steps = routes.map((route) => ({
		...route,
		key: route.index ? '/' : `/${route.path}`,
	}));

	return (
		<ProductPurchase>
			<ProductPurchase.Header product={product}>
				<ProductPurchase.HeaderAccount account={selectedAccount} />
			</ProductPurchase.Header>

			<ProductPurchase.Steps
				activeKey={pathname}
				className="mt-5 px-8"
				onClickIndicator={(step) => navigate(step.key)}
				steps={steps}
			/>

			<ProductPurchase.Body>
				<Outlet
					context={{
						accounts,
						product,
						selectedAccount,
						setSelectedAccount,
						solutionTypeSpecificationValue,
					}}
				/>
			</ProductPurchase.Body>
		</ProductPurchase>
	);
};

export default ProductPurchaseOutlet;
