CREATE TABLE "Usuario" (
  "id_usuario" NUMBER,
  "login" VARCHAR(20) UNIQUE NOT NULL,
  "senha" VARCHAR(20) NOT NULL,
  "nome" VARCHAR(100) NOT NULL,
  "tipo" CHAR(1) NOT NULL,
  PRIMARY KEY ("id_usuario")
);

CREATE TABLE "Comprador" (
  "id_comprador" NUMBER,
  "cpf" CHAR(11) UNIQUE NOT NULL,
  "id_usuario" NUMBER UNIQUE NOT NULL,
  PRIMARY KEY ("id_comprador"),
  CONSTRAINT "FK_Comprador.id_usuario"
    FOREIGN KEY ("id_usuario")
      REFERENCES "Usuario"("id_usuario")
);

CREATE TABLE "Companhia" (
  "id_companhia" NUMBER,
  "cnpj" CHAR(14) UNIQUE NOT NULL,
  "nome_fantasia" VARCHAR(100) NOT NULL,
  "id_usuario" NUMBER UNIQUE NOT NULL,
  PRIMARY KEY ("id_companhia"),
  CONSTRAINT "FK_Companhia.id_usuario"
    FOREIGN KEY ("id_usuario")
      REFERENCES "Usuario"("id_usuario")
);

CREATE TABLE "Venda" (
  "id_venda" NUMBER,
  "status" VARCHAR2(20) NOT NULL,
  "data_venda" DATE NOT NULL,
  "id_companhia" NUMBER NOT NULL,
  "id_comprador" NUMBER NOT NULL,
  PRIMARY KEY ("id_venda"),
  CONSTRAINT "FK_Venda.id_comprador"
    FOREIGN KEY ("id_comprador")
      REFERENCES "Comprador"("id_comprador"),
  CONSTRAINT "FK_Venda.id_companhia"
    FOREIGN KEY ("id_companhia")
      REFERENCES "Companhia"("id_companhia")
);

CREATE TABLE "Trecho" (
  "id_trecho" NUMBER,
  "origem" CHAR(3) NOT NULL,
  "destino" CHAR(3) NOT NULL,
  "id_companhia" NUMBER NOT NULL,
  PRIMARY KEY ("id_trecho"),
  CONSTRAINT "FK_Trecho.id_companhia"
    FOREIGN KEY ("id_companhia")
      REFERENCES "Companhia"("id_companhia")
);

CREATE TABLE "Passagem" (
  "id_passagem" NUMBER,
  "codigo" NUMBER UNIQUE NOT NULL,
  "data_partida" DATE NOT NULL,
  "data_chegada" DATE NOT NULL,
  "disponivel" CHAR(1) NOT NULL,
  "valor" NUMBER NOT NULL,
  "id_trecho" NUMBER NOT NULL,
  "id_venda" NUMBER NOT NULL,
  PRIMARY KEY ("id_passagem"),
  CONSTRAINT "FK_Passagem.id_venda"
    FOREIGN KEY ("id_venda")
      REFERENCES "Venda"("id_venda"),
  CONSTRAINT "FK_Passagem.id_trecho"
    FOREIGN KEY ("id_trecho")
      REFERENCES "Trecho"("id_trecho")
);

-- sequences --

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
CREATE SEQUENCE seq_usuario
START WITH 1
INCREMENT BY 1
NOCACHE NOCYCLE;