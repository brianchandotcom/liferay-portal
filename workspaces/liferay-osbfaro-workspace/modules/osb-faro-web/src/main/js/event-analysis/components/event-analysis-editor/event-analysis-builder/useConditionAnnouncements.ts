import {Attribute, Event} from 'event-analysis/utils/types';
import {getConditionName} from 'event-analysis/utils/utils';
import {sub} from 'shared/util/lang';
import {useAttributes} from '../context/attributes';
import {useEffect, useRef} from 'react';

type Condition = {attributeId: string; displayName?: string};

type Conditions = {
	attributes: {[key: string]: Attribute};
	breakdowns: {[key: string]: Condition};
	event?: Event;
	filters: {[key: string]: Condition};
};

const getChangedNames = (
	previousItems: Conditions['breakdowns'],
	items: Conditions['breakdowns'],
	attributes: Conditions['attributes']
): string[] =>
	Object.keys(items)
		.filter((id) => !previousItems[id])
		.map((id) =>
			getConditionName(attributes[items[id].attributeId], items[id])
		);

const getMessages = (previous: Conditions, current: Conditions): string[] => {
	const messages: string[] = [];

	if (previous.event?.id !== current.event?.id) {
		if (previous.event) {
			messages.push(
				sub(Liferay.Language.get('removed-x'), [
					previous.event.displayName || previous.event.name,
				]) as string
			);
		}

		if (current.event) {
			messages.push(
				sub(Liferay.Language.get('added-x'), [
					current.event.displayName || current.event.name,
				]) as string
			);
		}

		return messages;
	}

	[
		...getChangedNames(
			previous.breakdowns,
			current.breakdowns,
			current.attributes
		),
		...getChangedNames(
			previous.filters,
			current.filters,
			current.attributes
		),
	].forEach((name) =>
		messages.push(sub(Liferay.Language.get('added-x'), [name]) as string)
	);

	[
		...getChangedNames(
			current.breakdowns,
			previous.breakdowns,
			previous.attributes
		),
		...getChangedNames(
			current.filters,
			previous.filters,
			previous.attributes
		),
	].forEach((name) =>
		messages.push(sub(Liferay.Language.get('removed-x'), [name]) as string)
	);

	return messages;
};

const useConditionAnnouncements = (
	event: Event | undefined,
	announce: (message: string) => void
) => {
	const {attributes, breakdowns, filters} = useAttributes();

	const previousRef = useRef<Conditions>({
		attributes,
		breakdowns,
		event,
		filters,
	});

	useEffect(() => {
		const current = {attributes, breakdowns, event, filters};

		const messages = getMessages(previousRef.current, current);

		previousRef.current = current;

		if (messages.length) {
			announce(messages.join('. '));
		}
	}, [attributes, breakdowns, event, filters]);
};

export default useConditionAnnouncements;
