import classNames from 'classnames';

import './DashboardNavigationList.scss'
import { DashboardNavigationListItem } from './DashboardNavigationListItem';
import { DashboardListItems } from './DashboardNavigation';
import { AppProps } from '../DashboardTable/DashboardTable';


interface DashboardNavigationListProps {
    navigationItemsMock: DashboardListItems[];
    navigationItemMock: DashboardListItems;
    setDashboardNavigationItems: (values: DashboardListItems[]) => void;
    dashboardNavigationItems: DashboardListItems[];
    onSelectAppChange: (value: AppProps) => void;
}

export function DashboardNavigationList({
    navigationItemsMock,
    navigationItemMock,
    dashboardNavigationItems,
    setDashboardNavigationItems,
    onSelectAppChange,
}: DashboardNavigationListProps) {
    const { itemIcon, itemTitle, itemName, itemSelected, items } = navigationItemMock;

    return (
        <>
            <div
                className={classNames('dashboard-navigation-body-list', {
                    'dashboard-navigation-body-list-selected': itemSelected
                })}
                onClick={() => {
                    const newItems = navigationItemsMock.map(navigationItem => {
                        if (navigationItem.itemName === itemName) {
                            return {
                                ...navigationItem,
                                itemSelected: true
                            }
                        }

                        return {
                            ...navigationItem,
                            itemSelected: false
                        }

                    });

                    setDashboardNavigationItems(newItems);
                }}
            >
                <img
                    className={classNames('dashboard-navigation-body-list-icon', {
                        'dashboard-navigation-body-list-icon-selected': itemSelected
                    })}
                    src={itemIcon}
                    alt="Apps icon"
                />
                <span
                    className={classNames('dashboard-navigation-body-list-text', {
                        'dashboard-navigation-body-list-text-selected': itemSelected
                    })}
                >
                    {itemTitle}
                </span>
            </div>

            {itemSelected && items?.map(item => (
                <DashboardNavigationListItem
                    key={item.name}
                    items={items}
                    item={item}
                    listName={itemName}
                    dashboardNavigationItems={dashboardNavigationItems}
                    setDashboardNavigationItems={setDashboardNavigationItems}
                    onSelectAppChange={onSelectAppChange}
                />
            ))}
        </>
    );
}