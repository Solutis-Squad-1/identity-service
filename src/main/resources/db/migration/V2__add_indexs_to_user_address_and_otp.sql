-- Index for table users
CREATE INDEX idx_users_id ON users (id);
CREATE INDEX idx_users_deleted ON users (deleted);
CREATE INDEX idx_users_name ON users (name);

-- Index for table addresses
CREATE INDEX idx_addresses_id ON addresses (id);
CREATE INDEX idx_addresses_deleted ON addresses (deleted);

-- Index for table otps
CREATE INDEX idx_otps_id ON otps (id);
CREATE INDEX idx_otps_deleted ON otps (deleted);
CREATE INDEX idx_otps_code ON otps (code);