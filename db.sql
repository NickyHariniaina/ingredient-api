-- Create database
CREATE DATABASE mini_dish_db;

-- Create user
CREATE USER mini_dish_db_manager WITH PASSWORD '123456';

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE mini_dish_db TO mini_dish_db_manager;

-- Grant schema privileges
GRANT ALL PRIVILEGES ON SCHEMA public TO mini_dish_db_manager;

-- Grant table privileges (for CRUD operations)
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO mini_dish_db_manager;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO mini_dish_db_manager;

-- Set default privileges for future tables
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON TABLES TO mini_dish_db_manager;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL PRIVILEGES ON SEQUENCES TO mini_dish_db_manager;
