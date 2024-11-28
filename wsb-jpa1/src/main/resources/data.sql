INSERT INTO ADDRESS (city, address_line1, address_line2, postal_code)
VALUES ('Warszawa', 'Aleje Jerozolimskie 1', NULL, '00-001');

INSERT INTO ADDRESS (city, address_line1, address_line2, postal_code)
VALUES ('Kraków', 'Rynek Główny 12', 'Mieszkanie 5', '31-001');

INSERT INTO ADDRESS (city, address_line1, address_line2, postal_code)
VALUES ('Wrocław', 'Plac Grunwaldzki 20', NULL, '50-001');


INSERT INTO DOCTOR (first_name, last_name, telephone_number, email, doctor_number, specialization, address_id)
VALUES ('Jan', 'Kowalski', '123456789', 'jan.kowalski@hospital.pl', 'DOC001', 'SURGEON', 1);

INSERT INTO DOCTOR (first_name, last_name, telephone_number, email, doctor_number, specialization, address_id)
VALUES ('Anna', 'Nowak', '987654321', 'anna.nowak@hospital.pl', 'DOC002', 'GP', 2);


INSERT INTO PATIENT (first_name, last_name, telephone_number, email, patient_number, date_of_birth, address_id)
VALUES ('Piotr', 'Zieliński', '111222333', 'piotr.zielinski@gmail.com', 'PAT001', '1990-05-15', 3);

INSERT INTO PATIENT (first_name, last_name, telephone_number, email, patient_number, date_of_birth, address_id)
VALUES ('Katarzyna', 'Wiśniewska', '444555666', 'katarzyna.wisniewska@gmail.com', 'PAT002', '1985-07-20', 2);


INSERT INTO VISIT (description, time, doctor_id, patient_id)
VALUES ('Kontrola pooperacyjna', '2024-11-28 10:00:00', 1, 1);

INSERT INTO VISIT (description, time, doctor_id, patient_id)
VALUES ('Konsultacja neurologiczna', '2024-11-28 12:00:00', 2, 2);


INSERT INTO MEDICAL_TREATMENT (description, type, visit_id)
VALUES ('ECHO serca', 'EKG', 1);

INSERT INTO MEDICAL_TREATMENT (description, type, visit_id)
VALUES ('Rezonans magnetyczny', 'RTG', 2);