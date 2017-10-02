-- sbt flywayMigrate

-- ユーザテーブル
CREATE TABLE mt_users (
  id              INTEGER PRIMARY KEY,
  name            VARCHAR(64) NOT NULL
);

-- 野菜テーブル
CREATE TABLE mt_vegetables (
  id              INTEGER PRIMARY KEY,
  name            VARCHAR(64) NOT NULL
);

-- トランザクション基本テーブル
CREATE TABLE tx (
  id              VARCHAR(32) PRIMARY KEY,
  transact_time   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  idempotency_id  VARCHAR(64) UNIQUE NOT NULL,
  category        SMALLINT NOT NULL,
  transact_type   SMALLINT NOT NULL
);

CREATE UNIQUE INDEX ix_tx_idempotency_id ON tx (idempotency_id);

-- 注文テーブル
CREATE TABLE tx_orders (
  id              VARCHAR(32) PRIMARY KEY REFERENCES tx (id),
  order_id        VARCHAR(32) NOT NULL,
  user_id         INTEGER NOT NULL REFERENCES mt_users (id),
  vegetable_id    INTEGER NOT NULL REFERENCES mt_vegetables (id),
  price           NUMERIC(12, 6),
  quantity        NUMERIC(12, 6)
);

CREATE INDEX ix_tx_orders_order_id ON tx_orders (order_id);
CREATE INDEX ix_tx_orders_account_id_order_id ON tx_orders (user_id, order_id);
CREATE INDEX ix_tx_orders_vegetable_id ON tx_orders (vegetable_id);
