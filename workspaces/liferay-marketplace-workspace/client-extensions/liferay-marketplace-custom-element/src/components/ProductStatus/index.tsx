/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';

import './ProductStatus.scss';

type ProductStatusProps = {
	productStatus: string;
};

const STATUS_CLASS_MAP: Record<string, string> = {
	'approved': 'product-status-icon-completed',
	'draft': 'product-status-icon-processing',
	'pending': 'product-status-icon-pending',
	'under-review': 'product-status-icon-pending',
};

const ProductStatus = ({productStatus}: ProductStatusProps) => {
	const statusKey = productStatus.toLowerCase().replace(' ', '-');
	const statusClass = STATUS_CLASS_MAP[statusKey] || '';

	return (
		<>
			<ClayIcon
				className={classNames('mr-2 product-status-icon', statusClass)}
				symbol="circle"
			/>
			<span className="product-status-text text-capitalize">
				{productStatus}
			</span>
		</>
	);
};

export default ProductStatus;
