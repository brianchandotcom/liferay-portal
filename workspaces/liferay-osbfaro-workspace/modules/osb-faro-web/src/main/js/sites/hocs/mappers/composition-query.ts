import {CompositionTypes} from 'shared/util/constants';
import {getSafeRangeSelectors} from 'shared/util/util';
import {RangeSelectors} from 'shared/types';
import {safeResultToProps} from 'shared/util/mappers';

const getMapResultToProps = (compositionBagName: CompositionTypes) =>
	safeResultToProps(
		({
			[compositionBagName]: {compositions, maxCount, total, totalCount}
		}: {
			[key: string]: {
				compositions: Array<any>;
				maxCount: number;
				total: number;
				totalCount: number;
			};
		}) => ({
			empty: !total,
			items: compositions,
			maxCount,
			total,
			totalCount
		})
	);

interface IMapPropsArgs {
	channelId: string;
	delta: number;
	id: string;
	page: number;
	rangeSelectors: RangeSelectors;
}

const mapPropsToOptions = ({
	channelId,
	delta,
	id,
	page,
	rangeSelectors
}: IMapPropsArgs) => ({
	variables: {
		channelId,
		id,
		size: delta,
		start: (page - 1) * delta,
		...getSafeRangeSelectors(rangeSelectors)
	}
});

const mapCardPropsToOptions = ({
	activeTabId,
	channelId,
	rangeSelectors
}: {
	activeTabId: string;
	channelId: string;
	rangeSelectors: RangeSelectors;
}) => ({
	variables: {
		activeTabId,
		channelId,
		size: 5,
		start: 0,
		...getSafeRangeSelectors(rangeSelectors)
	}
});

export {getMapResultToProps, mapCardPropsToOptions, mapPropsToOptions};
