import {Languages} from './types';
import {sub} from 'shared/util/lang';

export function buildLanguages(displayName: string): Languages {
	return {
		connectDescription: sub(
			Liferay.Language.get(
				'connect-your-x-account-to-analytics-cloud-to-start-importing-data'
			),
			[displayName]
		) as string,
		connectTitle: sub(Liferay.Language.get('connect-x'), [
			displayName
		]) as string,
		endpointHelper: sub(
			Liferay.Language.get(
				'this-is-analytics-cloud-url-x-will-redirect-to-after-a-user-authorizes-the-connection'
			),
			[displayName]
		) as string,
		endpointLabel: sub(
			Liferay.Language.get('copy-this-endpoint-url-to-your-x-instance'),
			[displayName]
		) as string,
		tokenLabel: sub(
			Liferay.Language.get('copy-this-token-to-your-x-instance'),
			[displayName]
		) as string
	};
}
