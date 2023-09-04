/* eslint-disable @typescript-eslint/no-unused-vars */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useEffect, useState} from 'react';

import solutionsIcon from '../../assets/icons/analytics_icon.svg';
import appsIcon from '../../assets/icons/apps_fill_icon.svg';
import membersIcon from '../../assets/icons/person_fill_icon.svg';
import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {DashboardTable} from '../../components/DashboardTable/DashboardTable';
import {PurchasedAppsDashboardTableRow} from '../../components/DashboardTable/PurchasedAppsDashboardTableRow';
import {getCompanyId} from '../../liferay/constants';
import {
	baseURL,
	getAccountInfoFromCommerce,
	getAccounts,
	getChannels,
	getCustomFieldExpandoValue,
	getMyUserAccount,
	getPlacedOrders,
	getProductAttachments,
	getUserAccounts,
} from '../../utils/api';
import {showAccountImage} from '../../utils/util';
import {DashboardPage} from '../DashBoardPage/DashboardPage';
import {
	AccountBriefProps,
	MemberProps,
	UserAccountProps,
	customerRoles,
	getRolesList,
	publisherRoles,
} from '../PublishedAppsDashboardPage/PublishedDashboardPageUtil';
import {
	customerAppPermissionDescriptions,
	customerDashboardPermissionDescriptions,
	initialAccountState,
	initialDashboardNavigationItems,
	tableHeaders,
} from './PurchasedDashboardPageUtil';

import './PurchasedAppsDashboardPage.scss';
import {MembersPage} from '../MembersPage/MembersPage';

export interface PurchasedAppProps {
	name: string;
	orderId: number;
	project?: string;
	provisioning: string;
	purchasedBy: string;
	purchasedDate: string;
	thumbnail: string;
	type: string;
	version: string;
}

interface PurchasedAppTable {
	items: PurchasedAppProps[];
	pageSize: number;
	totalCount: number;
}

const appMessages = {
	description: 'Manage apps purchase from the Marketplace',
	emptyStateMessage: {
		description1:
			'Purchase and install new apps and they will show up here.',
		description2: 'Click on “Add Apps” to start.',
		title: 'No Apps Yet',
	},
	title: 'My Apps',
};

const solutionMessages = {
	description: 'Manage solution trial and purchases from the Marketplace',
	emptyStateMessage: {
		description1:
			'Purchase and install new solutions and they will show up here.',
		description2: 'Click on “New Solutions” to start.',
		title: 'No Solutions Yet',
	},
	title: 'My Solutions',
};

