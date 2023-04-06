INSERT INTO course (id, name, price, original_price, description, course_type, pic)
VALUES
    (1, 'The Complete Spring Boot Development BootCamp', 16.99, 19.99, 'Become a Java Web Developer: MVC, REST API, OpenAPI Documentation, Testing, Spring Data JPA (SQL), Spring Security (JWT)', 'Spring Boot', '/image/spring-boot-development.jpg'),
    (2, 'Hibernate and Spring Data JPA', 12.99, 21.99, 'Master Hibernate, Remove the mystery of Spring Data JPA - Use Spring Boot 3!', 'Spring Boot', '/image/jpa-hibernate.jpg'),
    (3, 'The Complete SQL Bootcamp: Go from Zero to Hero', 15.99, 18.99, 'Become an expert at SQL!', 'SQL', '/image/The-Complete-SQL-Bootcamp.jpg'),
    (4, 'Python for Data Science and Machine Learning Bootcamp', 17.59, 24.59, 'Learn how to use NumPy, Pandas, Seaborn , Matplotlib , Plotly , Scikit-Learn , Machine Learning, Tensorflow , and more!', 'Machine Learning', '/image/python-data-science.jpg');

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

