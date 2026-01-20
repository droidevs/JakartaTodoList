-- =====================================================
-- Database Initialization Script for Jakarta Todo List
-- Azure SQL / SQL Server Version
-- =====================================================

-- Create users table
IF NOT EXISTS (SELECT * FROM sys.objects WHERE name='users' AND type='U')
CREATE TABLE users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    full_name VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL
);
GO

-- Create categories table
IF NOT EXISTS (SELECT * FROM sys.objects WHERE name='categories' AND type='U')
CREATE TABLE categories (
    id INT IDENTITY(1,1) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    color VARCHAR(20),
    description VARCHAR(300),
    user_id INT NOT NULL,
    CONSTRAINT fk_category_user FOREIGN KEY (user_id) REFERENCES users(id)
);
GO

-- Create todos table
IF NOT EXISTS (SELECT * FROM sys.objects WHERE name='todos' AND type='U')
CREATE TABLE todos (
    id INT IDENTITY(1,1) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(MAX),
    status VARCHAR(50) DEFAULT 'NEW',
    due_date DATE,
    user_id INT NOT NULL,
    category_id INT,
    CONSTRAINT fk_todo_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_todo_category FOREIGN KEY (category_id) REFERENCES categories(id)
);
GO

-- Create indexes for better performance
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'idx_todos_user_id')
    CREATE INDEX idx_todos_user_id ON todos(user_id);
GO

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'idx_todos_category_id')
    CREATE INDEX idx_todos_category_id ON todos(category_id);
GO

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'idx_todos_status')
    CREATE INDEX idx_todos_status ON todos(status);
GO

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'idx_todos_due_date')
    CREATE INDEX idx_todos_due_date ON todos(due_date);
GO

IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'idx_categories_user_id')
    CREATE INDEX idx_categories_user_id ON categories(user_id);
GO

-- Insert sample user (password: 'password123' hashed with BCrypt)
-- You can use this for testing, remove in production
IF NOT EXISTS (SELECT * FROM users WHERE username = 'testuser')
BEGIN
    INSERT INTO users (username, full_name, password_hash)
    VALUES ('testuser', 'Test User', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZRGdjGj/n3.rsQ0fEnxRPeIa5gVbK');
END
GO
