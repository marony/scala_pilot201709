-- sbt fluwauMigrate

CREATE TABLE snp_orders (
  id              VARCHAR(32) PRIMARY KEY REFERENCES tx (id),
  enable          BOOLEAN,
  order_id        VARCHAR(32) NOT NULL,
  user_id         INTEGER NOT NULL REFERENCES mt_users (id),
  vegetable_id    INTEGER NOT NULL REFERENCES mt_vegetables (id),
  now_price      NUMERIC(12, 6),
  leaves_quantity NUMERIC(12, 6)
);
