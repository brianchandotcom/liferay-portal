/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Skeleton} from '~/components';
import {useAppContext} from '~/features/project/context';
import useUserAccountsByAccountExternalReferenceCode from '~/features/project/pages/Project/TeamMembers/components/TeamMembersTable/hooks/useUserAccountsByAccountExternalReferenceCode';
import {SUBSCRIPTIONS_STATUS} from '~/features/project/utils/constants';
import {getSubscriptionStatus} from '~/features/project/utils/getSubscriptionStatus';
import i18n from '~/utils/I18n';

const LiferayContacts = ({koroneikiAccount, loading}) => {
	const [{subscriptions}] = useAppContext();

	const hasActiveTAMSubscription =
		Array.isArray(subscriptions) &&
		subscriptions
			?.filter((item) =>
				item.name?.includes('Technical Account Management')
			)
			?.some(
				(accountSubscription) =>
					getSubscriptionStatus(
						new Date(accountSubscription.startDate),
						new Date(accountSubscription.endDate)
					) === SUBSCRIPTIONS_STATUS.active
			);

	const [, {data: userAccountsData}] =
		useUserAccountsByAccountExternalReferenceCode(
			koroneikiAccount?.accountKey
		);

	const accountMembers =
		userAccountsData?.accountUserAccountsByExternalReferenceCode.items;

	const cxmUser = accountMembers?.find((user) =>
		user.selectedAccountSummary.roleBriefs.some(
			(role) => role.name === 'Customer Experience Manager'
		)
	);
	const solutionArchitectUser = accountMembers?.find((user) =>
		user.selectedAccountSummary.roleBriefs.some(
			(role) => role.name === 'Solution Architect'
		)
	);

	const ContactDetails = ({roleKey, user}) => {
		if (!user) {
			return null;
		}

		return (
			<div className="mb-3">
				<div className="font-weight-bold rounded-sm text-neutral-8 text-paragraph">
					{user.name}
				</div>

				<div className="mt-1 rounded-sm text-neutral-10 text-paragraph">
					{i18n.translate(roleKey)}
				</div>

				<div className="rounded-sm text-neutral-10 text-paragraph-sm">
					{user.emailAddress}
				</div>
			</div>
		);
	};

	return (
		<div className="mb-5 ml-xl-9">
			{loading ? (
				<>
					<Skeleton className="mb-4" height={22} width={140} />
					<Skeleton height={24} width={125} />
					<Skeleton className="mt-1" height={24} width={100} />
					<Skeleton className="mt-1" height={20} width={150} />
				</>
			) : (
				<>
					<h5
						className={`mb-4 rounded-sm ${hasActiveTAMSubscription && (solutionArchitectUser || cxmUser) ? 'text-neutral-10' : 'text-neutral-6'}`}
					>
						{i18n.translate(
							hasActiveTAMSubscription &&
								(solutionArchitectUser || cxmUser)
								? 'liferay-contacts'
								: 'liferay-contact'
						)}
					</h5>

					<div className="mb-3">
						<div className="font-weight-bold rounded-sm text-neutral-8 text-paragraph">
							{koroneikiAccount?.liferayContactName}
						</div>

						{koroneikiAccount?.liferayContactRole && (
							<div className="mt-1 rounded-sm text-neutral-10 text-paragraph">
								{koroneikiAccount?.liferayContactRole}
							</div>
						)}

						<div className="rounded-sm text-neutral-10 text-paragraph-sm">
							{koroneikiAccount?.liferayContactEmailAddress}
						</div>
					</div>

					{hasActiveTAMSubscription && (
						<>
							<ContactDetails
								roleKey="customer-experience-manager"
								user={cxmUser}
							/>
							<ContactDetails
								roleKey="solution-architect"
								user={solutionArchitectUser}
							/>
						</>
					)}
				</>
			)}
		</div>
	);
};

export default LiferayContacts;
