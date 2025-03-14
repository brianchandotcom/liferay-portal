/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloClient} from '@apollo/client/core/ApolloClient';
import {Formik} from 'formik';
import {IBusinessEvent} from '~/utils/types';

import CancelEventPage from './CancelEventPage';

interface IProps {
	businessEvent: IBusinessEvent;
	client: ApolloClient<any>;
	closeFunction?: (value: boolean) => void;
	onCancel: () => void;
}

const CancelEventForm: React.FC<IProps> = ({
	businessEvent,
	client,
	closeFunction,
	onCancel,
}) => {
	return (
		<Formik
			initialValues={{comment: ''}}
			onSubmit={() => {}}
			validateOnChange
		>
			{(formikProps) => (
				<CancelEventPage
					businessEvent={businessEvent}
					client={client}
					closeFunction={closeFunction}
					errors={formikProps.errors}
					onCancel={onCancel}
					touched={formikProps.touched}
					values={formikProps.values}
				/>
			)}
		</Formik>
	);
};

export default CancelEventForm;
