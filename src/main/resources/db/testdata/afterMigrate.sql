set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from usuario;
delete from usuario_grupo;
delete from restaurante_usuario_responsavel;
delete from pedido;
delete from item_pedido;
delete from foto_produto;

set foreign_key_checks=1;

alter table cidade auto_increment = 1; 
alter table cozinha auto_increment = 1; 
alter table forma_pagamento auto_increment = 1; 
alter table permissao auto_increment = 1; 
alter table grupo auto_increment = 1; 
alter table produto auto_increment = 1; 
alter table restaurante auto_increment = 1; 
alter table usuario auto_increment = 1; 
alter table cidade auto_increment = 1; 
alter table cidade auto_increment = 1; 
alter table pedido auto_increment = 1; 
alter table item_pedido auto_increment = 1; 

insert into estado (id, nome) values (1, 'Rio de Janeiro');
insert into estado (id, nome) values (2, 'São Paulo');
insert into estado (id, nome) values (3 ,'Minas Gerais');

insert into cidade(id, nome, estado_id) values (1, 'Paracambi', 1);
insert into cidade(id, nome, estado_id) values (2, 'Queimados', 1);
insert into cidade(id, nome, estado_id) values (3, 'Japeri', 1);

insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Brasileira');

insert into restaurante(id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, aberto) values (1, 'Quintal DiCasa', 2.99, 3, utc_timestamp, utc_timestamp, true, 1, '26600000', 'Rua Feliciana dos Anjos Teixeira', '582', 'Próximo ao antigo casarão', 'Sabugo', true);

insert into restaurante(id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values(2, 'Mocréia Bressan', 4.99, 2, utc_timestamp, utc_timestamp, true, true);
insert into restaurante(id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, aberto) values(3, 'Putitas', 3.99, 3, utc_timestamp, utc_timestamp, true, true);


insert into forma_pagamento(id, descricao) values(1, 'Cartão de crédito');
insert into forma_pagamento(id, descricao) values(2, 'Cartão de débito');
insert into forma_pagamento(id, descricao) values(3, 'Dinheiro');

insert into restaurante_forma_pagamento(restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 1), (3, 2);

insert into produto(id, nome, descricao, preco, ativo, restaurante_id) values(1, 'T-Bone Steak', '1 bife T-Bone Steak, arroz, farofa, vinagrete, batata-frita', 35.99, 1, 1);
insert into produto(id, nome, descricao, preco, ativo, restaurante_id) values(2, 'Filé de Tilapia', 'Porção que serve 2 pessoas', 45.99, 0, 1);
insert into produto(id, nome, descricao, preco, ativo, restaurante_id) values(3, 'Contra filé com fritas', '1 bife de contra filé, arroz, farofa, vinagrete, batata-frita', 19.99, 1, 2);

insert into grupo (id, nome) values (1, 'Gerente'), (2, 'Vendedor'), (3, 'Secretária'), (4, 'Cadastrador');

insert into permissao(id, nome, descricao) values(1, 'Excluir Restaurante', 'Essa permissão é para excluir um Restaurante, apenas admins podem fazer uso dela');

insert into usuario(id, nome, email, senha, data_cadastro) values(1, 'Nyjah', 'lucca.carlini1998@gmail.com', '123', utc_timestamp);

insert into usuario(id, nome, email, senha, data_cadastro) values(2, 'Chris', 'chris@gmail.com', '123', utc_timestamp);

insert into usuario(id, nome, email, senha, data_cadastro) values(3, 'Torey', 'torey@gmail.com', '123', utc_timestamp);

insert into usuario(id, nome, email, senha, data_cadastro) values(4, 'Paul', 'paul@gmail.com', '123', utc_timestamp);

insert into grupo_permissao(grupo_id, permissao_id) values(1, 1);

insert into usuario_grupo(usuario_id, grupo_id) values(1, 1);

insert into restaurante_usuario_responsavel(restaurante_id, usuario_id) values (1, 1);

insert into pedido(id, codigo, subtotal, taxa_frete, valor_total, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, 
endereco_bairro, status, data_criacao, data_confirmacao, data_entrega, data_cancelamento)
values (1, 'c6ac0f9b-0869-4f8f-8444-c2363a7afb65', 72, 10, 82, 1, 2, 1, 1, '26600000', 'Rua Feliciana dos Anjos Teixeira', '582', null, 'Sabugo', 'CONFIRMADO', '2022-07-08 15:01:20', null, null, null);

insert into item_pedido(id, quantidade, preco_unitario, preco_total, observacao, pedido_id, produto_id)
values(1, 2, 35.99, 72, 'Carne ao ponto', 1, 1);

insert into pedido(id, codigo, subtotal, taxa_frete, valor_total, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, 
endereco_bairro, status, data_criacao, data_confirmacao, data_entrega, data_cancelamento)
values (2, '520c62f8-87d8-451e-8e84-860b86be30e9', 39.99, 5, 44.99, 2, 1, 1, 1, '26600000', 'Rua Feliciana dos AnjosTeixeira', '582', null, 'Sabugo', 'CONFIRMADO', '2022-07-08 02:01:20', null, null, null);

insert into item_pedido(id, quantidade, preco_unitario, preco_total, observacao, pedido_id, produto_id)
values(2, 2, 19.99, 39.99, 'Carne ao ponto', 2, 2);

insert into pedido(id, codigo, subtotal, taxa_frete, valor_total, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, 
endereco_bairro, status, data_criacao, data_confirmacao, data_entrega, data_cancelamento)
values (3, '520c62f8-87d8-451e-9e94-860b86be30e9', 39.99, 5, 44.99, 2, 1, 1, 1, '26600000', 'Rua Feliciana dos AnjosTeixeira', '582', null, 'Sabugo', 'CONFIRMADO', '2022-07-07 15:01:20', null, null, null);

insert into item_pedido(id, quantidade, preco_unitario, preco_total, observacao, pedido_id, produto_id)
values(3, 2, 19.99, 39.99, 'Carne ao ponto', 3, 2);