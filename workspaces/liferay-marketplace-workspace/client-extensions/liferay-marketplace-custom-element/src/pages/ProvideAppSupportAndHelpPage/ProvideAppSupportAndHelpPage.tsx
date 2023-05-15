import {Header} from '../../components/Header/Header';
import {Input} from '../../components/Input/Input';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {Section} from '../../components/Section/Section';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {TYPES} from '../../manage-app-state/actionTypes';
import {saveSpecification} from '../../utils/util';

import './ProvideAppSupportAndHelpPage.scss';

interface ProvideAppSupportAndHelpPageProps {
	onClickBack: () => void;
	onClickContinue: () => void;
}

export function ProvideAppSupportAndHelpPage({
	onClickBack,
	onClickContinue,
}: ProvideAppSupportAndHelpPageProps) {
	const [
		{
			appDocumentationURL,
			appId,
			appInstallationGuideURL,
			appProductId,
			appUsageTermsURL,
			publisherWebsiteURL,
			supportURL,
		},
		dispatch,
	] = useAppContext();

	return (
		<div className="provide-app-support-and-help-page-container">
			<div className="provide-app-support-and-help-page-header">
				<Header
					description="Inform the support and help references. This will impact how users will experience this app’s customer support and learning."
					title="Provide app support and help"
				/>
			</div>

			<Section
				label="App Support and help"
				tooltip="Define the support and help references. Users can access these resources pre and post purchase to find out more information about your app or solution."
				tooltipText="More Info"
			>
				<Input
					label="Support URL"
					onChange={({target}) =>
						dispatch({
							payload: {
								id: supportURL?.id,
								value: target.value,
							},
							type: TYPES.UPDATE_APP_SUPPORT_URL,
						})
					}
					placeholder="http:// Enter app name"
					required
					value={supportURL?.value}
				/>

				<Input
					label="Publisher website URL"
					onChange={({target}) =>
						dispatch({
							payload: {
								id: publisherWebsiteURL?.id,
								value: target.value,
							},
							type: TYPES.UPDATE_APP_PUBLISHER_WEBSITE_URL,
						})
					}
					placeholder="http:// Enter app name"
					value={publisherWebsiteURL?.value}
				/>

				<Input
					label="App usage terms (EULA) URL"
					onChange={({target}) =>
						dispatch({
							payload: {
								id: appUsageTermsURL?.id,
								value: target.value,
							},
							type: TYPES.UPDATE_APP_USAGE_TERMS_URL,
						})
					}
					placeholder="http:// Enter app name"
					value={appUsageTermsURL?.value}
				/>

				<Input
					label="App documentation URL"
					onChange={({target}) =>
						dispatch({
							payload: {
								id: appDocumentationURL?.id,
								value: target.value,
							},
							type: TYPES.UPDATE_APP_DOCUMENTATION_URL,
						})
					}
					placeholder="http:// Enter app name"
					value={appDocumentationURL?.value}
				/>

				<Input
					label="App installation and uninstallation guide URL"
					onChange={({target}) =>
						dispatch({
							payload: {
								id: appInstallationGuideURL?.id,
								value: target.value,
							},
							type: TYPES.UPDATE_APP_INSTALLATION_AND_UNINSTALLATION_GUIDE_URL,
						})
					}
					placeholder="http:// Enter app name"
					value={appInstallationGuideURL?.value}
				/>
			</Section>

			<NewAppPageFooterButtons
				disableContinueButton={!supportURL?.value}
				onClickBack={() => onClickBack()}
				onClickContinue={async () => {
					const supportURLSpecificationId = await saveSpecification(
						appId,
						appProductId,
						supportURL?.id,
						'supportURL',
						'Support URL',
						supportURL?.value
					);

					if (supportURLSpecificationId !== -1) {
						dispatch({
							payload: {
								id: supportURLSpecificationId,
								value: supportURL.value,
							},
							type: TYPES.UPDATE_APP_SUPPORT_URL,
						});
					}
					else {
						dispatch({
							payload: {
								id: supportURL?.id,
								value: supportURL.value,
							},
							type: TYPES.UPDATE_APP_SUPPORT_URL,
						});
					}

					if (publisherWebsiteURL?.value) {
						const publisherWebsiteURLSpecificationId =
							await saveSpecification(
								appId,
								appProductId,
								publisherWebsiteURL?.id,
								'publisherWebsiteURL',
								'Publisher Web site URL',
								publisherWebsiteURL?.value
							);

						if (publisherWebsiteURLSpecificationId !== -1) {
							dispatch({
								payload: {
									id: publisherWebsiteURLSpecificationId,
									value: publisherWebsiteURL.value,
								},
								type: TYPES.UPDATE_APP_PUBLISHER_WEBSITE_URL,
							});
						}
						else {
							dispatch({
								payload: {
									id: publisherWebsiteURL?.id,
									value: publisherWebsiteURL.value,
								},
								type: TYPES.UPDATE_APP_PUBLISHER_WEBSITE_URL,
							});
						}
					}
					if (appUsageTermsURL?.value) {
						const appUsageTermsURLSpecificationId =
							await saveSpecification(
								appId,
								appProductId,
								appUsageTermsURL?.id,
								'appUsageTermsURL',
								'App Usage Terms URL',
								appUsageTermsURL?.value
							);

						if (appUsageTermsURLSpecificationId !== -1) {
							dispatch({
								payload: {
									id: appUsageTermsURLSpecificationId,
									value: appUsageTermsURL.value,
								},
								type: TYPES.UPDATE_APP_USAGE_TERMS_URL,
							});
						}
						else {
							dispatch({
								payload: {
									id: appUsageTermsURL?.id,
									value: appUsageTermsURL.value,
								},
								type: TYPES.UPDATE_APP_USAGE_TERMS_URL,
							});
						}
					}
					if (appDocumentationURL?.value) {
						const appDocumentationURLSpecificationId =
							await saveSpecification(
								appId,
								appProductId,
								appDocumentationURL?.id,
								'appDocumentationURL',
								'App Documentation URL',
								appDocumentationURL?.value
							);

						if (appDocumentationURLSpecificationId !== -1) {
							dispatch({
								payload: {
									id: appDocumentationURLSpecificationId,
									value: appDocumentationURL.value,
								},
								type: TYPES.UPDATE_APP_DOCUMENTATION_URL,
							});
						}
						else {
							dispatch({
								payload: {
									id: appDocumentationURL?.id,
									value: appDocumentationURL.value,
								},
								type: TYPES.UPDATE_APP_DOCUMENTATION_URL,
							});
						}
					}
					if (appInstallationGuideURL?.value) {
						const appInstallationGuideURLSpecificationId =
							await saveSpecification(
								appId,
								appProductId,
								appInstallationGuideURL?.id,
								'appInstallationGuideURL',
								'App Installation Guide URL',
								appInstallationGuideURL?.value
							);

						if (appInstallationGuideURLSpecificationId !== -1) {
							dispatch({
								payload: {
									id: appInstallationGuideURLSpecificationId,
									value: appInstallationGuideURL.value,
								},
								type: TYPES.UPDATE_APP_INSTALLATION_AND_UNINSTALLATION_GUIDE_URL,
							});
						}
						else {
							dispatch({
								payload: {
									id: appInstallationGuideURL?.id,
									value: appInstallationGuideURL.value,
								},
								type: TYPES.UPDATE_APP_INSTALLATION_AND_UNINSTALLATION_GUIDE_URL,
							});
						}
					}
					onClickContinue();
				}}
				showBackButton={true}
			/>
		</div>
	);
}
