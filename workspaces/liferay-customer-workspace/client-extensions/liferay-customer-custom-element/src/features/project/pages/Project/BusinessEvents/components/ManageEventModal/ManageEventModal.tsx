/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloClient} from '@apollo/client/core/ApolloClient';
import {Observer} from '@clayui/modal/lib/types';
import {Formik} from 'formik';
import {IBusinessEvent} from '~/utils/types';

import CancelEventPage from './CancelEventPage';
import RecordGoLiveEventPage from './RecordGoLiveEventPage';

interface IProps {
	accountExternalReferenceCode: string;
	businessEvent: IBusinessEvent;
	client: ApolloClient<any>;
	closeFunction?: (value: boolean) => void;
	modalType: string;
	observer: Observer;
	onCancel: () => void;
	onCompleted: () => void;
}

const ManageEventModal: React.FC<IProps> = ({
	accountExternalReferenceCode,
	businessEvent,
	client,
	closeFunction,
	modalType,
	observer,
	onCancel,
	onCompleted,
}) => {
	return (
		<>
			{modalType === 'cancelEvent' ? (
				<CancelEventPage
					accountExternalReferenceCode={accountExternalReferenceCode}
					businessEvent={businessEvent}
					client={client}
					closeFunction={closeFunction}
					modalType={modalType}
					observer={observer}
					onCancel={onCancel}
				/>
			) : (
				<Formik
					initialValues={{
						businessEvent: {
							actualGoLiveTime: {
								hours: '00',
								minutes: '00',
							},
							lastComment: '',
						},
					}}
					onSubmit={() => {}}
					validateOnChange
				>
					{(formikProps) => (
						<RecordGoLiveEventPage
							accountExternalReferenceCode={
								accountExternalReferenceCode
							}
							businessEvent={businessEvent}
							client={client}
							closeFunction={closeFunction}
							errors={formikProps.errors}
							modalType={modalType}
							observer={observer}
							onCompleted={onCompleted}
							setFieldValue={formikProps.setFieldValue}
							touched={formikProps.touched}
							values={formikProps.values}
						/>
					)}
				</Formik>
			)}
		</>
	);
};

export default ManageEventModal;
