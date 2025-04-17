import React, { useState } from "react";
import { ReactFieldBase } from "@liferay/dynamic-data-mapping-form-field-type";

export function Slider({
  label,
  name,
  onChange,
  predefinedValue,
  readOnly,
  value,
  ...otherProps
}) {
  const [currentValue, setCurrentValue] = useState(
    value ? value : predefinedValue
  );

  return (
    <ReactFieldBase
      label={label}
      name={name}
      predefinedValue={predefinedValue}
      {...otherProps}
    >
      <input
        className="ddm-field-slider form-control slider"
        disabled={readOnly}
        id="myRange"
        max={100}
        min={1}
        name={name}
        onInput={(event) => {
          setCurrentValue(event.target.value);
          onChange(event);
        }}
        type="range"
        value={currentValue ? currentValue : predefinedValue}
      />
    </ReactFieldBase>
  );
}
