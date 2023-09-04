/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useEffect, useState} from 'react';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {
	AppProps,
	DashboardTable,
} from '../../components/DashboardTable/DashboardTable';
import {PublishedAppsDashboardTableRow} from '../../components/DashboardTable/PublishedAppsDashboardTableRow';
import {
	getAccountInfoFromCommerce,
	getAccounts,
	getMyUserAccount,
	getProducts,
	getUserAccountsByAccountId,
} from '../../utils/api';
import {
	getProductVersionFromSpecifications,
	showAccountImage,
} from '../../utils/util';
import {AccountDetailsPage} from '../AccountDetailsPage/AccountDetailsPage';
import {DashboardPage} from '../DashBoardPage/DashboardPage';
import {
	AccountBriefProps,
	MemberProps,
	UserAccountProps,
	appTableHeaders,
	customerRoles,
	formatDate,
	getAppListProductIds,
	getAppListProductSpecifications,
	getProductTypeFromSpecifications,
	getRolesList,
	initialAccountsState,
	initialDashboardNavigationItems,
	publisherAppPermissionDescriptions,
	publisherDashboardPermissionDescriptions,
	publisherRoles,
} from './PublishedDashboardPageUtil';

import './PublishedAppsDashboardPage.scss';
import solutionsIcon from '../../assets/icons/analytics_icon.svg';
import appsIcon from '../../assets/icons/apps_fill_icon.svg';
import membersIcon from '../../assets/icons/person_fill_icon.svg';
import projectsIcon from '../../assets/icons/projects_icon.svg';
import {Liferay} from '../../liferay/liferay';
import {MembersPage} from '../MembersPage/MembersPage';
import {ProjectsPage} from '../ProjectsPage/ProjectsPage';

interface PublishedAppTable {
	items: AppProps[];
	pageSize: number;
	totalCount: number;
}

const appMessages = {
	description: 'Manage and publish apps on the Marketplace',
	emptyStateMessage: {
		description1: 'Publish apps and they will show up here.',
		description2: 'Click on “New App” to start.',
		title: 'No Apps Yet',
	},
	title: 'Apps',
};

const solutionMessages = {
	description: 'Manage solution trial and purchases from the Marketplace',
	emptyStateMessage: {
		description1: 'Publish solutions and they will show up here.',
		description2: 'Click on “New Solutions” to start.',
		title: 'No Solutions Yet',
	},
	title: 'My Solutions',
};

