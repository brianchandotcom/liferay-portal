import {COUNT, getSortFromOrderIOMap} from 'shared/util/pagination';
import {OrderByDirections} from 'shared/util/constants';

export {getMapResultToProps} from 'sites/hocs/mappers/composition-query';

interface IMapPropsArgs {
	channelId: string;
	delta: number;
	id: string;
	orderIOMap: unknown;
	page: number;
	query: string;
}

const mapPropsToOptions = ({
	channelId,
	delta,
	id,
	orderIOMap,
	page,
	query,
}: IMapPropsArgs) => ({
	variables: {
		active: true,
		channelId,
		id,
		keywords: query,
		size: delta,
		sort: getSortFromOrderIOMap(orderIOMap),
		start: (page - 1) * delta,
	},
});

const mapCardPropsToOptions = ({
	channelId,
	id,
}: {
	channelId: string;
	id: string;
}) => ({
	variables: {
		active: true,
		channelId,
		id,
		size: 5,
		sort: {
			column: COUNT,
			type: OrderByDirections.Descending,
		},
		start: 0,
	},
});

export {mapCardPropsToOptions, mapPropsToOptions};
