/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';

import './InsuficientResources.scss';

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {Outlet, useParams} from 'react-router-dom';

import catalogIcon from '../../../assets/icons/catalog_icon.svg';
import hourglass from '../../../assets/icons/hourglass_icon.svg';
import {AccountAndAppCard} from '../../../components/Card/AccountAndAppCard';
import {useDeliveryProduct} from '../../../hooks/data/useProduct';
import {ConsoleUserProject} from '../../../services/oauth/MarketplaceSpringBootOAuth2';
import {baseURL} from '../../../utils/api';
import {getUrlParam} from '../../../utils/getUrlParam';
import {
	getAccountImage,
	getThumbnailByProductAttachment,
	showAppImage,
} from '../../../utils/util';
import useGetResourceInfo from '../hooks/useGetResourceInfo';

enum Requirements {
	ram = 'ram',
	cpu = 'cpu',
}

const getRequiredLabel = (product: DeliveryProduct | undefined) => {
	let requirements = {
		cpu: 0,
		ram: 0,
	};

	for (const requirement in Requirements) {
		const currentSpecification = product?.productSpecifications.find(
			(specification) => {
				if (specification.specificationKey === requirement) {
					return true;
				}
			}
		);

		requirements = {
			...requirements,
			[requirement]: currentSpecification?.value,
		};
	}

	return `${requirements.cpu}CPUs, ${requirements.ram}GB RAM`;
};

export function InsuficientResources() {
	const {projectId} = useParams();
	const productId = getUrlParam('productId');
	const {data: product} = useDeliveryProduct(productId ?? '');
	const {convertMegabyteToGigabyte, project} = useGetResourceInfo({
		product,
		selectedProject: projectId,
	});

	const getUsageLabel = (project: ConsoleUserProject) => {
		return `${
			project.rootProjectPlanUsage?.cpu.limit -
			project.rootProjectPlanUsage?.cpu.used
		}CPUs,
			${convertMegabyteToGigabyte({
				inverseOperation: true,
				value:
					project.rootProjectPlanUsage.memory.limit -
					project.rootProjectPlanUsage?.memory?.used,
			})}GB RAM`;
	};

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
				'mt-0': window.location.href.includes('form'),
			})}
		>
			<div className="contact-sales-page-cards">
				<AccountAndAppCard
					category="Application"
					logo={appLogo || catalogIcon}
					title={
						<span className="m-0">
							<b>{appName.toUpperCase()}</b>

							<p className="contact-sales-page-required-resource-card m-0">
								{getRequiredLabel(product)}
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
					logo={getAccountImage(hourglass) as string}
					title={
						<span className="m-0">
							<b>{project.rootProjectId.toUpperCase()}</b>

							<p className="contact-sales-page-no-resource-card m-0">
								{getUsageLabel(project)}
							</p>
						</span>
					}
				/>
			</div>

			<div className="contact-sales-page-text">
				<div className="contact-sales-page-text">
					<Outlet />
				</div>
			</div>
		</div>
	);
}
