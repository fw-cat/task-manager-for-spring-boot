CREATE TABLE registration_tokens (
    id SERIAL PRIMARY KEY,
    user_id INT UNIQUE NOT NULL,
    token TEXT NOT NULL,
    status INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP NULL, -- ソフトデリート用のカラムを追加
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
