-- INSERTS --

-- usuarios --
-- [1] - Companhia
-- [2] - Comprador

INSERT INTO JAVAMOS_DECOLAR.USUARIO (id_usuario, login, senha, nome, tipo) 
	VALUES (seq_usuario.nextval, 'TAM123', 'senhasecreta', 'Companhia Tam', '1');
INSERT INTO JAVAMOS_DECOLAR.USUARIO (id_usuario, login, senha, nome, tipo) 
	VALUES (seq_usuario.nextval, 'GOL123', 'senhagol', 'Companhia Gol', '1');
INSERT INTO JAVAMOS_DECOLAR.USUARIO (id_usuario, login, senha, nome, tipo) 
	VALUES (seq_usuario.nextval, 'AZUL123', 'senhaazul', 'Companhia Azul', '1');
INSERT INTO JAVAMOS_DECOLAR.USUARIO (id_usuario, login, senha, nome, tipo) 
	VALUES (seq_usuario.nextval, 'FLY123', 'senhafly', 'Companhia Fly Emirates', '1');
INSERT INTO JAVAMOS_DECOLAR.USUARIO (id_usuario, login, senha, nome, tipo) 
	VALUES (seq_usuario.nextval, 'AMERICAN123', 'senhaamerican', 'Companhia American Airlines', '1');


INSERT INTO JAVAMOS_DECOLAR.USUARIO (id_usuario, login, senha, nome, tipo) 
	VALUES (seq_usuario.nextval, 'Castelovski', 'umasenha', 'Kelly Castelo', '2');
INSERT INTO JAVAMOS_DECOLAR.USUARIO (id_usuario, login, senha, nome, tipo) 
	VALUES (seq_usuario.nextval, 'bruno123', 'esqueciasenha', 'Bruno Rodrigues', '2');
INSERT INTO JAVAMOS_DECOLAR.USUARIO (id_usuario, login, senha, nome, tipo) 
	VALUES (seq_usuario.nextval, 'alasca123', 'alascalinda', 'Alasca Rodrigues', '2');
INSERT INTO JAVAMOS_DECOLAR.USUARIO (id_usuario, login, senha, nome, tipo) 
	VALUES (seq_usuario.nextval, 'risadinha', 'mari123', 'Mariana Machado', '2');
INSERT INTO JAVAMOS_DECOLAR.USUARIO (id_usuario, login, senha, nome, tipo) 
	VALUES (seq_usuario.nextval, 'robs', 'robervaldo123', 'Robervaldo da Silva', '2');

-- companhias --

INSERT INTO JAVAMOS_DECOLAR.COMPANHIA (id_companhia, cnpj, nome_fantasia, id_usuario) 
	VALUES (seq_companhia.nextval, '97450166000141', 'Tam Aviao', 1);
INSERT INTO JAVAMOS_DECOLAR.COMPANHIA (id_companhia, cnpj, nome_fantasia, id_usuario) 
	VALUES (seq_companhia.nextval, '82661331000137', 'Gol Aviao', 2);
INSERT INTO JAVAMOS_DECOLAR.COMPANHIA (id_companhia, cnpj, nome_fantasia, id_usuario) 
	VALUES (seq_companhia.nextval, '37537094000142', 'Azul Aviao', 3);
INSERT INTO JAVAMOS_DECOLAR.COMPANHIA (id_companhia, cnpj, nome_fantasia, id_usuario) 
	VALUES (seq_companhia.nextval, '70542335000117', 'Fly Emirates Aviao', 4);
INSERT INTO JAVAMOS_DECOLAR.COMPANHIA (id_companhia, cnpj, nome_fantasia, id_usuario) 
	VALUES (seq_companhia.nextval, '82859050000193', 'American Airlanes Aviao', 5);

-- compradores --

INSERT INTO JAVAMOS_DECOLAR.COMPRADOR (id_comprador, cpf, id_usuario) 
	VALUES (seq_comprador.nextval, '11958516007', 2);
INSERT INTO JAVAMOS_DECOLAR.COMPRADOR (id_comprador, cpf, id_usuario) 
	VALUES (seq_comprador.nextval, '84825855091', 7);
INSERT INTO JAVAMOS_DECOLAR.COMPRADOR (id_comprador, cpf, id_usuario) 
	VALUES (seq_comprador.nextval, '01873005008', 8);
INSERT INTO JAVAMOS_DECOLAR.COMPRADOR (id_comprador, cpf, id_usuario) 
	VALUES (seq_comprador.nextval, '68076150000', 9);
INSERT INTO JAVAMOS_DECOLAR.COMPRADOR (id_comprador, cpf, id_usuario) 
	VALUES (seq_comprador.nextval, '57793338073', 10);

-- trechos --

INSERT INTO JAVAMOS_DECOLAR.TRECHO (id_trecho, origem, destino, id_companhia) 
	VALUES (seq_comprador.nextval, 'BEL', 'CWB', 1);
