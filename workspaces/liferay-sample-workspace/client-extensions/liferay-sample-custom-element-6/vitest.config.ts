/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import react from '@vitejs/plugin-react-swc';
import {defineConfig} from 'vitest/config';

export default defineConfig({
	plugins: [react()],
	test: {
		environment: 'jsdom',
		globals: true,
		include: ['src/**/*.{test,spec}.{ts,tsx}'],
		setupFiles: ['./src/testSetup.ts'],
	},
});
