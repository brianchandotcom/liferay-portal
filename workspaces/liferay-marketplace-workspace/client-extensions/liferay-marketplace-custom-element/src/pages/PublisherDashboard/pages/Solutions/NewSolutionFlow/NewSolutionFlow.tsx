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

const solutionFlowItems = [
	{
		checked: false,
		description:
			'Review and accept the legal agreement between you and Liferay before proceeding, You are about to create a new solution submission.',
		label: 'Create',
		path: 'publisher',
		selected: true,
		title: 'Create template',
	},
	{
		checked: false,
		description:
			'Enter your solution details. This information will be used for submission, presentation, customer support, and search capabilities.',
		label: 'Profile',
		path: 'profile',
		selected: false,
		title: 'Define the solution profile',
	},
	{
		checked: false,
		description:
			'Design the storefront for your solution. This will set the information displayed on the solution’s page. This section is dedicated to creating the solution’s header.',
		label: 'Solution Header',
		path: 'header',
		selected: false,
		title: 'Customize solution header',
	},
	{
		checked: false,
		description:
			'Design the storefront for your solution. This will set the information displayed on the solution’s page. This section is dedicated to creating the solution’s detail content.',
		label: 'Solution Details',
		path: 'details',
		selected: false,
		title: 'Customize storefront solutions details',
	},
	{
		checked: false,
		description:
			'Define profile company information for your solution. This will inform users about this version’s updates on the storefront.',
		label: 'Company Profile',
		path: 'company',
		selected: false,
		title: 'Provide company profile details',
	},
	{
		checked: false,
		description:
			'Define contact us information for your solution. This will inform users about this version’s updates on the storefront.',
		label: 'Contact Us',
		path: 'contact',
		selected: false,
		title: 'Provide contact us details',
	},
	{
		checked: false,
		description:
			'Please, review before submitting. Once sent, you will not be able to edit any information until this submission is completely reviewed by Liferay.',
		label: 'Submit',
		path: 'submit',
		selected: false,
		title: 'Review and submit solution',
	},
];

const NewSolution = () => {
	const {data: account} = useAccount();
	const location = useLocation();
	const navigate = useNavigate();
	const lastPath = location.pathname.split('/').at(-1);

	const activeIndex = solutionFlowItems.findIndex(
		({path}) => path === lastPath
	);

	const activeRoute = solutionFlowItems[activeIndex];

	const button = {
		back: 'Back',
		continue: 'Continue',
	};

	const onClickButton = (buttonName: string) => {
		const isContinue = buttonName === button.continue;

		solutionFlowItems.map((_, index) => {
			if (index === activeIndex) {
				solutionFlowItems[index].selected = false;

				solutionFlowItems[
					isContinue ? index + 1 : index - 1
				].selected = true;

				isContinue
					? (solutionFlowItems[index].checked = true)
					: (solutionFlowItems[index - 1].checked = false);

				return solutionFlowItems;
			}
		});

		return navigate(
			solutionFlowItems[isContinue ? activeIndex + 1 : activeIndex - 1]
				.path
		);
	};

	return (
		<div className="">
			<NewAppToolBar
				accountImage={account?.logoURL}
				accountName={account?.name as string}
			/>

			<div className="d-flex justify-content-start mt-8">
				<AppFlowList appFlowListItems={solutionFlowItems as any} />

				<div className="ml-8 solutions-body-container">
					<h1 className="header-title mb-4">{activeRoute.title}</h1>

					<div className="header-description">
						{activeRoute.description}
					</div>

					<div className="mt-6">
						<Outlet></Outlet>
					</div>
				</div>

				<div className="align-items-end d-flex">
					{activeIndex !== 0 && (
						<ClayButton
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
	);
};

export default NewSolution;
