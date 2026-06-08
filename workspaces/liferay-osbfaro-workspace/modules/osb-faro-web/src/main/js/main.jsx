/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@clayui/css/lib/css/atlas.css';

import '../css/main.scss';

import './external-scripts';

import React from 'react';
import {createRoot} from 'react-dom/client';

import App from './App';

const root = createRoot(document.getElementById('faroApp'));
root.render(<App />);
