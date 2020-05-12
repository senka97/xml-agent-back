insert into car_brand (name) value ('Tesla');
insert into car_brand (name) value ('BMW');
insert into car_brand (name) value('Audi');
insert into car_brand (name) value('Mercedes');

insert into car_model (name, car_brand_id) value ('Model S', 1);
insert into car_model (name, car_brand_id) value ('x5', 2);
insert into car_model (name, car_brand_id) value('A8', 3);
insert into car_model (name, car_brand_id) value('SLR McLaren', 4);

insert into car (children_seats, rate ,mileage, car_model_id, car_class, fuel_type, trans_type) value (1, 3.5, 5000, 1,'SUV','GAS','MANUEL');
insert into car (children_seats, rate ,mileage, car_model_id, car_class, fuel_type, trans_type) value (0, 3.8, 2000, 2, 'SUV','GAS','MANUEL');
insert into car (children_seats, rate ,mileage, car_model_id, car_class, fuel_type, trans_type) value (2, 4, 10000, 3, 'SUV','GAS','MANUEL');

insert into photo (path, car_id) value ('@/assets/bmw_m2.jpg',1);
insert into photo (path, car_id) value ('@/assets/bmw_m2_2.jpg',2);
insert into photo (path, car_id) value ('@/assets/audi_a8.jpeg',3);

insert into price_list (alias, discount20Days, discount30Days, price_per_day, price_per_km) value ('Price list 1', 0, 15, 25, 3);

insert into advertisement (start_date, end_date, limit_km, cdw, car_id, price_list_id ) value ('2020-07-01','2020-07-29','0',false,1,1);
insert into advertisement (start_date, end_date, limit_km, cdw, car_id, price_list_id ) value ('2020-07-02','2020-07-30','0',true,2,1);
insert into advertisement (start_date, end_date, limit_km, cdw, car_id, price_list_id ) value ('2020-07-03','2020-07-31','0',false,3,1);





