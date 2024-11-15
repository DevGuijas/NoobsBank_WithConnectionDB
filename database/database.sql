-- Tabela principal de usuários
CREATE TABLE IF NOT EXISTS users (
    id serial PRIMARY KEY NOT NULL,
    full_name VARCHAR(80) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    telephone_number VARCHAR(11) NOT NULL UNIQUE,
    balance NUMERIC(20, 2) NOT NULL,
    auth_code VARCHAR(11) NOT NULL UNIQUE
);

-- Tabela separada para armazenar nomes completos
CREATE TABLE IF NOT EXISTS full_name (
    id_full_name serial PRIMARY KEY NOT NULL,
    user_id INT NOT NULL,
    full_name VARCHAR(80) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tabela separada para armazenar saldos
CREATE TABLE IF NOT EXISTS balance (
    id_user_balance serial PRIMARY KEY NOT NULL,
    user_id INT NOT NULL,
    balance NUMERIC(20, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tabela separada para armazenar códigos de autenticação
CREATE TABLE IF NOT EXISTS auth_code (
    id_user_authcode serial PRIMARY KEY NOT NULL,
    user_id INT NOT NULL,
    auth_code VARCHAR(11) NOT NULL UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Função para gerar um código único de 11 caracteres
CREATE OR REPLACE FUNCTION generate_unique_auth_code() RETURNS VARCHAR AS $$
DECLARE
    new_code VARCHAR(11);
BEGIN
    LOOP
        -- Gera um código aleatório de letras e números com 11 caracteres
        new_code := (
            SELECT string_agg(
                substring('ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789' FROM floor(random() * 36 + 1)::int FOR 1),
                ''
            ) FROM generate_series(1, 11)
        );

        -- Verifica se o código é único na tabela 'users'
        IF NOT EXISTS (SELECT 1 FROM users WHERE auth_code = new_code) THEN
            RETURN new_code;
        END IF;
    END LOOP;
END;
$$ LANGUAGE plpgsql;

-- Função do gatilho para inserir na tabela full_name
CREATE OR REPLACE FUNCTION insert_full_name() RETURNS TRIGGER AS $$
BEGIN
    -- Insere o full_name na tabela full_name
    INSERT INTO full_name (user_id, full_name) VALUES (NEW.id, NEW.full_name);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Função do gatilho para inserir na tabela balance
CREATE OR REPLACE FUNCTION insert_balance() RETURNS TRIGGER AS $$
BEGIN
    -- Insere o balance na tabela balance
    INSERT INTO balance (user_id, balance) VALUES (NEW.id, NEW.balance);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Função do gatilho para inserir na tabela auth_code
CREATE OR REPLACE FUNCTION insert_auth_code() RETURNS TRIGGER AS $$
BEGIN
    -- Insere o auth_code na tabela auth_code
    INSERT INTO auth_code (user_id, auth_code) VALUES (NEW.id, NEW.auth_code);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Gatilho que chama a função insert_full_name após inserir na tabela users
CREATE TRIGGER trg_insert_full_name
AFTER INSERT ON users
FOR EACH ROW EXECUTE FUNCTION insert_full_name();

-- Gatilho que chama a função insert_balance após inserir na tabela users
CREATE TRIGGER trg_insert_balance
AFTER INSERT ON users
FOR EACH ROW EXECUTE FUNCTION insert_balance();

-- Gatilho que chama a função insert_auth_code após inserir na tabela users
CREATE TRIGGER trg_insert_auth_code
AFTER INSERT ON users
FOR EACH ROW EXECUTE FUNCTION insert_auth_code();

-- Exemplo de inserção de dados
INSERT INTO users (full_name, cpf, telephone_number, balance, auth_code) VALUES (
    'OTOLARI ALARIE OHOHO',
    '12345678902',
    '71987429032',
    100.99,  -- Utiliza um número diretamente
    generate_unique_auth_code()
);

-- Consultas para verificar os dados
SELECT * FROM users;
SELECT * FROM full_name;
SELECT * FROM balance;
SELECT * FROM auth_code;

-- Drop table caso precise
DROP TABLE users, full_name, balance, auth_code;

-- 1. Listar todas as foreign keys da tabela 'full_name'
SELECT 
    conname AS constraint_name
FROM 
    pg_constraint
WHERE 
    conrelid = 'full_name'::regclass 
    AND contype = 'f';

-- 2. Listar todas as foreign keys da tabela 'balance'
SELECT 
    conname AS constraint_name
FROM 
    pg_constraint
WHERE 
    conrelid = 'balance'::regclass 
    AND contype = 'f';

-- 3. Listar todas as foreign keys da tabela 'auth_code'
SELECT 
    conname AS constraint_name
FROM 
    pg_constraint
WHERE 
    conrelid = 'auth_code'::regclass 
    AND contype = 'f';

-- 4. Listar todas as foreign keys da tabela 'users'
SELECT 
    conname AS constraint_name
FROM 
    pg_constraint
WHERE 
    conrelid = 'users'::regclass 
    AND contype = 'f';

-- Deletando
ALTER TABLE full_name DROP CONSTRAINT full_name_user_id_fkey;
ALTER TABLE balance DROP CONSTRAINT balance_user_id_fkey;
ALTER TABLE auth_code DROP CONSTRAINT auth_code_user_id_fkey;