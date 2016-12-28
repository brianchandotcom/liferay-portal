alter table Group_ add groupKey VARCHAR(150) null;

update Group_ set groupKey = name;

alter table Group_ add inheritContent BOOLEAN;