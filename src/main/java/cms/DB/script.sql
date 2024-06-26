CREATE TABLE Users (
                       user_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       username VARCHAR2(50) UNIQUE,
                       password VARCHAR2(50),
                       NAME VARCHAR2(100),
                       email VARCHAR2(100) UNIQUE,
                       role VARCHAR2(20) check (role in ('Organizer', 'Author', 'Reviewer'))
);

CREATE TABLE Venue (
                       venue_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       location VARCHAR2(100) UNIQUE
);

CREATE TABLE Conference (
                            conference_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            name VARCHAR2(100) UNIQUE,
                            start_date varchar2(50),
                            end_date varchar2(50),
                            deadline varchar2(50),
                            conference_code VARCHAR2(50) UNIQUE,
                            organizer_id NUMBER,
                            venue_id NUMBER,
                            FOREIGN KEY (organizer_id) REFERENCES Users(user_id),
                            FOREIGN KEY (venue_id) REFERENCES Venue(venue_id)
);

CREATE TABLE Reviewer_Conference (
                                     reviewer_id NUMBER,
                                     conference_id NUMBER,
                                     PRIMARY KEY (reviewer_id, conference_id),
                                     FOREIGN KEY (reviewer_id) REFERENCES Users(user_id),
                                     FOREIGN KEY (conference_id) REFERENCES Conference(conference_id)ON DELETE CASCADE
);

--No need for them now
CREATE TABLE Keyword (
                         keyword_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                         keyword_name VARCHAR2(50) UNIQUE
);

CREATE TABLE Paper (
                       paper_id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                       title VARCHAR2(100),
                       abstract_text VARCHAR2(1000),
                       status VARCHAR2(50),
                       author_id NUMBER,
                       reviewer_id NUMBER,
                       FOREIGN KEY (author_id) REFERENCES Users(user_id),
                       FOREIGN KEY (reviewer_id) REFERENCES Users(user_id)
);

CREATE TABLE Paper_Keyword (
                               paper_id NUMBER,
                               keyword_id NUMBER,
                               PRIMARY KEY (paper_id, keyword_id),
                               FOREIGN KEY (paper_id) REFERENCES Paper(paper_id),
                               FOREIGN KEY (keyword_id) REFERENCES Keyword(keyword_id)
);



-- Inserting REVIEWERS AS sample data into the Users table
-- Insert Reviewer 1
INSERT INTO Users (username, password, name, email, role)
VALUES ('john', 'pass1', 'John Doe', 'john@example.com', 'Reviewer');

-- Insert Reviewer 2
INSERT INTO Users (username, password, name, email, role)
VALUES ('alice', 'pass2', 'Alice Smith', 'alice@example.com', 'Reviewer');

-- Insert Reviewer 3
INSERT INTO Users (username, password, name, email, role)
VALUES ('eva', 'pass3', 'Eva Brown', 'eva@example.com', 'Reviewer');

-- Insert Reviewer 4
INSERT INTO Users (username, password, name, email, role)
VALUES ('mike', 'pass4', 'Mike Wilson', 'mike@example.com', 'Reviewer');

-- Insert Reviewer 5
INSERT INTO Users (username, password, name, email, role)
VALUES ('sophia', 'pass5', 'Sophia Johnson', 'sophia@example.com', 'Reviewer');

-- Insert Reviewer 6
INSERT INTO Users (username, password, name, email, role)
VALUES ('daniel', 'pass6', 'Daniel Lee', 'daniel@example.com', 'Reviewer');

-- Insert Reviewer 7
INSERT INTO Users (username, password, name, email, role)
VALUES ('olivia', 'pass7', 'Olivia Garcia', 'olivia@example.com', 'Reviewer');

-- Insert Reviewer 8
INSERT INTO Users (username, password, name, email, role)
VALUES ('william', 'pass8', 'William Martinez', 'william@example.com', 'Reviewer');

-- Insert Reviewer 9
INSERT INTO Users (username, password, name, email, role)
VALUES ('emily', 'pass9', 'Emily Anderson', 'emily@example.com', 'Reviewer');

-- Insert Reviewer 10
INSERT INTO Users (username, password, name, email, role)
VALUES ('liam', 'pass10', 'Liam Lopez', 'liam@example.com', 'Reviewer');


-- Inserting sample data into the Venue table
INSERT INTO Venue (location)
VALUES ('Location A');

INSERT INTO Venue (location)
VALUES ('Location B');

INSERT INTO Venue (location)
VALUES ('Location C');

INSERT INTO Venue (location)
VALUES ('Location D');

INSERT INTO Venue (location)
VALUES ('Location E');

INSERT INTO Venue (location)
VALUES ('Location F');

INSERT INTO Venue (location)
VALUES ('Location G');


