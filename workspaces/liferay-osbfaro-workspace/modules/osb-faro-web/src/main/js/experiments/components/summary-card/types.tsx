export enum Status {
	Completed = 'completed',
	Draft = 'draft',
	FinishedNoWinner = 'finished_no_winner',
	FinishedWinner = 'finished_winner',
	Running = 'running',
	Scheduled = 'scheduled',
	Terminated = 'terminated',
}

export interface IExperimentVariant {
	changes: number;
	confidenceInterval?: number[];
	control: boolean;
	dxpVariantId: string;
	dxpVariantName: string;
	improvement?: number;
	median?: number;
	probabilityToWin?: number;
	trafficSplit: number;
	uniqueVisitors: number;
}

export interface IExperiment {
	dateCreated?: string;
	dateModified?: string;
	dxpExperienceName?: string;
	dxpSegmentName?: string;
	dxpVariants: IExperimentVariant[];
	endDate?: string;
	goal?: {metric: string};
	id: string;
	metrics?: {variantMetrics: IExperimentVariant[]};
	name?: string;
	publishedDXPVariantId?: string;
	startDate?: string;
	status: string;
	trafficSplit?: number;
	winnerDXPVariantId?: string;
}