INSERT INTO JAVAMOS_DECOLAR.TRECHO (id_trecho, origem, destino, id_companhia) 
	VALUES (seq_comprador.nextval, 'POA', 'RJ', 2);
INSERT INTO JAVAMOS_DECOLAR.TRECHO (id_trecho, origem, destino, id_companhia) 
	VALUES (seq_comprador.nextval, 'SP', 'POA', 3);
INSERT INTO JAVAMOS_DECOLAR.TRECHO (id_trecho, origem, destino, id_companhia) 
	VALUES (seq_comprador.nextval, 'POA', 'FLO', 4);
INSERT INTO JAVAMOS_DECOLAR.TRECHO (id_trecho, origem, destino, id_companhia) 
	VALUES (seq_comprador.nextval, 'SP', 'RJ', 5);

-- vendas --

INSERT INTO JAVAMOS_DECOLAR.VENDA  (id_venda, status, data_venda, id_companhia, id_comprador) 
	VALUES (seq_venda.nextval, 'CONCLUIDO', TO_DATE(CURRENT_DATE, 'dd-mm-yyyy'), 1, 1);

INSERT INTO JAVAMOS_DECOLAR.VENDA  (id_venda, status, data_venda, id_companhia, id_comprador) 
	VALUES (seq_venda.nextval, 'CONCLUIDO', TO_DATE(CURRENT_DATE, 'dd-mm-yyyy'), 2, 2);

INSERT INTO JAVAMOS_DECOLAR.VENDA  (id_venda, status, data_venda, id_companhia, id_comprador) 
	VALUES (seq_venda.nextval, 'CONCLUIDO', TO_DATE(CURRENT_DATE, 'dd-mm-yyyy'), 3, 3);

INSERT INTO JAVAMOS_DECOLAR.VENDA  (id_venda, status, data_venda, id_companhia, id_comprador) 
	VALUES (seq_venda.nextval, 'CONCLUIDO', TO_DATE(CURRENT_DATE, 'dd-mm-yyyy'), 4, 4);

INSERT INTO JAVAMOS_DECOLAR.VENDA  (id_venda, status, data_venda, id_companhia, id_comprador) 
	VALUES (seq_venda.nextval, 'CONCLUIDO', TO_DATE(CURRENT_DATE, 'dd-mm-yyyy'), 5, 5);

-- passagens --
-- [0] - false
-- [1] - true

INSERT INTO JAVAMOS_DECOLAR.PASSAGEM (id_passagem, codigo, data_partida, data_chegada, disponivel, valor, id_trecho, id_venda) 
	VALUES (seq_passagem.nextval, '123456', 
		TO_DATE('17-02-2023', 'dd-mm-yyyy'), 
		TO_DATE('18-02-2023', 'dd-mm-yyyy'), 0, 200.9, 6, 1);
	
INSERT INTO JAVAMOS_DECOLAR.PASSAGEM (id_passagem, codigo, data_partida, data_chegada, disponivel, valor, id_trecho, id_venda) 
	VALUES (seq_passagem.nextval, '987654', 
		TO_DATE('17-02-2023', 'dd-mm-yyyy'), 
		TO_DATE('18-02-2023', 'dd-mm-yyyy'), 0, 650, 7, 2);
	
INSERT INTO JAVAMOS_DECOLAR.PASSAGEM (id_passagem, codigo, data_partida, data_chegada, disponivel, valor, id_trecho, id_venda) 
	VALUES (seq_passagem.nextval, '234567', 
		TO_DATE('17-02-2023', 'dd-mm-yyyy'), 
		TO_DATE('18-02-2023', 'dd-mm-yyyy'), 0, 790.5, 8, 3);
	
INSERT INTO JAVAMOS_DECOLAR.PASSAGEM (id_passagem, codigo, data_partida, data_chegada, disponivel, valor, id_trecho, id_venda) 
	VALUES (seq_passagem.nextval, '765432', 
		TO_DATE('17-02-2023', 'dd-mm-yyyy'), 
		TO_DATE('18-02-2023', 'dd-mm-yyyy'), 0, 364.2, 9, 4);
	
INSERT INTO JAVAMOS_DECOLAR.PASSAGEM (id_passagem, codigo, data_partida, data_chegada, disponivel, valor, id_trecho, id_venda) 
	VALUES (seq_passagem.nextval, '456789', 
		TO_DATE('17-02-2023', 'dd-mm-yyyy'), 
		TO_DATE('18-02-2023', 'dd-mm-yyyy'), 0, 861.8, 10, 5);
	
SELECT * FROM JAVAMOS_DECOLAR.USUARIO;
SELECT * FROM JAVAMOS_DECOLAR.TRECHO t;
SELECT * FROM JAVAMOS_DECOLAR.COMPANHIA c;
SELECT *FROM JAVAMOS_DECOLAR.COMPRADOR c2;
SELECT * FROM JAVAMOS_DECOLAR.VENDA;
SELECT * FROM JAVAMOS_DECOLAR.PASSAGEM p;
