import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import {useEffect, useState} from 'react';

import {DashboardNavigation} from '../../components/DashboardNavigation/DashboardNavigation';
import {DashboardMemberTableRow} from '../../components/DashboardTable/DashboardMemberTableRow';
import {
	AppProps,
	DashboardTable,
} from '../../components/DashboardTable/DashboardTable';
import {PublishedAppsDashboardTableRow} from '../../components/DashboardTable/PublishedAppsDashboardTableRow';
import {MemberProfile} from '../../components/MemberProfile/MemberProfile';
import {
	getAccountInfoFromCommerce,
	getAccounts,
	getMyUserAccount,
	getProductSpecifications,
	getProducts,
	getUserAccounts,
} from '../../utils/api';
import {showAccountImage} from '../../utils/util';
import {AccountDetailsPage} from '../AccountDetailsPage/AccountDetailsPage';
import {
	DashboardListItems,
	DashboardPage,
} from '../DashBoardPage/DashboardPage';
import {
	AccountBriefProps,
	MemberProps,
	UserAccountProps,
	customerRoles,
	initialDashboardNavigationItems,
	publisherRoles,
} from './PublishedDashboardPageUtil';

import './PublishedAppsDashboardPage.scss';
import {Liferay} from '../../liferay/liferay';
import {getProductVersionFromSpecifications} from '../../utils/util';
import {ProjectsPage} from '../ProjectsPage/ProjectsPage';
import solutionsIcon from '../../assets/icons/analytics_icon.svg';
import appsIcon from '../../assets/icons/apps_fill_icon.svg';
import membersIcon from '../../assets/icons/person_fill_icon.svg';
import projectsIcon from '../../assets/icons/projects_icon.svg';

const appTableHeaders = [
	{
		iconSymbol: 'order-arrow',
		title: 'Name',
		style: {width: '2%'},
	},
	{
		title: 'Version',
	},
	{
		title: 'Type',
	},
	{
		title: 'Last Updated',
	},
	{
		title: 'Status',
	},
];

const memberTableHeaders = [
	{
		iconSymbol: 'order-arrow',
		title: 'Name',
	},
	{
		title: 'Email',
	},
	{
		title: 'Role',
	},
];

const initialAccountsState: Account[] = [
	{
		externalReferenceCode: '',
		id: 0,
		name: '',
		description: '',
		type: '',
	},
];

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
	const [showDashboardNavigation, setShowDashboardNavigation] =
		useState(true);
	const [dashboardNavigationItems, setDashboardNavigationItems] = useState(
		initialDashboardNavigationItems
	);
	const [page, setPage] = useState(1);
	const [publishedAppTable, setPublishedAppTable] =
		useState<PublishedAppTable>({items: [], pageSize: 7, totalCount: 1});
	const [appsTotalCount, setAppTotalCount] = useState<number>(0);
	const [selectedNavigationItem, setSelectedNavigationItem] =
		useState('Apps');
	const [members, setMembers] = useState<MemberProps[]>(Array<MemberProps>());
	const [selectedMember, setSelectedMember] = useState<MemberProps>();
	const [selectedAccount, setSelectedAccount] = useState<Account>(
		initialAccountsState[0]
	);

	const buttonRedirectURL = Liferay.ThemeDisplay.getCanonicalURL().replaceAll(
		'/publisher-dashboard',
		'/create-new-app'
	);

	const formatDate = (date: string) => {
		const locale = Liferay.ThemeDisplay.getLanguageId().replace('_', '-');

		const dateOptions: Intl.DateTimeFormatOptions = {
			day: 'numeric',
			month: 'short',
			year: 'numeric',
		};

		const formattedDate = new Intl.DateTimeFormat(
			locale,
			dateOptions
		).format(new Date(date));

		return formattedDate;
	};

	async function getAppListProductSpecifications(productIds: number[]) {
		return await Promise.all(
			productIds.map(async (productId) => {
				return await getProductSpecifications({
					appProductId: productId,
				});
			})
		);
	}

	function getAppListProductIds(products: {items: Product[]}) {
		const productIds: number[] = [];

		products.items.map((product) => {
			productIds.push(product.productId);
		});

		return productIds;
	}

	function getProductTypeFromSpecifications(
		specifications: ProductSpecification[]
	) {
		let productType = 'no type';

		specifications.forEach((specification: ProductSpecification) => {
			if (specification.specificationKey === 'type') {
				productType = specification.value.en_US;

				if (productType === 'cloud') {
					productType = 'Cloud';
				}
				else if (productType === 'dxp') {
					productType = 'DXP';
				}
			}
		});

		return productType;
	}

	function getRolesList(accountBriefs: AccountBrief[]) {
		const rolesList: string[] = [];

		const accountBrief = accountBriefs.find(
			(accountBrief) => accountBrief.id === selectedAccount.id
		);

		accountBrief?.roleBriefs.forEach((role) => {
			rolesList.push(role.name);
		});

		return rolesList.join(', ');
	}

	useEffect(() => {
		const makeFetch = async () => {
			const accountsResponse = await getAccounts();

			const accountsPublisher = accountsResponse.items.filter(
				(currentAccount) => {
					const catalogIdCustomField =
						currentAccount.customFields?.find(
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
		(async () => {
			const accountCustomField = selectedAccount.customFields?.find(
				(customField) => customField.name === 'CatalogId'
			);

			if (accountCustomField) {
				const accountCatalogId = Number(
					accountCustomField.customValue.data
				);

				if (accountCatalogId && accountCatalogId !== 0) {
					setCatalogId(accountCatalogId);
					const appList = await getProducts();

					const appListProductIds: number[] =
						getAppListProductIds(appList);

					const appListProductSpecifications =
						await getAppListProductSpecifications(
							appListProductIds
						);

					let newAppList: AppProps[] = [];

					appList.items.forEach((product, index: number) => {
						const isApp = product.categories.find((category) => category.name === 'App');

						if (product.catalogId === accountCatalogId && isApp) {
							newAppList.push({
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

					const commerceAccountResponse =
						await getAccountInfoFromCommerce(selectedAccount.id);

					setCommerceAccount(commerceAccountResponse);

					const newDashboardNavigationItems = dashboardNavigationItems.map(navigationItems => {
						if (navigationItems.itemName === 'apps') {
							return {
								...navigationItems,
								items: newAppList.slice(0,4),
							};
						}

						return navigationItems;
					})

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
				}
			}
		})();
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
		(async () => {
			if (selectedNavigationItem === 'Members') {
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
							role: getRolesList(member.accountBriefs),
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
			}
		})();
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

			{selectedNavigationItem === 'Apps' && (
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

			{selectedNavigationItem === 'Solutions' && (
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
						{(item) => <></>}
					</DashboardTable>
				</DashboardPage>
			)}

			{selectedNavigationItem === 'Projects' && (
				<ProjectsPage
					dashboardNavigationItems={dashboardNavigationItems}
					icon={projectsIcon}
					selectedAccount={selectedAccount}
					setShowDashboardNavigation={setShowDashboardNavigation}
				/>
			)}

			{selectedNavigationItem === 'Members' && (
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

			{selectedNavigationItem === 'Account' && (
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
