/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {useLocation} from 'react-router-dom';

import createdProjectIcon from '../../../assets/images/created_project.svg';
import {Liferay} from '../../../liferay/liferay';
import {getSiteURL} from '../../../utils/site';

type GetSolutionFinishProps = {
	product: DeliveryProduct;
};

enum States {
	HOLD,
	PROCESSING,
}

const trialLabels = {
	[States.HOLD]: {
		body: 'You will be notified once the process begins, check your email for instructions.',
		title: 'project creation is on hold',
	},
	[States.PROCESSING]: {
		body: 'Expect two emails in 10 minutes or less to verify your project and extension environments are ready.',
		title: 'project is being created now.',
	},
};

const GetSolutionFinish: React.FC<GetSolutionFinishProps> = ({product}) => {
	const {search} = useLocation();

	const holdState = new URLSearchParams(search).get('state') === 'hold';

	const labels = trialLabels[holdState ? States.HOLD : States.PROCESSING];

	return (
		<div className="purchased-solutions-container" style={{marginTop: 100}}>
			<div className="align-items-center d-flex flex-column justify-content-center">
				<img
					alt="project icon"
					className="gate-card-image mb-6"
					src={createdProjectIcon}
				/>

				<h1 className="col-10 mb-2 mt-5 text-center">
					Your&nbsp;
					<span className="created-project-cart-title">
						{product?.name}
					</span>
					&nbsp;{labels.title}
				</h1>

				<span className="col-10 mt-4 text-center">{labels.body}</span>

				<ClayButton
					className="mt-6 py-3"
					onClick={() => {
						Liferay.Util.navigate(
							getSiteURL() + '/customer-dashboard/#/solutions'
						);
					}}
				>
					Return to Dashboard
					<span className="ml-3">
						<ClayIcon symbol="order-arrow-right" />
					</span>
				</ClayButton>
			</div>
		</div>
	);
};

export default GetSolutionFinish;
