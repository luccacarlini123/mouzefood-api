insert into estado (id, nome) values (1, 'Rio de Janeiro');
insert into estado (id, nome) values (2, 'São Paulo');
insert into estado (id, nome) values (3 ,'Minas Gerais');

insert into cidade(id, nome, estado_id) values (1, 'Paracambi', 1);
insert into cidade(id, nome, estado_id) values (2, 'Queimados', 1);
insert into cidade(id, nome, estado_id) values (3, 'Japeri', 1);

insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Brasileira');

insert into restaurante(nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro) values ('Quintal DiCasa', 2.99, 3, utc_timestamp, utc_timestamp, 1, '26600000', 'Rua Feliciana dos Anjos Teixeira', '582', 'Próximo ao antigo casarão', 'Sabugo');

insert into restaurante(nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values('Mocréia Bressan', 4.99, 2, utc_timestamp, utc_timestamp);
insert into restaurante(nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values('Putitas', 3.99, 3, utc_timestamp, utc_timestamp);


insert into forma_pagamento(id, descricao) values(1, 'Cartão de crédito');
insert into forma_pagamento(id, descricao) values(2, 'Cartão de débito');
insert into forma_pagamento(id, descricao) values(3, 'Dinheiro');

insert into restaurante_forma_pagamento(restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 1), (3, 2);

insert into produto(id, nome, descricao, preco, ativo, restaurante_id) values(1, 'T-Bone Steak', '1 bife T-Bone Steak, arroz, farofa, vinagrete, batata-frita', 35.99, 1, 1);
insert into produto(id, nome, descricao, preco, ativo, restaurante_id) values(2, 'Contra filé com fritas', '1 bife de contra filé, arroz, farofa, vinagrete, batata-frita', 19.99, 1, 2);

insert into grupo(id, nome) values(1, 'ADMIN');

insert into permissao(id, nome, descricao) values(1, 'Excluir Restaurante', 'Essa permissão é para excluir um Restaurante, apenas admins podem fazer uso dela');

insert into usuario(id, nome, email, senha, data_cadastro) values(1, 'Lucca', 'lucca@gmail.com', 'senha123', utc_timestamp);

insert into grupo_permissao(grupo_id, permissao_id) values(1, 1);

insert into usuario_grupo(usuario_id, grupo_id) values(1, 1);