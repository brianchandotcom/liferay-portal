import React from 'react';

// This span only stops click propagation (to prevent row selection); it is
// intentionally not an interactive control.

const StopClickPropagation = ({children}: {children: React.ReactNode}) => (
	<span onClick={(event) => event.stopPropagation()}>{children}</span>
);

export default StopClickPropagation;
