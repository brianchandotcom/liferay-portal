/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/* The New Discussion button is revealed only for users who may create a
   thread. Mirrors the forums-message-list ask button: the forumthreads
   collection actions must be read client-side, as the viewing user. */
const askBtn = fragmentElement.querySelector('#forumsHeroAskBtn');

if (askBtn) {
	Liferay.Util.fetch(Liferay.ThemeDisplay.getPortalURL() + '/o/c/forumthreads/scopes/'
		+ Liferay.ThemeDisplay.getScopeGroupId() + '?page=1&pageSize=1', {
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json'
		},
		method: 'GET'
	})
	.then(function(r) { return r.json(); })
	.then(function(data) {
		if (data && data.actions && (data.actions['post'] || data.actions['create'])) {
			askBtn.style.display = '';
		}
	})
	.catch(function() {});
}
