CREATE TABLE Fourms(
    fourm_name VARCHAR(50),
    popularity INT,
    PRIMARY KEY (fourm_name)
);
CREATE TABLE Posts(
    post_id serial NOT NULL,
    title VARCHAR(50),
    article VARCHAR(50),
    reply TEXT[],
    fourm_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (post_id),
    FOREIGN KEY (fourm_name)REFERENCES Fourms ON DELETE CASCADE
);

-- Insert Fourms
INSERT INTO Fourms(fourm_name, popularity) VALUES('Gossiping', 100);
INSERT INTO Fourms(fourm_name, popularity) VALUES('Joke', 23);

SELECT * FROM Fourms;

-- Insert Posts
INSERT INTO Posts(post_id, title, article, reply, fourm_name) VALUES(1, 'Girlfriend', 'How can I get girlfriend ?', ARRAY['Haha', 'I dont know'], 'Gossiping');
INSERT INTO Posts(post_id, title, article, reply, fourm_name) VALUES(2, 'Firends', 'I dont have a friend...', ARRAY['Haha', 'I can be', 'QQ'], 'Gossiping');
INSERT INTO Posts(post_id, title, article, reply, fourm_name) VALUES(3, 'Knock', 'Knock! Knock! ...', ARRAY['Then?', 'Whats the point ?'], 'Joke');
INSERT INTO Posts(post_id, title, article, reply, fourm_name) VALUES(4, 'Santa Claus', 'Hold! Hold! Hold!', ARRAY['XDD'], 'Joke');

SELECT * FROM Posts;

-- Delete Table
DROP TABLE Posts;
DROP TABLE Fourms;