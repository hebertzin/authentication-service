CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

ALTER TABLE users ADD COLUMN id_uuid UUID;

UPDATE users
SET id_uuid = uuid_generate_v4();

ALTER TABLE users DROP CONSTRAINT users_pkey;

ALTER TABLE users DROP COLUMN id;

ALTER TABLE users RENAME COLUMN id_uuid TO id;

ALTER TABLE users ADD PRIMARY KEY (id);
