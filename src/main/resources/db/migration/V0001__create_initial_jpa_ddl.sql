CREATE TABLE customers (
  customer_id  VARCHAR(50) NOT NULL UNIQUE,
  first_name VARCHAR(50) NOT NULL,
  last_name VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  birthday TIMESTAMP NOT NULL,
  cpf VARCHAR(15) NOT NULL,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP NOT NULL,
  PRIMARY KEY (
      customer_id
  )
)