import ClayManagementToolbar from '@clayui/management-toolbar';
import ClayNavigationBar from '@clayui/navigation-bar';
import ClayButton from '@clayui/button';
import classnames from 'classnames';

import liferayIcon from '../../assets/icons/liferay-icon.svg';

import './DashboardToolbar.scss';
import { useState } from 'react';

export function DashboardToolbar() {
    const [active, setActive] = useState('Dashboard');

    return (
        <div className='dashboard-toolbar-container'>
            <ClayManagementToolbar.ItemList expand>
                <div className='dashboard-toolbar-title-container'>
                    <img className='dashboard-toolbar-liferay-icon' src={liferayIcon} />
                    <span className='dashboard-toolbar-title' >Marketplace</span>
                </div>
            </ClayManagementToolbar.ItemList>

            <ClayManagementToolbar.ItemList >
                <ClayNavigationBar triggerLabel={active}>
                    <ClayNavigationBar.Item active={active === 'Applications'}>
                        <ClayButton
                            className='dashboard-toolbar-navigation-bar-button'
                            onClick={() => setActive('Applications')}
                        >
                            <span 
                                className={classnames(
                                    'dashboard-toolbar-navigation-bar-button-text', {
                                    'dashboard-toolbar-navigation-bar-button-text-active': active === 'Applications'
                                })}
                            >
                                Applications
                            </span>
                        </ClayButton>
                    </ClayNavigationBar.Item>
                    <ClayNavigationBar.Item active={active === 'Solutions'}>
                        <ClayButton
                            className='dashboard-toolbar-navigation-bar-button'
                            onClick={() => setActive('Solutions')}
                        >
                            <span 
                                className={classnames(
                                    'dashboard-toolbar-navigation-bar-button-text', {
                                    'dashboard-toolbar-navigation-bar-button-text-active': active === 'Solutions'
                                })}
                            >
                                Solutions
                            </span>
                        </ClayButton>
                    </ClayNavigationBar.Item>
                    <ClayNavigationBar.Item active={active === 'Dashboard'}>
                        <ClayButton
                            className='dashboard-toolbar-navigation-bar-button'
                            onClick={() => setActive('Dashboard')}
                        >
                            <span 
                                className={classnames(
                                    'dashboard-toolbar-navigation-bar-button-text', {
                                    'dashboard-toolbar-navigation-bar-button-text-active': active === 'Dashboard'
                                })}
                            >
                                Dashboard
                            </span>
                        </ClayButton>
                    </ClayNavigationBar.Item>
                </ClayNavigationBar>
            </ClayManagementToolbar.ItemList>
        </div>
    )
}