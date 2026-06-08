/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import HelpWidget from '~/shared/components/HelpWidget';
import {Modal} from '~/shared/types';

interface IWrappedComponentProps {
	close: Modal.close;
	currentUserId: string;
	faroSubscriptionIMap: Map<string, any>;
	groupId: string;
	open: Modal.open;
	serverLocation: string;
	workspaceName: string;
}

const withHelpWidget =
	(WrappedComponent: React.ComponentType<IWrappedComponentProps>) =>
	(props: IWrappedComponentProps) => (
		<>
			<WrappedComponent {...props} />

			<HelpWidget groupId={props.groupId} />
		</>
	);

export default withHelpWidget;
