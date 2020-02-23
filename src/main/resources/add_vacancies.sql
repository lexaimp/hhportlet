DROP TABLE IF EXISTS Vacancies;

CREATE TABLE Vacancies
(
    id            INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name          VARCHAR(255)                   NOT NULL,
    employer_name VARCHAR(255),
    published_at  VARCHAR(24),
    salary_from   DOUBLE,
    salary_to     DOUBLE,
    currency      VARCHAR(4)
);
