/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../../services/liferay';
import ModalContent from '../ModalContent';
import NoSettingsEmptyState from '../NoSettingsEmptyState';

type ContentWizardProps = {
	configured: boolean;
	fullscreen: boolean;
	isLoading: boolean;
	isLoadingContent: boolean;
	messages: any[];
	onClose: () => void;
	onSelectAsset: (asset: any) => void;
};

export default function ChatBody({
	configured,
	fullscreen,
	isLoading,
	isLoadingContent,
	messages,
	onClose,
	onSelectAsset,
}: ContentWizardProps) {
	if (isLoading) {
		return <b>Loading...</b>;
	}

	if (configured) {
		return (
			<ModalContent
				fullscreen={fullscreen}
				isLoadingContent={isLoadingContent}
				messages={messages}
				onSelectAsset={onSelectAsset}
			/>
		);
	}

	return (
		<NoSettingsEmptyState
			buttonProps={{
				onClick: () => {
					Liferay.Util.navigate(
						`/group/guest/~/control_panel/manage?p_p_id=com_liferay_client_extension_web_internal_portlet_ClientExtensionEntryPortlet_${Liferay.ThemeDisplay.getCompanyId()}_LXC_liferay_aicontentwizard_custom_element_settings#/create`
					);

					onClose();
				},
			}}
		/>
	);
}
