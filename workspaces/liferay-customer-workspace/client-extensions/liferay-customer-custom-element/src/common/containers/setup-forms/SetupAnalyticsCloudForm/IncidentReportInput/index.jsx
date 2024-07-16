/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import i18n from '../../../../I18n';
import {Input} from '../../../../components';
import useBannedDomains from '../../../../hooks/useBannedDomains';
import {isValidEmail} from '../../../../utils/validations.form';

const IncidentReportInput = ({activation, id}) => {
	const bannedDomains = useBannedDomains(activation.email);

	return (
		<ClayForm>
			<Input
				groupStyle="pb-1"
				helper={i18n.translate(
					'this-user-will-be-the-recepient-of-any-high-priority-communications'
				)}
				label={i18n.translate('incident-report-contact')}
				name={`activations.incidentReportContact[${id}].email`}
				placeholder="user@company.com"
				required
				type="email"
				validations={[(value) => isValidEmail(value, bannedDomains)]}
			/>
		</ClayForm>
	);
};

export default IncidentReportInput;
