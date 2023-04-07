INSERT INTO user (id, username, password, user_type)
VALUES
    (1, 'Irene', '$2a$10$mankHSFMTbgMyY8fJEUrT.Ci3LhDXRHunbk1KTu1OjuPeMSn/QQBe', 'CUSTOMER'),
    (2, 'learn365', '$2a$10$eZlcSIy9UQmc3I8iFSyrLO0ikv2kONNIq5xuwU7AKE9taf0K25TA.', 'ORGANIZATION'),
    (3, 'Rachel', '$2a$10$PpmsF0Uyf7e5xKT4SJyM/OPlV2BQASj8QX0FMludy7KKoNFaDdBAK', 'CUSTOMER');

INSERT INTO cart (id, total)
VALUES
    (1, 0),
    (2, 0);

INSERT INTO customer (id, cart_id)
VALUES
    (1, 1),
    (3, 2);

INSERT INTO organization (id, organization_name)
VALUES
    (1, 'Learn 365');

INSERT INTO course (id, name, price, original_price, description, course_type, pic, organization_id)
VALUES
    (1, 'The Complete Spring Boot Development BootCamp', 16.99, 19.99, 'Become a Java Web Developer: MVC, REST API, OpenAPI Documentation, Testing, Spring Data JPA (SQL), Spring Security (JWT)', 'Spring Boot', '/image/spring-boot-development.jpg', 1),
    (2, 'Hibernate and Spring Data JPA', 12.99, 21.99, 'Master Hibernate, Remove the mystery of Spring Data JPA - Use Spring Boot 3!', 'Spring Boot', '/image/jpa-hibernate.jpg', 1),
    (3, 'The Complete SQL Bootcamp: Go from Zero to Hero', 15.99, 18.99, 'Become an expert at SQL!', 'SQL', '/image/The-Complete-SQL-Bootcamp.jpg', 1),
    (4, 'Python for Data Science and Machine Learning Bootcamp', 17.59, 24.59, 'Learn how to use NumPy, Pandas, Seaborn , Matplotlib , Plotly , Scikit-Learn , Machine Learning, Tensorflow , and more!', 'Machine Learning', '/image/python-data-science.jpg', 1);

INSERT INTO teach_plan (id, pname, parent_id, grade, description, course_id, order_by)
VALUES
    (1, 'Course Introduction', 0, 1, '', 4, 1),
    (2, 'Environment Set-up', 0, 1, '', 4, 2),
    (3, 'Python for Data Analysis: Numpy', 0, 1, '', 4, 3),
    (4, 'Introduction to the course', 1, 2, '', 4, 1),
    (5, 'Course Help and Welcome', 1, 2, 'Just a quick thank you and how to get help in the course!', 4, 2),
    (6, 'Numpy Arrays', 3, 2, 'Part of Numpy section of the course!', 4, 1),
    (7, 'Numpy Indexing and Selection', 3, 2, 'Part of Numpy section of the course!', 4, 2),
    (8, 'Numpy Operations', 3, 2, 'Part of Numpy section of the course!', 4, 3),
    (9, 'Numpy Exercises', 3, 2, 'Part of Numpy section of the course!', 4, 4);

