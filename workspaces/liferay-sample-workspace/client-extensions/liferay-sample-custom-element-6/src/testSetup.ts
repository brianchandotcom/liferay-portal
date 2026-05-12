/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as matchers from '@testing-library/jest-dom/matchers';
import {cleanup} from '@testing-library/react';
import {afterEach, expect} from 'vitest';

expect.extend(matchers);

afterEach(() => {
	cleanup();
});
