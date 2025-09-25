-- Initial test data for H2 database
INSERT INTO users (id, username, password, isUser, mobile) VALUES
(1, 'testuser1', '$2a$12$YcHznDnHUk5m23htcQDte.U7qP2QKAw12PM8XR5Hb4Qm0kuSeeXfK', false, '0771111111'),
(2, 'testuser2', '$2a$12$YcHznDnHUk5m23htcQDte.U7qP2QKAw12PM8XR5Hb4Qm0kuSeeXfK', false, '0772222222');

INSERT INTO places (place_id, owner_id, place_name, latitude, longitude, address, rent, type, rooms) VALUES
(1, 1, 'Test Place 1', 6.9271, 79.8612, 'Colombo 01', 45000.0, 'Apartment', 3),
(2, 2, 'Test Place 2', 6.9200, 79.8600, 'Colombo 02', 35000.0, 'House', 2);