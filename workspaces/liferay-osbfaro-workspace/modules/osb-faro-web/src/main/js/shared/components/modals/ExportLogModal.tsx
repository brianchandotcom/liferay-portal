/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import moment from 'moment';
import React, {useState} from 'react';
import DateRangeInput, {DateRange} from '~/shared/components/DateRangeInput';
import Loading, {Align} from '~/shared/components/Loading';
import Modal from '~/shared/components/modal';
import {downloadDataAsFile} from '~/shared/util/util';

interface IExportLogModalProps {
	description: string;
	fileName: string;
	groupId: string;
	onClose: () => void;
	onSubmit: ({
		fromDate,
		toDate,
	}: {
		fromDate: string;
		toDate: string;
	}) => Promise<any>;
	title: string;
}

const ExportLogModal: React.FC<IExportLogModalProps> = ({
	description,
	fileName,
	groupId,
	onClose,
	onSubmit,
	title,
}) => {
	const [dateRange, setDateRange] = useState<DateRange>({
		end: '',
		start: '',
	});

	const [loading, setLoading] = useState<boolean>(false);

	const isValid = (): boolean =>
		moment(dateRange.end).isValid() && moment(dateRange.start).isValid();

	const {end: toDate, start: fromDate} = dateRange;

	return (
		<Modal className="export-log-modal-root">
			<Modal.Header onClose={onClose} title={title} />

			<Modal.Body>
				<p className="text-secondary">{description}</p>

				<div className="h4">
					{Liferay.Language.get('request-date-range')}
				</div>

				<div className="d-flex">
					<DateRangeInput
						className="w-100"
						groupId={groupId}
						onChange={setDateRange}
						value={dateRange}
					/>

					<ClayButton
						className="button-root ml-2"
						disabled={!isValid()}
						displayType="primary"
						onClick={() => {
							setLoading(true);

							onSubmit({fromDate, toDate})
								.then((data: string) => {
									downloadDataAsFile({
										data,
										name: fileName,
										type: 'text/csv',
									});

									setLoading(false);

									onClose();
								})
								.catch(() => {
									setLoading(false);
								});
						}}
					>
						{loading && <Loading align={Align.Left} />}

						{Liferay.Language.get('download')}
					</ClayButton>
				</div>
			</Modal.Body>
		</Modal>
	);
};

export default ExportLogModal;
