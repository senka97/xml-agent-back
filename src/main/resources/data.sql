insert into authority (name) value ('ROLE_AGENT');
insert into agent (email, password) value ('agent@gmail.com', '$2a$10$z8pgIaOYaOE7MgeY7g/ceuqt377Zp8U/ZWQK2N5i1/8Sn4PYM1IIS');
insert into agent_authority (agent_id, authority_id) value (1,1);

insert into car_brand (name) value ('Tesla');
insert into car_brand (name) value ('BMW');
insert into car_model (name, car_brand_id) value ('Model S', 1);
insert into car_model (name, car_brand_id) value ('x5', 2);
