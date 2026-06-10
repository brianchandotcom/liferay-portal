/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

/**
 * Holds a ref to a container element living inside the widget's shadow root.
 * Clay overlays (modal, dropdowns) portal to `document.body` by default, which
 * escapes the shadow root and loses the injected Atlas CSS. Components read
 * this ref and pass it to Clay's `containerElementRef` so overlays render
 * inside the shadow tree where Atlas applies.
 */
const ShadowPortalContext =
	React.createContext<React.RefObject<Element> | null>(null);

export default ShadowPortalContext;
