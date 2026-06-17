import {getSafeRangeSelectors} from 'shared/util/util';
import {Interval, RangeSelectors} from 'shared/types';
import {safeResultToProps} from 'shared/util/mappers';

interface IIndividualMetric {
	trend: {percentage: number};
	value: number;
}

interface IIndividualMetricsResult {
	individualMetric: {
		anonymousIndividualsMetric: IIndividualMetric;
		knownIndividualsMetric: IIndividualMetric;
		totalIndividualsMetric: IIndividualMetric;
	};
}

export const CHART_DATA_ID_1 = 'knownIndividuals';
export const CHART_DATA_ID_2 = 'anonymousIndividuals';

export const LANG_MAP = {
	[CHART_DATA_ID_1]: Liferay.Language.get('known-visitors'),
	[CHART_DATA_ID_2]: Liferay.Language.get('anonymous-visitors'),
};

export const mapPropsToOptions = ({
	channelId,
	interval,
	rangeSelectors,
}: {
	channelId: string;
	interval: Interval;
	rangeSelectors: RangeSelectors;
}) => ({
	variables: {
		channelId,
		interval,
		...getSafeRangeSelectors(rangeSelectors),
	},
});

export const mapResultToProps = safeResultToProps(
	({
		individualMetric: {
			anonymousIndividualsMetric,
			knownIndividualsMetric,
			totalIndividualsMetric,
		},
	}: IIndividualMetricsResult) => ({
		items: [
			{
				...totalIndividualsMetric,
				id: 'totalIndividuals',
				info: undefined as {content: string; title: string} | undefined,
				title: Liferay.Language.get('total-individuals'),
			},
			{
				...knownIndividualsMetric,
				id: 'knownIndividualsMetric',
				info: {
					content: Liferay.Language.get(
						'current-total-of-known-individuals-that-are-tracked-by-analytics-cloud.-an-individual-is-considered-known-if-we-have-any-identifiable-information-about-the-individual'
					),
					title: Liferay.Language.get('known-individuals'),
				},
				title: Liferay.Language.get('known'),
			},
			{
				...anonymousIndividualsMetric,
				id: 'anonymousIndividualsMetric',
				info: {
					content: Liferay.Language.get(
						'current-total-of-anonymous-individuals-that-are-tracked-by-analytics-cloud.-inactive-anonymous-individuals-are-automatically-removed-if-they-dont-have-activities-during-the-retention-period'
					),
					title: Liferay.Language.get('anonymous-individuals'),
				},
				title: Liferay.Language.get('anonymous'),
			},
		].map(({id, info, title, trend: {percentage}, value}) => ({
			change: percentage,
			id,
			info,
			title,
			total: value,
		})),
	})
);
