/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useSelector} from '@xstate/store/react';
import classNames from 'classnames';
import {Outlet, useLocation} from 'react-router-dom';

import hourglass from '../../../../../assets/icons/hourglass_icon.svg';
import {AccountAndAppCard} from '../../../../../components/Card/AccountAndAppCard';
import ProductPurchase from '../../../../../components/ProductPurchase';
import {ConsoleUserProject} from '../../../../../services/oauth/types';
import {convertSize} from '../../../../../utils/filesize';
import {normalizeURLProtocol} from '../../../../../utils/string';
import {productPurchaseStore} from '../../../store';

import './index.scss';

const getProductRequirements = (product: DeliveryProduct) => {
	const requirements = {
		cpu: 0,
		ram: 0,
	};

	for (const requirement in requirements) {
		const currentSpecification = product?.productSpecifications.find(
			(specification) => specification.specificationKey === requirement
		);

		(requirements as any)[requirement] = currentSpecification?.value
			? Number(currentSpecification?.value)
			: 0;
	}

	return requirements;
};

const getRequiredLabel = (product: DeliveryProduct) => {
	const requirements = getProductRequirements(product);

	return `${requirements.cpu} CPUs, ${requirements.ram} GB RAM`;
};

const getUsageLabel = (
	project: ConsoleUserProject,
	product: DeliveryProduct
) => {
	const requirements = getProductRequirements(product);

	const remainingResource = {
		cpu:
			project.rootProjectPlanUsage?.cpu.limit -
			project.rootProjectPlanUsage?.cpu.used,
		ram: convertSize(
			project.rootProjectPlanUsage.memory.limit -
				project.rootProjectPlanUsage?.memory?.used,
			'MB',
			'GB'
		),
	};

	return `${requirements.cpu - remainingResource.cpu}CPUs,
		${requirements.ram - remainingResource.ram}GB RAM`;
};

type InsuficientResourcesProps = {
	product: DeliveryProduct;
};

export function InsuficientResources({product}: InsuficientResourcesProps) {
	const project = useSelector(
		productPurchaseStore,
		({context}) => context.project
	);

	const location = useLocation();

	const {name, urlImage} = product;

	if (!project) {
		return <ClayLoadingIndicator />;
	}

	return (
		<ProductPurchase
			className={classNames('contact-sales-page-content', {
				'mt-0': location.pathname.includes('form'),
			})}
		>
			<div className="contact-sales-page-cards">
				<AccountAndAppCard
					category="Application"
					logo={normalizeURLProtocol(urlImage)}
					title={
						<span className="m-0">
							<div>{name}</div>

							<small>
								{getRequiredLabel(product as DeliveryProduct)}
							</small>
						</span>
					}
				/>

				<ClayIcon
					className="contact-sales-page-icon m-0"
					symbol="arrow-right-full"
				/>

				<AccountAndAppCard
					category="Project"
					className="contact-sales-page-no-resource"
					logo={hourglass as string}
					title={
						<span className="m-0">
							<div>{project.rootProjectId.toUpperCase()}</div>

							<small className="text-danger">
								{getUsageLabel(project, product)}
							</small>
						</span>
					}
				/>
			</div>

			<Outlet />
		</ProductPurchase>
	);
}
