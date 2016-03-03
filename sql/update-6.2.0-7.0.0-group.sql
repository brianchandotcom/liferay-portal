alter table Group_ add groupKey STRING;

update Group_ set groupKey = name;

alter table Group_ add inheritContent BOOLEAN;