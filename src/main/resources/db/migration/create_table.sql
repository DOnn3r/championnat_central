-- création des types enum
create type poste as enum ('ATT', 'ML', 'DF', 'GK');
create type status as enum ('NOT_STARTED', 'STARTED', 'FINISHED');
create type duration_unit as enum ('SECOND', 'MINUTE', 'HOUR');

-- table championnat
create table championnat (
                             id_championnat uuid primary key,
                             nom varchar(50) unique,
                             pays varchar(50)
);

-- table club
create table club (
                      id_club uuid primary key,
                      nom varchar(100) unique,
                      acronyme varchar(10),
                      annee_creation integer,
                      nom_stade varchar(100),
                      id_championnat uuid,
                      constraint fk_club_championnat foreign key (id_championnat) references championnat(id_championnat)
);

-- table joueur
create table joueur (
                        id_joueur uuid primary key,
                        nom varchar(100),
                        numero integer,
                        poste poste,
                        nationalite varchar(50),
                        age integer,
                        id_club uuid,
                        constraint fk_joueur_club foreign key (id_club) references club(id_club),
                        constraint uq_joueur_numero unique (numero, id_club)
);


-- table entraineur
create table entraineur (
                            id_entraineur uuid primary key,
                            nom varchar(100),
                            nationalite varchar(50),
                            age Integer,
                            id_club uuid unique,
                            constraint fk_entraineur_club foreign key (id_club) references club(id_club)
);

-- table saison
create table saison (
                        id_saison uuid primary key,
                        year integer,
                        season_year varchar(20) unique,
                        status status
);

-- table match
create table match (
                       id_match uuid primary key,
                       date_heure timestamp,
                       stade varchar(100),
                       id_club_domicile uuid,
                       id_club_exterieur uuid,
                       score_domicile integer not null default 0 check ( score_domicile >= 0 ),
                       score_exterieur integer not null default 0 check ( score_exterieur >= 0 ),
                       id_saison uuid,
                       id_championnat uuid,
                       status status,
                       constraint fk_match_club_domicile foreign key (id_club_domicile) references club(id_club),
                       constraint fk_match_club_exterieur foreign key (id_club_exterieur) references club(id_club),
                       constraint fk_match_saison foreign key (id_saison) references saison(id_saison),
                       constraint fk_match_championnat foreign key (id_championnat) references championnat(id_championnat),
                       constraint check_clubs_differents check (id_club_domicile is distinct from id_club_exterieur)
    );

-- table stats_club_saison
create table stats_club_saison (
                                   id_stats uuid primary key,
                                   id_club uuid not null,
                                   id_saison uuid not null ,
                                   points integer not null default 0 check (points >= 0),
                                   buts_marques integer not null default 0 check (buts_marques >= 0),
                                   buts_encaisses integer not null default 0 check (buts_encaisses >= 0),
                                   difference_buts integer generated always as (buts_marques - buts_encaisses) stored,
                                   clean_sheets integer not null default 0 check (clean_sheets >= 0),
                                   constraint fk_stats_club foreign key (id_club) references club(id_club),
                                   constraint fk_stats_saison foreign key (id_saison) references saison(id_saison),
                                   constraint uq_stats_club_saison unique (id_club, id_saison)
);

-- table stats_joueur_saison
create table stats_joueur_saison (
                                     id_stats uuid primary key,
                                     id_joueur uuid,
                                     id_saison uuid,
                                     buts_marques integer not null default 0 check (buts_marques >= 0),
                                     buts_contre_son_camp integer not null default 0 check (buts_contre_son_camp >= 0),
                                     duree_jeu_value integer check (duree_jeu_value >= 0),
                                     duree_jeu_unit duration_unit default 'SECOND',
                                     constraint fk_stats_joueur foreign key (id_joueur) references joueur(id_joueur),
                                     constraint fk_stats_saison_joueur foreign key (id_saison) references saison(id_saison),
                                     constraint uq_stats_joueur_saison unique (id_joueur, id_saison)
);


-- table but
create table but (
                     id_but uuid primary key,
                     id_match uuid,
                     id_joueur uuid,
                     id_club uuid,
                     minute integer check (minute between 1 and 120),
                     contre_son_camp boolean,
                     constraint fk_but_match foreign key (id_match) references match(id_match),
                     constraint fk_but_joueur foreign key (id_joueur) references joueur(id_joueur),
                     constraint fk_but_club foreign key (id_club) references club(id_club)
);