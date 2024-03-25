import {useNavigate} from 'react-router-dom';
import {DashboardEmptyTable} from '../../../components/DashboardTable/DashboardEmptyTable';
import Table from '../../../components/Table/Table';

import OrderStatus from '../../../components/OrderStatus';
import {
	formatDate,
	getProductTypeFromSpecifications,
} from '../../PublishedAppsDashboard/PublishedDashboardPageUtil';
import i18n from '../../../i18n';

type AppsTableProps = {
	items: PublisherRequestInfo[];
};

const AppAdministratorTable: React.FC<AppsTableProps> = ({items}) => {
	const navigate = useNavigate();

	if (!items?.length) {
		return (
			<DashboardEmptyTable
				description1={i18n.translate(
					'purchase-and-install-new-apps-and-they-will-show-up-here'
				)}
				description2={i18n.translate('click-on-add-apps-to-start')}
				icon="grid"
				title={i18n.translate('no-apps-yet')}
			/>
		);
	}

	return (
		<div>
			<Table
				columns={[
					{
						key: 'name',
						render: (name, {thumbnail}) => (
							<div>
								<img
									alt="App Image"
									className="app-details-page-table-icon"
									src={thumbnail}
								/>

								<span className="font-weight-semi-bold ml-2 text-nowrap">
									{name?.en_US}
								</span>
							</div>
						),
						title: i18n.translate('name'),
					},
					{
						key: 'productSpecifications',
						render: (_, {productSpecifications}) => (
							<div className="text-capitalize">
								{getProductTypeFromSpecifications(
									productSpecifications
								)}
							</div>
						),
						title: i18n.translate('app-type'),
					},

					{
						key: 'modifiedDate',
						render: (modifiedDate) => (
							<b>{formatDate(modifiedDate)}</b>
						),
						title: i18n.translate('last-update'),
					},
					{
						key: 'workflowStatusInfo',
						render: (workflowStatusInfo) => (
							<OrderStatus orderStatus={workflowStatusInfo.label}>
								{workflowStatusInfo.label}
							</OrderStatus>
						),
						title: i18n.translate('status'),
					},
				]}
				rows={items}
			/>
		</div>
	);
};

export default AppAdministratorTable;
