/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';

import './LicenseAgreement.scss';

const LicenseAgreement = () => (
	<div className="license-agreement-container">
		<div className="border rounded">
			<div className="align-items-baseline d-flex justify-content-center p-5">
				<div className="mx-3">
					<ClayIcon symbol="document-text" />
				</div>

				<h3 className="">Liferay Publisher License Agreement</h3>
			</div>

			<div className="border-top p-5">
				PLEASE READ THE FOLLOWING LIFERAY PUBLISHER PROGRAM LICENSE
				AGREEMENT TERMS AND CONDITIONS CAREFULLY BEFORE DONLOADING OR
				USING THE LIFERAY SOFTWARE OR LIFERAY SERVICES. THESE TERMS AND
				CONDITIONS CONSTITUTE A LEGAL AGREEMENT BETWEEN YOU AND LIFERAY.
			</div>

			<div className="p-5">
				Duis aute irure dolor in reprehenderit in voluptate velit esse
				cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat
				cupidatat non proident, sunt in culpa qui officia deserunt
				mollit anim id est laborum. Cras mattis consectetur purus sit
				amet fermentum. Integer posuere erat a ante venenatis dapibus
				posuere velit aliquet. Fusce dapibus, tellus ac cursus commodo,
				tortor mauris condimentum nibh, ut fermentum massa justo sit
				amet risus. Fusce dapibus, tellus ac cursus commodo, tortor
				mauris condimentum nibh, ut fermentum massa justo sit amet
				risus. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
				Integer posuere erat a ante venenatis dapibus posuere velit
				aliquet. Cras justo odio, dapibus ac facilisis in, egestas eget
				quam.
			</div>
		</div>

		<span className="">
			By clicking on the button &quot;continue&quot; below, I confirm that
			I have read and agree to be bound by the{' '}
			<a href="#">Liferay Publisher Program License Agreement.</a> I also
			confirm that I am of the legal age of majority in the jurisdiction
			where I reside (at least 18 years of age in many countries).
		</span>

		<hr></hr>
	</div>
);

export default LicenseAgreement;
