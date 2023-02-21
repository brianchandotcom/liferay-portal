import ClayTable from '@clayui/table';

import circleFill from '../../assets/icons/circle_fill.svg';

import './DashboardTableRow.scss';

import classNames from 'classnames';

import starEmpty from '../../assets/icons/star-empty.svg';
import starFill from '../../assets/icons/star-fill.svg';
import {AppProps} from './DashboardTable';

interface DashboardTableRowProps {
	app: AppProps;
}

export function DashboardTableRow({app}: DashboardTableRowProps) {
	const {
		image,
		name,
		rating,
		status,
		type,
		updatedBy,
		updatedDate,
		updatedResponsible,
		version,
	} = app;

	return (
		<ClayTable.Row>
			<ClayTable.Cell>
				<div className="dashboard-table-row-name-container">
					<img
						alt="App Image"
						className="dashboard-table-row-name-logo"
						src={image}
					/>

					<span className="dashboard-table-row-name-text">
						{name}
					</span>
				</div>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<span className="dashboard-table-row-text">{version}</span>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<span className="dashboard-table-row-text">{type}</span>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<div className="dashboard-table-row-last-updated-container">
					<span className="dashboard-table-row-last-updated-date">
						{updatedDate}
					</span>

					<span className="dashboard-table-row-last-updated-responsible">
						{updatedResponsible}
					</span>
				</div>

				<span className="dashboard-table-row-last-updated-by">
					{updatedBy}
				</span>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<div className="dashboard-table-row-rating-container">
					<span className="dashboard-table-row-rating-text">
						{rating}
					</span>

					<div>
						{Array(5)
							.fill(0)
							.map((_, index) => (
								<img
									className="dashboard-table-row-rating-star"
									key={index}
									src={
										index < Math.floor(Number(rating))
											? starFill
											: starEmpty
									}
								/>
							))}
					</div>
				</div>
			</ClayTable.Cell>

			<ClayTable.Cell>
				<div className="dashboard-table-row-status-container">
					<img
						alt="Circle Fill"
						className={classNames(
							'dashboard-table-row-status-icon',
							{
								'dashboard-table-row-status-icon-hidden':
									status === 'Hidden',
								'dashboard-table-row-status-icon-pending':
									status === 'Pending',
								'dashboard-table-row-status-icon-published':
									status === 'Published',
							}
						)}
						src={circleFill}
					/>

					<span className="dashboard-table-row-published-text">
						{status}
					</span>
				</div>
			</ClayTable.Cell>
		</ClayTable.Row>
	);
}
