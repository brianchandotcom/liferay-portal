
import { ReactNode } from 'react';
import { FieldBase } from '../FieldBase';
import './Section.scss';

interface SectionProps {
    className?: string;
    description?: string;
    disabled?: boolean;
    label?: string;
    required?: boolean;
    tooltip?: string;
    tooltipText?: string;
    children: ReactNode;
}

export function Section({
    className,
    description,
    disabled = false,
    label,
    required = false,
    tooltip,
    tooltipText,
    children
}: SectionProps) {
    return (
        <FieldBase
            className={className}
            description={description}
            label={label}
            required={required}
            tooltip={tooltip}
            tooltipText={tooltipText}
        >
            {!disabled && (
                <div className='section-divider'></div>
            )}

            {children}
        </FieldBase>
    );
}