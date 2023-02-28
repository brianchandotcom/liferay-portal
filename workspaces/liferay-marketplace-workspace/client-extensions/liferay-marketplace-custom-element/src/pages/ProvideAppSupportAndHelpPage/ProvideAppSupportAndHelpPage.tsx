import {Header} from '../../components/Header/Header';
import {Input} from '../../components/Input/Input';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {Section} from '../../components/Section/Section';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {TYPES} from '../../manage-app-state/actionTypes';

import './ProvideAppSupportAndHelpPage.scss';
import {
	createProductSpecification,
	createSpecification,
	updateProductSpecification,
} from '../../utils/api';

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

	async function saveAndUpdate(
		productSpecificationId: number,
		key: string,
		title: string,
		value: string,
		action: TYPES
	) {
		const id = await submitSupportURLs(
			productSpecificationId,
			key,
			title,
			value
		);

		dispatch({
			payload: {
				id,
				value,
			},
			type: action,
		});
	}

	async function submitSupportURLs(
		productSpecificationId: number,
		key: string,
		title: string,
		value: string
	): Promise<void> {
		const dataSpecification = await createSpecification({
			body: {
				key,
				title: {en_US: title},
			},
		});
		if (productSpecificationId) {
			updateProductSpecification({
				body: {
					specificationKey: dataSpecification.key,
					value: {en_US: value},
				},
				id: productSpecificationId,
			});

			return;
		}
		else {
			const {id} = await createProductSpecification({
				appId,
				body: {
					productId: appProductId,
					specificationId: dataSpecification.id,
					specificationKey: dataSpecification.key,
					value: {en_US: value},
				},
			});

			return id;
		}
	}

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
				tooltip="More info"
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
				onClickContinue={() => {
					saveAndUpdate(
						supportURL?.id,
						'supportURL',
						'Support URL',
						supportURL?.value,
						TYPES.UPDATE_APP_SUPPORT_URL
					);

					if (publisherWebsiteURL?.value) {
						saveAndUpdate(
							publisherWebsiteURL?.id,
							'publisherWebsiteURL',
							'Publisher Web site URL',
							publisherWebsiteURL?.value,
							TYPES.UPDATE_APP_PUBLISHER_WEBSITE_URL
						);
					}
					if (appUsageTermsURL?.value) {
						saveAndUpdate(
							appUsageTermsURL?.id,
							'appUsageTermsURL',
							'App Usage Terms URL',
							appUsageTermsURL?.value,
							TYPES.UPDATE_APP_USAGE_TERMS_URL
						);
					}
					if (appDocumentationURL?.value) {
						saveAndUpdate(
							appDocumentationURL?.id,
							'appDocumentationURL',
							'App Documentation URL',
							appDocumentationURL?.value,
							TYPES.UPDATE_APP_DOCUMENTATION_URL
						);
					}
					if (appInstallationGuideURL?.value) {
						saveAndUpdate(
							appInstallationGuideURL?.id,
							'appInstallationGuideURL',
							'App Installation Guide URL',
							appInstallationGuideURL?.value,
							TYPES.UPDATE_APP_INSTALLATION_AND_UNINSTALLATION_GUIDE_URL
						);
					}
					onClickContinue();
				}}
				showBackButton={true}
			/>
		</div>
	);
}
