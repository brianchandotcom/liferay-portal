import {createContext, useCallback, useContext, useState} from 'react';

export const AnnounceContext = createContext<(message: string) => void>(
	() => {}
);

export const useAnnounce = () => useContext(AnnounceContext);

export const useAnnouncement = () => {
	const [announcement, setAnnouncement] = useState('');

	const announce = useCallback(
		(message: string) =>
			setAnnouncement((previousMessage) =>
				previousMessage === message ? `${message} ` : message
			),
		[]
	);

	return {announce, announcement};
};
