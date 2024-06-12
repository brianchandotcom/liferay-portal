/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {useNavigate} from 'react-router-dom';

import useRuns from '../../hooks/useRuns';
import i18n from '../../i18n';
import Form from '../Form';

type CompareRunsProps = {
	setVisible: (state: boolean) => void;
};

const CompareRuns: React.FC<CompareRunsProps> = ({setVisible}) => {
	const {compareRuns, setRunA, setRunB} = useRuns();

	const validateCompareButtons = !(compareRuns?.runA && compareRuns?.runB);
	const navigate = useNavigate();

	return (
		<div className="align-items d-flex flex-column justify-content-between m-3">
			<div className="align-items-center d-flex justify-content-between">
				<label className="mb-0">{i18n.sub('compare-x', 'runs')}</label>

				<span
					className="cursor-pointer"
					onClick={() => setVisible(false)}
				>
					<ClayIcon symbol="times" />
				</span>
			</div>

			<Form.Divider />

			<div className="compare-runs-popover mt-3">
				<ClayLayout.Row>
					<ClayLayout.Col>
						<ClayButton block className="runs-buttons">
							{compareRuns?.runA
								? `${i18n.translate('run-a')} : ${
										compareRuns?.runA
									}`
								: i18n.translate('run-a')}
						</ClayButton>
					</ClayLayout.Col>

					<ClayLayout.Col>
						<ClayButton block className="runs-buttons">
							{compareRuns?.runB
								? `${i18n.translate('run-b')} : ${
										compareRuns?.runB
									}`
								: i18n.translate('run-b')}
						</ClayButton>
					</ClayLayout.Col>
				</ClayLayout.Row>

				<div className="d-flex justify-content-between mb-3 mt-4">
					<ClayButton
						disabled={validateCompareButtons}
						displayType="primary"
						onClick={() => {
							navigate(
								`/compare-runs/${compareRuns.runA}/${compareRuns.runB}/teams`
							);

							setVisible(false);
						}}
					>
						{i18n.translate('compare')}
					</ClayButton>

					<ClayButton
						displayType="secondary"
						onClick={() => {
							setRunA(null);
							setRunB(null);
						}}
					>
						{i18n.translate('clear')}
					</ClayButton>
				</div>
			</div>
		</div>
	);
};

export default CompareRuns;
