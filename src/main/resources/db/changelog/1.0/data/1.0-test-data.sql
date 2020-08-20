INSERT INTO test.household (id, population, cost) VALUES (1, 2, 0.00);
INSERT INTO test.user (id, household, role, login, password, name, email, registration_date, last_failed_login) VALUES (1, 1, 'FAMILY_GUY', 'test', '098f6bcd4621d373cade4e832627b4f6', 'Testowy Krzysiek', 'krzysiek.testowy@test.pl', '2020-01-20 20:06:00', '2020-01-21 20:06:00');
INSERT INTO test.user (id, household, role, login, password, name, email, registration_date, last_failed_login) VALUES (2, 1, 'MEMBER', 'test_member', '098f6bcd4621d373cade4e832627b4f6', 'Testowy uzytkownik', 'uzytkownik.testowy@test.pl', '2020-01-21 20:06:00', '2020-01-21 20:06:00');
INSERT INTO test.balance (id, household, type, generation_date, burden, income, balance) VALUES (1, 1, 'SUMMARY', '2020-02-29', 2000.00, 6000.00, 4000.00);
INSERT INTO test.balance (id, household, type, generation_date, burden, income, balance) VALUES (2, 1, 'PREDICTION', '2020-03-01', 2000.00, 6000.00, 8000.00);
INSERT INTO test.balance (id, household, type, generation_date, burden, income, balance) VALUES (3, 1, 'GENERATED', '2020-03-04', 2000.00, 5000.00, 7000.00);
INSERT INTO test.balance (id, household, type, generation_date, burden, income, balance) VALUES (4, 1, 'GENERATED', '2020-03-11', 2000.00, 10000.00, 12000.00);
INSERT INTO test.balance (id, household, type, generation_date, burden, income, balance) VALUES (5, 1, 'GENERATED', '2020-03-18', 3000.00, 6000.00, 7000.00);
INSERT INTO test.balance (id, household, type, generation_date, burden, income, balance) VALUES (6, 1, 'SUMMARY', '2020-03-31', 3000.00, 6000.00, 7000.00);
INSERT INTO test.balance (id, household, type, generation_date, burden, income, balance) VALUES (7, 1, 'PREDICTION', '2020-04-01', 3000.00, 6000.00, 13000.00);
INSERT INTO test.balance (id, household, type, generation_date, burden, income, balance) VALUES (8, 1, 'SUMMARY', '2020-04-30', 3000.00, 6000.00, 13000.00);
INSERT INTO test.balance (id, household, type, generation_date, burden, income, balance) VALUES (9, 1, 'PREDICTION', '2020-05-01', 3000.00, 6000.00, 16000.00);
INSERT INTO test.balance (id, household, type, generation_date, burden, income, balance) VALUES (10, 1, 'SUMMARY', '2020-05-31', 3000.00, 6000.00, 16000.00);
INSERT INTO test.balance (id, household, type, generation_date, burden, income, balance) VALUES (11, 1, 'PREDICTION', '2020-06-01', 3000.00, 6000.00, 19000.00);
INSERT INTO test.balance (id, household, type, generation_date, burden, income, balance) VALUES (12, 1, 'SUMMARY', '2020-06-30', 3000.00, 6000.00, 19000.00);
INSERT INTO test.balance (id, household, type, generation_date, burden, income, balance) VALUES (13, 1, 'PREDICTION', '2020-07-01', 3000.00, 6000.00, 22000.00);
INSERT INTO test.balance (id, household, type, generation_date, burden, income, balance) VALUES (14, 1, 'SUMMARY', '2020-07-31', 3000.00, 6000.00, 22000.00);
INSERT INTO test.balance (id, household, type, generation_date, burden, income, balance) VALUES (15, 1, 'PREDICTION', '2020-08-01', 3000.00, 6000.00, 25000.00);
INSERT INTO test.cashflow (id, household, category, start_date, end_date, `interval`, amount, description) VALUES (1, 1, 'INCOME', '2020-02-01', '2021-02-01', 1, 6000.00, 'pensja standardowa');
INSERT INTO test.cashflow (id, household, category, start_date, end_date, `interval`, amount, description) VALUES (2, 1, 'INCOME', '2020-02-01', '2021-02-01', 1, 4000.00, 'pensja dodatkowa');
INSERT INTO test.cashflow (id, household, category, start_date, end_date, `interval`, amount, description) VALUES (3, 1, 'EXPENSE', '2020-02-01', '2021-02-01', 1, 3000.00, 'na zycie');
INSERT INTO test.cashflow (id, household, category, start_date, end_date, `interval`, amount, description) VALUES (4, 1, 'EXPENSE', '2020-02-01', '2021-02-01', 1, 1000.00, 'na uczelnie');
INSERT INTO test.debt_card (id, household, `limit`, balance, renewal_percentage, annual_percentage, bank, name) VALUES (1, 1, 10000.00, 8000.00, 2.50, 8.50, 'MBank', 'karta debetowa');
INSERT INTO test.goals (id, household, category, amount, name, description, priority) VALUES (1, 1, 'SAVINGS', 80000.00, 'mieszkaniowe', 'na poczet wpłaty własnej na mieszkanie', 1);
INSERT INTO test.goals (id, household, category, amount, name, description, priority) VALUES (2, 1, 'SHOPPING', 4000.00, 'rower', 'na zakup roweru', 1);
INSERT INTO test.insurance (id, household, type, description, `interval`, cost, end_date, vehicle_tid, name, vehicle_lp) VALUES (1, 1, 'LIFE', 'ubezpieczenie na życie i zdrowie AXA', 1, 80.00, '2025-07-30', null, 'życie AXA', null);
INSERT INTO test.insurance (id, household, type, description, `interval`, cost, end_date, vehicle_tid, name, vehicle_lp) VALUES (2, 1, 'CAR', null, 4, 420.00, '2021-07-30', '2020-04-15', 'Toyota', 'TSK12345');
INSERT INTO test.investment (id, household, category, `interval`, start_date, end_date, investment_percentage, amount, name) VALUES (1, 1, 'INVESTMENT', 12, '2020-07-01', '2021-07-01', 3.00, 10000.00, 'lokata długoterminowa 12m');
INSERT INTO test.investment (id, household, category, `interval`, start_date, end_date, investment_percentage, amount, name) VALUES (2, 1, 'INVESTMENT', 1, '2020-06-01', '2025-08-01', 0.10, 10000.00, 'lokata dynamiczna');
INSERT INTO test.loan (id, household, bank, annual_loan_percentage, start_date, end_date, amount, installment_amount) VALUES (1, 1, 'INGBank', 8.50, '2020-08-01', '2022-08-01', 10000.00, 499.00);
ó