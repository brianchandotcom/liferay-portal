import {Header} from '../../components/Header/Header';
import {Input} from '../../components/Input/Input';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {Section} from '../../components/Section/Section';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {TYPES} from '../../manage-app-state/actionTypes';
import {createProductSpecification, createSpecification} from '../../utils/api';

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
							payload: {
								value: target.value,
							},
							type: TYPES.UPDATE_APP_VERSION,
						})
					}
					placeholder="0.0.0"
					required
					tooltip="version"
					value={appVersion}
				/>

				<Input
					component="textarea"
					label="Notes"
					localized
					onChange={({target}) =>
						dispatch({
							payload: {
								value: target.value,
							},
							type: TYPES.UPDATE_APP_NOTES,
						})
					}
					placeholder="Enter app description"
					required
					tooltip="notes"
					value={appNotes}
				/>
			</Section>

			<NewAppPageFooterButtons
				disableContinueButton={!appVersion || !appNotes}
				onClickBack={() => onClickBack()}
				onClickContinue={() => {
					const submitVersionDatails = async () => {
						const dataSpecification = await createSpecification({
							body: {
								key: 'version',
								title: {en_US: 'Version'},
							},
						});

						createProductSpecification({
							appId,
							body: {
								productId: appProductId,
								specificationId: dataSpecification.id,
								specificationKey: dataSpecification.key,
								value: {en_US: appVersion},
							},
						});
					};

					const submitNotesDatails = async () => {
						const dataSpecification = await createSpecification({
							body: {
								key: 'notes',
								title: {en_US: 'Notes'},
							},
						});

						createProductSpecification({
							appId,
							body: {
								productId: appProductId,
								specificationId: dataSpecification.id,
								specificationKey: dataSpecification.key,
								value: {en_US: appNotes},
							},
						});
					};

					submitVersionDatails();
					submitNotesDatails();
					onClickContinue();
				}}
			/>
		</div>
	);
}
