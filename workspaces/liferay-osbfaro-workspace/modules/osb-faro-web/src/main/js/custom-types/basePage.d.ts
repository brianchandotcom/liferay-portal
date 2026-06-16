import {Router} from 'shared/util/constants';

declare namespace BasePage {
	interface Context {
		filters: Object;
		router: Router;
	}
}
