/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from "@clayui/icon";
import { Button as ClayButton } from '@clayui/core';
import i18n from "~/common/I18n";

const DocumentFileItem = ({
	onDelete,
	uploadedFile,
}) => {
	return (
		<div className="document-file-list-item-container"> 
			<div className="document-file-list-item-left-content"> 
				<div className="document-file-list-item-left-content-icon-container">
					<ClayIcon
						aria-label="Document Icon"
						className="document-file-list-item-left-content-icon"
						symbol="document-default"
					/>
				</div>

				<div className="document-file-list-item-left-content-text-container">
					<span className="d-flex document-file-list-item-left-content-text-file-name">
						{uploadedFile?.fileName}
					</span>

					<span className="document-file-list-item-left-content-text-file-size">
						{String(uploadedFile?.readableSize)}
					</span>
				</div>
			</div>

			<ClayButton 
				aria-label="Remove"
				className="document-file-list-item-button"
				onClick={() => onDelete(uploadedFile)}
			>
				{i18n.translate('remove')}
			</ClayButton>
		</div>
	);
}

export default DocumentFileItem;