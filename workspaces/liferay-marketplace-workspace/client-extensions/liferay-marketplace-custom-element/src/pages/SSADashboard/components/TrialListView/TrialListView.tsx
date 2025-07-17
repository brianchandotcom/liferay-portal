import {format} from 'date-fns';
import ListView, {ListViewProps} from '../../../../components/ListView';
import {ManagementToolbarProps} from '../../../../components/ListView/components/ManagementToolbar';
import i18n from '../../../../i18n';
import TrialStatus from '../TrialStatus/TrialStatus';
import ExtensionStatus from '../ExtensionStatus/ExtensionStatus';
import {getExtensionStatusFromTrialSettings} from '../../util';
import {OrderCustomFields} from '../../../../enums/Order';
import {Action} from '../../../../utils/constants';
import {TrialSettings} from '../../enums/SSATrials';
import {SSASettings} from '../../types';

type TrialsListViewProps = {
	actions: Action[];
	isSortable?: boolean;
	listViewProps?: Partial<ListViewProps<PlacedOrder>>;
	managementToolbarProps?: {
		visible?: boolean;
	} & Omit<
		ManagementToolbarProps,
		| 'actions'
		| 'onSelectAllRows'
		| 'rowSelectable'
		| 'tableProps'
		| 'totalItems'
	>;
	resourceUrl: string;
};

export default function TrialListView({
	actions,
	listViewProps,
	managementToolbarProps,
	resourceUrl,
}: TrialsListViewProps) {
	return (
		<ListView<PlacedOrder>
			emptyStateProps={{title: i18n.translate('no-orders-yet')}}
			id="ssa-trials"
			managementToolbarProps={{
				filterSchema: 'administratorOrders',
				...managementToolbarProps,
			}}
			resource={resourceUrl}
			tableProps={{
				actions,
				columns: [
					{
						id: 'placedOrderItems',
						name: 'Project ID',
						render: (_, {customFields, id}) => {
							const trialSettings =
								customFields[OrderCustomFields.TRIAL_SETTINGS];
							const SSASettings =
								trialSettings === ''
									? {projectId: 'none'}
									: (JSON.parse(trialSettings)[
											TrialSettings.SSA_SETTINGS
										] as SSASettings);

							return (
								<span className="font-weight-semi-bold ml-2">
									{SSASettings?.projectId ?? id}
								</span>
							);
						},
					},
					{
						id: 'author',
						name: 'Created By',
						render: (author, {createDate}) => {
							return (
								<div className="d-flex flex-column">
									<span className="dashboard-table-row-text">
										{author}
									</span>

									<span className="dashboard-table-row-purchased-date">
										{new Date(
											createDate
										).toLocaleDateString('en-US', {
											day: 'numeric',
											month: 'short',
											year: 'numeric',
										})}
									</span>
								</div>
							);
						},
						sortable: true,
					},
					{
						id: 'id',
						name: 'Order ID',
						sortable: true,
					},
					{
						id: 'orderTypeExternalReferenceCode',
						name: i18n.translate('type'),
						render: (orderTypeExternalReferenceCode) => {
							return (
								<span className="label label-info">
									{orderTypeExternalReferenceCode}
								</span>
							);
						},
						sortable: true,
					},
					{
						id: 'createDate',
						name: 'End Date',
						render: (_, {customFields}) => {
							return customFields[OrderCustomFields.END_DATE]
								? format(
										new Date(
											customFields[
												OrderCustomFields.END_DATE
											]
										),
										'dd MMM, yyyy'
									).toString()
								: 'DNE';
						},
						sortable: true,
					},
					{
						id: 'orderStatusInfo',
						name: 'Trial Status',
						render: (orderStatusInfo) => (
							<TrialStatus trialStatus={orderStatusInfo?.label} />
						),
					},
					{
						id: 'customFields',
						name: 'Extension Status',
						render: (customFields) => (
							<ExtensionStatus
								extensionStatus={getExtensionStatusFromTrialSettings(
									customFields[
										OrderCustomFields.TRIAL_SETTINGS
									]
								)}
							/>
						),
					},
				],
			}}
			{...listViewProps}
		/>
	);
}
