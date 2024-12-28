/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useSelector} from '@xstate/store/react';
import {useContext, useMemo} from 'react';
import {useNavigate} from 'react-router-dom';

import ProductPurchase from '../../../../../components/ProductPurchase';
import {MarketplaceContext} from '../../../../../context/MarketplaceContext';
import i18n from '../../../../../i18n';
import {Liferay} from '../../../../../liferay/liferay';
import {getProductPriceModel} from '../../../../../utils/productUtils';
import useGetResourceInfo from '../../../../GetApp/hooks/useGetResourceInfo';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import ProductPurchaseApp from '../../../services/ProductPurchaseApp';
import {productPurchaseStore} from '../../../store/AppPurchaseStore';
import CloudProjects from './CloudProjects';
import NoProjectAvailable from './NoProjectAvailable';

const ProjectSelection = () => {
	const {properties} = useContext(MarketplaceContext);
	const navigate = useNavigate();
	const project = useSelector(
		productPurchaseStore,
		({context}) => context.project
	);

	const {
		actions: {nextStep, previousStep},
		handlePurchase,
		product,
		selectedAccount,
	} = useProductPurchaseOutletContext();

	const {isFreeApp} = getProductPriceModel(product);

	const {
		hasConsoleProjectsAvailable,
		hasResources,
		isLoading,
		resourceRequest,
	} = useGetResourceInfo({
		product,
		selectedProject: project?.rootProjectId,
		shouldFetch: true,
	});

	const cloudProjects = useMemo(
		() => resourceRequest?.userProjects ?? [],
		[resourceRequest?.userProjects]
	);

	if (isLoading) {
		return <ClayLoadingIndicator />;
	}

	if (!hasConsoleProjectsAvailable) {
		return <NoProjectAvailable />;
	}

	const continueButtonProps = {
		children: i18n.translate(isFreeApp ? 'get-app' : 'continue'),
		disabled: isLoading || !project,
		onClick: () => {
			if (!hasResources) {
				return navigate(
					`/insuficient-resources/${project.rootProjectId}/${
						(selectedAccount as Account).id
					}`
				);
			}

			if (isFreeApp) {
				return handlePurchase(ProductPurchaseApp);
			}

			nextStep();
		},
	};

	return (
		<ProductPurchase.Shell
			className="d-flex flex-column"
			footerProps={{
				backButtonProps: {onClick: previousStep},
				continueButtonProps,
			}}
			title={i18n.translate('project-selection')}
		>
			<span
				className="mb-4 secondary-text"
				dangerouslySetInnerHTML={{
					__html: i18n.sub('x-available-for-you', [
						'projects-and-resources',
						Liferay.ThemeDisplay.getUserEmailAddress(),
					]),
				}}
			/>

			<CloudProjects project={project} projects={cloudProjects} />

			<p className="secondary-text">
				{i18n.translate('not-seeing-a-specific-project')}

				<a
					className="font-weight-bold ml-1"
					href={properties.contactSupportURL}
					target="_blank"
				>
					{i18n.translate('contact-support')}
				</a>
			</p>
		</ProductPurchase.Shell>
	);
};

export default ProjectSelection;
