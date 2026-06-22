export namespace Alert {
	export enum Types {
		Alert = 'ALERT',
		Default = 'DEFAULT',
		Error = 'ERROR',
		Pending = 'PENDING',
		Success = 'SUCCESS',
		Warning = 'WARNING'
	}

	export type AddAlert = ({
		alertType,
		message,
		timeout
	}: {
		alertType: Types;
		message: string;
		timeout?: boolean;
	}) => Promise<any>;

	export type RemoveAlert = () => (action: {
		payload: {
			id: string;
		};
		type: 'REMOVE_ALERT';
	}) => void;
}
