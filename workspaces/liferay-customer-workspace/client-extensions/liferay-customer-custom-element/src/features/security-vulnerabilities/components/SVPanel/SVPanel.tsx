/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '~/utils/I18n';

import './SVPanel.css';

interface IProps {
	link: String;
	linkText: String;
	text: String;
}

const SVPanel = ({link, linkText, text}: IProps) => {
	return (
		<div className="sv-panel-content">
			<div className="sv-panel-box">
				<p
					className="align-items-start justify-content-start"
					dangerouslySetInnerHTML={{
						__html: i18n.sub(
							text,
							'<a href="' +
								link +
								'">' +
								i18n.translate(linkText) +
								'</a>'
						),
					}}
				/>
			</div>
		</div>
	);
};

export default SVPanel;
