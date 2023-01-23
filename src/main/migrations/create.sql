

CREATE TABLE bagr.flight (id int PRIMARY KEY,src_airport TEXT,dest_airport TEXT, actual_arrival_time DATETIME, scheduled_arrival_time DATETIME NOT NULL, actual_departure_time DATETIME, scheduled_departure_time DATETIME NOT NULL, gate TEXT, is_loaded BOOLEAN, status TEXT, belt TEXT);

CREATE TABLE bagr.passenger( id int PRIMARY KEY,flight_id int, FOREIGN KEY(flight_id) REFERENCES bagr.flight(id), first_name TEXT, last_name TEXT, age int, pnr TEXT, is_checked_in BOOLEAN, metadata JSON, itinerary_id int NULL);

CREATE TABLE bagr.itinerary(id int PRIMARY KEY, checkin_luggage_qty int, checkin_luggage_weight int, cabin_luggage_weight int, add_ons JSON, passenger_id int, FOREIGN KEY(passenger_id) REFERENCES bagr.passenger(id));

CREATE TABLE bagr.executive( id int PRIMARY KEY, name TEXT, total_checkins TEXT, total_baggage int, username VARCHAR(255) UNIQUE NOT NULL, hashed_password VARCHAR(255) UNIQUE NOT NULL);

CREATE TABLE bagr.ground_crew( id int PRIMARY KEY, status TEXT, flight_id int UNIQUE, FOREIGN KEY(flight_id) REFERENCES bagr.flight(id));