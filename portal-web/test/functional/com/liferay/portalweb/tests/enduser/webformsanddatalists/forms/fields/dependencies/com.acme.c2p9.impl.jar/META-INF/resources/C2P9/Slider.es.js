"use strict";

Liferay.Loader.define("dynamic-data-mapping-form-field-type-c2p9-slider@1.0.0/C2P9/Slider.es", ['module', 'exports', 'require', 'dynamic-data-mapping-form-field-type', 'liferay!frontend-js-react-web$react'], function (module, exports, require) {
  var define = undefined;
  var global = window;
  {
    function _objectWithoutProperties(obj, keys) {
      var target = {};

      for (var i in obj) {
        if (keys.indexOf(i) >= 0) continue;
        if (!Object.prototype.hasOwnProperty.call(obj, i)) continue;
        target[i] = obj[i];
      }

      return target;
    }

    Object.defineProperty(exports, "__esModule", {
      value: true
    });
    exports.default = Slider;
    var _dynamicDataMappingFormFieldType = require("dynamic-data-mapping-form-field-type");
    var _react = _interopRequireWildcard(require("liferay!frontend-js-react-web$react"));
    function _getRequireWildcardCache(e) {
      if ("function" != typeof WeakMap) return null;var r = new WeakMap(),
          t = new WeakMap();return (_getRequireWildcardCache = function (e) {
        return e ? t : r;
      })(e);
    }
    function _interopRequireWildcard(e, r) {
      if (!r && e && e.__esModule) return e;if (null === e || "object" != typeof e && "function" != typeof e) return { default: e };var t = _getRequireWildcardCache(r);if (t && t.has(e)) return t.get(e);var n = { __proto__: null },
          a = Object.defineProperty && Object.getOwnPropertyDescriptor;for (var u in e) if ("default" !== u && {}.hasOwnProperty.call(e, u)) {
        var i = a ? Object.getOwnPropertyDescriptor(e, u) : null;i && (i.get || i.set) ? Object.defineProperty(n, u, i) : n[u] = e[u];
      }return n.default = e, t && t.set(e, n), n;
    }
    function _extends() {
      return _extends = Object.assign ? Object.assign.bind() : function (n) {
        for (var e = 1; e < arguments.length; e++) {
          var t = arguments[e];for (var r in t) ({}).hasOwnProperty.call(t, r) && (n[r] = t[r]);
        }return n;
      }, _extends.apply(null, arguments);
    }

    //# sourceMappingURL=Slider.es.js.map
    function Slider(_ref) {
      let {
        label,
        name,
        onChange,
        predefinedValue,
        readOnly,
        value
      } = _ref,
          otherProps = _objectWithoutProperties(_ref, ["label", "name", "onChange", "predefinedValue", "readOnly", "value"]);
      const [currentValue, setCurrentValue] = (0, _react.useState)(value ? value : predefinedValue);
      return (/*#__PURE__*/_react.default.createElement(_dynamicDataMappingFormFieldType.ReactFieldBase, _extends({
          label: label,
          name: name,
          predefinedValue: predefinedValue
        }, otherProps), /*#__PURE__*/_react.default.createElement("input", {
          className: "ddm-field-slider form-control slider",
          disabled: readOnly,
          id: "myRange",
          max: 100,
          min: 1,
          name: name,
          onInput: event => {
            setCurrentValue(event.target.value);
            onChange(event);
          },
          type: "range",
          value: currentValue ? currentValue : predefinedValue
        }))
      );
    }
  }
});
//# sourceMappingURL=Slider.es.js.map