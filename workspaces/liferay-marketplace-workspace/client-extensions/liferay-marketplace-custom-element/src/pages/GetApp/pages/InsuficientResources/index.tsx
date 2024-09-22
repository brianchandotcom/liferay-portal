/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';

import './index.scss';

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {Outlet, useLocation} from 'react-router-dom';

import hourglass from '../../../../assets/icons/hourglass_icon.svg';
import {AccountAndAppCard} from '../../../../components/Card/AccountAndAppCard';
import {useDeliveryProduct} from '../../../../hooks/data/useProduct';
import {ConsoleUserProject} from '../../../../services/oauth/types';
import {baseURL} from '../../../../utils/api';
import {getUrlParam} from '../../../../utils/getUrlParam';
import {
	getThumbnailByProductAttachment,
	showAppImage,
} from '../../../../utils/util';
import {useGetAppContext} from '../../GetAppContextProvider';
import {convertMegabyteToGigabyte} from '../../hooks/useGetResourceInfo';

const getProductRequirements = (product: DeliveryProduct) => {
	const requirements = {
		cpu: 0,
		ram: 0,
	};

	for (const requirement in requirements) {
		const currentSpecification = product?.productSpecifications.find(
			(specification) => specification.specificationKey === requirement
		);

		(requirements as any)[requirement] = currentSpecification?.value;
	}

	return requirements;
};

const getRequiredLabel = (product: DeliveryProduct) => {
	const requirements = getProductRequirements(product);

	return `${requirements.cpu}CPUs, ${requirements.ram}GB RAM`;
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
		ram: convertMegabyteToGigabyte({
			inverseOperation: true,
			value:
				project.rootProjectPlanUsage.memory.limit -
				project.rootProjectPlanUsage?.memory?.used,
		}),
	};

	return `${requirements.cpu - remainingResource.cpu}CPUs,
		${requirements.ram - remainingResource.ram}GB RAM`;
};

export function InsuficientResources() {
	const [
		{
			appResourceInfo: {project},
		},
	] = useGetAppContext();
	const location = useLocation();
	const productId = getUrlParam('productId');
	const {data: product} = useDeliveryProduct(productId ?? '');

	const {name: appName = ''} = product ?? {};

	const appIcon = getThumbnailByProductAttachment(product?.images);
	const appLogo = showAppImage(appIcon as string).replace(
		(appIcon as string)?.split('/o')[0],
		baseURL
	);

	if (!project) {
		return <ClayLoadingIndicator />;
	}

	return (
		<div
			className={classNames('contact-sales-page-content', {
				'mt-0': location.pathname.includes('form'),
			})}
		>
			<div className="contact-sales-page-cards">
				<AccountAndAppCard
					category="Application"
					logo={appLogo || 'catalog'}
					title={
						<span className="m-0">
							<b>{appName}</b>

							<p className="contact-sales-page-required-resource-card m-0">
								{getRequiredLabel(product as DeliveryProduct)}
							</p>
						</span>
					}
				/>

				<div className="icon-container">
					<ClayIcon
						className="contact-sales-page-icon m-0"
						symbol="arrow-right-full"
					/>
				</div>

				<AccountAndAppCard
					category="Project"
					className="contact-sales-page-no-resource"
					logo={hourglass as string}
					title={
						<span className="m-0">
							<b>{project.rootProjectId.toUpperCase()}</b>

							<p className="contact-sales-page-no-resource-card m-0">
								{getUsageLabel(
									project,
									product as DeliveryProduct
								)}
							</p>
						</span>
					}
				/>
			</div>

			<div className="contact-sales-page-text">
				<Outlet />
			</div>
		</div>
	);
}
