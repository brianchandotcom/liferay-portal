import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useEffect, useState} from 'react';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {DashboardMemberTableRow} from '../../components/DashboardTable/DashboardMemberTableRow';
import {DashboardTable} from '../../components/DashboardTable/DashboardTable';
import {PurchasedAppsDashboardTableRow} from '../../components/DashboardTable/PurchasedAppsDashboardTableRow';
import {MemberProfile} from '../../components/MemberProfile/MemberProfile';
import {getCompanyId} from '../../liferay/constants';
import {
	baseURL,
	getAccountInfoFromCommerce,
	getAccounts,
	getChannels,
	getMyUserAccount,
	getPlacedOrders,
	getSKUCustomFieldExpandoValue,
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

import './PurchasedAppsDashboardPage.scss';
import {
	initialAccountState,
	initialDashboardNavigationItems,
	memberTableHeaders,
	tableHeaders,
} from './PurchasedDashboardPageUtil';
import solutionsIcon from '../../assets/icons/analytics_icon.svg';
import appsIcon from '../../assets/icons/apps_fill_icon.svg';
import membersIcon from '../../assets/icons/person_fill_icon.svg';

import './PurchasedAppsDashboardPage.scss';

export interface PurchasedAppProps {
	image: string;
	name: string;
	orderId: number;
	project?: string;
	provisioning: string;
	purchasedBy: string;
	purchasedDate: string;
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

const memberMessages = {
	description: 'Manage users in your development team and invite new ones',
	emptyStateMessage: {
		description1: 'Create new members and they will show up here.',
		description2: 'Click on “New Member” to start.',
		title: 'No Members Yet',
	},
	title: 'Members',
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
	const [purchasedAppTable, setPurchasedAppTable] =
		useState<PurchasedAppTable>({items: [], pageSize: 7, totalCount: 1});
	const [page, setPage] = useState<number>(1);
	const [dashboardNavigationItems, setDashboardNavigationItems] = useState(
		initialDashboardNavigationItems
	);
	const [members, setMembers] = useState<MemberProps[]>(Array<MemberProps>());
	const [solutionsItems, setSolutionsItems] = useState<PlacedOrder[]>([]);
	const [selectedMember, setSelectedMember] = useState<MemberProps>();
	const [selectedNavigationItem, setSelectedNavigationItem] =
		useState('My Apps');
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

				const commerceAccountResponse =
					await getAccountInfoFromCommerce(selectedAccount.id);

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

						const version = await getSKUCustomFieldExpandoValue({
							companyId: Number(getCompanyId()),
							customFieldName: 'version',
							skuId: placeOrderItem.skuId,
						});

						return {
							image: placeOrderItem.thumbnail,
							name: placeOrderItem.name,
							orderId: order.id,
							provisioning: order.orderStatusInfo.label_i18n,
							purchasedBy: order.author,
							purchasedDate: formattedDate,
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

				const currentUserAccountBriefs =
					currentUserAccount.accountBriefs.find(
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
						{(item) => <></>}
					</DashboardTable>
				</DashboardPage>
			)}

			{!loading && selectedNavigationItem === 'Members' && (
				<DashboardPage
					dashboardNavigationItems={dashboardNavigationItems}
					messages={memberMessages}
				>
					{selectedMember ? (
						<MemberProfile
							member={selectedMember}
							setSelectedMember={setSelectedMember}
						></MemberProfile>
					) : (
						<DashboardTable<MemberProps>
							emptyStateMessage={memberMessages.emptyStateMessage}
							icon={membersIcon}
							items={members}
							tableHeaders={memberTableHeaders}
						>
							{(item) => (
								<DashboardMemberTableRow
									item={item}
									key={item.name}
									onSelectedMemberChange={setSelectedMember}
								/>
							)}
						</DashboardTable>
					)}
				</DashboardPage>
			)}
		</div>
	);
}
