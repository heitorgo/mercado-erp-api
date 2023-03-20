set foreign_key_checks = 0;

delete from empresa;
delete from loja;
delete from caixa;
delete from cargo;
delete from funcionario;
delete from venda;
delete from forma_pagamento;
delete from venda_forma_pagamento;

set foreign_key_checks = 1;

alter table empresa auto_increment = 1;
alter table loja auto_increment = 1;
alter table caixa auto_increment = 1;
alter table cargo auto_increment = 1;
alter table funcionario auto_increment = 1;
alter table venda auto_increment = 1;
alter table forma_pagamento auto_increment = 1;

insert into empresa (id, nome, razao_social, data_cadastro, data_atualizacao, ativo) values(1, "Auto Peças Itu", "Auto Peças Itu LTDA", utc_timestamp, utc_timestamp, true);
insert into empresa (id, nome, razao_social, data_cadastro, data_atualizacao, ativo) values(2, "Atelie Mãos de Costura", "Atelie Mãos de Costura LTDA", utc_timestamp, utc_timestamp, true);
insert into empresa (id, nome, razao_social, data_cadastro, data_atualizacao, ativo) values(3, "Mercearia Util", "Mercearia Util LTDA", utc_timestamp, utc_timestamp, true);

insert into loja (id, nome, saldo, empresa_id, data_cadastro, data_atualizacao, ativo) values(1, "Loja 1", 0, 1, utc_timestamp, utc_timestamp, true);
insert into loja (id, nome, saldo, empresa_id, data_cadastro, data_atualizacao, ativo) values(2, "Loja 1", 0, 2, utc_timestamp, utc_timestamp, true);
insert into loja (id, nome, saldo, empresa_id, data_cadastro, data_atualizacao, ativo) values(3, "Loja 1", 0, 3, utc_timestamp, utc_timestamp, true);

insert into cargo (id, titulo, remuneracao, loja_id, data_cadastro, data_atualizacao, ativo) values(1, "Gerente", 5000, 1, utc_timestamp, utc_timestamp, true );
insert into cargo (id, titulo, remuneracao, loja_id, data_cadastro, data_atualizacao, ativo) values(2, "Vendedor", 1800, 1, utc_timestamp, utc_timestamp, true );
insert into cargo (id, titulo, remuneracao, loja_id, data_cadastro, data_atualizacao, ativo) values(3, "Caixa", 1300, 1, utc_timestamp, utc_timestamp, true );
insert into cargo (id, titulo, remuneracao, loja_id, data_cadastro, data_atualizacao, ativo) values(4, "Caixa", 1800, 3, utc_timestamp, utc_timestamp, true );

insert into funcionario (id, nome, cargo_id, data_cadastro, data_atualizacao, ativo) values(1, "Cleber", 1, utc_timestamp, utc_timestamp, true);
insert into funcionario (id, nome, cargo_id, data_cadastro, data_atualizacao, ativo) values(2, "Maria", 3, utc_timestamp, utc_timestamp, true);
insert into funcionario (id, nome, cargo_id, data_cadastro, data_atualizacao, ativo) values(3, "Mateus", 2, utc_timestamp, utc_timestamp, true);
insert into funcionario (id, nome, cargo_id, data_cadastro, data_atualizacao, ativo) values(4, "Pedro", 4, utc_timestamp, utc_timestamp, true);

insert into caixa (id, nome, loja_id, saldo, data_cadastro, data_atualizacao, ativo) values(1, "Caixa Principal", 1, 0, utc_timestamp, utc_timestamp, true);
insert into caixa (id, nome, loja_id, saldo, data_cadastro, data_atualizacao, ativo) values(2, "Caixa Principal", 2, 0, utc_timestamp, utc_timestamp, true);
insert into caixa (id, nome, loja_id, saldo, data_cadastro, data_atualizacao, ativo) values(3, "Caixa Principal", 3, 0, utc_timestamp, utc_timestamp, true);

insert into forma_pagamento (id, titulo, data_cadastro, data_atualizacao, ativo) values(1, "Dinheiro", utc_timestamp, utc_timestamp, true);
insert into forma_pagamento (id, titulo, data_cadastro, data_atualizacao, ativo) values(2, "Débito", utc_timestamp, utc_timestamp, true);
insert into forma_pagamento (id, titulo, data_cadastro, data_atualizacao, ativo) values(3, "Crédito", utc_timestamp, utc_timestamp, true);
insert into forma_pagamento (id, titulo, data_cadastro, data_atualizacao, ativo) values(4, "Pix", utc_timestamp, utc_timestamp, true);

insert into venda (id, valor, descricao, caixa_id, funcionario_id, data_cadastro, data_atualizacao) values(1, 20, "Venda de gás", 3, 4, utc_timestamp, utc_timestamp);
insert into venda (id, valor, descricao, caixa_id, funcionario_id, data_cadastro, data_atualizacao) values(2, 20, "Venda de bolacha", 3, 4, utc_timestamp, utc_timestamp);
insert into venda (id, valor, descricao, caixa_id, funcionario_id, data_cadastro, data_atualizacao) values(3, 5.25, "Venda de parafusos", 1, 3, utc_timestamp, utc_timestamp);
