import documentIcon from '../../assets/icons/document_icon.svg';
import downloadIcon from '../../assets/icons/download_icon.svg';
import {Header} from '../../components/Header/Header';
import {NewAppPageFooterButtons} from '../../components/NewAppPageFooterButtons/NewAppPageFooterButtons';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {TYPES} from '../../manage-app-state/actionTypes';

import './CreateNewAppPage.scss';

interface CreateNewAppPageProps {
	onClickContinue: () => void;
}

export function CreateNewAppPage({onClickContinue}: CreateNewAppPageProps) {
	const [_, dispatch] = useAppContext();
	const queryString = window.location.search;

	const urlParams = new URLSearchParams(queryString);

	const catalogId = urlParams.get('catalogId');

	return (
		<div className="create-new-app-container">
			<div className="create-new-app-header">
				<Header
					description="Review and accept the legal agreement between Acme Co. (publisher), you, and Liferay before proceeding, You are about to create a new app submission."
					title="Create new app"
				/>
			</div>

			<div className="create-new-app-card-container">
				<div className="create-new-app-card-header">
					<div className="create-new-app-card-header-left-content">
						<div className="create-new-app-card-header-icon-container">
							<img
								alt="Document Icon"
								className="create-new-app-card-header-icon"
								src={documentIcon}
							/>
						</div>

						<span className="create-new-app-card-header-text">
							Liferay Publisher License Agreement
						</span>
					</div>

					<button className="create-new-app-card-header-button">
						Download
						<img
							alt="Download Icon"
							className="create-new-app-card-header-button-icon"
							src={downloadIcon}
						/>
					</button>
				</div>

				<div className="create-new-app-card-body">
					<span className="create-new-app-card-body-text-primary">
						PLEASE READ THE FOLLOWING LIFERAY PUBLISHER PROGRAM
						LICENSE AGREEMENT TERMS AND CONDITIONS CAREFULLY BEFORE
						DONLOADING OR USING THE LIFERAY SOFTWARE OR LIFERAY
						SERVICES. THESE TERMS AND CONDITIONS CONSTITUTE A LEGAL
						AGREEMENT BETWEEN YOU AND LIFERAY.
					</span>

					<span className="create-new-app-card-body-text-secondary">
						Duis aute irure dolor in reprehenderit in voluptate
						velit esse cillum dolore eu fugiat nulla pariatur.
						Excepteur sint occaecat cupidatat non proident, sunt in
						culpa qui officia deserunt mollit anim id est laborum.
						Cras mattis consectetur purus sit amet fermentum.
						Integer posuere erat a ante venenatis dapibus posuere
						velit aliquet. Fusce dapibus, tellus ac cursus commodo,
						tortor mauris condimentum nibh, ut fermentum massa justo
						sit amet risus. Fusce dapibus, tellus ac cursus commodo,
						tortor mauris condimentum nibh, ut fermentum massa justo
						sit amet risus. Lorem ipsum dolor sit amet, consectetur
						adipiscing elit. Integer posuere erat a ante venenatis
						dapibus posuere velit aliquet. Cras justo odio, dapibus
						ac facilisis in, egestas eget quam.
					</span>
				</div>
			</div>

			<div className="create-new-app-info-footer-container">
				<span className="create-new-app-info-footer">
					By clicking on the button &quot;continue&quot; below, I
					confirm that I have read and agree to be bound by the{' '}
					<a href="#">Liferay Publisher Program License Agreement.</a>{' '}
					I also confirm that I am of the legal age of majority in the
					jurisdiction where I reside (at least 18 years of age in
					many countries).
				</span>
			</div>

			<NewAppPageFooterButtons
				onClickContinue={() => {
					dispatch({
						payload: {
							value: catalogId,
						},
						type: TYPES.UPDATE_CATALOG_ID,
					});

					onClickContinue();
				}}
				showBackButton={false}
			/>
		</div>
	);
}
