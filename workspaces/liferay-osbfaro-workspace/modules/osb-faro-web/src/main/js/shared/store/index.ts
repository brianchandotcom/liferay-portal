import {DEVELOPER_MODE} from 'shared/util/constants';
import {loadState} from './local-storage';

let configureStore = require('./store-prod').default;

if (DEVELOPER_MODE) {
	configureStore = require('./store-dev').default;
}

const store = configureStore(loadState());

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;
