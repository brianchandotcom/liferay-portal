declare namespace CustomRecords {
	interface Property {
		entityName: string;
		entityType: string;
		id: any;
		label: string;
		name: string;
		options: object[];
		propertyKey: string;
		type: string;
	}

	interface User {
		email: string;
		groupId: string;
		hasPermission: (permissions: Array<string> | string) => boolean;
		id: string;
		isAdmin: () => boolean;
		isMember: () => boolean;
		isOwner: () => boolean;
		name: string;
		status: number;
	}
}
