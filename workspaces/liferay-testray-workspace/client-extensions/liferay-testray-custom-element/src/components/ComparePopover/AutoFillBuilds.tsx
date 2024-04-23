/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import {useObjectPermission} from '~/hooks/data/useObjectPermission';
import useAutoFillBuild from '~/hooks/useAutofillBuild';

import i18n from '../../i18n';
import {Liferay} from '../../services/liferay';
import {testrayBuildImpl} from '../../services/rest';
import Form from '../Form';

type AutofillBuildsProps = {
	setVisible: (state: boolean) => void;
};

const AutofillBuilds: React.FC<AutofillBuildsProps> = ({setVisible}) => {
	const {autoFillBuild, setBuildA, setBuildB} = useAutoFillBuild();

	const buildsPermission = useObjectPermission('/builds');
	const disbleButtonAutofillBuilds = buildsPermission.canCreate;

	const validateAutoFillButton = !(
		autoFillBuild?.buildA && autoFillBuild?.buildB
	);

	const onAutoFill = () => {
		if (!autoFillBuild.buildA || !autoFillBuild.buildB) {
			return;
		}

		testrayBuildImpl
			.autofill(autoFillBuild.buildA, autoFillBuild.buildB)
			.then((reponse) =>
				Liferay.Util.openToast({
					message: i18n.sub(
						'x-case-results-were-autofilled',
						reponse.caseAmount
					),
				})
			)
			.then(() => {
				setBuildA(null);
				setBuildB(null);
				setVisible(false);
			})
			.catch(() =>
				Liferay.Util.openToast({
					message: i18n.translate('an-unexpected-error-occurred'),
					type: 'danger',
				})
			);
	};

	return (
		<div className="align-items d-flex flex-column justify-content-between m-3">
			<div>
				<label className="mb-0">
					{i18n.sub('auto-fill-x', 'builds')}
				</label>

				<span
					className="cursor-pointer"
					onClick={() => setVisible(false)}
				></span>
			</div>

			<Form.Divider />

			<div className="mt-3">
				<ClayLayout.Row>
					<ClayLayout.Col>
						<ClayButton
							block
							className="text-uppercase"
							disabled={!autoFillBuild.buildA}
							displayType="primary"
						>
							{autoFillBuild?.buildA
								? `${i18n.translate('build-a')} : ${
										autoFillBuild?.buildA
								  }`
								: i18n.translate('add-build-a')}
						</ClayButton>
					</ClayLayout.Col>

					<ClayLayout.Col>
						<ClayButton
							block
							className="text-uppercase"
							disabled={!autoFillBuild.buildB}
							displayType="primary"
						>
							{autoFillBuild?.buildB
								? `${i18n.translate('run-b')} : ${
										autoFillBuild?.buildB
								  }`
								: i18n.translate('add-build-b')}
						</ClayButton>
					</ClayLayout.Col>
				</ClayLayout.Row>

				<div className="d-flex justify-content-between mt-5">
					<ClayButton
						disabled={
							validateAutoFillButton ||
							!disbleButtonAutofillBuilds
						}
						displayType="primary"
						onClick={() => onAutoFill()}
					>
						{i18n.sub('auto-fill-x', 'builds')}
					</ClayButton>
					<ClayButton
						className="ml-5"
						displayType="secondary"
						onClick={() => {
							setBuildA(null);
							setBuildB(null);
						}}
					>
						{i18n.translate('clear')}
					</ClayButton>
				</div>
			</div>
		</div>
	);
};

export default AutofillBuilds;
