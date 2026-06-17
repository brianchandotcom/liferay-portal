import {fromJS, Map, Record} from 'immutable';

interface IDataSource {
	createDate?: number;
	credentials?: Map<string, any>;
	disabled?: boolean;
	event?: string;
	fileName?: string;
	id?: string;
	name?: string;
	properties?: Map<string, any>;
	provider?: Map<string, any>;
	providerType?: string;
	state?: string;
	status?: string;
	type?: number;
	url?: string;
}

export default class DataSource
	extends Record({
		contactsSelected: false,
		createDate: 0,
		credentials: Map(),
		disabled: false,
		event: null,
		fileName: null,
		id: null,
		name: '',
		properties: null,
		provider: null,
		providerType: '',
		sitesSelected: false,
		state: null,
		status: null,
		type: 1,
		url: null,
	})
	implements IDataSource
{
	declare contactsSelected: boolean;
	declare createDate?: number;
	declare credentials?: Map<string, any>;
	declare disabled?: boolean;
	declare event?: string;
	declare fileName?: string;
	declare id?: string;
	declare name?: string;
	declare properties?: Map<string, any>;
	declare provider?: Map<string, any>;
	declare providerType?: string;
	declare sitesSelected: boolean;
	declare state?: string;
	declare status?: string;
	declare type?: number;
	declare url?: string;

	constructor(props: IDataSource) {
		super(fromJS(props));
	}
}
