// src/main/resources/META-INF/resources/C2P9/Slider.es.js
import React, { useState } from "react";
import { ReactFieldBase } from "@liferay/dynamic-data-mapping-form-field-type";
function Slider({
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
  return /* @__PURE__ */ React.createElement(
    ReactFieldBase,
    {
      label,
      name,
      predefinedValue,
      ...otherProps
    },
    /* @__PURE__ */ React.createElement(
      "input",
      {
        className: "ddm-field-slider form-control slider",
        disabled: readOnly,
        id: "myRange",
        max: 100,
        min: 1,
        name,
        onInput: (event) => {
          setCurrentValue(event.target.value);
          onChange(event);
        },
        type: "range",
        value: currentValue ? currentValue : predefinedValue
      }
    )
  );
}
export {
  Slider
};
