/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';

import Form from '../../../../../../components/MarketplaceForm';

const IconsBlock = () => {
	const {observer, onOpenChange, open} = useModal();

	return (
		<div className="mb-2 p-4">
			<Form.Label className="mt-1" htmlFor="description">
				Add up to 8 icons
			</Form.Label>

			<ClayButton
				className="align-items-center d-flex flex-row justify-content-center mt-3 rounded-lg w-100"
				displayType="secondary"
				onClick={() => onOpenChange(true)}
			>
				<span className="d-flex flex-row inline-item inline-item-before">
					<ClayIcon symbol="plus" />
				</span>
				Choose Icon
			</ClayButton>

			{open && (
				<ClayModal center observer={observer}>
					<ClayModal.Body className="mb-1">
						<h1 className="d-flex justify-content-between">
							Choose an Icon
						</h1>

						<p className="text-black-50">
							Pick the icon you want to use
						</p>

						<div className="align-items-end d-flex justify-content-end mt-8">
							<ClayButton
								className="mr-2"
								displayType="secondary"
								onClick={() => onOpenChange(false)}
							>
								Cancel
							</ClayButton>

							<ClayButton displayType="primary">Save</ClayButton>
						</div>
					</ClayModal.Body>
				</ClayModal>
			)}
		</div>
	);
};

export default IconsBlock;
