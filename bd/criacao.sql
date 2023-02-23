-- 	CREATE USER --

--CREATE USER JAVAMOS_DECOLAR IDENTIFIED BY oracle;
GRANT CONNECT TO JAVAMOS_DECOLAR;
GRANT CONNECT, RESOURCE, DBA TO VEM_SER;
GRANT CREATE SESSION TO JAVAMOS_DECOLAR;
GRANT DBA TO JAVAMOS_DECOLAR;
GRANT CREATE VIEW, CREATE PROCEDURE, CREATE SEQUENCE TO JAVAMOS_DECOLAR;
GRANT CREATE MATERIALIZED VIEW TO JAVAMOS_DECOLAR;
GRANT GLOBAL QUERY REWRITE TO JAVAMOS_DECOLAR;
GRANT SELECT ANY TABLE TO JAVAMOS_DECOLAR;

-- CLEAN EVERYTHING IF NEEDED --
DROP TABLE JAVAMOS_DECOLAR.PASSAGEM;
DROP TABLE JAVAMOS_DECOLAR.VENDA;
DROP TABLE JAVAMOS_DECOLAR.TRECHO;
DROP TABLE JAVAMOS_DECOLAR.COMPANHIA;
DROP TABLE JAVAMOS_DECOLAR.COMPRADOR;
DROP TABLE JAVAMOS_DECOLAR.USUARIO;
DROP SEQUENCE JAVAMOS_DECOLAR.seq_usuario;
DROP SEQUENCE JAVAMOS_DECOLAR.seq_companhia;
DROP SEQUENCE JAVAMOS_DECOLAR.seq_comprador;
DROP SEQUENCE JAVAMOS_DECOLAR.seq_passagem;
DROP SEQUENCE JAVAMOS_DECOLAR.seq_trecho;
DROP SEQUENCE JAVAMOS_DECOLAR.seq_venda;

 -- CREATE TABLES --

CREATE TABLE JAVAMOS_DECOLAR.USUARIO (
  id_usuario NUMBER,
  login VARCHAR2(20) UNIQUE NOT NULL,
  senha VARCHAR2(20) NOT NULL,
  nome VARCHAR2(100) NOT NULL,
  tipo NUMBER(1) NOT NULL,
  PRIMARY KEY (id_usuario)
);

CREATE TABLE JAVAMOS_DECOLAR.COMPRADOR (
  id_comprador NUMBER,
  cpf CHAR(11) UNIQUE NOT NULL,
  id_usuario NUMBER UNIQUE NOT NULL,
  PRIMARY KEY (id_comprador),
  CONSTRAINT FK_COMPRADOR_ID_USUARIO FOREIGN KEY (id_usuario) REFERENCES USUARIO(id_usuario)
);

CREATE TABLE JAVAMOS_DECOLAR.COMPANHIA (
  id_companhia NUMBER,
  cnpj CHAR(14) UNIQUE NOT NULL,
  nome_fantasia VARCHAR2(100) NOT NULL,
  id_usuario NUMBER UNIQUE NOT NULL,
  PRIMARY KEY (id_companhia),
  CONSTRAINT FK_COMPANHIA_ID_USUARIO FOREIGN KEY (id_usuario) REFERENCES USUARIO(id_usuario)
);

CREATE TABLE JAVAMOS_DECOLAR.VENDA (
  id_venda NUMBER,
  status VARCHAR2(20) NOT NULL,
  data_venda DATE NOT NULL,
  id_companhia NUMBER NOT NULL,
  id_comprador NUMBER NOT NULL,
  PRIMARY KEY (id_venda),
  CONSTRAINT FK_VENDA_ID_COMPRADOR FOREIGN KEY (id_comprador) REFERENCES COMPRADOR(id_comprador),
  CONSTRAINT FK_VENDA_ID_COMPANHIA FOREIGN KEY (id_companhia) REFERENCES COMPANHIA(id_companhia)
);

CREATE TABLE JAVAMOS_DECOLAR.TRECHO (
  id_trecho NUMBER,
  origem CHAR(3) NOT NULL,
  destino CHAR(3) NOT NULL,
  id_companhia NUMBER NOT NULL,
  PRIMARY KEY (id_trecho),
  CONSTRAINT FK_TRECHO_ID_COMPANHIA FOREIGN KEY (id_companhia) REFERENCES COMPANHIA(id_companhia)
);

CREATE TABLE JAVAMOS_DECOLAR.PASSAGEM (
  id_passagem NUMBER,
  codigo NUMBER UNIQUE NOT NULL,
  data_partida DATE NOT NULL,
  data_chegada DATE NOT NULL,
  disponivel CHAR(1) NOT NULL,
  valor NUMBER(19,4) NOT NULL,
  id_trecho NUMBER NOT NULL,
  id_venda NUMBER,
  PRIMARY KEY (id_passagem),
  CONSTRAINT FK_PASSAGEM_ID_VENDA FOREIGN KEY (id_venda) REFERENCES VENDA(id_venda),
  CONSTRAINT FK_PASSAGEM_ID_TRECHO FOREIGN KEY (id_trecho) REFERENCES TRECHO(id_trecho)
);

-- SEQUENCES --

CREATE SEQUENCE seq_usuario
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;

CREATE SEQUENCE seq_companhia
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;

CREATE SEQUENCE seq_comprador
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;

CREATE SEQUENCE seq_passagem
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;

CREATE SEQUENCE seq_trecho
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;

CREATE SEQUENCE seq_venda
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;