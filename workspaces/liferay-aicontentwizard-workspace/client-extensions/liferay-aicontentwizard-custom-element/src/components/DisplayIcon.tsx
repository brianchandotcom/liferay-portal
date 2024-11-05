/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function DisplayIcon({onClick}: {onClick: any}) {
	return (
		<li className="control-menu-nav-item">
			<span className="lfr-portal-tooltip" title="Liferay Assistant">
				<a
					className="btn btn-monospaced btn-sm control-menu-nav-link lfr-icon-item taglib-icon"
					data-target="#clayDefaultModal"
					href="#"
					onClick={onClick}
				>
					<span className="icon-monospaced">
						<svg
							aria-hidden="true"
							className="lexicon-icon lexicon-icon-pencil"
							focusable="false"
						>
							<use href="/o/classic-theme/images/clay/icons.svg#stars"></use>
						</svg>
					</span>

					<span className="hide-accessible sr-only taglib-text">
						Liferay Assistant
					</span>
				</a>
			</span>
		</li>
	);
}
