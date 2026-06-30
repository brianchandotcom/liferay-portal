import {List} from 'immutable';
import {Property} from 'shared/util/records';
import {PropertyTypes} from '../constants';

const createWebProperty = ({
	label,
	name,
}: {
	label: string;
	name: string;
}): Property =>
	new Property({
		entityName: Liferay.Language.get('individual'),
		label,
		name,
		propertyKey: 'web',
		type: PropertyTypes.Behavior,
	});

const WEB_BEHAVIORS = List(
	[
		{label: Liferay.Language.get('click'), name: 'click'},
		{label: Liferay.Language.get('comment'), name: 'comment'},
		{label: Liferay.Language.get('download'), name: 'download'},
		{label: Liferay.Language.get('impression'), name: 'impression'},
		{label: Liferay.Language.get('submit'), name: 'submit'},
		{label: Liferay.Language.get('view-asset'), name: 'viewAsset'},
		{label: Liferay.Language.get('view-page'), name: 'viewPage'},
	].map(createWebProperty)
);

export default WEB_BEHAVIORS;
