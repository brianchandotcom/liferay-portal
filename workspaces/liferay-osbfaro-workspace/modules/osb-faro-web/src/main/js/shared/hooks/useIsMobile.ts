import {useEffect, useState} from 'react';

/**
 * Same breakpoint Clay's `SidePanel` uses to switch to its mobile overlay,
 * which traps focus while the panel is open.
 */
const getIsMobile = () => document.body.clientWidth < 768;

export const useIsMobile = () => {
	const [isMobile, setIsMobile] = useState(getIsMobile);

	useEffect(() => {
		const handleResize = () => setIsMobile(getIsMobile());

		window.addEventListener('resize', handleResize);

		return () => window.removeEventListener('resize', handleResize);
	}, []);

	return isMobile;
};
