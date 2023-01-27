create table funcionario_cargo (
	funcionario_id bigint not null,
	cargo_id bigint not null,
	
	primary key (funcionario_id, cargo_id)
) engine=InnoDB default charset=utf8mb4;

alter table funcionario_cargo add constraint fk_funcionario_cargo_funcionario
foreign key (funcionario_id) references funcionario (id);

alter table funcionario_cargo add constraint fk_funcionario_cargo_cargo
foreign key (cargo_id) references cargo (id);