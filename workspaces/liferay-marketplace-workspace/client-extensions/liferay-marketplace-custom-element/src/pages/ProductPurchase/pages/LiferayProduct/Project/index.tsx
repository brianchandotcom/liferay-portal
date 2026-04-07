/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useSelector} from '@xstate/store/react';
import {useContext} from 'react';
import useSWR from 'swr';

import ProductPurchase from '../../../../../components/ProductPurchase';
import RadioCardList from '../../../../../components/RadioCardList/RadioCardList';
import {MarketplaceContext} from '../../../../../context/MarketplaceContext';
import i18n from '../../../../../i18n';
import {Liferay} from '../../../../../liferay/liferay';
import HeadlessSalesforceProject from '../../../../../services/rest/HeadlessSalesforceProject';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import {productPurchaseStore} from '../../../store/AppPurchaseStore';
import NoProjectAvailable from './NoProjectAvailable';

const ProjectSelection = () => {
	const {properties} = useContext(MarketplaceContext);

	const salesforceProject = useSelector(
		productPurchaseStore,
		({context}) => context.salesforceProject
	);

	const {
		actions: {nextStep, previousStep},
		selectedAccount,
	} = useProductPurchaseOutletContext();

	const accountKey = selectedAccount?.externalReferenceCode || '';

	const isKoroneikiAccount = accountKey.startsWith('KOR-');

	const {data: salesforceProjects, isLoading} = useSWR(
		isKoroneikiAccount
			? `/account/${accountKey}/salesforce-projects`
			: null,
		() => HeadlessSalesforceProject.getAccountSalesforceProjects(accountKey)
	);

	if (isLoading) {
		return <ClayLoadingIndicator />;
	}

	if (!salesforceProjects?.length || !isKoroneikiAccount) {
		return <NoProjectAvailable />;
	}

	const continueButtonProps = {
		children: i18n.translate('continue'),
		disabled: isLoading || !salesforceProject,
		onClick: () => {
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
			<RadioCardList<SalesforceProject>
				contentList={salesforceProjects.map(
					(_salesforceProject, index) => ({
						fullTitle: true,
						id: index,
						selected:
							_salesforceProject.externalReferenceCode ===
							salesforceProject?.externalReferenceCode,
						title: (
							<span className="font-weight-semi-bold">
								{_salesforceProject.name}
							</span>
						),
						value: _salesforceProject,
					})
				)}
				leftRadio
				onSelect={(radioOption: RadioOption<SalesforceProject>) =>
					productPurchaseStore.send({
						salesforceProject: radioOption.value,
						type: 'setSalesforceProject',
					})
				}
			/>

			<p className="secondary-text">
				{i18n.translate('not-seeing-a-specific-project')}

				<a
					className="font-weight-semi-bold ml-1"
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
