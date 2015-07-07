CREATE TABLE tb_endereco (
  id_endereco SERIAL NOT NULL,
  logradouro_endereco VARCHAR(128) NOT NULL,
  numero_endereco VARCHAR(16) NOT NULL,
  complemento_endereco VARCHAR(16) NULL,
  bairro_endereco VARCHAR(64) NOT NULL,
  cidade_endereco VARCHAR(128) NOT NULL,
  uf_endereco CHAR(2) NOT NULL,
  cep_endereco NUMERIC(8) NOT NULL,
  id_localidade NUMERIC(8) NOT NULL,
  PRIMARY KEY(id_endereco)
);

CREATE TABLE tb_serie (
  id_serie SERIAL NOT NULL,
  nome_serie VARCHAR(128) NOT NULL,
  tipo_serie CHAR(1) NOT NULL,
  PRIMARY KEY(id_serie)
);

CREATE TABLE tb_escola (
  id_escola SERIAL NOT NULL,
  nome_escola VARCHAR(64) NULL,
  endereco SERIAL NOT NULL,
  PRIMARY KEY(id_escola),
  INDEX ix_escola_1(endereco),
  FOREIGN KEY(endereco)
    REFERENCES tb_endereco(id_endereco)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE tb_aluno (
  id_aluno SERIAL NOT NULL,
  endereco SERIAL NOT NULL,
  nome_aluno VARCHAR(128) NOT NULL,
  nascimento_aluno DATE NOT NULL,
  pai_aluno VARCHAR(128) NULL,
  mae_aluno VARCHAR(128) NOT NULL,
  cpf_aluno VARCHAR(15) NOT NULL,
  PRIMARY KEY(id_aluno),
  INDEX ix_aluno_1(endereco),
  FOREIGN KEY(endereco)
    REFERENCES tb_endereco(id_endereco)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE TABLE tb_matricula (
  ano_matricula INTEGER(4) UNSIGNED NOT NULL,
  aluno SERIAL NOT NULL,
  serie SERIAL NOT NULL,
  escola SERIAL NOT NULL,
  PRIMARY KEY(ano_matricula, aluno),
  INDEX ix_matricula_1(aluno),
  INDEX ix_matricula_2(escola),
  INDEX ix_matricula_3(serie),
  FOREIGN KEY(aluno)
    REFERENCES tb_aluno(id_aluno)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(escola)
    REFERENCES tb_escola(id_escola)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(serie)
    REFERENCES tb_serie(id_serie)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
);

CREATE SEQUENCE tb_aluno_id_aluno_seq
  INCREMENT 0
  MINVALUE 0
  MAXVALUE 0
  START 0
  CACHE 0
  CYCLE;

CREATE SEQUENCE tb_endereco_id_endereco_seq
  INCREMENT 0
  MINVALUE 0
  MAXVALUE 0
  START 0
  CACHE 0
  CYCLE;

CREATE SEQUENCE tb_escola_id_escola_seq
  INCREMENT 0
  MINVALUE 0
  MAXVALUE 0
  START 0
  CACHE 0
  CYCLE;

CREATE SEQUENCE tb_serie_id_serie_seq
  INCREMENT 0
  MINVALUE 0
  MAXVALUE 0
  START 0
  CACHE 0
  CYCLE;