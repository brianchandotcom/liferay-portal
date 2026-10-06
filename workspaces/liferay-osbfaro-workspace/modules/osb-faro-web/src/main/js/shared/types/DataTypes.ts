export enum DataTypes {
	Boolean = 'BOOLEAN',
	Date = 'DATE',
	Duration = 'DURATION',
	Number = 'NUMBER',
	String = 'STRING',
}

export const DATA_TYPE_ICONS_MAP = {
	[DataTypes.Boolean]: 'check',
	[DataTypes.Date]: 'date',
	[DataTypes.Duration]: 'time',
	[DataTypes.Number]: 'integer',
	[DataTypes.String]: 'text',
};
