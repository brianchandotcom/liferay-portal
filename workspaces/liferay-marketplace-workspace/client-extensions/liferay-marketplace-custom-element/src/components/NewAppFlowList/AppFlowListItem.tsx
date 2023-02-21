import ClayIcon from '@clayui/icon';

import circleFill from '../../assets/icons/circle_fill.svg';
import checkFill from '../../assets/icons/check_fill.svg';
import radioSelected from '../../assets/icons/radio-button-checked-2.svg';

import './AppFlowListItem.scss'
import classNames from 'classnames';

interface AppFlowListItemProps {
    checked?: boolean;
    selected?: boolean;
    text: string;
}

export function AppFlowListItem({
    checked = false,
    selected = false,
    text
}: AppFlowListItemProps) {
    const getIcon = () => {
        if (checked) {
            return checkFill
        }

        if (selected) {
            return radioSelected
        }

        return circleFill
    }

    return (
        <div className='app-flow-list-item-container'>
            <img
                className={classNames('app-flow-list-item-icon', {
                    'app-flow-list-item-icon-checked': checked,
                    'app-flow-list-item-icon-selected': selected,
                })}
                src={getIcon()}
                alt={checkFill ? "check fill" : selected ? "radio selected" : "circle fill"}
            />

            <li className={classNames('app-flow-list-item-text', {
                'app-flow-list-item-text-checked': checked || selected,
            })}>
                {text}
            </li>
        </div>
    );
}