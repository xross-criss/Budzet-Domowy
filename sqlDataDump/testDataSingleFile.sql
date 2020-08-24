INSERT INTO test.household (id, population, cost) VALUES (1, 2, 0.00);
INSERT INTO test.cashflow (id, household, category, start_date, end_date, `period`, amount, description) VALUES (1, 1, 'INCOME', '2020-02-01', '2021-02-01', 1, 6000.00, 'pensja standardowa');
INSERT INTO test.cashflow (id, household, category, start_date, end_date, `period`, amount, description) VALUES (2, 1, 'INCOME', '2020-02-01', '2021-02-01', 1, 4000.00, 'pensja dodatkowa');
INSERT INTO test.cashflow (id, household, category, start_date, end_date, `period`, amount, description) VALUES (3, 1, 'EXPENSE', '2020-02-01', '2021-02-01', 1, 3000.00, 'na zycie');
INSERT INTO test.cashflow (id, household, category, start_date, end_date, `period`, amount, description) VALUES (4, 1, 'EXPENSE', '2020-02-01', '2021-02-01', 1, 1000.00, 'na uczelnie');
INSERT INTO test.user (id, household, role, login, password, name, email, registration_date, last_failed_login) VALUES (1, 1, 'FAMILY_GUY', 'test', '098f6bcd4621d373cade4e832627b4f6', 'Testowy Krzysiek', 'krzysiek.testowy@test.pl', '2020-01-20 20:06:00', '2020-01-21 20:06:00');
INSERT INTO test.user (id, household, role, login, password, name, email, registration_date, last_failed_login) VALUES (2, 1, 'MEMBER', 'test_member', '098f6bcd4621d373cade4e832627b4f6', 'Testowy uzytkownik', 'uzytkownik.testowy@test.pl', '2020-01-21 20:06:00', '2020-01-21 20:06:00');