/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './Footer.scss';

export function Footer() {
	return (
		<footer className="footer-container">
			<div className="footer-left-container">
				<span className="footer-text">
					© 2022 Liferay Inc. All Rights Reserved
				</span>

				<a className="footer-text" href="#">
					Content Policy
				</a>

				<a className="footer-text" href="#">
					Terms
				</a>

				<a className="footer-text" href="#">
					Privacy
				</a>
			</div>

			<a className="footer-text-bold" href="#">
				Send Feedback
			</a>
		</footer>
	);
}
