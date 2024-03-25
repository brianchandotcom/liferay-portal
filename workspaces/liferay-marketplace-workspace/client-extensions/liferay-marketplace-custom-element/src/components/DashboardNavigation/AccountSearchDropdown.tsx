import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import InfiniteScroll from 'react-infinite-scroll-component';
import {getAccountImage} from '../../utils/util';
import Search from './Search';

const AccountSearchDropdown: React.FC<any> = ({
	accountAppsNumber,
	accountIcon,
	accountsSearch,
	currentAccount,
	DropdownItems,
}) => {
	const {infiniteSearch, items} = accountsSearch;
	const {allowFetching, fetchMore, search, setSearch} = infiniteSearch;

	return (
		<ClayDropDown
			menuElementAttrs={{
				className: 'dashboard-navigation-container-dropdown p-0',
			}}
			trigger={
				<div className="dashboard-navigation-header">
					<div className="dashboard-navigation-header-left-content">
						<img
							alt="account logo"
							className="dashboard-navigation-header-logo"
							draggable={false}
							src={getAccountImage(accountIcon)}
						/>

						<div className="dashboard-navigation-header-text-container">
							<span
								className="dashboard-navigation-header-title"
								title={currentAccount?.name}
							>
								{currentAccount?.name}
							</span>

							{!!accountAppsNumber && (
								<span className="dashboard-navigation-header-apps">
									{accountAppsNumber} apps
								</span>
							)}
						</div>
					</div>

					<ClayIcon
						className="dashboard-navigation-header-arrow-down"
						symbol="caret-bottom"
					/>
				</div>
			}
		>
			{!!(
				!!infiniteSearch.search.length || infiniteSearch.totalCount > 5
			) && (
				<div className="dashboard-navigation-container-dropdown-body">
					<Search search={search} setSearch={setSearch} />
				</div>
			)}

			{infiniteSearch.totalCount > 5 ? (
				<InfiniteScroll
					dataLength={items.length}
					endMessage={
						<p className="text-center">
							<b>Yay! You have seen it all</b>
						</p>
					}
					hasMore={allowFetching}
					height={200}
					loader={<ClayLoadingIndicator />}
					next={fetchMore}
				>
					<DropdownItems accounts={items} />
				</InfiniteScroll>
			) : (
				<div className="py-2">
					<DropdownItems accounts={items} />
				</div>
			)}
		</ClayDropDown>
	);
};

export default AccountSearchDropdown;
