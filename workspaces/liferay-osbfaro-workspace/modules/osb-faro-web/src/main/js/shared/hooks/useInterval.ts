import {useEffect} from 'react';

export const useInterval = <T>(tickFn: () => T, delay: number = 0): void => {
	let intervalId: ReturnType<typeof setInterval>;

	useEffect(() => {
		intervalId = setInterval(tickFn, delay);

		return () => {
			clearInterval(intervalId);
		};
	}, [delay]);
};
