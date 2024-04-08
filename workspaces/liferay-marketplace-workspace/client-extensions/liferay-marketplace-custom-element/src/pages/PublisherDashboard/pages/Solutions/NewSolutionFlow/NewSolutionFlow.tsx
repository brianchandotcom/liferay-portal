/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Outlet, useLocation, useNavigate} from 'react-router-dom';

import {AppFlowList} from '../../../../../components/NewAppFlowList/AppFlowList';
import {NewAppToolBar} from '../../../../../components/NewAppToolBar/NewAppToolBar';
import {useAccount} from '../../../../../hooks/data/useAccounts';

import './NewSolutionFlow.scss';
import {SOLUTION_FLOW_ITEMS} from '../constants';

const NewSolution = () => {
	const {data: account} = useAccount();
	const location = useLocation();
	const navigate = useNavigate();
	const lastPath = location.pathname.split('/').at(-1);

	const activeIndex = SOLUTION_FLOW_ITEMS.findIndex(
		({path}) => path === lastPath
	);

	const activeRoute = SOLUTION_FLOW_ITEMS[activeIndex];

	const button = {
		back: 'Back',
		continue: 'Continue',
	};

	const onClickButton = (buttonName: string) => {
		const isContinue = buttonName === button.continue;

		SOLUTION_FLOW_ITEMS.map((_, index) => {
			if (index === activeIndex) {
				SOLUTION_FLOW_ITEMS[index].selected = false;

				SOLUTION_FLOW_ITEMS[
					isContinue ? index + 1 : index - 1
				].selected = true;

				isContinue
					? (SOLUTION_FLOW_ITEMS[index].checked = true)
					: (SOLUTION_FLOW_ITEMS[index - 1].checked = false);

				return SOLUTION_FLOW_ITEMS;
			}
		});

		return navigate(
			SOLUTION_FLOW_ITEMS[isContinue ? activeIndex + 1 : activeIndex - 1]
				.path
		);
	};

	return (
		<>
			<NewAppToolBar
				accountImage={account?.logoURL}
				accountName={account?.name as string}
			/>

			<div className="d-flex justify-content-center mt-8">
				<AppFlowList appFlowListItems={SOLUTION_FLOW_ITEMS as any} />

				<div className="ml-8 solutions-body-container">
					<h1 className="header-title mb-4">{activeRoute.title}</h1>

					<div className="header-description">
						{activeRoute.description}
					</div>

					<div className="mt-6">
						<Outlet></Outlet>
					</div>

					<div className="d-flex justify-content-end">
						{activeIndex !== 0 && (
							<ClayButton
								className="mr-4"
								displayType="secondary"
								onClick={() => onClickButton(button.back)}
							>
								Back
							</ClayButton>
						)}
						<ClayButton
							displayType="primary"
							onClick={() => onClickButton(button.continue)}
						>
							Continue
						</ClayButton>
					</div>
				</div>
			</div>
		</>
	);
};

export default NewSolution;
