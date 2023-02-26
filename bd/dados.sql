-- INSERTS --

-- usuarios --
-- [1] - Companhia

INSERT INTO AVIACAO.USUARIO (id_usuario, login, senha, nome, tipo)
	VALUES (AVIACAO.seq_usuario.nextval, 'tam123', 'senhasecreta', 'companhia tam', '1');
INSERT INTO AVIACAO.USUARIO (id_usuario, login, senha, nome, tipo)
	VALUES (AVIACAO.seq_usuario.nextval, 'gol123', 'senhagol', 'companhia gol', '1');
INSERT INTO AVIACAO.USUARIO (id_usuario, login, senha, nome, tipo)
	VALUES (AVIACAO.seq_usuario.nextval, 'azul123', 'senhaazul', 'companhia azul', '1');
INSERT INTO AVIACAO.USUARIO (id_usuario, login, senha, nome, tipo)
	VALUES (AVIACAO.seq_usuario.nextval, 'fly123', 'senhafly', 'companhia fly emirates', '1');
INSERT INTO AVIACAO.USUARIO (id_usuario, login, senha, nome, tipo)
	VALUES (AVIACAO.seq_usuario.nextval, 'american123', 'senhaamerican', 'companhia american airlines', '1');

-- [2] - Comprador

INSERT INTO AVIACAO.USUARIO (id_usuario, login, senha, nome, tipo)
	VALUES (AVIACAO.seq_usuario.nextval, 'castelovski', 'umasenha', 'kelly castelo', '2');
INSERT INTO AVIACAO.USUARIO (id_usuario, login, senha, nome, tipo)
	VALUES (AVIACAO.seq_usuario.nextval, 'bruno123', 'esqueciasenha', 'bruno rodrigues', '2');
INSERT INTO AVIACAO.USUARIO (id_usuario, login, senha, nome, tipo)
	VALUES (AVIACAO.seq_usuario.nextval, 'alasca123', 'alascalinda', 'alasca rodrigues', '2');
INSERT INTO AVIACAO.USUARIO (id_usuario, login, senha, nome, tipo)
	VALUES (AVIACAO.seq_usuario.nextval, 'risadinha', 'mari123', 'mariana machado', '2');
INSERT INTO AVIACAO.USUARIO (id_usuario, login, senha, nome, tipo)
	VALUES (AVIACAO.seq_usuario.nextval, 'robs', 'robervaldo123', 'robervaldo da silva', '2');

-- companhias --

INSERT INTO AVIACAO.COMPANHIA (id_companhia, cnpj, nome_fantasia, id_usuario)
	VALUES (AVIACAO.seq_companhia.nextval, '97450166000141', 'tam aviao', 1);
INSERT INTO AVIACAO.COMPANHIA (id_companhia, cnpj, nome_fantasia, id_usuario)
	VALUES (AVIACAO.seq_companhia.nextval, '82661331000137', 'gol aviao', 2);
INSERT INTO AVIACAO.COMPANHIA (id_companhia, cnpj, nome_fantasia, id_usuario)
	VALUES (AVIACAO.seq_companhia.nextval, '37537094000142', 'azul aviao', 3);
INSERT INTO AVIACAO.COMPANHIA (id_companhia, cnpj, nome_fantasia, id_usuario)
	VALUES (AVIACAO.seq_companhia.nextval, '70542335000117', 'fly emirates aviao', 4);
INSERT INTO AVIACAO.COMPANHIA (id_companhia, cnpj, nome_fantasia, id_usuario)
	VALUES (AVIACAO.seq_companhia.nextval, '82859050000193', 'american airlines aviao', 5);

-- compradores --

INSERT INTO AVIACAO.COMPRADOR (id_comprador, cpf, id_usuario)
	VALUES (AVIACAO.seq_comprador.nextval, '11958516007', 2);
INSERT INTO AVIACAO.COMPRADOR (id_comprador, cpf, id_usuario)
	VALUES (AVIACAO.seq_comprador.nextval, '84825855091', 7);
INSERT INTO AVIACAO.COMPRADOR (id_comprador, cpf, id_usuario)
	VALUES (AVIACAO.seq_comprador.nextval, '01873005008', 8);
INSERT INTO AVIACAO.COMPRADOR (id_comprador, cpf, id_usuario)
	VALUES (AVIACAO.seq_comprador.nextval, '68076150000', 9);
INSERT INTO AVIACAO.COMPRADOR (id_comprador, cpf, id_usuario)
	VALUES (AVIACAO.seq_comprador.nextval, '57793338073', 10);

-- trechos --

INSERT INTO AVIACAO.TRECHO (id_trecho, origem, destino, id_companhia)
	VALUES (AVIACAO.seq_trecho.nextval, 'BEL', 'CWB', 1);
INSERT INTO AVIACAO.TRECHO (id_trecho, origem, destino, id_companhia)
	VALUES (AVIACAO.seq_trecho.nextval, 'POA', 'RJ', 2);
INSERT INTO AVIACAO.TRECHO (id_trecho, origem, destino, id_companhia)
	VALUES (AVIACAO.seq_trecho.nextval, 'SP', 'POA', 3);
