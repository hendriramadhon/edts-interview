create table if not exists event(
	id serial primary key,
	name text not null,
	seat int2 default 0,
	start_time time not null,
	end_time time not null
);

create table if not exists event_booking(
	id serial primary key,
	event_id int2 not null,
	booked_by text not null,
	booking_date timestamptz not null,
	CONSTRAINT event_booking_event_id_fkey FOREIGN KEY (event_id) REFERENCES event(id)
);
