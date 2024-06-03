/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import {useMemo, useState} from 'react';
import {useOutletContext} from 'react-router-dom';
import useSWR from 'swr';

import {useMarketplaceContext} from '../../../../../../context/MarketplaceContext';
import useGetProductByOrderId from '../../../../../../hooks/useGetProductByOrderId';
import HeadlessCommerceDeliveryCatalogImpl from '../../../../../../services/rest/HeadlessCommerceDeliveryCatalog';
import DownloadTable from './DownloadTable';

type OutletContext = ReturnType<typeof useGetProductByOrderId>;

const Download = () => {
	const marketplaceContext = useMarketplaceContext();
	const outletContext = useOutletContext<OutletContext['data']>();
	const [search, setSearch] = useState('');

	const channel = marketplaceContext.channel;
	const virtualProducts = outletContext?.placedOrder.placedOrderItems;
	const hasVersionSpecification = outletContext?.product.productSpecifications.find(
		(specification) => specification.specificationKey === 'latest-version'
	);

	const {data: skus = [], isLoading} = useSWR(
		hasVersionSpecification
			? null
			: `marketplace-order-${outletContext?.placedOrder.id}`,
		() =>
			Promise.all(
				virtualProducts.map((orderItem: PlacedOrderItems) =>
					HeadlessCommerceDeliveryCatalogImpl.getSkuInfo(
						channel.id,
						orderItem.productId,
						orderItem.skuId,
						new URLSearchParams({
							accountId: '-1',
						})
					)
				)
			)
	);

	const virualItems = useMemo(() => {
		const hasSkuVersion = skus[0]?.customFields.find(
			(customField: CustomField) =>
				customField.name === 'Version' && customField.customValue.data
		);

		const virtualItemsWithVersion = virtualProducts[0].virtualItems?.map(
			(virtualItem: VirtualItem) => ({
				...virtualItem,
				productVersion: hasVersionSpecification
					? hasVersionSpecification.value
					: hasSkuVersion?.customValue.data,
			})
		);

		return virtualItemsWithVersion.filter((item: VirtualItem) =>
			item.version.toLowerCase().includes(search)
		);
	}, [hasVersionSpecification, search, skus, virtualProducts]);

	return (
		<div>
			<div className="align-items-center bg-light d-flex justify-content-center my-6 p-3 rounded-lg">
				<ClayForm.Group className="mb-0 w-100">
					<ClayInput.Group stacked>
						<ClayInput.GroupItem prepend>
							<ClayInput
								className="bg-white border-0"
								onChange={({target}) => setSearch(target.value)}
								placeholder="Search"
								type="text"
								value={search}
							/>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem prepend shrink>
							<ClayInput.GroupText className="bg-white border-0">
								<ClayButtonWithIcon
									aria-label="Search"
									className="border-0"
									displayType="unstyled"
									symbol="search"
								/>
							</ClayInput.GroupText>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>
			</div>

			<DownloadTable loading={isLoading} virualItems={virualItems} />
		</div>
	);
};

export default Download;
