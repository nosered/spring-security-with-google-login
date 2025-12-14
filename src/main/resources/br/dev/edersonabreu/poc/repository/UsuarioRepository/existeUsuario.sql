SELECT EXISTS (
    SELECT 1 
    FROM usuario
    WHERE email = :email
);