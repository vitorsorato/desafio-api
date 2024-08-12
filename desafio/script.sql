CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    limite INTEGER NOT NULL,
    saldo INTEGER NOT NULL
);

INSERT INTO cliente (nome, limite, saldo)
VALUES
    ('o barato sai caro', 1000 * 100, 0),
    ('zan corp ltda', 800 * 100, 0),
    ('les cruders', 10000 * 100, 0),
    ('padaria joia de cocaia', 100000 * 100, 0),
    ('kid mais', 5000 * 100, 0);

CREATE TABLE transacao (
    id SERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    valor INTEGER NOT NULL,
    tipo VARCHAR(1) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    realizada_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

INSERT INTO transacao (cliente_id, valor, tipo, descricao, realizada_em)
VALUES
    (1, 1000, 'd', 'Exemplo de transação', CURRENT_TIMESTAMP),
    (2, 500, 'c', 'Outro exemplo', CURRENT_TIMESTAMP);
