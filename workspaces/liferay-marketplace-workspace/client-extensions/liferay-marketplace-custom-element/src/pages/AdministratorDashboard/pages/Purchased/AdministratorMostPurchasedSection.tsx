/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect, useState} from 'react';

import Loading from '../../../../components/Loading';
import Page from '../../../../components/Page';
import {
	APP_ORDER_TYPES,
	LIFERAY_PRODUCT_ORDER_TYPES,
} from '../../../../enums/Order';
import {useFetch} from '../../../../hooks/useFetch';
import i18n from '../../../../i18n';
import HeadlessCommerceAdminCatalog from '../../../../services/rest/HeadlessCommerceAdminCatalog';
import {safeJSONParse} from '../../../../utils/util';
import AdministratorMostPurchasedListView, {
	PurchasedItem,
} from './AdministratorMostPurchasedListView';

type ProductsCountEntry = {
	orderTypeExternalReferenceCode: string;
	productId: number;
	total: number;
};

type PurchasedReportValue = ProductsCountEntry[];

type ReportsEntry = {
	value?: string;
};

type SplitProducts = {
	apps: PurchasedItem[];
	liferayProducts: PurchasedItem[];
};

const REPORTS_ENDPOINT = 'o/c/reports';
const REPORTS_FILTER = "externalReferenceCode eq 'PRODUCT-PURCHASES-COUNT'";
const TOP_PRODUCTS_COUNT = 5;

function getTopProducts(
	productsPurchased: ProductsCountEntry[],
	allowedTypes: readonly string[]
): ProductsCountEntry[] {
	return productsPurchased
		.filter(({orderTypeExternalReferenceCode}) =>
			allowedTypes.includes(orderTypeExternalReferenceCode)
		)
		.sort((a, b) => {
			if (b.total !== a.total) {
				return b.total - a.total;
			}

			return a.productId - b.productId;
		})
		.slice(0, TOP_PRODUCTS_COUNT);
}

async function withProductDetails(
	entries: ProductsCountEntry[]
): Promise<PurchasedItem[]> {
	return Promise.all(
		entries.map(async ({productId, total}) => {
			let productName = '';
			let thumbnail = '';

			try {
				const product =
					await HeadlessCommerceAdminCatalog.getProduct(productId);

				productName = product?.name?.en_US ?? '';
				thumbnail = product?.thumbnail ?? '';
			}
			catch {}

			return {
				productName:
					productName || i18n.translate('product-unavailable'),
				purchaseCount: total,
				thumbnail,
			};
		})
	);
}

const AdministratorMostPurchasedSection: React.FC = () => {
	const [products, setProducts] = useState<SplitProducts>({
		apps: [],
		liferayProducts: [],
	});
	const [loadingDetails, setLoadingDetails] = useState(false);

	const {data: reportsData, loading} = useFetch<APIResponse<ReportsEntry>>(
		`${REPORTS_ENDPOINT}?filter=${encodeURIComponent(REPORTS_FILTER)}`
	);

	useEffect(() => {
		let cancelled = false;

		if (!reportsData?.items?.length) {
			setProducts({apps: [], liferayProducts: []});

			return;
		}

		const productsPurchased = safeJSONParse<PurchasedReportValue>(
			reportsData.items[0]?.value || '',
			[]
		);

		const topApps = getTopProducts(productsPurchased, APP_ORDER_TYPES);
		const topLiferayProducts = getTopProducts(
			productsPurchased,
			LIFERAY_PRODUCT_ORDER_TYPES
		);

		if (!topApps.length && !topLiferayProducts.length) {
			setProducts({apps: [], liferayProducts: []});

			return;
		}

		setLoadingDetails(true);

		Promise.all([
			withProductDetails(topApps),
			withProductDetails(topLiferayProducts),
		])
			.then(([apps, liferayProducts]) => {
				if (!cancelled) {
					setProducts({apps, liferayProducts});
					setLoadingDetails(false);
				}
			})
			.catch((error) => {
				console.error(error);

				if (!cancelled) {
					setLoadingDetails(false);
				}
			});

		return () => {
			cancelled = true;
		};
	}, [reportsData]);

	if (loading || loadingDetails) {
		return <Loading />;
	}

	return (
		<>
			<Page
				pageRendererProps={{
					className: 'border py-2 rounded-lg mb-8 mt-8',
				}}
				title={i18n.translate('most-purchased-apps')}
			>
				<AdministratorMostPurchasedListView items={products.apps} />
			</Page>

			<Page
				pageRendererProps={{className: 'border py-2 rounded-lg'}}
				title={i18n.translate('most-purchased-products')}
			>
				<AdministratorMostPurchasedListView
					items={products.liferayProducts}
				/>
			</Page>
		</>
	);
};

export default AdministratorMostPurchasedSection;
