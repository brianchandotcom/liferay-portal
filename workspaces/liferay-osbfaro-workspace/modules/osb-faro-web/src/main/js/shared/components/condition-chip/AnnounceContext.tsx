import React, {createContext, useCallback, useContext, useState} from 'react';

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

export const AnnounceProvider: React.FC<{children: React.ReactNode}> = ({
	children,
}) => {
	const {announce, announcement} = useAnnouncement();

	return (
		<AnnounceContext.Provider value={announce}>
			{children}

			<div aria-live="polite" className="sr-only" role="status">
				{announcement}
			</div>
		</AnnounceContext.Provider>
	);
};
