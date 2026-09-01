create table saved_game
(
    id uuid primary key,
    book_id varchar(255) not null unique,
    current_section_id integer not null,
    health integer not null,
    status varchar(50) not null,
    created_at timestamp not null,
    updated_at timestamp not null
);