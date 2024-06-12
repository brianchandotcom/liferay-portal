/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {partnerLevelProperties} from '../../../../common/components/dashboard/mock';
import AccountEntry from '../../../../common/interfaces/accountEntry';
import Opportunity from '../../../../common/interfaces/opportunity';
import PartnerLevel from '../../../../common/interfaces/partnerLevel';
import UserAccount from '../../../../common/interfaces/userAccount';
import {LiferayAPIs} from '../../../../common/services/liferay/common/enums/apis';
import LiferayItems from '../../../../common/services/liferay/common/interfaces/liferayItems';
import useGet from '../../../../common/services/liferay/object/useGet';
import {Filters} from '../../../../common/utils/constants/filters';

export default function useAccountInformation() {
	const [headcountAccumulator, setHeadcountAccumulator] = useState({
		partnerMarketingUser: 0,
		partnerSalesUser: 0,
	});
	const [aRRResults, setARRResults] = useState({
		growthArrTotal: 0,
		targetArr: 0,
	});
	const [checkedProperties, setCheckedProperties] = useState({
		arr: false,
		headcount: false,
		marketingPerformance: false,
		marketingPlan: false,
		solutionDeliveryCertification: false,
	});

	const {data: userAccount} = useGet<UserAccount>(
		`/o/${LiferayAPIs.HEADERLESS_ADMIN_USER}/my-user-account`
	);

	const {data: account, isValidating: isValidatingAccount} =
		useGet<AccountEntry>(
			userAccount?.accountBriefs[0]?.externalReferenceCode &&
				`/o/${LiferayAPIs.HEADERLESS_ADMIN_USER}/accounts/by-external-reference-code/${userAccount.accountBriefs[0].externalReferenceCode}`
		);

	const currency = account ? account.currency : 'USD';

	const {data: accountUserAccounts} = useGet<LiferayItems<UserAccount[]>>(
		account?.externalReferenceCode &&
			`/o/${LiferayAPIs.HEADERLESS_ADMIN_USER}/accounts/by-external-reference-code/${account.externalReferenceCode}/user-accounts?pageSize=-1`
	);

	const {data: opportunities, isValidating: isValidatingOpportunities} =
		useGet<LiferayItems<Opportunity[]>>(
			account?.name &&
				`/o/${LiferayAPIs.OBJECT}/opportunitysfs?pageSize=200&sort=closeDate:desc&filter=${Filters.LEVEL_DASHBOARD.opportunities}`
		);

	const {data: partnerLevel, isValidating: isValidatingPartnerLevel} =
		useGet<PartnerLevel>(
			account?.r_prtLvlToAcc_c_partnerLevelERC &&
				`/o/${LiferayAPIs.OBJECT}/partnerlevels/by-external-reference-code/${account.r_prtLvlToAcc_c_partnerLevelERC}`
		);

	const opportunitiesNB =
		opportunities &&
		opportunities.items.filter(
			(opportunity) => opportunity.type === 'New Business'
		);

	const opportunitiesNPEB =
		opportunities &&
		opportunities.items.filter(
			(opportunity) =>
				opportunity.type === 'New Project Existing Business'
		);

	const opportunitiesEB =
		opportunities &&
		opportunities.items.filter(
			(opportunity) =>
				opportunity.type === 'Existing Business' &&
				opportunity.hasRenewal &&
				opportunity.growthArr > 0
		);

	const opportunitiesCount =
		opportunitiesNPEB &&
		opportunitiesNB &&
		opportunitiesEB &&
		opportunitiesNPEB.length +
			opportunitiesNB.length +
			opportunitiesEB.length;

	useEffect(() => {
		const getARRValues = (
			opportunitiesData: LiferayItems<Opportunity[]>,
			accountData: AccountEntry
		) => {
			const aRRResults = opportunitiesData.items.reduce(
				(aRRAccumulator, data: Opportunity) => ({
					growthArrTotal:
						(Number(aRRAccumulator.growthArrTotal) || 0) +
						(Number(data.growthArr) || 0),
					targetArr: Number(accountData.targetArr) || 0,
				}),
				{
					growthArrTotal: 0,
					targetArr: Number(accountData.targetArr) || 0,
				}
			);

			return aRRResults;
		};

		const formatCheckedProperties = (
			aRRResults: {[key: string]: number},
			accountData: AccountEntry,
			opportunitiesCount?: number
		) => {
			const properties = {
				arr: false,
				headcount: false,
				marketingPerformance: false,
				marketingPlan: false,
				solutionDeliveryCertification: false,
			};

			const headcount = {
				partnerMarketingUser: 0,
				partnerSalesUser: 0,
			};

			if (partnerLevel?.partnerLevelType.key !== 'authorized') {
				properties.solutionDeliveryCertification =
					accountData.solutionDeliveryCertification;

				properties.marketingPlan = accountData.marketingPlan;

				properties.marketingPerformance = Boolean(
					accountData.marketingPerformance
				);

				if (partnerLevel?.partnerLevelType.key === 'gold') {
					const hasMatchingARR =
						aRRResults.growthArrTotal >=
						partnerLevelProperties[
							partnerLevel.partnerLevelType.key
						].goalARR;

					const hasMatchingNPOrNB =
						(opportunitiesCount as number) >=
						partnerLevelProperties[
							partnerLevel.partnerLevelType.key
						].opportunitiesCount;

					properties.arr = hasMatchingARR || hasMatchingNPOrNB;
				}

				if (
					partnerLevel?.partnerLevelType.key === 'platinum' &&
					aRRResults.growthArrTotal > 0
				) {
					properties.arr = true;
				}

				properties.headcount = true;
			}

			return {
				headcount,
				properties,
			};
		};

		if (
			userAccount &&
			opportunities &&
			account &&
			accountUserAccounts &&
			partnerLevel
		) {
			const aRRResults = getARRValues(opportunities, account);

			const {headcount, properties} = formatCheckedProperties(
				aRRResults,
				account,
				opportunitiesCount
			);

			setARRResults(aRRResults);
			setHeadcountAccumulator(headcount);
			setCheckedProperties(properties);
		}
	}, [
		userAccount,
		opportunities,
		account,
		accountUserAccounts,
		partnerLevel,
		opportunitiesCount,
	]);

	return {
		aRRResults,
		account,
		checkedProperties,
		currency,
		headcount: headcountAccumulator,
		loading:
			isValidatingOpportunities ||
			isValidatingPartnerLevel ||
			isValidatingAccount,
		opportunitiesCount,
		partnerLevel,
	};
}
