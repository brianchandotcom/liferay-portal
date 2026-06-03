import BundleRouter from 'route-middleware/BundleRouter';
import Loading from 'shared/components/Loading';
import React, {lazy, Suspense} from 'react';
import RouteNotFound from 'shared/components/RouteNotFound';
import {Routes} from 'shared/util/router';
import {Switch} from 'react-router-dom';

const AccessTokenList = lazy(
	() => import(/* webpackChunkName: "AccessTokenList" */ './AccessTokenList')
);

interface IApisProps extends React.HTMLAttributes<HTMLDivElement> {
	groupId: string;
}

const DataPrivacy: React.FC<IApisProps> = () => (
	<Suspense fallback={<Loading />}>
		<Switch>
			<BundleRouter
				data={AccessTokenList}
				path={Routes.SETTINGS_APIS_TOKEN_LIST}
			/>

			<RouteNotFound />
		</Switch>
	</Suspense>
);

export default DataPrivacy;
