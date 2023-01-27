create table venda_forma_pagamento (
	venda_id bigint not null,
	forma_pagamento_id bigint not null,
	
	primary key (venda_id, forma_pagamento_id)
) engine=InnoDB default charset=utf8mb4;

alter table venda_forma_pagamento add constraint fk_venda_forma_pagamento_venda
foreign key (venda_id) references venda (id);

alter table venda_forma_pagamento add constraint fk_venda_forma_pagamento_forma_pagamento
foreign key (forma_pagamento_id) references forma_pagamento (id);