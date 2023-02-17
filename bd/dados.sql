-- INSERTS --

-- usuarios --
-- [1] - Companhia
-- [2] - Comprador

INSERT INTO JAVAMOS_DECOLAR.USUARIO (id_usuario, login, senha, nome, tipo) 
	VALUES (seq_usuario.nextval, 'TAM123', 'senhasecreta', 'Companhia Tam', '1');
INSERT INTO JAVAMOS_DECOLAR.USUARIO (id_usuario, login, senha, nome, tipo) 
	VALUES (seq_usuario.nextval, 'Castelovski', 'umasenha', 'Kelly Castelo', '2');

-- companhias --

INSERT INTO JAVAMOS_DECOLAR.COMPANHIA (id_companhia, cnpj, nome_fantasia, id_usuario) 
	VALUES (seq_companhia.nextval, '97450166000141', 'Tam Aviao', 1);

-- compradores --

INSERT INTO JAVAMOS_DECOLAR.COMPRADOR (id_comprador, cpf, id_usuario) 
	VALUES (seq_comprador.nextval, '11958516007', 2);

-- trechos --

INSERT INTO JAVAMOS_DECOLAR.TRECHO (id_trecho, origem, destino, id_companhia) 
	VALUES (seq_comprador.nextval, 'BEL', 'CWB', 1);

-- vendas --

INSERT INTO JAVAMOS_DECOLAR.VENDA  (id_venda, status, data_venda, id_companhia, id_comprador) 
	VALUES (seq_venda.nextval, 'CONCLUIDO', TO_DATE(CURRENT_DATE, 'dd-mm-yyyy'), 1, 1);

-- passagens --
-- [0] - false
-- [1] - true

INSERT INTO JAVAMOS_DECOLAR.PASSAGEM (id_passagem, codigo, data_partida, data_chegada, disponivel, valor, id_trecho, id_venda) 
	VALUES (seq_passagem.nextval, '123456', 
		TO_DATE('17-02-2023', 'dd-mm-yyyy'), 
		TO_DATE('18-02-2023', 'dd-mm-yyyy'), 0, 200.9, 2, 1);
