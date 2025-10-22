#!/bin/bash

mysql -u root -p"$MYSQL_ROOT_PASSWORD" <<-EOSQL
    CREATE DATABASE IF NOT EXISTS mesago_auth_db;
    CREATE DATABASE IF NOT EXISTS mesago_inventario;
    CREATE DATABASE IF NOT EXISTS mesago_proveedores;
    -- Añade aquí más líneas de CREATE DATABASE para tus otros microservicios cuando los actives.
EOSQL