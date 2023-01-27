create table loja(
	id bigint not null auto_increment,
	nome varchar(60) not null,
	saldo double not null,
    data_cadastro timestamp not null,
    data_atualizacao timestamp not null,
    ativo boolean not null,
    empresa_id bigint not null,
    
    primary key (id)
    
)engine=InnoDB default charset=utf8mb4;

alter table loja add constraint fk_loja_empresa
foreign key (empresa_id) references empresa (id);