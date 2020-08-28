FROM postgres:latest

#ENV ANONAPP_DB_USER movage
#ENV ANONAPP_DB_DATABASE anonapp_db
#ENV ANONAPP_DB_PASSWORD 1024
ENV POSTGRES_PASSWORD password
ENV POSTGRES_DB anonapp_db
ADD init/00_init_user.sql /docker-entrypoint-initdb.d/