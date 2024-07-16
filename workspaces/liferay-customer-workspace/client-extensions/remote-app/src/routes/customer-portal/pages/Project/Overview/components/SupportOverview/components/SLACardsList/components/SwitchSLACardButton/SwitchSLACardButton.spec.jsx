/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {render, screen} from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import {vi} from 'vitest';
import SwitchButton from '.';

describe('SwitchButton', () => {
	it('changes SlaCard when clicking the button', async () => {
		const functionMock = vi.fn();
		const user = userEvent.setup();

		render(<SwitchButton handleClick={functionMock} />);

		expect(screen.getByRole('button')).toBeInTheDocument();
		await user.click(screen.getByRole('button'));
		expect(functionMock).toHaveBeenCalled();
	});
});
