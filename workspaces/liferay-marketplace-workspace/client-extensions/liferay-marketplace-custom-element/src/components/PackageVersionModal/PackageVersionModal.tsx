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
import i18n from '../../i18n';
import {useAppContext} from '../../manage-app-state/AppManageState';
import {TYPES} from '../../manage-app-state/actionTypes';
import {getProductById} from '../../utils/api';
import {getCustomFieldValue} from '../../utils/customFieldUtil';

interface PackageVersionModal {
	appProductId: number;
	currentVersions: string[];
	handleClose: () => void;
	handleConfirm: (versions: string[]) => void;
}

export function PackageVersionModal({
	appProductId,
	currentVersions,
	handleClose,
	handleConfirm,
}: PackageVersionModal) {
	const [, dispatch] = useAppContext();
	const {observer, onClose} = useModal({
		onClose: handleClose,
	});
	const [checkboxVersions, setCheckboxVersions] = useState<CheckboxVersion[]>(
		[]
	);
	const [versionSelected, setVersionSelected] = useState('');
	const [versionList, setVersionList] = useState<any>([]);

	function getSelectedVersions() {
		return checkboxVersions
			.filter((versionCheck) => versionCheck.isChecked)
			.map((versionCheck) => versionCheck.versionName);
	}

	const handleVersionSelection = (selectedVersionName: string) => {
		const versionsChecked = checkboxVersions.map((version) => {
			if (selectedVersionName === version.versionName) {
				version.isChecked = !version.isChecked;
			}

			return version;
		});

		dispatch({
			payload: {
				versionName: selectedVersionName,
			},
			type: TYPES.UPLOAD_BUILD_PACKAGE_FILES,
		});

		setCheckboxVersions(versionsChecked);
	};

	const getProductVersionList = async () => {
		const product = await getProductById({
			productId: appProductId,
		});

		const versionList = getCustomFieldValue(
			product.customFields ?? [],
			'Liferay Version'
		);

		setVersionList(versionList);
	};

	useEffect(() => {
		getProductVersionList();
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	useEffect(() => {
		const mapVersions = versionList.map((version: string) => {
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
			<ClayModal.Header>
				{i18n.translate('select-compatible-versions')}
			</ClayModal.Header>

			<ClayModal.Body>
				<p>
					{i18n.translate(
						'select-the-versions-of-liferay-that-your-app-is-compatible-with'
					)}
				</p>

				<ClayManagementToolbar>
					<ClayManagementToolbar.Search onlySearch>
						<ClayInput.Group>
							<ClayInput.GroupItem>
								<ClayInput
									aria-label="Search"
									className="form-control input-group-inset input-group-inset-after"
									onChange={(event) =>
										setVersionSelected(event.target.value)
									}
									type="text"
									value={versionSelected}
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
							.filter((version: string) =>
								version
									.toLowerCase()
									.match(versionSelected.toLowerCase())
							)
							.map((version: string, index: number) => (
								<ClayCheckbox
									checked={checkboxVersions[index]?.isChecked}
									key={index}
									label={version}
									name={`version-${index}`}
									onChange={(event) =>
										handleVersionSelection(
											event.target.value
										)
									}
									value={version}
								/>
							))}
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
							{i18n.translate('cancel')}
						</ClayButton>

						<ClayButton
							onClick={() => {
								handleConfirm(getSelectedVersions());
								onClose();
							}}
						>
							{i18n.translate('confirm')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
