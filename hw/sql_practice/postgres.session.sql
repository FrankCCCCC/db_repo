-- CREATE TABLE users ("id" serial PRIMARY KEY NOT NULL, 
--                    "name" varchar(50) NOT NULL, 
--                    "karma" integer NOT NULL);
-- CREATE TABLE posts ("id" serial PRIMARY KEY NOT NULL,
--                     "text" text NOT NULL,
--                     "authorId" integer NOT NULL REFERENCES users ON DELETE CASCADE,
--                     "ts" bigint NOT NULL DEFAULT (extract (epoch from now())));
-- INSERT INTO users(id, name, karma) VALUES (0, 'John', 50);
-- INSERT INTO users(id, name, karma) VALUES (1, 'Bob', 100);
-- INSERT INTO users(id, name, karma) VALUES (2, 'Jenny', 150);
INSERT INTO posts ("text", "authorId") VALUES ('Today is a good day!', 43);
INSERT INTO posts ("text", "authorId") VALUES ('Today is not a good day!', 44);

-- Delete
-- DELETE FROM users;
-- DELETE FROM posts;

-- Batch Insertion
-- INSERT INTO users (name, karma) SELECT 'User' || s, round(random() * 100) FROM generate_series(1, 10) AS s;
-- INSERT INTO posts (text, "authorId") SELECT 'Dummy word' || i || '.', round(random() * 10) + 1 FROM generate_series(20, 1000) AS s(i);

-- Update
-- UPDATE users SET karma = karma + 10 WHERE name = 'Bob';

SELECT * FROM users;
SELECT * FROM posts;
-- SELECT * FROM users WHERE id < 100 AND name ILIKE '%User%' ORDER BY id DESC;
-- SELECT MIN(karma) FROM users;
-- SELECT MAX(karma) FROM users;
-- SELECT AVG(karma) FROM users;
-- SELECT COUNT(karma) FROM users;

-- Analyze
-- EXPLAIN ANALYSE SELECT * FROM users WHERE id < 500 AND name ILIKE '%User%' ORDER BY id DESC;
EXPLAIN ANALYSE SELECT * FROM posts WHERE text LIKE '%word%';

-- Create Extension
-- CREATE EXTENSION pg_trgm;

-- Create Indexes
-- CREATE INDEX posts_idx_ts ON posts USING btree(ts);
-- CREATE INDEX post_idx_text_trgm ON posts USING GIN(text gin_trgm_ops);