export function PublishedAppsDashboardPage() {
	const [accounts, setAccounts] = useState<Account[]>(initialAccountsState);
	const [catalogId, setCatalogId] = useState<number>();
	const [commerceAccount, setCommerceAccount] = useState<CommerceAccount>();
	const [selectedApp, setSelectedApp] = useState<AppProps>();
	const [showDashboardNavigation, setShowDashboardNavigation] = useState(
		true
	);
	const [dashboardNavigationItems, setDashboardNavigationItems] = useState(
		initialDashboardNavigationItems
	);
	const [page, setPage] = useState(1);
	const [publishedAppTable, setPublishedAppTable] = useState<
		PublishedAppTable
	>({items: [], pageSize: 7, totalCount: 1});
	const [appsTotalCount, setAppTotalCount] = useState<number>(0);
	const [selectedNavigationItem, setSelectedNavigationItem] = useState(
		'Apps'
	);
	const [members, setMembers] = useState<MemberProps[]>(Array<MemberProps>());
	const [_selectedMember, setSelectedMember] = useState<MemberProps>();
	const [selectedAccount, setSelectedAccount] = useState<Account>(
		initialAccountsState[0]
	);
	const [loading, setLoading] = useState(false);

	const buttonRedirectURL = Liferay.ThemeDisplay.getCanonicalURL().replaceAll(
		'/publisher-dashboard',
		'/create-new-app'
	);

	useEffect(() => {
		const makeFetch = async () => {
			const accountsResponse = await getAccounts();

			const accountsPublisher = accountsResponse.items.filter(
				(currentAccount) => {
					const catalogIdCustomField = currentAccount.customFields?.find(
						(customField) => customField.name === 'CatalogId'
					);

					return catalogIdCustomField?.customValue.data !== '';
				}
			);

			setAccounts(accountsPublisher);
			setSelectedAccount(accountsPublisher[0]);
		};

		makeFetch();
	}, []);

	useEffect(() => {
		const makeFetch = async () => {
			setLoading(true);

			const accountCustomField = selectedAccount.customFields?.find(
				(customField) => customField.name === 'CatalogId'
			);

			if (accountCustomField) {
				const accountCatalogId = Number(
					accountCustomField.customValue.data
				);

				if (accountCatalogId && accountCatalogId !== 0) {
					setCatalogId(accountCatalogId);
					const {items: productsItems} = await getProducts(
						'productChannels, attachments'
					);

					const appListProductIds: number[] = getAppListProductIds(
						productsItems
					);

					const appListProductSpecifications = await getAppListProductSpecifications(
						appListProductIds
					);

					const newAppList: AppProps[] = [];

					productsItems.forEach((product, index: number) => {
						const marketPlaceChannel = !!product.productChannels.find(
							(channel) => channel.name === 'Marketplace Channel'
						);

						const isApp = product.categories.find(
							(category) => category.name === 'App'
						);

						if (
							isApp &&
							marketPlaceChannel &&
							product.catalogId === accountCatalogId
						) {
							newAppList.push({
								attachments: product.attachments,
								catalogId: product.catalogId,
								externalReferenceCode:
									product.externalReferenceCode,
								name: product.name.en_US,
								productId: product.productId,
								status: product.workflowStatusInfo.label.replace(
									/(^\w|\s\w)/g,
									(m: string) => m.toUpperCase()
								),
								thumbnail: product.thumbnail,
								type: getProductTypeFromSpecifications(
									appListProductSpecifications[index]
								),
								updatedDate: formatDate(product.modifiedDate),
								version: getProductVersionFromSpecifications(
									appListProductSpecifications[index]
								),
							});
						}
					});

					const commerceAccountResponse = await getAccountInfoFromCommerce(
						selectedAccount.id
					);

					setCommerceAccount(commerceAccountResponse);

					const newDashboardNavigationItems = dashboardNavigationItems.map(
						(navigationItems) => {
							if (navigationItems.itemName === 'apps') {
								return {
									...navigationItems,
									items: newAppList.slice(0, 4),
								};
							}

							return navigationItems;
						}
					);

					setDashboardNavigationItems(newDashboardNavigationItems);
					setAppTotalCount(newAppList.length);
					setPublishedAppTable({
						items: newAppList.slice(
							publishedAppTable.pageSize * (page - 1),
							publishedAppTable.pageSize * (page - 1) +
								publishedAppTable.pageSize
						),
						pageSize: publishedAppTable.pageSize,
						totalCount: newAppList.length,
					});

					setLoading(false);
				}
			}
		};

		makeFetch();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [page, publishedAppTable.pageSize, selectedAccount]);

	useEffect(() => {
		const clickedNavigationItem =
			dashboardNavigationItems.find(
				(dashboardNavigationItem) =>
					dashboardNavigationItem.itemSelected
			) || dashboardNavigationItems[0];

		if (clickedNavigationItem.itemTitle !== 'Members') {
			setSelectedMember(undefined);
		}

		setSelectedNavigationItem(clickedNavigationItem?.itemTitle as string);
	}, [dashboardNavigationItems]);

	useEffect(() => {
		const makeFetch = async () => {
			if (selectedNavigationItem === 'Members') {
				setLoading(true);

				const currentUserAccountResponse = await getMyUserAccount();

				const currentUserAccount = {
					accountBriefs: currentUserAccountResponse.accountBriefs,
					isCustomerAccount: false,
					isPublisherAccount: false,
				};

				const currentUserAccountBriefs = currentUserAccount.accountBriefs.find(
					(accountBrief: {id: number}) =>
						accountBrief.id === selectedAccount.id
				);

				if (currentUserAccountBriefs) {
					customerRoles.forEach((customerRole) => {
						if (
							currentUserAccountBriefs.roleBriefs.find(
								(role: {name: string}) =>
									role.name === customerRole
							)
						) {
							currentUserAccount.isCustomerAccount = true;
						}
					});

					publisherRoles.forEach((publisherRole) => {
						if (
							currentUserAccountBriefs.roleBriefs.find(
								(role: {name: string}) =>
									role.name === publisherRole
							)
						) {
							currentUserAccount.isPublisherAccount = true;
						}
					});
				}

				const accountsListResponse = await getUserAccountsByAccountId(
					selectedAccount.id
				);

				const membersList = accountsListResponse?.items.map(
					(member: UserAccountProps) => {
						return {
							accountBriefs: member.accountBriefs,
							dateCreated: member.dateCreated,
							email: member.emailAddress,
							image: member.image,
							isCustomerAccount: false,
							isInvitedMember: false,
							isPublisherAccount: false,
							lastLoginDate: member.lastLoginDate,
							name: member.name,
							role: getRolesList(
								member.accountBriefs,
								selectedAccount.id
							),
							userId: member.id,
						} as MemberProps;
					}
				);

				membersList.forEach((member: MemberProps) => {
					const rolesList = member.role.split(', ');

					customerRoles.forEach((customerRole) => {
						if (rolesList.find((role) => role === customerRole)) {
							member.isCustomerAccount = true;
						}
					});

					publisherRoles.forEach((publisherRole) => {
						if (rolesList.find((role) => role === publisherRole)) {
							member.isPublisherAccount = true;
						}
					});
				});

				let filteredMembersList: MemberProps[] = [];

				filteredMembersList = membersList.filter(
					(member: MemberProps) => {
						if (
							member.accountBriefs.find(
								(accountBrief: AccountBriefProps) =>
									accountBrief.externalReferenceCode ===
									selectedAccount.externalReferenceCode
							) &&
							member.isPublisherAccount
						) {
							return true;
						}

						return false;
					}
				);

				setMembers(filteredMembersList);
				setLoading(false);
			}
		};

		makeFetch();
	}, [selectedAccount, selectedNavigationItem]);

	return (
		<div className="published-apps-dashboard-page-container">
			{showDashboardNavigation && (
				<DashboardNavigation
					accountAppsNumber={appsTotalCount}
					accountIcon={showAccountImage(commerceAccount?.logoURL)}
					accounts={accounts}
					currentAccount={selectedAccount}
					dashboardNavigationItems={dashboardNavigationItems}
					onSelectAppChange={setSelectedApp}
					setDashboardNavigationItems={setDashboardNavigationItems}
					setSelectedAccount={setSelectedAccount}
				/>
			)}

			{loading && (
				<ClayLoadingIndicator
					className="published-apps-dashboard-page-loading-indicator"
					displayType="primary"
					shape="circle"
					size="md"
				/>
			)}

			{!loading && selectedNavigationItem === 'Apps' && (
				<DashboardPage
					buttonHref={`${buttonRedirectURL}?catalogId=${catalogId}`}
					buttonMessage="+ New App"
					dashboardNavigationItems={dashboardNavigationItems}
					messages={appMessages}
					selectedApp={selectedApp}
					setSelectedApp={setSelectedApp}
				>
					<DashboardTable<AppProps>
						emptyStateMessage={appMessages.emptyStateMessage}
						icon={appsIcon}
						items={publishedAppTable.items}
						tableHeaders={appTableHeaders}
					>
						{(item) => (
							<PublishedAppsDashboardTableRow
								item={item}
								key={item.name}
							/>
						)}
					</DashboardTable>

					{publishedAppTable.items.length ? (
						<ClayPaginationBarWithBasicItems
							active={page}
							activeDelta={publishedAppTable.pageSize}
							defaultActive={1}
							ellipsisBuffer={3}
							ellipsisProps={{
								'aria-label': 'More',
								'title': 'More',
							}}
							onActiveChange={setPage}
							showDeltasDropDown={false}
							totalItems={publishedAppTable.totalCount}
						/>
					) : (
						<></>
					)}
				</DashboardPage>
			)}

			{!loading && selectedNavigationItem === 'Solutions' && (
				<DashboardPage
					dashboardNavigationItems={dashboardNavigationItems}
					messages={solutionMessages}
					selectedApp={selectedApp}
					setSelectedApp={setSelectedApp}
				>
					<DashboardTable
						emptyStateMessage={solutionMessages.emptyStateMessage}
						icon={solutionsIcon}
						items={[]}
						tableHeaders={[]}
					>
						{() => <></>}
					</DashboardTable>
				</DashboardPage>
			)}

			{!loading && selectedNavigationItem === 'Projects' && (
				<ProjectsPage
					dashboardNavigationItems={dashboardNavigationItems}
					icon={projectsIcon}
					selectedAccount={selectedAccount}
					setShowDashboardNavigation={setShowDashboardNavigation}
				/>
			)}

			{!loading && selectedNavigationItem === 'Members' && (
				<MembersPage
					dashboardNavigationItems={dashboardNavigationItems}
					icon={membersIcon}
					isCustomerDashboard={false}
					isPublisherDashboard={true}
					listOfRoles={publisherRoles}
					rolesPermissionDescription={{
						appPermissions: publisherAppPermissionDescriptions,
						dashboardPermissions: publisherDashboardPermissionDescriptions,
					}}
					selectedAccount={selectedAccount}
					setShowDashboardNavigation={setShowDashboardNavigation}
				/>
			)}

			{!loading && selectedNavigationItem === 'Account' && (
				<AccountDetailsPage
					commerceAccount={commerceAccount}
					dashboardNavigationItems={dashboardNavigationItems}
					selectedAccount={selectedAccount}
					setDashboardNavigationItems={setDashboardNavigationItems}
					totalApps={appsTotalCount}
					totalMembers={members.length}
				/>
			)}
		</div>
	);
}
