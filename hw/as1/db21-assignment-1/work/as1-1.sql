CREATE TABLE Professors(
    id serial NOT NULL,
    name VARCHAR(50),
    rank INT,
    PRIMARY KEY (id)
);
CREATE TABLE Projects(
    project_number serial NOT NULL,
    sponsor_name VARCHAR(50),
    starting_date timestamp, 
    ending_date timestamp, 
    budget INT,
    managed_by INT NOT NULL,
    PRIMARY KEY (project_number),
    FOREIGN KEY (managed_by)REFERENCES Professors ON DELETE CASCADE
);
create type Degree as enum('Bachelor', 'Master', 'PhD');
CREATE TABLE Students(
    student_id serial NOT NULL,
    name VARCHAR(50),
    degree_program Degree,
    instructor INT NOT NULL,
    PRIMARY KEY (student_id),
    FOREIGN KEY (instructor)REFERENCES Professors ON DELETE CASCADE
);
CREATE TABLE Works(
    project_number INT NOT NULL,
    student_id INT NOT NULL,
    starting_date timestamp,
    ending_date timestamp,
    paid INT,
    PRIMARY KEY (project_number, student_id),
    FOREIGN KEY (project_number)REFERENCES Projects ON DELETE CASCADE,
    FOREIGN KEY (student_id)REFERENCES Students ON DELETE CASCADE
);

-- Insert Professors
INSERT INTO Professors(id, name, rank) VALUES(1, 'sh-wu', 1);
INSERT INTO Professors(id, name, rank) VALUES(2, 'st-tsai', 2);
INSERT INTO Professors(id, name, rank) VALUES(3, 'j-chou', 3);

SELECT * FROM Professors;

-- Insert Projects
INSERT INTO Projects(project_number, sponsor_name, starting_date, ending_date, budget, managed_by) VALUES(1, 'MOST', '2020-01-08 04:05:06', '2021-01-08 04:05:06', 10000, 1);
INSERT INTO Projects(project_number, sponsor_name, starting_date, ending_date, budget, managed_by) VALUES(2, 'MOST', '2020-01-08 04:05:06', '2021-01-08 04:05:06', 10000, 2);
INSERT INTO Projects(project_number, sponsor_name, starting_date, ending_date, budget, managed_by) VALUES(3, 'MOST', '2020-01-08 04:05:06', '2021-01-08 04:05:06', 10000, 3);

SELECT * FROM Projects;

-- Insert Students
INSERT INTO Students(student_id, name, degree_program, instructor) VALUES(1, 'cs-chan', 'Master', 1);
INSERT INTO Students(student_id, name, degree_program, instructor) VALUES(2, 'jk-yang', 'Bachelor', 2);
INSERT INTO Students(student_id, name, degree_program, instructor) VALUES(3, 'ys-lin', 'PhD', 3);

SELECT * FROM Students;

-- Insert Works
INSERT INTO Works(project_number, student_id, starting_date, ending_date, paid) VALUES(1, 1, '2020-01-08 04:05:06', '2021-01-08 04:05:06', 1000);
INSERT INTO Works(project_number, student_id, starting_date, ending_date, paid) VALUES(2, 1, '2020-01-08 04:05:06', '2021-01-08 04:05:06', 1000);

INSERT INTO Works(project_number, student_id, starting_date, ending_date, paid) VALUES(2, 2, '2020-01-08 04:05:06', '2021-01-08 04:05:06', 1000);
INSERT INTO Works(project_number, student_id, starting_date, ending_date, paid) VALUES(3, 2, '2020-01-08 04:05:06', '2021-01-08 04:05:06', 1000);

INSERT INTO Works(project_number, student_id, starting_date, ending_date, paid) VALUES(3, 3, '2020-01-08 04:05:06', '2021-01-08 04:05:06', 1000);
INSERT INTO Works(project_number, student_id, starting_date, ending_date, paid) VALUES(1, 3, '2020-01-08 04:05:06', '2021-01-08 04:05:06', 1000);

SELECT * FROM Works;

-- Delete Table
DROP TABLE Works;
DROP TABLE Students;
DROP TABLE Projects;
DROP TABLE Professors;
