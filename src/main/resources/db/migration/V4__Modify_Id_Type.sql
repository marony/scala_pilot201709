ALTER TABLE tx_orders DROP CONSTRAINT tx_orders_user_id_fkey;
ALTER TABLE tx_orders DROP CONSTRAINT tx_orders_vegetable_id_fkey;
ALTER TABLE snp_orders DROP CONSTRAINT snp_orders_user_id_fkey;
ALTER TABLE snp_orders DROP CONSTRAINT snp_orders_vegetable_id_fkey;

DELETE FROM mt_users;
DELETE FROM mt_vegetables;

ALTER TABLE tx_orders ALTER COLUMN user_id TYPE VARCHAR(16);
ALTER TABLE tx_orders ALTER COLUMN vegetable_id TYPE VARCHAR(16);

ALTER TABLE mt_users ALTER COLUMN id TYPE VARCHAR(16);
ALTER TABLE mt_vegetables ALTER COLUMN id TYPE VARCHAR(16);

ALTER TABLE snp_orders ALTER COLUMN user_id TYPE VARCHAR(16);
ALTER TABLE snp_orders ALTER COLUMN vegetable_id TYPE VARCHAR(16);

ALTER TABLE tx_orders ADD FOREIGN KEY (user_id) REFERENCES mt_users(id);
ALTER TABLE tx_orders ADD FOREIGN KEY (vegetable_id) REFERENCES mt_vegetables(id);
ALTER TABLE snp_orders ADD FOREIGN KEY (user_id) REFERENCES mt_users(id);
ALTER TABLE snp_orders ADD FOREIGN KEY (vegetable_id) REFERENCES mt_vegetables(id);

INSERT INTO mt_users (id, name) VALUES ('00000001', 'まろ');
INSERT INTO mt_users (id, name) VALUES ('00000002', '知らない人');

INSERT INTO mt_vegetables (id, name) VALUES ('A001', 'スイカ');
INSERT INTO mt_vegetables (id, name) VALUES ('A002', 'トマト');