-- Inserting sample data into the Conference table
INSERT INTO Conference (name, start_date, end_date, deadline, conference_code, organizer_id, venue_id)
VALUES ('Conference 3', '2024-06-10', '2024-06-15', '2024-05-25', 'CONF003', 5, 2);

INSERT INTO Conference (name, start_date, end_date, deadline, conference_code, organizer_id, venue_id)
VALUES ('Conference 6', '2024-06-10', '2024/06/15', '2024/05/25', 'CONF006', 5, 2);

INSERT INTO Conference (name, start_date, end_date, deadline, conference_code, organizer_id, venue_id)
VALUES ('Conference 1', '2024-06-10', '2024-06-15', '2024-05-25', 'CONF001', 5, 1);

INSERT INTO Conference (name, start_date, end_date, deadline, conference_code, organizer_id, venue_id)
VALUES ('Conference 2', '2024-06-10', '2024-06-15', '2024-05-25', 'CONF002', 5, 3);

INSERT INTO Conference (name, start_date, end_date, deadline, conference_code, organizer_id, venue_id)
VALUES ('Conference 4', '2024-06-10', '2024-06-15', '2024-05-25', 'CONF004', 5, 4);

INSERT INTO Conference (name, start_date, end_date, deadline, conference_code, organizer_id, venue_id)
VALUES ('Conference 5', '2024-06-10', '2024-06-15', '2024-05-25', 'CONF005', 5, 5);

-- Inserting sample data into the Keyword table
INSERT INTO Keyword (keyword_name) VALUES ('Data Science');

INSERT INTO Keyword (keyword_name) VALUES ('Machine Learning');

INSERT INTO Keyword (keyword_name) VALUES ('Artificial Intelligence');

INSERT INTO Keyword (keyword_name) VALUES ('Computer Vision');

INSERT INTO Keyword (keyword_name) VALUES ('Natural Language Processing');



-- Inserting sample data into the Paper table
INSERT INTO Paper (title, abstract_text, status, author_id, reviewer_id)
VALUES ('Paper 3', 'Abstract of Paper 3 is placed here.', 'Accepted', 5, 1);

INSERT INTO Paper (title, abstract_text, status, author_id, reviewer_id)
VALUES ('Paper 1', 'Abstract of Paper 1 is placed here.', 'Accepted', 3, 2);

INSERT INTO Paper (title, abstract_text, status, author_id, reviewer_id)
VALUES ('Paper 2', 'Abstract of Paper 2 is placed here.', 'Accepted', 5, 3);

INSERT INTO Paper (title, abstract_text, status, author_id, reviewer_id)
VALUES ('Paper 4', 'Abstract of Paper 4 is placed here.', 'Accepted', 3, 4);

INSERT INTO Paper (title, abstract_text, status, author_id, reviewer_id)
VALUES ('Paper 5', 'Abstract of Paper 5 is placed here.', 'Accepted', 5, 5);

-- Inserting sample data into the Paper_Keyword table
INSERT INTO Paper_Keyword (paper_id, keyword_id) VALUES (3, 3);

INSERT INTO Paper_Keyword (paper_id, keyword_id) VALUES (1, 1);

INSERT INTO Paper_Keyword (paper_id, keyword_id) VALUES (2, 2);

INSERT INTO Paper_Keyword (paper_id, keyword_id) VALUES (4, 4);

INSERT INTO Paper_Keyword (paper_id, keyword_id) VALUES (5, 5);


-- Create a view for the Conference table, belong to specific organizer
CREATE VIEW Conference_View AS
SELECT c.conference_id, c.name, c.start_date, c.end_date, c.deadline, c.conference_code, u.username AS organizer, v.location AS venue
FROM Conference c
         INNER JOIN Users u ON c.organizer_id = u.user_id
         INNER JOIN Venue v ON c.venue_id = v.venue_id;

-- get the available venues
SELECT * FROM Venue WHERE venue_id NOT IN (SELECT venue_id FROM Conference);

-- Create view for the available venues
CREATE VIEW Available_Venues AS
SELECT * FROM Venue WHERE venue_id NOT IN (SELECT venue_id FROM Conference);



-- Drop the tables, views and sequences, in correct order
DROP VIEW Conference_View;
DROP VIEW Available_Venues;

DROP TABLE Paper_Keyword;
DROP TABLE Paper;
DROP TABLE Keyword;
DROP TABLE REVIEWER_CONFERENCE;
DROP TABLE Conference;
DROP TABLE Venue;
DROP TABLE Users;


commit;



-- show all tables
select *
from REVIEWER_CONFERENCE;
select * from Conference;

select * from USERS where role = 'Reviewer' and user_id not in (select reviewer_id from REVIEWER_CONFERENCE);

-- get reviewers of specific conference
select * from Users where user_id in (select reviewer_id from REVIEWER_CONFERENCE where conference_id = 33);