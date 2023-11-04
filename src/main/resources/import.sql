INSERT INTO addresses (street, home_no, flat_no, city, postal_code) VALUES
('ul. Restauracyjna', '1', '2', 'Warszawa', '00-001'),
('ul. Smakowa', '2','3', 'Kraków', '30-002'),
('ul. Smaczna ', '2','5a', 'Gdańsk', '80-003'),
('ul. Kulinarna', '10','1', 'Poznań', '60-004'),
('ul. Jedzeniowa', '20','10', 'Wrocław', '50-005'),
('ul. Smakowita', '2','3', 'Łódź', '90-006');
INSERT INTO restaurants (name, address_id, type_of_cuisine) VALUES
('Restauracja Polska Smakołyk', 1, 'POLSKA'),
('Trattoria Bella Italia', 2, 'WŁOSKA'),
('Kuchnia Kebaba', 3, 'KEBAB'),
('Smak Chin', 4, 'CHIŃSKA'),
('Sushi King', 5, 'SUSHI'),
('Burger Joint', 6, 'BURGER');
INSERT INTO dishes (name, description, price) VALUES
('Bigos', 'Tradycyjna polska potrawa z kapusty kiszonej i mięsa', 12.99),
('Pizza Margherita', 'Pizza z sosem pomidorowym, mozzarellą i bazylią', 15.99),
('Kebab wołowy', 'Kebab z wołowiną i warzywami w bułce pita', 8.99),
('Kaczka po pekińsku', 'Kaczka w cienkim placku z sosem', 18.99),
('Sashimi mix', 'Mieszanka świeżych kawałków ryb surowych', 22.99),
('Burger BBQ', 'Burger z grillowaną wołowiną i sosem BBQ', 11.99),
('Pierogi ruskie', 'Pierogi nadziewane serem i ziemniakami', 10.99),
('Lasagne', 'Makaron w sosie mięsnym i bolognese', 14.99),
('Kotlet schabowy', 'Kotlet panierowany z ziemniakami i surówką', 13.99),
('Tom Kha Kai', 'Tajska zupa z kurczakiem i mlekiem kokosowym', 9.99),
('Ratatouille', 'Duszone warzywa z ziołami i oliwą', 11.99),
('Sushi California Roll', 'Sushi z krabem, awokado i ogórkiem', 17.99),
('Kanapka z rybą', 'Świeża kanapka z łososiem i warzywami', 8.99),
('Spaghetti Carbonara', 'Makaron z jajkami, boczkiem i parmezanem', 12.99),
('Sałatka grecka', 'Świeże warzywa z serem feta i oliwkami', 9.99),
('Kurczak curry', 'Kurczak w sosie curry z ryżem', 15.99),
('Kluski śląskie', 'Tradycyjne kluski z mięsem i sosem', 11.99),
('Tortilla z kurczakiem', 'Tortilla z kurczakiem, warzywami i sosem', 8.99),
('Krewetki w czosnku', 'Krewetki smażone w czosnku i masłem', 16.99),
('Tacos z mięsem', 'Tacos z mięsem mielonym i warzywami', 10.99),
('Kotlet mielony', 'Kotlet mielony z ziemniakami i surówką', 13.99),
('Kurczak na ostro', 'Kurczak w pikantnym sosie', 12.99),
('Kanapka z szynką', 'Kanapka z szynką i serem', 6.99),
('Karkówka z grilla', 'Karkówka grillowana z frytkami', 14.99),
('Carpaccio wołowe', 'Carpaccio z wołowiny z rukolą', 18.99),
('Sałatka z tuńczykiem', 'Sałatka z tuńczykiem, jajkiem i warzywami', 13.99),
('Tosty z serem', 'Grillowane tosty z serem', 7.99),
('Koktajl owocowy', 'Świeży koktajl owocowy', 5.99),
('Risotto z grzybami', 'Risotto z grzybami i parmezanem', 16.99),
('Ciasto czekoladowe', 'Deser z czekoladą i lodami', 9.99);
INSERT INTO restaurant_dish (restaurant_id, dish_id) VALUES
(1, 9),
(1, 10),
(1, 11),
(1, 12),
(1, 13);

INSERT INTO restaurant_dish (restaurant_id, dish_id) VALUES
(2, 14),
(2, 15),
(2, 16),
(2, 17),
(2, 18);

INSERT INTO restaurant_dish (restaurant_id, dish_id) VALUES
(3, 19),
(3, 20),
(3, 21),
(3, 22),
(3, 23);

INSERT INTO restaurant_dish (restaurant_id, dish_id) VALUES
(4, 24),
(4, 25),
(4, 26),
(4, 27),
(4, 28);

INSERT INTO restaurant_dish (restaurant_id, dish_id) VALUES
(5, 29),
(5, 30),
(5, 1),
(5, 2),
(5, 3);

INSERT INTO restaurant_dish (restaurant_id, dish_id) VALUES
(6, 4),
(6, 5),
(6, 6),
(6, 7),
(6, 8);

INSERT INTO users(first_name, last_name, email, password, extra_points) VALUES
('Jan', 'Nowak', 'nowak@example.com', '{bcrypt}$2a$10$ekPcaMnn28t7GP7GSGcF3uhe2xx3YVMr.FrC1B/kUkzw2/UM0Anm2', 100);