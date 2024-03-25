import {DashboardEmptyTable} from '../../../components/DashboardTable/DashboardEmptyTable';
import Table from '../../../components/Table/Table';
import {format} from 'date-fns';
import ClayLabel from '@clayui/label';
import Modal from '../../../components/Modal';
import {useModal} from '@clayui/modal';
import PublisherSummaryContent from '../../PublisherGate/components/PublisherSummaryContent';
import {useState} from 'react';
import ClayButton from '@clayui/button';
import {Status} from '@clayui/modal/lib/types';
import {updateUserAdditionalInfos} from '../../../utils/api';
import fetcher from '../../../services/fetcher';
import {Liferay} from '../../../liferay/liferay';
import i18n from '../../../i18n';
import {KeyedMutator} from 'swr';

type AppsTableProps = {
	items: PublisherRequestInfo[];
	mutate: any;
};

const STATUS = {
	completed: 'success',
	inProgress: 'info',
	open: 'secondary',
	rejected: 'danger',
};

const PublisherRequestTable: React.FC<AppsTableProps> = ({items, mutate}) => {
	const {observer, onOpenChange, open} = useModal();
	const [selectedRequest, setSelectedRequest] =
		useState<PublisherRequestInfo>();

	const showModalButtons = selectedRequest?.requestStatus?.key === 'open';

	const selectedRequestStatus = STATUS[
		selectedRequest?.requestStatus?.key as keyof typeof STATUS
	] as Status;

	if (!items?.length) {
		return (
			<DashboardEmptyTable
				description1={i18n.translate(
					'purchase-and-install-new-apps-and-they-will-show-up-here'
				)}
				description2={i18n.translate('click-on-add-apps-to-start')}
				icon="grid"
				title={i18n.translate('no-become-a-publisher-request')}
			/>
		);
	}

	return (
		<div>
			<Table
				columns={[
					{
						key: 'firstName',
						render: (name, {lastName}) => (
							<span className="text-capitalize text-nowrap">{`${name}  ${lastName}`}</span>
						),
						title: i18n.translate('name'),
					},
					{
						key: 'emailAddress',
						title: i18n.translate('email'),
					},
					{
						key: 'requestDescription',
						render: (requestDescription) => (
							<span
								className="text-truncate"
								title={requestDescription}
							>
								{requestDescription}
							</span>
						),
						truncate: true,
						title: i18n.translate('description'),
					},
					{
						key: 'creator',
						render: (creator) => (
							<div>{creator?.name ?? 'Guest'}</div>
						),
						title: i18n.translate('requester'),
					},
					{
						key: 'dateCreated',
						render: (dateCreated) => (
							<span className=" ml-2 text-capitalize text-nowrap">
								{format(new Date(dateCreated), 'MMM dd, yyyy')}
							</span>
						),
						title: i18n.translate('request-created'),
					},
					{
						key: 'requestStatus',
						render: (requestStatus) => (
							<ClayLabel
								className="text-nowrap"
								displayType={
									STATUS[
										requestStatus?.key as keyof typeof STATUS
									] as Status
								}
							>
								{requestStatus?.name}
							</ClayLabel>
						),
						title: i18n.translate('status'),
					},
				]}
				onClickRow={(item: PublisherRequestInfo) => {
					setSelectedRequest(item);
					onOpenChange(true);
				}}
				rows={items}
			/>

			<Modal
				observer={observer}
				size={'lg'}
				title={`Review Request ${selectedRequest?.requestStatus?.name}`}
				visible={open}
				status={selectedRequestStatus}
				last={
					showModalButtons ? (
						<div>
							<ClayButton
								displayType="danger"
								className="mr-3"
								onClick={async () => {
									await fetcher.patch(
										`o/c/requestpublisheraccounts/${Number(
											selectedRequest?.id
										)}`,
										{
											requestStatus: 'rejected',
										}
									);

									mutate(items);

									Liferay.Util.openToast({
										message: i18n.translate('success'),
										type: 'success',
									});

									onOpenChange(false);
								}}
							>
								{i18n.translate('decline')}
							</ClayButton>
							<ClayButton
								displayType="primary"
								onClick={async () => {
									await fetcher.patch(
										`o/c/requestpublisheraccounts/${Number(
											selectedRequest?.id
										)}`,
										{
											requestStatus: 'completed',
										}
									);

									mutate(items);

									Liferay.Util.openToast({
										message: i18n.translate('success'),
										type: 'success',
									});

									onOpenChange(false);
								}}
							>
								{i18n.translate('aprove')}
							</ClayButton>
						</div>
					) : undefined
				}
			>
				<PublisherSummaryContent userInfo={selectedRequest} />
			</Modal>
		</div>
	);
};

export default PublisherRequestTable;
