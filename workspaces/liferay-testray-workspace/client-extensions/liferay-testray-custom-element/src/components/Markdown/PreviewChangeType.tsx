/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import MarkdownPreview from '.';

type PreviewInformationProps = {
	data: string;
	displayType: string;
};

const PreviewInformation: React.FC<PreviewInformationProps> = ({
	data,
	displayType,
}) => {
	if (displayType === 'markdown') {
		return <MarkdownPreview markdown={data} />;
	}

	return <p>{data}</p>;
};

export default PreviewInformation;
