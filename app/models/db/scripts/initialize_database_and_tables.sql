CREATE DATABASE finances;
\c finances;
CREATE TYPE expenditure_category AS ENUM ('Groceries',
                                            'Restaurants',
                                            'Travel',
                                            'Vacation',
                                            'Gifts',
                                            'Miscellaneous',
                                            'Personal Expenditures',
                                            'Pharmacy & Home Supplies',
                                            'Newspapers & Publications',
                                            'Entertainment',
                                            'Health & Fitness',
                                            'Video Games',
                                            'Housing & Rent',
                                            'Clothing',
                                            'Charity',
                                            'Investments',
                                            'Cash Expenditures',
                                            'Education',
                                            'Insurance',
                                            'Medical Expenses',
                                            'Internet & Utilities');

/* Entries are of the form
id (integer)    day (date)          charger (text)                      category (expenditure_category)     amount (numeric)
0           8/20/16             WHOLEFDS RVR 101 340 RI CAMBRIDGE   Groceries                           15.45
*/
CREATE TABLE t_expenditures (
    id          SERIAL                  PRIMARY KEY NOT NULL,
    day         DATE                    NOT NULL,
    charger     TEXT                    NOT NULL,
    category    expenditure_category    NOT NULL,
    amount      NUMERIC                 NOT NULL
);