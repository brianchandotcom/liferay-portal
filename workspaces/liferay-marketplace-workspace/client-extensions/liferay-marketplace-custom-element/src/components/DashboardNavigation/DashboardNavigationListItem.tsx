import classNames from 'classnames';

import circleFill from '../../assets/icons/circle_fill.svg';

import './DashboardNavigationListItem.scss';
import { AppProps } from '../DashboardTable/DashboardTable';
import { DashboardListItems } from './DashboardNavigation';

interface DashboardNavigationListItem {
    item: AppProps;
    items: AppProps[]
    listName: string;
    onSelectAppChange: (value: AppProps) => void;
    dashboardNavigationItems: DashboardListItems[];
    setDashboardNavigationItems: (values: DashboardListItems[]) => void;
}

export function DashboardNavigationListItem({
    item,
    items,
    listName,
    setDashboardNavigationItems,
    onSelectAppChange,
    dashboardNavigationItems,
}: DashboardNavigationListItem) {
    const {
        image,
        name,
        version,
        status,
        selected
    } = item;

    return (
        <div 
            className={classNames('dashboard-navigation-body-list-item', {
                'dashboard-navigation-body-list-item-selected': selected
            })}
            onClick={() => {
                const newItems = items.map(item => {
                    if(item.name === name) {
                        return {
                            ...item,
                            selected: !item.selected
                        }
                    }
                    
                    return {
                        ...item,
                        selected: false
                    }
                })

                const newDashboardNavigationItems = dashboardNavigationItems.map(navigationItem => {
                    if(navigationItem.itemName === listName) {
                        return {
                            ...navigationItem,
                            items: newItems
                        }
                    }

                    return navigationItem
                })

                setDashboardNavigationItems(newDashboardNavigationItems);

                onSelectAppChange(item);
            }}
        >
            <div>
                <img
                    className='dashboard-navigation-body-list-item-app-logo'
                    src={image}
                    alt="App Image"
                />
                <span className='dashboard-navigation-body-list-item-app-title'>{name}</span>
                <span className='dashboard-navigation-body-list-item-app-version'>{version}</span>
            </div>
            <img
                className={classNames('dashboard-navigation-body-list-item-app-status', {
                    'dashboard-navigation-body-list-item-app-status-hidden': status === 'Hidden',
                    'dashboard-navigation-body-list-item-app-status-pending': status === 'Pending',
                    'dashboard-navigation-body-list-item-app-status-published': status === 'Published',
                })}
                src={circleFill}
                alt="Circle fill"
            />
        </div>
    );
}