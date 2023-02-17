CREATE TABLE "Usuario" (
  "id_usuario" NUMBER,
  "login" VARCHAR(20),
  "senha" VARCHAR(20),
  "nome" VARCHAR(100),
  "tipo" CHAR(1),
  PRIMARY KEY ("id_usuario")
);

CREATE TABLE "Comprador" (
  "id_comprador" NUMBER,
  "cpf" CHAR(11),
  "id_usuario" NUMBER,
  PRIMARY KEY ("id_comprador"),
  CONSTRAINT "FK_Comprador.id_usuario"
    FOREIGN KEY ("id_usuario")
      REFERENCES "Usuario"("id_usuario")
);

CREATE TABLE "Companhia" (
  "id_companhia" NUMBER,
  "cnpj" CHAR(14),
  "nome_fantasia" VARCHAR(100),
  "id_usuario" NUMBER,
  PRIMARY KEY ("id_companhia"),
  CONSTRAINT "FK_Companhia.id_usuario"
    FOREIGN KEY ("id_usuario")
      REFERENCES "Usuario"("id_usuario")
);

CREATE TABLE "Venda" (
  "id_venda" NUMBER,
  "status" VARCHAR2(20),
  "data" DATE,
  "id_companhia" NUMBER,
  "id_comprador" NUMBER,
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
  "origem" CHAR(3),
  "destino" CHAR(3),
  "id_companhia" NUMBER,
  PRIMARY KEY ("id_trecho"),
  CONSTRAINT "FK_Trecho.id_companhia"
    FOREIGN KEY ("id_companhia")
      REFERENCES "Companhia"("id_companhia")
);

CREATE TABLE "Passagem" (
  "id_passagem" NUMBER,
  "codigo" NUMBER,
  "data_partida" DATE,
  "data_chegada" DATE,
  "disponivel" CHAR(1),
  "valor" NUMBER,
  "id_trecho" NUMBER,
  "id_venda" NUMBER,
  PRIMARY KEY ("id_passagem"),
  CONSTRAINT "FK_Passagem.id_venda"
    FOREIGN KEY ("id_venda")
      REFERENCES "Venda"("id_venda"),
  CONSTRAINT "FK_Passagem.id_trecho"
    FOREIGN KEY ("id_trecho")
      REFERENCES "Trecho"("id_trecho")
);