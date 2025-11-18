/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useOutletContext} from 'react-router-dom';

import {DetailedCard} from '../../../../../../components/DetailedCard/DetailedCard';
import QATable from '../../../../../../components/QATable';
import {ProductSpecificationKey} from '../../../../../../enums/Product';
import i18n from '../../../../../../i18n';
import {getProductSpecificationValue} from '../../../../../../utils/productUtils';

type GetProductSpecificationProps = {
	hrefContentType?: 'URL' | 'EMAIL';
	productSpecificationKey: ProductSpecificationKey;
};

const Support = () => {
	const {product} = useOutletContext<any>();

	const getProductSpecification = ({
		hrefContentType = 'URL',
		productSpecificationKey,
	}: GetProductSpecificationProps) => {
		const specificationValue = getProductSpecificationValue(
			productSpecificationKey,
			product
		);

		if (!specificationValue?.length) {
			return '-';
		}

		return (
			<a
				href={
					hrefContentType === 'URL'
						? specificationValue
						: `mailto:${specificationValue}`
				}
				target="_blank"
			>
				{specificationValue}
			</a>
		);
	};

	return (
		<div className="app-details-page-container mt-6">
			<div className="app-details-body-container">
				<DetailedCard
					cardIconAltText="Profile Icon"
					cardTitle={i18n.translate('support')}
					clayIcon="info-book"
				>
					<QATable
						items={[
							{
								title: i18n.translate('support-email'),
								value: getProductSpecification({
									hrefContentType: 'EMAIL',
									productSpecificationKey:
										ProductSpecificationKey.APP_SUPPORT_EMAIL,
								}),
							},
							{
								title: i18n.translate('support-url'),
								value: getProductSpecification({
									productSpecificationKey:
										ProductSpecificationKey.APP_SUPPORT_URL,
								}),
							},
							{
								title: i18n.translate('publisher-website'),
								value: getProductSpecification({
									productSpecificationKey:
										ProductSpecificationKey.APP_SUPPORT_PUBLISHER_WEBSITE_URL,
								}),
							},
							{
								title: i18n.translate('app-usage-terms-eula'),
								value: getProductSpecification({
									productSpecificationKey:
										ProductSpecificationKey.APP_SUPPORT_USAGE_TERMS_URL,
								}),
							},
							{
								title: i18n.translate('app-documentation'),
								value: getProductSpecification({
									productSpecificationKey:
										ProductSpecificationKey.APP_SUPPORT_DOCUMENTATION_URL,
								}),
							},
							{
								title: i18n.translate(
									'app-installation-and-uninstallation-guide'
								),
								value: getProductSpecification({
									productSpecificationKey:
										ProductSpecificationKey.APP_SUPPORT_INSTALLATION_GUIDE_URL,
								}),
							},
						]}
					/>
				</DetailedCard>
			</div>
		</div>
	);
};

export default Support;
