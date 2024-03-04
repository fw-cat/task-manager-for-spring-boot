CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    user_id BIGINT(16) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status INT NOT NULL,
    due_dt DATE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP NULL -- ソフトデリート用のカラムを追加
) CHARACTER SET utf8mb4 COLLATE 'utf8mb4_general_ci';