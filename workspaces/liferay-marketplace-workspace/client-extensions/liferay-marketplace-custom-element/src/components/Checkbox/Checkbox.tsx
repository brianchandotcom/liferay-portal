import {ClayCheckbox} from '@clayui/form';

import './Checkbox.scss'

interface CheckboxProps {
    label?: string;
    description?: string;
    checked: boolean;
    onChange: () => void;
    readOnly?: boolean;
}

export function Checkbox({
    checked,
    description,
    label,
    onChange,
    readOnly = false
}: CheckboxProps) {
    return (
        <div className='checkbox-base-container'>
            <ClayCheckbox
                readOnly={readOnly}
                checked={checked}
                onChange={() => onChange()}
            />
            
            <div className='checkbox-texts-container'>
                <span className='checkbox-label-text'>{label}</span>
                <span className='checkbox-description-text'>
                    {description}
                </span>
            </div>
        </div>
    );
}