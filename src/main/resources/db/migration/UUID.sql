create extension if not exists pgcrypto;


alter table championnat
    alter column id_championnat set default gen_random_uuid();

alter table club
    alter column id_club set default gen_random_uuid();

alter table joueur
    alter column id_joueur set default gen_random_uuid();

alter table entraineur
    alter column id_entraineur set default gen_random_uuid();

alter table saison
    alter column id_saison set default gen_random_uuid();

alter table match
    alter column id_match set default gen_random_uuid();

alter table stats_club_saison
    alter column id_stats set default gen_random_uuid();

alter table stats_joueur_saison
    alter column id_stats set default gen_random_uuid();

alter table but
    alter column id_but set default gen_random_uuid();
