create table cargo(
	id bigint not null auto_increment,
	titulo varchar(60) not null,
	remuneracao double not null,
    data_cadastro timestamp not null,
    data_atualizacao timestamp not null,
    ativo boolean not null,
    loja_id bigint not null,
    
    primary key (id)
    
)engine=InnoDB default charset=utf8mb4;

alter table cargo add constraint fk_cargo_loja
foreign key (loja_id) references loja (id);