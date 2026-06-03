import React from 'react';
import {isEqual} from 'lodash';

function useUpdateValueOnChange<T>(value: T): T | undefined {
	const ref = React.useRef<T>();

	if (!isEqual(value, ref.current)) {
		ref.current = value;
	}

	return ref.current;
}

export function useDeepEqualEffect(
	callback: React.EffectCallback,
	args: React.DependencyList
) {
	React.useEffect(callback, useUpdateValueOnChange(args));
}
