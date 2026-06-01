/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ComponentProps, ReactNode, useState} from 'react';
import {Navigate} from 'react-router-dom';
import useSWR from 'swr';

import AccountSelection from '../../../../../components/Checkout/AccountSelection';
import ProductPurchase from '../../../../../components/ProductPurchase';
import ProductPurchaseFooter from '../../../../../components/ProductPurchase/Footer';
import {useMarketplaceContext} from '../../../../../context/MarketplaceContext';
import i18n from '../../../../../i18n';
import analyticsOAuth2 from '../../../../../services/oauth/Analytics';
import {useProductPurchaseOutletContext} from '../../../ProductPurchaseOutlet';
import KnockoutEmptyState from '../../../components/KnockoutEmptyState';

type LDPAccountSelectionProps = {
	children?: ReactNode;
	footerProps?: ComponentProps<typeof ProductPurchaseFooter>;
};

const LDPAccountSelection: React.FC<LDPAccountSelectionProps> = ({
	children,
	footerProps,
}) => {
	const [loading, setLoading] = useState(false);
	const [showKnockout, setShowKnockout] = useState(false);
	const {myUserAccount} = useMarketplaceContext();

	const {
		accounts,
		actions: {nextStep},
		productTypeRoute,
		selectedAccount,
		setSelectedAccount,
	} = useProductPurchaseOutletContext();

	const [, nextRoute] = productTypeRoute.routes ?? [];

	const accountKey = selectedAccount?.externalReferenceCode ?? '';
	const isKoroneikiAccount = accountKey.startsWith('KOR-');

	const singleAccount = accounts.length === 1;

	const {error, isValidating} = useSWR(
		singleAccount && isKoroneikiAccount
			? `/analytics/plan/${accountKey}`
			: null,
		() => analyticsOAuth2.getPlan(accountKey)
	);

	const productTypeMetadata = productTypeRoute?.metadata;

	async function callNextStep() {
		if (!isKoroneikiAccount) {
			return setShowKnockout(true);
		}

		setLoading(true);

		analyticsOAuth2
			.getPlan(selectedAccount.externalReferenceCode)
			.then(nextStep)
			.catch(() => setShowKnockout(true))
			.finally(() => setLoading(false));
	}

	const invalidAccount = error || (singleAccount && !isKoroneikiAccount);

	const _showKnockout = invalidAccount || showKnockout;

	if (
		!isValidating &&
		nextRoute &&
		productTypeMetadata?.skipSingleAccountSelection &&
		accounts.length === 1 &&
		!_showKnockout
	) {
		return <Navigate to={nextRoute.path} />;
	}

	return (
		<ProductPurchase.Shell
			footerProps={{
				backButtonProps: {className: 'd-none'},
				continueButtonProps: {
					disabled: !selectedAccount || _showKnockout || loading,
					onClick: callNextStep,
				},
				...footerProps,
			}}
			title={i18n.translate('account-selection')}
		>
			{!_showKnockout && !!accounts.length && (
				<AccountSelection
					onSelectAccount={setSelectedAccount}
					selectedAccount={selectedAccount}
					userAccount={myUserAccount}
				/>
			)}

			{_showKnockout && (
				<KnockoutEmptyState
					description={
						<>
							<p>
								The account you&apos;ve selected isn&apos;t
								linked to an active Liferay DXP subscription,
								which is required to continue. Please sign in
								with a subscribed account or contact your
								administrator for access.
							</p>

							<strong className="text-4">
								Need help?{' '}
								<a href="mailto:support@liferay.com">
									support@liferay.com
								</a>
							</strong>
						</>
					}
					title="Account not eligible for purchase"
				/>
			)}

			{children}
		</ProductPurchase.Shell>
	);
};

export default LDPAccountSelection;
