CREATE TABLE IF NOT EXISTS roles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(20),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(120) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_users_username (username)
);

CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS profiles (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email_address VARCHAR(255) NOT NULL,
    profile_type VARCHAR(20) NOT NULL,
    street_address VARCHAR(255),
    street_number VARCHAR(10),
    city VARCHAR(100),
    postal_code VARCHAR(20),
    country VARCHAR(100),
    PRIMARY KEY (id),
    UNIQUE KEY uk_profiles_email_address (email_address)
);

CREATE TABLE IF NOT EXISTS albums (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    profile_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT fk_albums_profile FOREIGN KEY (profile_id) REFERENCES profiles (id)
);

CREATE TABLE IF NOT EXISTS album_photos (
    album_id BIGINT NOT NULL,
    photo_url VARCHAR(1024) NOT NULL,
    CONSTRAINT fk_album_photos_album FOREIGN KEY (album_id) REFERENCES albums (id)
);

CREATE TABLE IF NOT EXISTS service_catalogs (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    profile_id BIGINT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    category VARCHAR(50) NOT NULL,
    price_from DOUBLE NOT NULL,
    price_to DOUBLE NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_service_catalogs_profile FOREIGN KEY (profile_id) REFERENCES profiles (id)
);

CREATE TABLE IF NOT EXISTS quotes (
    quote_id VARCHAR(255) NOT NULL,
    title VARCHAR(255),
    event_type VARCHAR(255),
    guest_quantity INT NOT NULL,
    location VARCHAR(255),
    total_price DOUBLE NOT NULL,
    state VARCHAR(255),
    event_date DATETIME,
    organizer_id BIGINT,
    host_id BIGINT,
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    PRIMARY KEY (quote_id)
);

CREATE TABLE IF NOT EXISTS service_items (
    service_item_id VARCHAR(255) NOT NULL,
    description VARCHAR(255),
    quantity INT NOT NULL,
    unit_price DOUBLE NOT NULL,
    total_price DOUBLE NOT NULL,
    quote_id VARCHAR(255),
    created_at DATETIME NOT NULL,
    updated_at DATETIME NOT NULL,
    PRIMARY KEY (service_item_id)
);

CREATE TABLE IF NOT EXISTS social_events (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    title VARCHAR(255),
    event_date DATE,
    customer_name VARCHAR(255),
    place VARCHAR(255),
    value_status VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS reviews (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    content VARCHAR(255),
    rating INT,
    full_name VARCHAR(255),
    social_event_date DATETIME(6),
    profile_id BIGINT,
    social_event_id BIGINT,
    PRIMARY KEY (id)
);
