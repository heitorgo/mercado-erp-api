set foreign_key_checks = 0;

delete from empresa;
delete from loja;
delete from caixa;
delete from cargo;
delete from funcionario;
delete from venda;
delete from forma_pagamento;
delete from funcionario_cargo;
delete from venda_forma_pagamento;

set foreign_key_checks = 1;

alter table empresa auto_increment = 1;
alter table loja auto_increment = 1;
alter table caixa auto_increment = 1;
alter table cargo auto_increment = 1;
alter table funcionario auto_increment = 1;
alter table venda auto_increment = 1;
alter table forma_pagamento auto_increment = 1;

insert into empresa (id, nome, data_cadastro, data_atualizacao, ativo) values(1, "Auto Peças Itu", utc_timestamp, utc_timestamp, true);
insert into empresa (id, nome, data_cadastro, data_atualizacao, ativo) values(2, "Atelie maos de costura", utc_timestamp, utc_timestamp, true);
insert into empresa (id, nome, data_cadastro, data_atualizacao, ativo) values(3, "Mercearia do seu ze", utc_timestamp, utc_timestamp, true);

insert into loja (id, nome, saldo, empresa_id, data_cadastro, data_atualizacao, ativo) values(1, "Loja 1", 0, 1, utc_timestamp, utc_timestamp, true);
insert into loja (id, nome, saldo, empresa_id, data_cadastro, data_atualizacao, ativo) values(2, "Loja 2", 0, 1, utc_timestamp, utc_timestamp, true);
insert into loja (id, nome, saldo, empresa_id, data_cadastro, data_atualizacao, ativo) values(3, "Loja 1", 0, 2, utc_timestamp, utc_timestamp, true);
insert into loja (id, nome, saldo, empresa_id, data_cadastro, data_atualizacao, ativo) values(4, "Loja 1", 0, 3, utc_timestamp, utc_timestamp, true);

insert into cargo (id, titulo, remuneracao, loja_id, data_cadastro, data_atualizacao, ativo) values(1, "CEO", 20000, 1, utc_timestamp, utc_timestamp, true );
insert into cargo (id, titulo, remuneracao, loja_id, data_cadastro, data_atualizacao, ativo) values(2, "Gerente", 5000, 1, utc_timestamp, utc_timestamp, true );
insert into cargo (id, titulo, remuneracao, loja_id, data_cadastro, data_atualizacao, ativo) values(3, "Vendedor", 1800, 1, utc_timestamp, utc_timestamp, true );
insert into cargo (id, titulo, remuneracao, loja_id, data_cadastro, data_atualizacao, ativo) values(4, "Caixa", 1200, 1, utc_timestamp, utc_timestamp, true );

insert into funcionario (id, nome, data_cadastro, data_atualizacao, ativo) values(1, "Josivalda", utc_timestamp, utc_timestamp, true);
insert into funcionario (id, nome, data_cadastro, data_atualizacao, ativo) values(2, "Cleber", utc_timestamp, utc_timestamp, true);
insert into funcionario (id, nome, data_cadastro, data_atualizacao, ativo) values(3, "Maria", utc_timestamp, utc_timestamp, true);
insert into funcionario (id, nome, data_cadastro, data_atualizacao, ativo) values(4, "Mateus", utc_timestamp, utc_timestamp, true);
insert into funcionario (id, nome, data_cadastro, data_atualizacao, ativo) values(5, "Pedro", utc_timestamp, utc_timestamp, true);
insert into funcionario (id, nome, data_cadastro, data_atualizacao, ativo) values(6, "José", utc_timestamp, utc_timestamp, true);

insert into funcionario_cargo (funcionario_id, cargo_id) values(2,1);
insert into funcionario_cargo (funcionario_id, cargo_id) values(3,2);
insert into funcionario_cargo (funcionario_id, cargo_id) values(4,3);
insert into funcionario_cargo (funcionario_id, cargo_id) values(5,4);
insert into funcionario_cargo (funcionario_id, cargo_id) values(4,4);

insert into caixa (id, nome, loja_id, saldo, data_cadastro, data_atualizacao, ativo) values(1, "Caixa Principal", 1, 0, utc_timestamp, utc_timestamp, true);

insert into forma_pagamento (id, titulo, data_cadastro, data_atualizacao, ativo) values(1, "Dinheiro", utc_timestamp, utc_timestamp, true);
insert into forma_pagamento (id, titulo, data_cadastro, data_atualizacao, ativo) values(2, "Débito", utc_timestamp, utc_timestamp, true);
insert into forma_pagamento (id, titulo, data_cadastro, data_atualizacao, ativo) values(3, "Crédito", utc_timestamp, utc_timestamp, true);
insert into forma_pagamento (id, titulo, data_cadastro, data_atualizacao, ativo) values(4, "Pix", utc_timestamp, utc_timestamp, true);

insert into venda (id, valor, descricao, caixa_id, funcionario_id, data_cadastro, data_atualizacao) values(1, 20, "Venda de gás", 1, 4, utc_timestamp, utc_timestamp);
insert into venda (id, valor, descricao, caixa_id, funcionario_id, data_cadastro, data_atualizacao) values(2, 20, "Venda de bolacha", 1, 4, utc_timestamp, utc_timestamp);
insert into venda (id, valor, descricao, caixa_id, funcionario_id, data_cadastro, data_atualizacao) values(3, 20, "Venda de escova de dente", 1, 4, utc_timestamp, utc_timestamp);
