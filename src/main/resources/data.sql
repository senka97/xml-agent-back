insert into authority (name) value ('ROLE_AGENT');
insert into agent (email, password, main_id, name, surname, company_name, company_number, address) value ('agent@gmail.com', '$2a$10$z8pgIaOYaOE7MgeY7g/ceuqt377Zp8U/ZWQK2N5i1/8Sn4PYM1IIS', 2, 'Agent1', 'Agent', 'My company', '123456789', 'Alekse Santica 4');
insert into agent_authority (agent_id, authority_id) value (1,1);

insert into car_brand (name) value ('Tesla');
insert into car_brand (name) value ('BMW');
insert into car_brand (name) value('Audi');
insert into car_brand (name) value('Mercedes-Benz');

insert into car_brand (name) value('Kia');
insert into car_brand (name) value('Alfa Romeo');
insert into car_brand (name) value('Chevrolet');
insert into car_brand (name) value('Volkswagen');
insert into car_brand (name) value('FIAT');
insert into car_brand (name) value('Ford');
insert into car_brand (name) value('Hyundai');
insert into car_brand (name) value('Porsche');
insert into car_brand (name) value ('Toyota');

insert into car_model (name, car_brand_id) value ('Model S', 1);
insert into car_model (name, car_brand_id) value ('x5', 2);
insert into car_model (name, car_brand_id) value('A8', 3);
insert into car_model (name, car_brand_id) value('SLR McLaren', 4);

insert into car_model (name, car_brand_id) value ('Model X', 1);
insert into car_model (name, car_brand_id) value ('Model Y', 1);
insert into car_model (name, car_brand_id) value ('Model 3', 1);
insert into car_model (name, car_brand_id) value ('Cybertruck', 1);

insert into car_model (name, car_brand_id) value ('i3', 2);
insert into car_model (name, car_brand_id) value ('i8', 2);
insert into car_model (name, car_brand_id) value ('M5', 2);
insert into car_model (name, car_brand_id) value ('X1', 2);

insert into car_model (name, car_brand_id) value ('A3', 3);
insert into car_model (name, car_brand_id) value ('A4', 3);
insert into car_model (name, car_brand_id) value ('A5', 3);
insert into car_model (name, car_brand_id) value ('A6', 3);

insert into car_model (name, car_brand_id) value ('A-Class', 4);
insert into car_model (name, car_brand_id) value ('AMG GT', 4);
insert into car_model (name, car_brand_id) value ('C-Class', 4);
insert into car_model (name, car_brand_id) value ('CLS', 4);

insert into car_model (name, car_brand_id) value ('Rio', 5);
insert into car_model (name, car_brand_id) value ('Sportage', 5);
insert into car_model (name, car_brand_id) value ('Stinger', 5);
insert into car_model (name, car_brand_id) value ('Sedona', 5);

insert into car_model (name, car_brand_id) value ('4C', 6);
insert into car_model (name, car_brand_id) value ('Giulia', 6);
insert into car_model (name, car_brand_id) value ('Stelvio', 6);
insert into car_model (name, car_brand_id) value ('Mito', 6);

insert into car_model (name, car_brand_id) value ('Colorado', 7);
insert into car_model (name, car_brand_id) value ('Camaro', 7);
insert into car_model (name, car_brand_id) value ('Malibu', 7);
insert into car_model (name, car_brand_id) value ('Cruze', 7);

insert into car_model (name, car_brand_id) value ('Passat', 8);
insert into car_model (name, car_brand_id) value ('Jetta', 8);
insert into car_model (name, car_brand_id) value ('Tiguan', 8);
insert into car_model (name, car_brand_id) value ('Golf', 8);

insert into car_model (name, car_brand_id) value ('500X', 9);
insert into car_model (name, car_brand_id) value ('500L', 9);
insert into car_model (name, car_brand_id) value ('500e', 9);
insert into car_model (name, car_brand_id) value ('500c', 9);

insert into car_model (name, car_brand_id) value ('Escape', 10);
insert into car_model (name, car_brand_id) value ('Fiesta', 10);
insert into car_model (name, car_brand_id) value ('Taurus', 10);
insert into car_model (name, car_brand_id) value ('Flex', 10);

