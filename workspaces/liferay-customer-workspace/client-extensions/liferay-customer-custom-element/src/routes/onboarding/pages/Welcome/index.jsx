/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../../../../common/I18n';
import {Button} from '../../../../common/components';
import Layout from '../../../../common/containers/setup-forms/Layout';
import {IntroOnboarding} from '../../../../common/icons';
import {useOnboarding} from '../../context';
import {actionTypes} from '../../context/reducer';
import {ONBOARDING_STEP_TYPES} from '../../utils/constants';
import WelcomeSkeleton from './Skeleton';

const Welcome = () => {
	const [, dispatch] = useOnboarding();

	return (
		<Layout
			className="align-items-center d-flex flex-column pt-6 px-6"
			footerProps={{
				middleButton: (
					<Button
						displayType="primary"
						onClick={() =>
							dispatch({
								payload: ONBOARDING_STEP_TYPES.invites,
								type: actionTypes.CHANGE_STEP,
							})
						}
					>
						{i18n.translate('start-project-setup')}
					</Button>
				),
			}}
			headerProps={{
				greetings: i18n.translate('ready-set-go'),
				title: i18n.translate('let-s-set-up-your-project'),
			}}
		>
			<IntroOnboarding
				className="mb-4 pb-1"
				draggable={false}
				height={237}
				width={331}
			/>

			<p className="mb-0 px-1 text-center text-neutral-8">
				{i18n.translate(
					'we-ll-start-by-adding-any-team-members-to-your-project-and-complete-your-product-activation'
				)}
			</p>
		</Layout>
	);
};

Welcome.Skeleton = WelcomeSkeleton;

export default Welcome;
