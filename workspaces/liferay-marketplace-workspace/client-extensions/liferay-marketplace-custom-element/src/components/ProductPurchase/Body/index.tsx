/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';

type ProductPurchaseBodyProps = {
	children: ReactNode;
};

const ProductPurchaseBody: React.FC<ProductPurchaseBodyProps> = ({
	children,
}) => <div className="border d-flex flex-column p-5 rounded">{children}</div>;

export default ProductPurchaseBody;
