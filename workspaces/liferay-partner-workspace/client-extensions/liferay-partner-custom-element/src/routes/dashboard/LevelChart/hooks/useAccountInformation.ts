/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

import {PartnerRoles} from '../../../../common/components/dashboard/enums/partnerRoles';
import {PartnershipLevels} from '../../../../common/components/dashboard/enums/partnershipLevels';
import {partnerLevelProperties} from '../../../../common/components/dashboard/mock';
import AccountEntry from '../../../../common/interfaces/accountEntry';
import Opportunity from '../../../../common/interfaces/opportunity';
import PartnerLevel from '../../../../common/interfaces/partnerLevel';
import Role from '../../../../common/interfaces/role';
import UserAccount from '../../../../common/interfaces/userAccount';
import {LiferayAPIs} from '../../../../common/services/liferay/common/enums/apis';
import LiferayItems from '../../../../common/services/liferay/common/interfaces/liferayItems';
import useGet from '../../../../common/services/liferay/object/useGet';

export default function useAccountInformation() {
	const [headcountAccumulator, setHeadcountAccumulator] = useState({
		partnerMarketingUser: 0,
		partnerSalesUser: 0,
	});
	const [aRRResults, setARRResults] = useState({
		aRRAmountTotal: 0,
		growthArrTotal: 0,
		renewalArrTotal: 0,
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

	const {data: account, isValidating: isValidatingAccount} = useGet<
		AccountEntry
	>(
		userAccount?.accountBriefs[0]?.externalReferenceCode &&
			`/o/${LiferayAPIs.HEADERLESS_ADMIN_USER}/accounts/by-external-reference-code/${userAccount.accountBriefs[0].externalReferenceCode}`
	);

	const {data: accountUserAccounts} = useGet<LiferayItems<UserAccount[]>>(
		account?.externalReferenceCode &&
			`/o/${LiferayAPIs.HEADERLESS_ADMIN_USER}/accounts/by-external-reference-code/${account.externalReferenceCode}/user-accounts`
	);

	const {
		data: opportunities,
		isValidating: isValidatingOpportunities,
	} = useGet<LiferayItems<Opportunity[]>>(
		account?.name &&
			`/o/${LiferayAPIs.OBJECT}/opportunitysfs?pageSize=200&filter=accountName eq '${account.name}'`
	);

	const {data: partnerLevel, isValidating: isValidatingPartnerLevel} = useGet<
		PartnerLevel
	>(
		account?.r_prtLvlToAcc_c_partnerLevelERC &&
			`/o/${LiferayAPIs.OBJECT}/partnerlevels/by-external-reference-code/${account.r_prtLvlToAcc_c_partnerLevelERC}`
	);

	useEffect(() => {
		const getARRValues = (
			accountData: AccountEntry,
			opportunitiesData: LiferayItems<Opportunity[]>
		) => {
			const aRRResults = opportunitiesData.items.reduce(
				(aRRAccumulator, data: Opportunity) => ({
					aRRAmountTotal:
						aRRAccumulator.aRRAmountTotal +
						data.growthArr +
						data.renewalArr,
					growthArrTotal:
						aRRAccumulator.growthArrTotal + data.growthArr,
					renewalArrTotal:
						aRRAccumulator.renewalArrTotal + data.renewalArr,
				}),
				{
					aRRAmountTotal: 0,
					growthArrTotal: 0,
					renewalArrTotal: 0,
				}
			);

			return aRRResults;
		};

		const formatCheckedProperties = (
			aRRResults: {[key: string]: number},
			accountData: AccountEntry
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
						(accountData.newProjectExistingBusiness as number) >=
						partnerLevelProperties[
							partnerLevel.partnerLevelType.key
						].newProjectExistingBusiness;

					properties.arr = hasMatchingARR || hasMatchingNPOrNB;
				}

				if (
					partnerLevel?.partnerLevelType.key === 'platinum' &&
					aRRResults.aRRAmountTotal > 0
				) {
					properties.arr = true;
				}

				accountUserAccounts?.items.forEach((user: UserAccount) => {
					if (
						user.accountBriefs
							?.find(
								(entry) =>
									entry.externalReferenceCode ===
									accountData.externalReferenceCode
							)
							?.roleBriefs?.find(
								(role: Role) =>
									role.name === PartnerRoles.MARKETING_USER
							)
					) {
						headcount.partnerMarketingUser += 1;
					}

					if (
						user.accountBriefs
							.find(
								(entry) =>
									entry.externalReferenceCode ===
									accountData.externalReferenceCode
							)
							?.roleBriefs?.find(
								(role: Role) =>
									role.name === PartnerRoles.SALES_USER
							)
					) {
						headcount.partnerSalesUser += 1;
					}
				});

				const hasEnoughPartnerMarketingUser =
					headcount.partnerMarketingUser >=
					partnerLevelProperties[
						partnerLevel?.partnerLevelType.key as PartnershipLevels
					].partnerMarketingUser;

				const hasEnoughPartnerSalesUser =
					headcount.partnerSalesUser >=
					partnerLevelProperties[
						partnerLevel?.partnerLevelType.key as PartnershipLevels
					].partnerSalesUser;

				if (
					hasEnoughPartnerMarketingUser &&
					hasEnoughPartnerSalesUser
				) {
					properties.headcount = true;
				}
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
			const aRRResults = getARRValues(account, opportunities);

			const {headcount, properties} = formatCheckedProperties(
				aRRResults,
				account
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
	]);

	return {
		aRRResults,
		account,
		checkedProperties,
		headcount: headcountAccumulator,
		loading:
			isValidatingOpportunities ||
			isValidatingPartnerLevel ||
			isValidatingAccount,
		partnerLevel,
	};
}
