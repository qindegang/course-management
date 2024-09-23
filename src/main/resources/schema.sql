DROP TABLE IF EXISTS course_signup;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS courses;

CREATE TABLE students (
  id BIGINT NOT NULL AUTO_INCREMENT,
  email VARCHAR(100) NOT NULL UNIQUE,
  PRIMARY KEY (id));

CREATE TABLE courses (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL UNIQUE,
  PRIMARY KEY (id));

CREATE TABLE course_signup (
  student_id BIGINT NOT NULL,
  course_id BIGINT NOT NULL,
  PRIMARY KEY (student_id, course_id),
  FOREIGN KEY (student_id) REFERENCES students(id),
  FOREIGN KEY (course_id) REFERENCES courses(id));
