/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {OperationOption, graphql} from '@apollo/client/react/hoc';
import ClayLink from '@clayui/link';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';
import ReactDOMServer from 'react-dom/server';
import Card from '~/shared/components/Card';
import HeatmapChart from '~/shared/components/HeatmapChart';
import BaseCard from '~/shared/components/base-card';
import BasePage from '~/shared/components/base-page';
import ChartTooltip, {
	Alignments,
	Weights,
} from '~/shared/components/chart-tooltip';
import {ReportContainer} from '~/shared/components/download-report/DownloadPDFReport';
import {compose, withEmpty, withError, withLoading} from '~/shared/hoc';
import VisitorsByTimeQuery from '~/shared/queries/VisitorsByTimeQuery';
import {IBasePageContext} from '~/shared/types';
import {sub} from '~/shared/util/lang';
import URLConstants from '~/shared/util/url-constants';

import {
	mapPropsToOptions,
	mapResultToProps,
} from './mappers/visitors-by-time-query';

export const formatHour = function formatHour(hour: string) {
	const hourAsNumber = parseInt(hour, 10);
	const suffix = hourAsNumber >= 12 ? 'PM' : 'AM';
	let hourDisplay = hourAsNumber;

	if (hourAsNumber === 0) {
		hourDisplay = 12;
	}
	else if (hourAsNumber > 12) {
		hourDisplay = hourAsNumber - 12;
	}

	return `${hourDisplay} ${suffix}`;
};

export const renderTooltip = function renderTooltip({
	column,
	row,
	value,
}: {
	column: string;
	row: string;
	value: number;
}) {
	return ReactDOMServer.renderToString(
		<ChartTooltip
			header={[
				{
					columns: [
						{
							label: `${column} - ${formatHour(row)}`,
							weight: Weights.Semibold,
						},
					],
				},
			]}
			rows={[
				{
					columns: [
						{
							align: Alignments.Center,
							label: sub(Liferay.Language.get('x-visitors'), [
								value.toLocaleString(),
							]) as string,
						},
					],
				},
			]}
		/>
	);
};

const HeatmapChartWithData = compose<any>(
	graphql(VisitorsByTimeQuery, {
		options: mapPropsToOptions,
		props: mapResultToProps,
	} as OperationOption<object, object>),
	withLoading(),
	withError({page: false}),
	withEmpty({
		description: (
			<>
				<span className="mr-1">
					{Liferay.Language.get(
						'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
					)}
				</span>

				<ClayLink
					href={URLConstants.SitesDashboardVisitorsByDayAndTime}
					key="DOCUMENTATION"
					target="_blank"
				>
					{Liferay.Language.get(
						'learn-more-about-visitors-by-day-and-time'
					)}
				</ClayLink>
			</>
		),
		title: Liferay.Language.get(
			'there-are-no-visitors-on-the-selected-period'
		),
	})
)(HeatmapChart);

interface IVisitorsByTimeCardProps extends React.HTMLAttributes<HTMLElement> {
	label: string;
}

const VisitorsByTimeCard: React.FC<IVisitorsByTimeCardProps> = ({
	className,
	label,
}) => {
	const {router} = useContext(
		BasePage.Context as React.Context<IBasePageContext>
	);

	return (
		<BaseCard
			className={className}
			label={label}
			legacyDropdownRangeKey={false}
			reportContainer={ReportContainer.VisitorsByTimeCard}
		>
			{({rangeSelectors}) => (
				<Card.Body>
					<HeatmapChartWithData
						columnAxisFormatter={(col: string) => col.slice(0, 3)}
						rangeSelectors={rangeSelectors}
						renderTooltip={renderTooltip}
						router={router}
						rowAxisFormatter={formatHour}
					/>
				</Card.Body>
			)}
		</BaseCard>
	);
};

VisitorsByTimeCard.propTypes = {
	className: PropTypes.string,
	label: PropTypes.string.isRequired,
};

export default VisitorsByTimeCard;
