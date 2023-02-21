import './CheckboxDataCard.scss';

import { Tooltip } from '../Tooltip/Tooltip';
import { Checkbox } from '../Checkbox/Checkbox';

interface CheckboxItem {
    checked: boolean;
    description?: string;
    label?: string;
    name: string;
}

export interface BaseCheckboxDataCard {
    checkboxItems: CheckboxItem[];
    icon: string;
    name: string;
    title: string;
    tooltip: string;
    tooltipText: string;
}
interface CheckboxDataCardProps extends BaseCheckboxDataCard {
    onChange: (cardName: string, selectedCheckboxName: string) => void;
}

export function CheckboxDataCard({
    checkboxItems,
    icon,
    name,
    title,
    tooltip,
    tooltipText,
    onChange,
}: CheckboxDataCardProps) {
    return (
        <div className='checkbox-data-card-container'>
            <div className='checkbox-data-card-left-info'>
                <div className='checkbox-data-card-left-info-group'>
                    <span className='checkbox-data-card-left-info-title'>{title}</span>
                    <img className='checkbox-data-card-left-info-icon' src={icon} alt='Person Fill'  />
                </div>

                <Tooltip 
                    tooltip={tooltip}
                    tooltipText={tooltipText}
                />
            </div>

            <div className='checkbox-data-card-checkbox-list-container'>
                {checkboxItems.map(checkboxItem => (
                    <Checkbox
                        checked={checkboxItem.checked}
                        description={checkboxItem.description}
                        label={checkboxItem.label}
                        key={checkboxItem.name}
                        onChange={() => onChange(name, checkboxItem.name)}
                    />
                ))}
            </div>
        </div>
    );
}