INSERT INTO AVIACAO.TRECHO (id_trecho, origem, destino, id_companhia)
	VALUES (AVIACAO.seq_trecho.nextval, 'POA', 'FLO', 4);
INSERT INTO AVIACAO.TRECHO (id_trecho, origem, destino, id_companhia)
	VALUES (AVIACAO.seq_trecho.nextval, 'SP', 'RJ', 5);

-- vendas --

INSERT INTO AVIACAO.VENDA  (id_venda, status, data_venda, id_companhia, id_comprador, codigo)
	VALUES (AVIACAO.seq_venda.nextval, 'CONCLUIDO', TO_TIMESTAMP(CURRENT_DATE,'dd-MM-yyyyHH24:MI'), 1, 1, '2569');

INSERT INTO AVIACAO.VENDA  (id_venda, status, data_venda, id_companhia, id_comprador, codigo)
	VALUES (AVIACAO.seq_venda.nextval, 'CONCLUIDO', TO_TIMESTAMP(CURRENT_DATE,'dd-MM-yyyyHH24:MI'), 2, 2, '3375');

INSERT INTO AVIACAO.VENDA  (id_venda, status, data_venda, id_companhia, id_comprador, codigo)
	VALUES (AVIACAO.seq_venda.nextval, 'CONCLUIDO', TO_TIMESTAMP(CURRENT_DATE,'dd-MM-yyyyHH24:MI'), 3, 3, '1658');

INSERT INTO AVIACAO.VENDA  (id_venda, status, data_venda, id_companhia, id_comprador, codigo)
	VALUES (AVIACAO.seq_venda.nextval, 'CONCLUIDO', TO_TIMESTAMP(CURRENT_DATE,'dd-MM-yyyyHH24:MI'), 4, 4, '6978');

INSERT INTO AVIACAO.VENDA  (id_venda, status, data_venda, id_companhia, id_comprador, codigo)
	VALUES (AVIACAO.seq_venda.nextval, 'CONCLUIDO', TO_TIMESTAMP(CURRENT_DATE,'dd-MM-yyyyHH24:MI'), 5, 5, '0132');

-- passagens --
-- [0] - false
-- [1] - true

INSERT INTO AVIACAO.PASSAGEM (id_passagem, codigo, data_partida, data_chegada, disponivel, valor, id_trecho, id_venda)
	VALUES (AVIACAO.seq_passagem.nextval, '1234',
		TO_TIMESTAMP('17-02-2023 16:18','dd-MM-yyyyHH24:MI'),
        TO_TIMESTAMP('18-02-2023 16:18','dd-MM-yyyyHH24:MI'), 0, 200.9, 1, 1);
	
INSERT INTO AVIACAO.PASSAGEM (id_passagem, codigo, data_partida, data_chegada, disponivel, valor, id_trecho, id_venda)
	VALUES (AVIACAO.seq_passagem.nextval, '9876',
        TO_TIMESTAMP('17-02-2023 16:18','dd-MM-yyyyHH24:MI'),
	    TO_TIMESTAMP('18-02-2023 16:18','dd-MM-yyyyHH24:MI'), 0, 650, 2, 2);
	
INSERT INTO AVIACAO.PASSAGEM (id_passagem, codigo, data_partida, data_chegada, disponivel, valor, id_trecho, id_venda)
	VALUES (AVIACAO.seq_passagem.nextval, '2345',
        TO_TIMESTAMP('17-02-2023 16:18','dd-MM-yyyyHH24:MI'),
	    TO_TIMESTAMP('18-02-2023 16:18','dd-MM-yyyyHH24:MI'), 0, 790.5, 3, 3);
	
INSERT INTO AVIACAO.PASSAGEM (id_passagem, codigo, data_partida, data_chegada, disponivel, valor, id_trecho, id_venda)
	VALUES (AVIACAO.seq_passagem.nextval, '7654',
        TO_TIMESTAMP('17-02-2023 16:18','dd-MM-yyyyHH24:MI'),
	    TO_TIMESTAMP('18-02-2023 16:18','dd-MM-yyyyHH24:MI'), 0, 364.2, 4, 4);
	
INSERT INTO AVIACAO.PASSAGEM (id_passagem, codigo, data_partida, data_chegada, disponivel, valor, id_trecho, id_venda)
	VALUES (AVIACAO.seq_passagem.nextval, '4567',
	    TO_TIMESTAMP('17-02-2023 16:18','dd-MM-yyyyHH24:MI'),
	    TO_TIMESTAMP('18-02-2023 16:18','dd-MM-yyyyHH24:MI'), 0, 861.8, 5, 5);
	
SELECT * FROM AVIACAO.USUARIO;
SELECT * FROM AVIACAO.TRECHO t;
SELECT * FROM AVIACAO.COMPANHIA companhia;
SELECT *FROM AVIACAO.COMPRADOR comprador;
SELECT * FROM AVIACAO.VENDA v;
SELECT * FROM AVIACAO.PASSAGEM p;

