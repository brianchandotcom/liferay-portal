import {VerticalNav as l} from '@clayui/core';
import u from '@clayui/icon';
import i from 'react';
function d(n) {
	return n &&
		n.__esModule &&
		Object.prototype.hasOwnProperty.call(n, 'default')
		? n.default
		: n;
}
var p = {exports: {}};

/*!
	Copyright (c) 2018 Jed Watson.
	Licensed under the MIT License (MIT), see
	http://jedwatson.github.io/classnames
*/
(function (n) {
	(function () {
		var a = {}.hasOwnProperty;
		function e() {
			for (var s = '', t = 0; t < arguments.length; t++) {
				var o = arguments[t];
				o && (s = c(s, r(o)));
			}
			return s;
		}
		function r(s) {
			if (typeof s == 'string' || typeof s == 'number') return s;
			if (typeof s != 'object') return '';
			if (Array.isArray(s)) return e.apply(null, s);
			if (
				s.toString !== Object.prototype.toString &&
				!s.toString.toString().includes('[native code]')
			)
				return s.toString();
			var t = '';
			for (var o in s) a.call(s, o) && s[o] && (t = c(t, o));
			return t;
		}
		function c(s, t) {
			return t ? (s ? s + ' ' + t : s + t) : s;
		}
		n.exports
			? ((e.default = e), (n.exports = e))
			: (window.classNames = e);
	})();
})(p);
var m = p.exports;
const f = /* @__PURE__ */ d(m),
	h = {
		'account-management': 'briefcase',
		'pending-orders': '',
		'placed-orders': '',
		'catalog': 'tag',
		'dashboard': 'analytics',
		'orders': 'order-form',
		'quotes': 'order-pencil',
		'returns': 'document-pending',
		'shipments': 'truck',
	};
function v(n) {
	return n.length
		? n.map((a) => {
				var e, r;
				return {
					active: a.active,
					href: a == null ? void 0 : a.href,
					icon:
						(a == null ? void 0 : a.icon) ??
						h[a.friendlyURL.replace('/', '')],
					id: a.id,
					initialExpanded:
						a.active &&
						!!(
							(e = a == null ? void 0 : a.items) != null &&
							e.length
						),
					items:
						(r = a == null ? void 0 : a.items) != null && r.length
							? a.items.map((c) => ((c.isChild = !0), c))
							: [],
					label: a.label,
				};
			})
		: {};
}
function N({entries: n, spritemap: a}) {
	return /* @__PURE__ */ i.createElement(
		'div',
		{className: 'minium-primary-navigation'},

		/* @__PURE__ */ i.createElement(
			l,
			{
				items: v(n),
				spritemap: a,
			},
			(e) => {
				var r;
				return /* @__PURE__ */ i.createElement(
					l.Item,
					{
						active: e.active,
						className: f({
							'has-children': !!(
								(r = e == null ? void 0 : e.items) != null &&
								r.length
							),
							'is-child': !!(e != null && e.isChild),
						}),
						href: e.href,
						initialExpanded: e.initialExpanded,
						items: e.items,
						key: e.id,
					},

					/* @__PURE__ */ i.createElement(
						'span',
						{className: 'align-items-center d-flex'},
						(e == null ? void 0 : e.icon) &&

							/* @__PURE__ */ i.createElement(
								'span',
								{
									className: f('mx-2', 'nav-item-icon', {
										'bg-primary': e.active,
									}),
								},

								/* @__PURE__ */ i.createElement(u, {
									symbol: e.icon,
									spritemap: a,
								})
							),

						/* @__PURE__ */ i.createElement(
							'span',
							{className: 'nav-item-label'},
							e.label
						)
					)
				);
			}
		)
	);
}
export {N as default};
