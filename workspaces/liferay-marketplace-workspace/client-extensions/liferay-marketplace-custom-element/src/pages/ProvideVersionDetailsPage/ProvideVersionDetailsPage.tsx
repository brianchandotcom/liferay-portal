import {Header} from '../../components/Header/Header';
import {Input} from '../../components/Input/Input';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {Section} from '../../components/Section/Section';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {TYPES} from '../../manage-app-state/actionTypes';
import {saveSpecification} from '../../utils/util';

import './ProvideVersionDetailsPage.scss';

interface ProvideVersionDetailsPageProps {
	onClickBack: () => void;
	onClickContinue: () => void;
}

export function ProvideVersionDetailsPage({
	onClickBack,
	onClickContinue,
}: ProvideVersionDetailsPageProps) {
	const [{appId, appNotes, appProductId, appVersion}, dispatch] =
		useAppContext();

	return (
		<div className="provide-version-details-page-container">
			<div className="provide-version-details-page-header">
				<Header
					description="Define version information for your app. This will inform users about this version’s updates on the storefront."
					title="Provide version details"
				/>
			</div>

			<Section
				label="App Version"
				tooltip="More info"
				tooltipText="More Info"
			>
				<Input
					helpMessage="This is the first version of the app to be published"
					label="Version"
					onChange={({target}) =>
						dispatch({
							payload: {id: appVersion?.id, value: target.value},
							type: TYPES.UPDATE_APP_VERSION,
						})
					}
					placeholder="0.0.0"
					required
					tooltip="version"
					value={appVersion?.value}
				/>

				<Input
					component="textarea"
					label="Notes"
					localized
					onChange={({target}) =>
						dispatch({
							payload: {id: appNotes?.id, value: target.value},
							type: TYPES.UPDATE_APP_NOTES,
						})
					}
					placeholder="Enter app description"
					required
					tooltip="notes"
					value={appNotes?.value}
				/>
			</Section>

			<NewAppPageFooterButtons
				disableContinueButton={!appVersion || !appNotes}
				onClickBack={() => onClickBack()}
				onClickContinue={async () => {
					const versionSpecificationId = await saveSpecification(
						appId,
						appProductId,
						appVersion?.id,
						'version',
						'Version',
						appVersion?.value
					);

					if (versionSpecificationId !== -1) {
						dispatch({
							payload: {
								id: versionSpecificationId,
								value: appVersion.value,
							},
							type: TYPES.UPDATE_APP_VERSION,
						});
					}
					else {
						dispatch({
							payload: {
								id: appVersion?.id,
								value: appVersion.value,
							},
							type: TYPES.UPDATE_APP_VERSION,
						});
					}

					const noteSpecificationId = await saveSpecification(
						appId,
						appProductId,
						appNotes?.id,
						'notes',
						'Notes',
						appNotes?.value
					);

					if (noteSpecificationId !== -1) {
						dispatch({
							payload: {
								id: noteSpecificationId,
								value: appNotes.value,
							},
							type: TYPES.UPDATE_APP_NOTES,
						});
					}
					else {
						dispatch({
							payload: {id: appNotes?.id, value: appNotes.value},
							type: TYPES.UPDATE_APP_NOTES,
						});
					}
					onClickContinue();
				}}
			/>
		</div>
	);
}
