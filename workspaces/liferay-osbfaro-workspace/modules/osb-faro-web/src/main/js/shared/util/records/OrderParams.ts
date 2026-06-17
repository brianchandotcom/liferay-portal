import {OrderByDirections} from '../constants';
import {Record} from 'immutable';

interface IOrderParams {
	field: string;
	sortOrder: OrderByDirections;
}

export default class OrderParams
	extends Record({
		field: null,
		sortOrder: null,
	})
	implements IOrderParams
{
	declare field: string;
	declare sortOrder: OrderByDirections;

	constructor(props = {}) {
		super(props);
	}
}
