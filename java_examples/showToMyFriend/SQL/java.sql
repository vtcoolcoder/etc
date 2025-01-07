CREATE TABLE javanotes (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    subject VARCHAR(200) NOT NULL,
    note VARCHAR(18000) NOT NULL
);


INSERT INTO javanotes (subject, note) VALUES
('тема1', 'заметка1'),
('тема2', 'заметка2'),
('тема3', 'заметка3');


UPDATE javanotes SET 
note = 'new value...'
WHERE subject = 'некая тема...';


DELETE FROM javanotes 
WHERE id = 2 OR id = 3;


SELECT * FROM javanotes
WHERE id = 1;


ALTER TABLE javanotes ADD COLUMN example BIGINT;
ALTER TABLE javanotes DROP COLUMN example;
