/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '~/common/I18n';

import './SVPanel.css';

interface IProps {
	link?: String;
	text: String;
}

const SVPanel = ({link, text}: IProps) => {
	const htmlTags: String[] = [];

	if (link) {
		htmlTags.push(`<a href='${link}'>`);
		htmlTags.push('</a>');
	}

	return (
		<div className="sv-panel-content">
			<div className="sv-panel-box">
				<p
					className="align-items-start justify-content-start"
					dangerouslySetInnerHTML={{
						__html: link
							? i18n.sub(text, htmlTags)
							: i18n.translate(text),
					}}
				/>
			</div>
		</div>
	);
};

export default SVPanel;