insert into car_model (name, car_brand_id) value ('Kona', 11);
insert into car_model (name, car_brand_id) value ('Nexo', 11);
insert into car_model (name, car_brand_id) value ('Sonata', 11);
insert into car_model (name, car_brand_id) value ('Venue', 11);

insert into car_model (name, car_brand_id) value ('Cayman', 12);
insert into car_model (name, car_brand_id) value ('Macan', 12);
insert into car_model (name, car_brand_id) value ('Panamera', 12);
insert into car_model (name, car_brand_id) value ('Boxster', 12);

insert into car_model (name, car_brand_id) value ('Prius', 13);
insert into car_model (name, car_brand_id) value ('Sienna', 13);
insert into car_model (name, car_brand_id) value ('Yaris', 13);
insert into car_model (name, car_brand_id) value ('Corolla', 13);

insert into car (children_seats, rate ,mileage, car_model_id, car_class, fuel_type, trans_type, has_android_app, main_id) value (1, 3.5, 5000, 1,'Suv','Gas','Semi_automatic', true,1);
insert into car (children_seats, rate ,mileage, car_model_id, car_class, fuel_type, trans_type, has_android_app, main_id) value (0, 3.8, 2000, 2, 'Station_vagon','Gas','Manuel', false,2);
--insert into car (children_seats, rate ,mileage, car_model_id, car_class, fuel_type, trans_type, has_android_app) value (2, 4, 10000, 3, 'Sport_car','Gas','Semi_automatic', true);

insert into photo (path, car_id) value ('./carPictures/1_0.png',1);
insert into photo (path, car_id) value ('./carPictures/1_1.png',1);
insert into photo (path, car_id) value ('./carPictures/2_0.png',2);
insert into photo (path, car_id) value ('./carPictures/2_1.png',2);
insert into photo (path, car_id) value ('./carPictures/2_2.png',2);
--insert into photo (path, car_id) value ('./carPictures/3_0.png',3);
--insert into photo (path, car_id) value ('./carPictures/3_1.png',3);


insert into price_list (alias, discount20Days, discount30Days, price_per_day, price_per_km, price_for_cdw, main_id, removed) value ('Price list - agent 1', 0, 15, 25, 3, 120, 1, false);
--insert into price_list (alias, discount20Days, discount30Days, price_per_day, price_per_km) value ('Summer Price List', 10, 20, 30, 4);

insert into advertisement (start_date, end_date, limit_km, cdw, car_id, price_list_id, location, main_id) value ('2020-06-01','2020-07-20','0',false,1,1,'Novi Sad', 1);
insert into advertisement (start_date, end_date, limit_km, cdw, car_id, price_list_id, location, main_id ) value ('2020-06-01','2020-07-30','0',true,2,1,'Novi Sad', 2);
--insert into advertisement (start_date, end_date, limit_km, cdw, car_id, price_list_id, location ) value ('2020-06-01','2020-06-30','0',false,3,2, 'Beograd');

--insert into comment (from_comment,content,car_id) value ('Pera','<iframe src="javascript:alert(`xss`)">',1);

insert into request (main_id, status, client_first_name, client_last_name, client_phone_number, client_email ) values (1,'Paid', 'Client1' ,'Client', '123456789', 'isa2019pacijent@outlook.com' );
insert into request_ad (main_id, advertisement_id, current_price_per_km, payment, start_date, end_date, request_id, report_created) values (1,1,3,125,'2020-06-04','2020-06-08',1, false);

insert into request (main_id, status, client_first_name, client_last_name, client_phone_number, client_email ) values (2,'Paid', 'Client1' ,'Client', '123456789', 'isa2019pacijent@outlook.com' );
insert into request_ad (main_id, advertisement_id, current_price_per_km, payment, start_date, end_date, request_id, report_created) values (2,2,3,125,'2020-06-09','2020-06-13',2, false);


insert into reservation (main_id, advertisement_id, start_date, end_date, current_price_per_km, client_first_name, client_last_name,client_email, client_phone_number, payment, report_created) values (1,1,'2020-06-14','2020-06-16',3,'Petar', 'Petrovic','petar@gmail.com','061265835',150, false);