export function PurchasedAppsDashboardPage() {
	const [accounts, setAccounts] = useState<Account[]>(initialAccountState);
	const [commerceAccount, setCommerceAccount] = useState<CommerceAccount>();
	const [selectedAccount, setSelectedAccount] = useState<Account>(
		accounts[0]
	);
	const [purchasedAppTable, setPurchasedAppTable] = useState<
		PurchasedAppTable
	>({items: [], pageSize: 7, totalCount: 1});
	const [page, setPage] = useState<number>(1);
	const [dashboardNavigationItems, setDashboardNavigationItems] = useState(
		initialDashboardNavigationItems
	);
	const [_members, setMembers] = useState<MemberProps[]>(
		Array<MemberProps>()
	);
	const [solutionsItems, setSolutionsItems] = useState<PlacedOrder[]>([]);
	const [_selectedMember, setSelectedMember] = useState<MemberProps>();
	const [selectedNavigationItem, setSelectedNavigationItem] = useState(
		'My Apps'
	);
	const [loading, setLoading] = useState(false);

	useEffect(() => {
		const makeFetch = async () => {
			const accountsResponse = await getAccounts();

			const accountsList = accountsResponse.items.map(
				(account: Account) => {
					return {
						externalReferenceCode: account.externalReferenceCode,
						id: account.id,
						name: account.name,
					} as Account;
				}
			);

			setAccounts(accountsList);
			setSelectedAccount(accountsList[0]);
		};

		makeFetch();
	}, []);

	useEffect(() => {
		const makeFetch = async () => {
			if (selectedAccount.id !== 0) {
				setLoading(true);

				const channels = await getChannels();

				const channel =
					channels.find(
						(channel) => channel.name === 'Marketplace Channel'
					) || channels[0];

				const placedOrders = await getPlacedOrders(
					selectedAccount?.id || 50307,
					channel.id,
					page,
					purchasedAppTable.pageSize
				);

				const commerceAccountResponse = await getAccountInfoFromCommerce(
					selectedAccount.id
				);

				setCommerceAccount(commerceAccountResponse);

				const filteredAppOrders = placedOrders.items.filter(
					({orderTypeExternalReferenceCode}) =>
						orderTypeExternalReferenceCode === 'CLOUDAPP' ||
						orderTypeExternalReferenceCode === 'DXPAPP'
				);

				const filteredSolutionsOrders = placedOrders.items.filter(
					({orderTypeExternalReferenceCode}) =>
						orderTypeExternalReferenceCode === 'SOLUTION30'
				);

				const newAppOrderItems = await Promise.all(
					filteredAppOrders.map(async (order) => {
						const [placeOrderItem] = order.placedOrderItems;

						const date = new Date(order.createDate);
						const options: Intl.DateTimeFormatOptions = {
							day: 'numeric',
							month: 'short',
							year: 'numeric',
						};
						const formattedDate = date.toLocaleDateString(
							'en-US',
							options
						);

						const version = await getCustomFieldExpandoValue({
							className:
								'com.liferay.commerce.product.model.CPInstance',
							classPK: placeOrderItem.skuId,
							columnName: 'version',
							companyId: Number(getCompanyId()),
							tableName: 'CUSTOM_FIELDS',
						});

						const attachments = await getProductAttachments(
							selectedAccount.id,
							channel.id as number,
							placeOrderItem.productId
						);

						let orderThumbnail;

						if (attachments) {
							orderThumbnail = await (async () => {
								const promises = attachments.map(
									async (currentAttachment) => {
										const attachmentsCustomField = await getCustomFieldExpandoValue(
											{
												className:
													'com.liferay.commerce.product.model.CPAttachmentFileEntry',
												classPK: currentAttachment.id,
												columnName: 'App Icon',
												companyId: Number(
													getCompanyId()
												),
												tableName: 'CUSTOM_FIELDS',
											}
										);

										return attachmentsCustomField[0] ===
											'Yes'
											? currentAttachment
											: null;
									}
								);

								const results = await Promise.all(promises);

								return results.find(
									(attachment) => attachment !== null
								);
							})();
						}

						return {
							name: placeOrderItem.name,
							orderId: order.id,
							provisioning: order.orderStatusInfo.label_i18n,
							purchasedBy: order.author,
							purchasedDate: formattedDate,
							thumbnail: orderThumbnail?.src as string,
							type: placeOrderItem.subscription
								? 'Subscription'
								: 'Perpetual',
							version: !Object.keys(version).length
								? ''
								: version,
						};
					})
				);

				setSolutionsItems(filteredSolutionsOrders);

				setPurchasedAppTable((previousPurchasedAppTable) => {
					return {
						...previousPurchasedAppTable,
						items: newAppOrderItems,
						totalCount: placedOrders.totalCount,
					};
				});

				setLoading(false);
			}
		};

		makeFetch();
	}, [page, purchasedAppTable.pageSize, selectedAccount]);

	useEffect(() => {
		const clickedNavigationItem =
			dashboardNavigationItems.find(
				(dashboardNavigationItem) =>
					dashboardNavigationItem.itemSelected
			) || dashboardNavigationItems[0];

		setSelectedNavigationItem(clickedNavigationItem?.itemTitle as string);

		if (clickedNavigationItem.itemTitle !== 'Members') {
			setSelectedMember(undefined);
		}
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

				const accountsListResponse = await getUserAccounts();

				const membersList = accountsListResponse.items.map(
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
							member.isCustomerAccount
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
		<div className="purchased-apps-dashboard-page-container">
			<DashboardNavigation
				accountAppsNumber={purchasedAppTable.items.length}
				accountIcon={showAccountImage(commerceAccount?.logoURL)}
				accounts={accounts}
				currentAccount={selectedAccount}
				dashboardNavigationItems={dashboardNavigationItems}
				setDashboardNavigationItems={setDashboardNavigationItems}
				setSelectedAccount={setSelectedAccount}
			/>

			{loading && (
				<ClayLoadingIndicator
					className="purchased-apps-dashboard-page-loading-indicator"
					displayType="primary"
					shape="circle"
					size="md"
				/>
			)}

			{!loading && selectedNavigationItem === 'My Apps' && (
				<DashboardPage
					buttonHref={baseURL + '/web/marketplace/'}
					buttonMessage="Add Apps"
					dashboardNavigationItems={dashboardNavigationItems}
					messages={appMessages}
				>
					<DashboardTable<PurchasedAppProps>
						emptyStateMessage={appMessages.emptyStateMessage}
						icon={appsIcon}
						items={purchasedAppTable.items}
						tableHeaders={tableHeaders}
					>
						{(item) => (
							<PurchasedAppsDashboardTableRow
								item={item}
								key={item.name}
							/>
						)}
					</DashboardTable>

					{purchasedAppTable.items.length ? (
						<ClayPaginationBarWithBasicItems
							active={page}
							activeDelta={purchasedAppTable.pageSize}
							defaultActive={1}
							ellipsisBuffer={3}
							ellipsisProps={{
								'aria-label': 'More',
								'title': 'More',
							}}
							onActiveChange={setPage}
							showDeltasDropDown={false}
							totalItems={purchasedAppTable?.totalCount}
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
				>
					<DashboardTable
						emptyStateMessage={solutionMessages.emptyStateMessage}
						icon={solutionsIcon}
						items={solutionsItems}
						tableHeaders={[]}
					>
						{() => <></>}
					</DashboardTable>
				</DashboardPage>
			)}

			{!loading && selectedNavigationItem === 'Members' && (
				<MembersPage
					dashboardNavigationItems={dashboardNavigationItems}
					icon={membersIcon}
					isCustomerDashboard={true}
					isPublisherDashboard={false}
					listOfRoles={customerRoles}
					rolesPermissionDescription={{
						appPermissions: customerAppPermissionDescriptions,
						dashboardPermissions: customerDashboardPermissionDescriptions,
					}}
					selectedAccount={selectedAccount}
				/>
			)}
		</div>
	);
}
