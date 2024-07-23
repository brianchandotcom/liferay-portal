/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useState} from 'react';
import {useObjectPermission} from '~/hooks/data/useObjectPermission';
import useAutofillBuild from '~/hooks/useAutofillBuild';
import useRuns from '~/hooks/useRuns';

import i18n from '../../i18n';
import {Liferay} from '../../services/liferay';
import {testrayBuildImpl} from '../../services/rest';
import Form from '../Form';

type AutofillBuildsProps = {
	setType: (state: 'autofill' | 'compareRuns') => void;
	setVisible: (state: boolean) => void;
};

const AutofillBuilds: React.FC<AutofillBuildsProps> = ({
	setType,
	setVisible,
}) => {
	const {autofillBuild, setBuildA, setBuildB} = useAutofillBuild();
	const [loading, setLoading] = useState<boolean>(false);
	const {setRunA, setRunB} = useRuns();

	const buildsPermission = useObjectPermission('/builds');
	const disbleButtonAutofillBuilds = buildsPermission.canCreate;

	const validateAutofillButton = !(
		autofillBuild?.buildA && autofillBuild?.buildB
	);

	const onAutofill = () => {
		if (!autofillBuild.buildA || !autofillBuild.buildB) {
			return;
		}

		setLoading(true);

		testrayBuildImpl
			.autofill(autofillBuild.buildA, autofillBuild.buildB)
			.then(({caseAmount, testrayRunId1, testrayRunId2}) => {
				Liferay.Util.openToast({
					message: i18n.sub(
						'x-case-results-were-autofilled',
						caseAmount
					),
				});

				setRunA(testrayRunId1);
				setRunB(testrayRunId2);

				setType('compareRuns');

				Liferay.Util.openToast({
					message: i18n.sub('runs-x-and-x-ready-to-be-compared', [
						testrayRunId1,
						testrayRunId2,
					]),
				});
			})
			.then(() => {
				setBuildA(null);
				setBuildB(null);
				setLoading(false);
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
			<div className="align-items-center d-flex justify-content-between">
				<label className="mb-0">
					{i18n.sub('auto-fill-x', 'builds')}
				</label>

				<span
					className="cursor-pointer"
					onClick={() => setVisible(false)}
				>
					<ClayIcon symbol="times" />
				</span>
			</div>

			<Form.Divider />

			<div className="autofill-builds-popover mt-3">
				<ClayLayout.Row>
					<ClayLayout.Col>
						<ClayButton block className="build-buttons">
							{autofillBuild?.buildA
								? `${i18n.translate('build-a')} : ${
										autofillBuild?.buildA
									}`
								: i18n.translate('build-a')}
						</ClayButton>
					</ClayLayout.Col>

					<ClayLayout.Col>
						<ClayButton block className="build-buttons">
							{autofillBuild?.buildB
								? `${i18n.translate('build-b')} : ${
										autofillBuild?.buildB
									}`
								: i18n.translate('build-b')}
						</ClayButton>
					</ClayLayout.Col>
				</ClayLayout.Row>

				<div className="d-flex justify-content-between mt-5">
					<ClayButton
						className="align-items-center d-flex"
						disabled={
							loading ||
							validateAutofillButton ||
							!disbleButtonAutofillBuilds
						}
						displayType="primary"
						onClick={() => onAutofill()}
					>
						{loading && (
							<ClayLoadingIndicator className="mb-0 mr-2 mt-0" />
						)}
						{i18n.translate('auto-fill')}
					</ClayButton>
					<ClayButton
						className="ml-5"
						disabled={loading}
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
