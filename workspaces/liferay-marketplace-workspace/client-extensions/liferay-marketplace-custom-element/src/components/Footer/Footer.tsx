/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
