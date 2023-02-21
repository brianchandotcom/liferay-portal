import Select, {StylesConfig} from 'react-select'
import makeAnimated from 'react-select/animated';

import './MultiSelect.scss';
import { FieldBase } from '../FieldBase';
import classNames from 'classnames';
import { useRef, useState } from 'react';

type Item = {
    label: string;
    value: string;
    checked: boolean;
}

interface MultiSelectProps {
    className?: string;
    helpMessage?: string;
    hideFeedback?: boolean;
    label?: string;
    localized?: boolean;
    placeholder?: string;
    onChange: (values: Item[]) => void;
    required?: boolean;
    tooltip?: string;
    items: Item[]
}

const colourStyles: StylesConfig<any, true> = {
    control: (styles) => ({ 
        ...styles,
        border: "2px solid #B1B2B9",
        borderRadius: "8px",
    }),
    multiValue: (styles) => {
      return {
        ...styles,
        backgroundColor: "#E6EBF5",
        color: "#1C3667",
      };
    },
    multiValueRemove: (styles) => ({
    ...styles,
      color: "#1C3667",
      ':hover': {
        backgroundColor: "#1C3667",
        color: 'white',
      },
    }),
  };

export function MultiSelect({
    helpMessage,
    hideFeedback,
    label,
    localized,
    placeholder,
    onChange,
    required,
    tooltip,
    className,
    items,
}: MultiSelectProps) {
    const animatedComponents = makeAnimated();

    return (
        <FieldBase
            className={classNames("multiselect-container", className)}
            helpMessage={helpMessage}
            hideFeedback={hideFeedback}
            label={label}
            localized={localized}
            required={required}
            tooltip={tooltip}
        >
            <Select
                isMulti
                components={animatedComponents}
                placeholder={placeholder}
                styles={colourStyles}
                onChange={(newValue) => newValue && onChange(newValue as Item[])}
                options={items}
            />
        </FieldBase>
    );
}