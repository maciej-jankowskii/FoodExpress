INSERT INTO addresses (street, home_no, flat_no, city, postal_code) VALUES
('ul. Restauracyjna', '1', '2', 'Warszawa', '00-001'),
('ul. Smakowa', '2','3', 'Kraków', '30-002'),
('ul. Smaczna ', '2','5a', 'Gdańsk', '80-003'),
('ul. Kulinarna', '10','1', 'Poznań', '60-004'),
('ul. Jedzeniowa', '20','10', 'Wrocław', '50-005'),
('ul. Smakowita', '2','3', 'Łódź', '90-006'),
('ul. Testowa', '1', '1', 'Test', '00-001');
INSERT INTO restaurants (name, address_id, type_of_cuisine) VALUES
('Restauracja Polska Smakołyk', 1, 'POLSKA'),
('Trattoria Bella Italia', 2, 'WŁOSKA'),
('Kuchnia Kebaba', 3, 'KEBAB'),
('Smak Chin', 4, 'CHIŃSKA'),
('Sushi King', 5, 'SUSHI'),
('Burger Joint', 6, 'BURGER');
INSERT INTO dishes (name, description, price, restaurant_id) VALUES
('Bigos', 'Tradycyjna polska potrawa z kapusty kiszonej i mięsa', 12.99, 1),
('Pizza Margherita', 'Pizza z sosem pomidorowym, mozzarellą i bazylią', 15.99, 1),
('Kebab wołowy', 'Kebab z wołowiną i warzywami w bułce pita', 8.99, 1),
('Kaczka po pekińsku', 'Kaczka w cienkim placku z sosem', 18.99, 1),
('Sashimi mix', 'Mieszanka świeżych kawałków ryb surowych', 22.99, 1),
('Burger BBQ', 'Burger z grillowaną wołowiną i sosem BBQ', 11.99, 2),
('Pierogi ruskie', 'Pierogi nadziewane serem i ziemniakami', 10.99, 2),
('Lasagne', 'Makaron w sosie mięsnym i bolognese', 14.99, 2),
('Kotlet schabowy', 'Kotlet panierowany z ziemniakami i surówką', 13.99, 2),
('Tom Kha Kai', 'Tajska zupa z kurczakiem i mlekiem kokosowym', 9.99, 2),
('Ratatouille', 'Duszone warzywa z ziołami i oliwą', 11.99, 3),
('Sushi California Roll', 'Sushi z krabem, awokado i ogórkiem', 17.99, 3),
('Kanapka z rybą', 'Świeża kanapka z łososiem i warzywami', 8.99, 3),
('Spaghetti Carbonara', 'Makaron z jajkami, boczkiem i parmezanem', 12.99, 3),
('Sałatka grecka', 'Świeże warzywa z serem feta i oliwkami', 9.99, 3),
('Kurczak curry', 'Kurczak w sosie curry z ryżem', 15.99, 4),
('Kluski śląskie', 'Tradycyjne kluski z mięsem i sosem', 11.99, 4),
('Tortilla z kurczakiem', 'Tortilla z kurczakiem, warzywami i sosem', 8.99, 4),
('Krewetki w czosnku', 'Krewetki smażone w czosnku i masłem', 16.99,4),
('Tacos z mięsem', 'Tacos z mięsem mielonym i warzywami', 10.99,4),
('Kotlet mielony', 'Kotlet mielony z ziemniakami i surówką', 13.99,5),
('Kurczak na ostro', 'Kurczak w pikantnym sosie', 12.99,5),
('Kanapka z szynką', 'Kanapka z szynką i serem', 6.99,5),
('Karkówka z grilla', 'Karkówka grillowana z frytkami', 14.99,5),
('Carpaccio wołowe', 'Carpaccio z wołowiny z rukolą', 18.99,5),
('Sałatka z tuńczykiem', 'Sałatka z tuńczykiem, jajkiem i warzywami', 13.99,6),
('Tosty z serem', 'Grillowane tosty z serem', 7.99,6),
('Koktajl owocowy', 'Świeży koktajl owocowy', 5.99,6),
('Risotto z grzybami', 'Risotto z grzybami i parmezanem', 16.99,6),
('Ciasto czekoladowe', 'Deser z czekoladą i lodami', 9.99,6);

INSERT INTO users(first_name, last_name, email, password, extra_points, address_id) VALUES
('Jan', 'Nowak', 'nowak@example.com', '{bcrypt}$2a$10$ekPcaMnn28t7GP7GSGcF3uhe2xx3YVMr.FrC1B/kUkzw2/UM0Anm2', 1000.0, 7);