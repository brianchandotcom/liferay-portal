import React from 'react';

export enum Alignments {
	Center = 'center',
	Left = 'left',
	Right = 'right'
}

export enum Weights {
	Light = 'light',
	Normal = 'normal',
	Semibold = 'semibold',
	Bold = 'bold'
}

export type Column = {
	align?: Alignments;
	className?: string;
	color?: string;
	colspan?: number;
	label: string | (() => React.ReactNode);
	truncated?: boolean;
	weight?: Weights;
	width?: number;
};
