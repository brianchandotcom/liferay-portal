/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useSelector} from '@xstate/store/react';
import {useContext, useMemo} from 'react';

import ProductPurchase from '../../../../../components/ProductPurchase';
import {MarketplaceContext} from '../../../../../context/MarketplaceContext';
import i18n from '../../../../../i18n';
import {Liferay} from '../../../../../liferay/liferay';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import {productPurchaseStore} from '../../../store/AppPurchaseStore';
import NoProjectAvailable from './NoProjectAvailable';
import koroneikiOAuth2 from '../../../../../services/oauth/Koroneiki';
import useSWR from 'swr';
import {KoroneikiChildAccounts} from '../../../../../services/oauth/types';
import RadioCardList from '../../../../../components/RadioCardList/RadioCardList';

const ProjectSelection = () => {
	const {properties} = useContext(MarketplaceContext);

	const koroneikiProject = useSelector(
		productPurchaseStore,
		({context}) => context.koroneikiProject
	);

	const {
		actions: {nextStep, previousStep},
		selectedAccount,
	} = useProductPurchaseOutletContext();

	const accountERC = selectedAccount?.externalReferenceCode;

	const {data: childAccounts = [], isLoading} = useSWR(
		`/account/${accountERC}/child-accounts`,
		() => koroneikiOAuth2.getChildAccounts(accountERC)
	);

	const noChildAccount = childAccounts.length === 0;

	if (isLoading) {
		return <ClayLoadingIndicator />;
	}

	if (noChildAccount) {
		return <NoProjectAvailable />;
	}

	const continueButtonProps = {
		children: i18n.translate('continue'),
		disabled: isLoading || !koroneikiProject,
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
			<RadioCardList
				contentList={childAccounts.map((childAccount, index) => ({
					fullTitle: true,
					id: index,
					selected: childAccount.key === koroneikiProject?.key,
					title: (
						<div>
							<div className="h5 m-0">
								{childAccount.code.toUpperCase()}
							</div>
						</div>
					),
					value: childAccount,
				}))}
				leftRadio
				onSelect={(radioOption: RadioOption<KoroneikiChildAccounts>) =>
					productPurchaseStore.send({
						koroneikiProject: radioOption.value,
						type: 'setKoreoneikiProject',
					})
				}
			/>

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
