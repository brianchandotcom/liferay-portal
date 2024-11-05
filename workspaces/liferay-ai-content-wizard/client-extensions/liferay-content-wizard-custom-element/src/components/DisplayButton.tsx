/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function DisplayButton({onClick}: {onClick: any}) {
	return (
		<div id="AIButton">
			<div className="button-wrapper">
				<button className="button" onClick={onClick} type="button">
					<span className="icon-monospaced">
						<svg
							aria-hidden="true"
							className="lexicon-icon lexicon-icon-pencil"
							focusable="false"
						>
							<use href="/o/classic-theme/images/clay/icons.svg#stars"></use>
						</svg>
					</span>
					&nbsp; Liferay Assistant
				</button>
				<div className="button-bg"></div>
			</div>
		</div>
	);
}
