/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const isUserSignedIn = themeDisplay.isSignedIn();

if (!isUserSignedIn) {
    const signInContainer = document.querySelector(".signin-container");
    const signInLink = document.createElement('a');
    const signInMessage = document.createElement('span');
	
	  signInLink.textContent = 'Sign In';
	  signInLink.setAttribute('href', '/home?p_p_id=com_liferay_login_web_portlet_LoginPortlet&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&_com_liferay_login_web_portlet_LoginPortlet_mvcRenderCommandName=%2Flogin%2Flogin&saveLastPath=false');
	  signInMessage.textContent = 'to save your progress';
    
	  signInContainer.appendChild(signInLink);	
	  signInContainer.appendChild(signInMessage);	
} 

