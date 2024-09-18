/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useLayoutEffect} from 'react';
import {
	NavigateFunction,
	useNavigate,
	useOutletContext,
} from 'react-router-dom';
import {z} from 'zod';

import {Header} from '../../../components/Header/Header';
import Loading from '../../../components/Loading';
import MarketoForm from '../../../components/MarketoForm';
import {useMarketplaceContext} from '../../../context/MarketplaceContext';
import i18n from '../../../i18n';
import {Liferay} from '../../../liferay/liferay';
import zodSchema from '../../../schema/zod';
import {getSiteURL} from '../../../utils/site';
import {usePurchasedOrders} from '../../CustomerDashboard/usePurchasedOrders';

export type UserForm = z.infer<typeof zodSchema.accountCreator>;

const TrialUnavailable = () => (
	<div>
		<h1 className="text-center">Trial not available.</h1>

		<p className="mt-7">
			Dear <strong>{Liferay.ThemeDisplay.getUserName()}</strong>, based on
			our records, you have already completed a trial. Therefore currently
			we are unable to start your trial. Please contact our sales
			department via email -
			<a className="ml-1" href="mailto:sales@liferay.com">
				sales@liferay.com
			</a>
			.
		</p>

		<ClayButton
			displayType="secondary"
			onClick={() => {
				Liferay.Util.navigate(getSiteURL() + '/pre-built-trial');
			}}
		>
			Return to trial page
		</ClayButton>
	</div>
);

const AccountForm = () => {
	const {properties} = useMarketplaceContext();
	const {selectedAccount} = useOutletContext<{
		accounts: Account[];
		selectedAccount: Account;
	}>();
	const navigate = useNavigate();
	const {onSubmit} = useOutletContext<any>();

	const {
		data: placedOrderResponse = {items: []},
		isLoading,
		isValidating,
	} = usePurchasedOrders({
		accountId: selectedAccount?.id,
		channelId: Liferay.CommerceContext.commerceChannelId,
		orderTypeExternalReferenceCodes: ['SOLUTION30', 'SOLUTIONS7'],
		page: 1,
		pageSize: 200,
	});

	if (isLoading || isValidating) {
		return <Loading />;
	}

	if (
		properties.trialAccountCheck === 'true' &&
		placedOrderResponse.items.length
	) {
		return <TrialUnavailable />;
	}

	return (
		<div>
			<Header
				description={
					<div className="d-flex flex-column justify-content-center text-center w-100">
						<p className="m-0">
							Your trial is provisioned by the Liferay
							Marketplace.
						</p>

						<p>
							To continue, please enter the required information.
						</p>
					</div>
				}
				title={
					<div className="d-flex flex-column justify-content-center text-center">
						Create a Trial
					</div>
				}
			/>

			<MarketoForm
				footerElement={(buttonElement: HTMLButtonElement) => {
					const backButton = document.createElement('button');
					const parentElement = buttonElement.parentElement;

					if (parentElement) {
						parentElement.classList.add(
							'd-flex',
							'justify-content-between'
						);
					}

					backButton.classList.add('btn', 'btn-secondary');
					backButton.onclick = () => navigate('..');
					backButton.textContent = i18n.translate('back');
					backButton.type = 'button';

					buttonElement.classList.add('btn', 'btn-primary');
					buttonElement.classList.remove('mktoButton');
					buttonElement.insertAdjacentElement(
						'beforebegin',
						backButton
					);
				}}
				formId={properties.marketoFormId}
				onSubmit={onSubmit}
				submitText={i18n.translate('start-trial')}
			/>
		</div>
	);
};

const GetSolutionForm = () => {
	const {accounts, navigate, selectedAccount} = useOutletContext<{
		accounts: Account[];
		navigate: NavigateFunction;
		selectedAccount?: Account;
	}>();

	useLayoutEffect(() => {
		if (accounts.length > 1 && !selectedAccount) {
			navigate('/', {replace: true});
		}
	}, [selectedAccount, accounts.length, navigate]);

	return <AccountForm />;
};

export default GetSolutionForm;
