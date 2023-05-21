SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;
SET default_tablespace = '';
SET default_table_access_method = heap;

CREATE TABLE public.user_info (
    id character varying(64) NOT NULL,
    name character varying(30) NOT NULL,
    email character varying(256) NOT NULL,
    created_by character varying(30),
    created_at timestamp without time zone,
    updated_by character varying(30),
    updated_at timestamp without time zone
);

ALTER TABLE ONLY public.user_info
    ADD CONSTRAINT user_info_pkey PRIMARY KEY (id);