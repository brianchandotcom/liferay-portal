/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import {useEffect, useState} from 'react';
import {createPortal} from 'react-dom';

import AIWizard from '../components/AIWizard';
import DisplayButton from '../components/DisplayButton';
import DisplayIcon from '../components/DisplayIcon';

function App() {
	const [container, setContainer] = useState<{
		sidebar: HTMLElement | null;
		topbar: HTMLElement | null;
	}>({
		sidebar: null,
		topbar: null,
	});

	const modal = useModal({defaultOpen: false});

	useEffect(() => {
		setContainer({
			sidebar: document.querySelector('#productMenuSidebar'),
			topbar: document.querySelector(
				'.control-menu-nav-item.layout-reports-icon'
			),
		});
	}, []);

	return (
		<>
			{modal.open && <AIWizard modal={modal} />}

			{container.topbar &&
				createPortal(
					<DisplayIcon onClick={() => modal.onOpenChange(true)} />,
					container.topbar
				)}

			{container.sidebar &&
				createPortal(
					<DisplayButton onClick={() => modal.onOpenChange(true)} />,
					container.sidebar
				)}
		</>
	);
}

export default App;
