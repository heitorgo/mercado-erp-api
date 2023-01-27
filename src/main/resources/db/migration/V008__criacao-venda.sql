create table venda(
	id bigint not null auto_increment,
	valor double not null,
    descricao varchar(60) not null,
    data_cadastro timestamp not null,
    data_atualizacao timestamp not null,
    
    funcionario_id bigint not null,
    caixa_id bigint not null,
    
    primary key (id)
    
)engine=InnoDB default charset=utf8mb4;

alter table venda add constraint fk_venda_funcionario
foreign key (funcionario_id) references funcionario (id);

alter table venda add constraint fk_venda_caixa
foreign key (caixa_id) references caixa (id);