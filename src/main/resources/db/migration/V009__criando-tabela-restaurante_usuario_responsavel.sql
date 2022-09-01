create table restaurante_usuario_responsavel(
	restaurante_id bigint not null,
    usuario_id bigint not null,
    primary key(usuario_id, restaurante_id)
) engine=InnoDB charset=UTF8MB4;

alter table restaurante_usuario_responsavel add constraint fk_restaurante_id foreign key (restaurante_id) references restaurante (id);
alter table restaurante_usuario_responsavel add constraint fk_usuario_id foreign key (usuario_id) references usuario (id);