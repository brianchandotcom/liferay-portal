/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayCheckbox, ClayInput} from '@clayui/form';
import ClayManagementToolbar from '@clayui/management-toolbar';
import ClayModal, {useModal} from '@clayui/modal';
import {useEffect, useState} from 'react';

import './PackageVersionModal.scss';

interface PackageVersionModal {
	appERC: string;
	currentVersions: string[];
	handleClose: () => void;
	handleConfirm: (versions: string[]) => void;
}

export function PackageVersionModal({appERC, currentVersions, handleClose, handleConfirm}: PackageVersionModal) {
	const {observer, onClose} = useModal({
		onClose: handleClose
	});
	const [checkboxVersions, setCheckboxVersions] = useState<CheckboxVersion[]>([]);
	const [value, setValue] = useState('');
	const [versionList, setVersionList] = useState<string[]>(['Liferay DXP 7.4 U1', 'Liferay Portal 7.4 GA1', 'Liferay DXP 7.4 U2', 'Liferay Portal 7.4 GA2', 'Liferay DXP 7.4 U3', 'Liferay Portal 7.4 GA3', 'Liferay DXP 7.4 U4', 'Liferay Portal 7.4 GA4', 'Liferay DXP 7.4 U5', 'Liferay Portal 7.4 GA5', 'Liferay DXP 7.4 U6', 'Liferay Portal 7.4 GA6', 'Liferay DXP 7.4 U7', 'Liferay Portal 7.4 GA7', 'Liferay DXP 7.4 U8', 'Liferay Portal 7.4 GA8', 'Liferay DXP 7.4 U9', 'Liferay Portal 7.4 GA9', 'Liferay DXP 7.4 U10', 'Liferay Portal 7.4 GA10', 'Liferay DXP 7.4 U11']);

	function getSelectedVersions() {
		return checkboxVersions
			.filter((versionCheck) => versionCheck.isChecked)
			.map((versionCheck) => versionCheck.versionName);
	}

	const handleVersionSelection = (selectedVersionName: string) => {
		const versionsChecked = checkboxVersions.map((version) => {
			if (selectedVersionName === version.versionName) {
				version.isChecked = !version.isChecked;

				return version;
			}

			return version;
		}, []);

		setCheckboxVersions(versionsChecked);
	};
	
	useEffect(() => {
		const mapVersions = versionList.map((version) => {
			let isChecked = false;
			if (currentVersions.includes(version)) {
				isChecked = true;
			}

			return {isChecked, versionName: version};
		});

		setCheckboxVersions(mapVersions);
	}, [currentVersions, versionList]);

	return (
		<ClayModal
			center
			className="package-version-modal-container"
			observer={observer}
		>
			<ClayModal.Header>Select Compatible Versions</ClayModal.Header>

			<ClayModal.Body>
				<p>
					Select the versions of Liferay that your app is compatible with.
				</p>

				<ClayManagementToolbar>
					<ClayManagementToolbar.Search onlySearch>
						<ClayInput.Group>
							<ClayInput.GroupItem>
								<ClayInput
									aria-label="Search"
									className="form-control input-group-inset input-group-inset-after"
									onChange={(event) =>
										setValue(event.target.value)
									}
									type="text"
									value={value}
								/>
								<ClayInput.GroupInsetItem after tag="span">
									<ClayButtonWithIcon
										aria-labelledby="search icon"
										displayType="unstyled"
										symbol="search"
										type="submit"
									/>
								</ClayInput.GroupInsetItem>
							</ClayInput.GroupItem>
						</ClayInput.Group>
					</ClayManagementToolbar.Search>
				</ClayManagementToolbar>

				<ClayForm className="modal-form">
					<ClayForm.Group>
						{versionList
							.filter((version) =>
								version.toLowerCase().match(value.toLowerCase())
							)
							.map((version, index) => (
								<ClayCheckbox
									checked={checkboxVersions[index]?.isChecked}
									key={index}
									label={version}
									name={`version-${index}`}
									onChange={(event) => handleVersionSelection(event.target.value)}
									value={version}
								/>
							))
						}
					</ClayForm.Group>
				</ClayForm>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => onClose()}
						>
							Cancel
						</ClayButton>

						<ClayButton
							onClick={() => {
								handleConfirm(getSelectedVersions());
								onClose();
							}}
						>
							Confirm
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
