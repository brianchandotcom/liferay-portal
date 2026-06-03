import React from 'react';
import {Prompt} from 'react-router';

const NavigationWarning = ({
	message = Liferay.Language.get(
		'you-have-unsaved-changes-that-will-be-discarded-by-navigating-away-from-this-page.-do-you-want-to-leave-and-discard-your-changes'
	),
	...otherProps
}) => (
	<Prompt
		message={nextLocation =>
			nextLocation.pathname === window.location.pathname || message
		}
		{...otherProps}
	/>
);

export default NavigationWarning;
