-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT RANDOM_UUID(),
    cedula VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    genero VARCHAR(20),
    email VARCHAR(255) NOT NULL UNIQUE,
    carrera VARCHAR(100),
    password VARCHAR(255) NOT NULL,
    rol VARCHAR(30) NOT NULL DEFAULT 'ESTUDIANTE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Índices para búsquedas rápidas
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_cedula ON users(cedula);

-- Tabla de actividades de reciclaje (para el dashboard)
CREATE TABLE IF NOT EXISTS recycling_activities (
    id UUID PRIMARY KEY DEFAULT RANDOM_UUID(),
    user_id UUID NOT NULL,
    material VARCHAR(50) NOT NULL,
    material_type VARCHAR(30) NOT NULL,
    weight DECIMAL(10,2) NOT NULL,
    weight_unit VARCHAR(10) DEFAULT 'kg',
    points INTEGER NOT NULL,
    status VARCHAR(20) DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Tabla de recompensas
CREATE TABLE IF NOT EXISTS rewards (
    id UUID PRIMARY KEY DEFAULT RANDOM_UUID(),
    title VARCHAR(100) NOT NULL,
    description TEXT,
    points_cost INTEGER NOT NULL,
    stock INTEGER DEFAULT 0,
    image_url VARCHAR(255),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de canjes (rewards redimidos)
CREATE TABLE IF NOT EXISTS redemptions (
    id UUID PRIMARY KEY DEFAULT RANDOM_UUID(),
    user_id UUID NOT NULL,
    reward_id UUID NOT NULL,
    points_spent INTEGER NOT NULL,
    status VARCHAR(20) DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (reward_id) REFERENCES rewards(id)
);

-- Datos de ejemplo para pruebas
INSERT INTO users (cedula, nombre, apellido, genero, email, carrera, password, rol) 
VALUES ('1234567890', 'Juan', 'Pérez', 'masculino', 'juan@test.com', 'Ingeniería Ambiental', 
        '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqrqQzBZN0UfGNEKjN7XhJz2L8Y1e5G', 'ESTUDIANTE')
ON CONFLICT (email) DO NOTHING;