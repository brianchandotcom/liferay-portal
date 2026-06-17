import sendRequest from 'shared/util/request';

interface ILifecycle {
	description?: string;
	id: string;
	name?: string;
	segmentId?: string;
}

interface IFetchLifecycles {
	groupId: string;
}

export async function fetchLifecycles({
	groupId,
}: IFetchLifecycles): Promise<ILifecycle[]> {
	return sendRequest({
		method: 'GET',
		path: `contacts/${groupId}/account-lifecycle`,
	});
}

export interface IAccountLifecycle {
	accountId?: string;
	id: string;
}

export interface IAccountLifecycleStageStatus {
	description?: string;
	displayOrder: number;
	endDate?: number | null;
	id: string;
	maxDuration?: number | null;
	stageType: string;
	startDate?: number | null;
}

export interface IAccountLifecycleStatus {
	accountLifecycleStageStatuses?: IAccountLifecycleStageStatus[];
	id: string;
	name: string;
}

interface IFetchOverviewMetrics {
	country?: string;
	groupId?: string;
	industry?: string;
	lifecycleId: string;
}

export async function fetchOverviewMetrics({
	country,
	groupId,
	industry,
	lifecycleId,
}: IFetchOverviewMetrics) {
	return sendRequest({
		data: {
			...(country && {country}),
			...(industry && {industry}),
		},
		method: 'GET',
		path: `contacts/${groupId}/account-lifecycle/${lifecycleId}/overview`,
	});
}

interface IFetchLifecycleStages {
	country?: string;
	groupId?: string;
	industry?: string;
	lifecycleId: string;
}

export async function fetchLifecycleStages({
	country,
	groupId,
	industry,
	lifecycleId,
}: IFetchLifecycleStages) {
	return sendRequest({
		data: {
			...(country && {country}),
			...(industry && {industry}),
		},
		method: 'GET',
		path: `contacts/${groupId}/account-lifecycle/${lifecycleId}/stages`,
	});
}

export async function fetchAccountLifecycles({
	groupId,
}: {
	groupId: string;
}): Promise<IAccountLifecycle[]> {
	return sendRequest({
		method: 'GET',
		path: `contacts/${groupId}/account-lifecycle`,
	});
}
