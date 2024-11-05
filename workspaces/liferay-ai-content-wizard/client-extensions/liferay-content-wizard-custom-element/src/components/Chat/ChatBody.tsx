import { Liferay } from '../../services/liferay';
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
                isLoadingContent={isLoadingContent}
                fullscreen={fullscreen}
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
                        `/group/liferay-ai-content-wizard/~/control_panel/manage?p_p_id=com_liferay_client_extension_web_internal_portlet_ClientExtensionEntryPortlet_${Liferay.ThemeDisplay.getCompanyId()}_LXC_liferay_content_wizard_settings_custom_element#/create`
                    );

                    onClose();
                },
            }}
        />
    );
}
