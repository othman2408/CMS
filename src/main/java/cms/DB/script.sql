CREATE TABLE Users
(
    user_id  NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR2(50) UNIQUE,
    password VARCHAR2(50),
    NAME     VARCHAR2(100),
    email    VARCHAR2(100) UNIQUE,
    role     VARCHAR2(20) check ( role in ('Organizer', 'Author', 'Reviewer') )
);