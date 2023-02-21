import { AppFlowListItem } from './AppFlowListItem';
import './AppFlowList.scss'
import { AppFlowListItemProps } from '../../pages/AppCreationFlow/AppCreationFlowUtil';

interface AppFlowListProps {
    appFlowListItems: AppFlowListItemProps[];
}

export function AppFlowList({ appFlowListItems }: AppFlowListProps) {
    return (
        <div className='app-flow-list-container'>
            <ul className='app-flow-list-ul'>
                {appFlowListItems.map(appItem => (
                    <AppFlowListItem
                        text={appItem.label}
                        checked={appItem.checked}
                        key={appItem.name}
                        selected={appItem.selected}
                    />
                ))}
            </ul>
        </div>
    );
}