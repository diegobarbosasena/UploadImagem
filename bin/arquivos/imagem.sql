drop database if exists dbimagem;

create database dbimagem;

use dbimagem;

create table imagem (
	
    id bigint auto_increment primary key,
    nome varchar(20),
    imagem longblob 
);

select * from imagem order by id desc;