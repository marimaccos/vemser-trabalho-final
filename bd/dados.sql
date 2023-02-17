-- INSERTS --

-- usuarios --
-- [1] - Companhia
-- [2] - Comprador

INSERT INTO usuario (id_usuario, login, senha, nome, tipo) 
VALUES (VEM_SER.seq_usuario.nextval, 'TAM123', 'senhasecreta', 'Companhia Tam', 1);
INSERT INTO usuario (id_usuario, login, senha, nome, tipo) 
VALUES (VEM_SER.seq_usuario.nextval, 'Castelovski', 'umasenha', 'Kelly Castelo', 2);

-- companhias --

INSERT INTO companhia (id_companhia, cnpj, nome_fantasia, id_usuario) 
VALUES (VEM_SER.seq_companhia.nextval, '97450166000141', 'Tam Aviação', 1);

-- compradores --

INSERT INTO comprador (id_comprador, cpf, id_usuario) 
VALUES (VEM_SER.seq_comprador.nextval, '11958516007', 2);

-- trechos --

INSERT INTO trecho (id_trecho, origem, destino, id_companhia) 
VALUES (VEM_SER.seq_comprador.nextval, 'BEL', 'CWB', 1);

-- passagens --
-- [0] - FALSE 
-- [1] - TRUE

INSERT INTO passagem (id_passagem, codigo, data_partida, data_chegada, disponivel, valor, id_trecho, id_venda) 
VALUES (VEM_SER.seq_passagem.nextval, '123456', 
TO_DATE('17-02-2023', 'dd-mm-yyyy'), 
TO_DATE('18-02-2023', 'dd-mm-yyyy'), 0, 13, 1, 1);

-- vendas --

INSERT INTO venda (id_venda, status, data_venda, id_companhia, id_comprador) 
VALUES (VEM_SER.seq_venda.nextval, 'CONCLUÍDO', TO_DATE(CURRENT_DATE, 'dd-mm-yyyy'), 1, 1);
