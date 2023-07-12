insert into user_entity(id, email, email_constraint, email_verified, enabled, realm_id, username, created_timestamp, not_before)
    values ('eae85ea6-1be7-46df-8231-ac355074fdf4',
            'stoneforever@mmail.uk',
            'stoneforever@mmail.uk',
            'f',
            't',
            'ddd02889-9b44-4fc6-a5f6-161af2d8d02d',
            'kc_admin',
            1689151707934,
            0);

insert into user_entity(id, email, email_constraint, email_verified, enabled, realm_id, username, created_timestamp, not_before)
    values ('f156142e-4cf1-4a6d-8534-2cb71dc0f709',
            'spitfire@gmail.com',
            'spitfire@gmail.com',
            'f',
            't',
            'ddd02889-9b44-4fc6-a5f6-161af2d8d02d',
            'spitfire',
            1689151753526,
            0);

insert into user_entity(id, email, email_constraint, email_verified, enabled, realm_id, username, created_timestamp, not_before)
    values ('6fbba4dd-b094-4e46-bdcc-e018dff356c9',
            'motorclaw@somemail.com',
            'motorclaw@somemail.com',
            'f',
            't',
            'ddd02889-9b44-4fc6-a5f6-161af2d8d02d',
            'claw',
            1689151837444,
            0);


insert into credential(id, type, user_id, created_date, user_label, secret_data, credential_data, priority)
    values ('33c89603-cff3-42c1-bfbc-58e869192e0c',
            'password',
            'f156142e-4cf1-4a6d-8534-2cb71dc0f709',
            1689151804809,
            'My password',
            '{"value":"lqNFGi5x1bZ3Y6SS2ImVNqiiuJL/U1/Cj2Fl6Wfti98=","salt":"8CYQnjxwSB+vh+ncaithGA==","additionalParameters":{}}',
            '{"hashIterations":27500,"algorithm":"pbkdf2-sha256","additionalParameters":{}}',
            10);

insert into credential(id, type, user_id, created_date, user_label, secret_data, credential_data, priority)
    values ('3101f1f7-a635-4a58-8f56-9514e939093c',
            'password',
            'eae85ea6-1be7-46df-8231-ac355074fdf4',
            1689151818041,
            'My password',
            '{"value":"BkaubEGIIc6xfWKI1Vo5bNK16FkwgWG5hPu+vhiKcAA=","salt":"t4weHMt9ydil9cYDER3n0Q==","additionalParameters":{}}',
            '{"hashIterations":27500,"algorithm":"pbkdf2-sha256","additionalParameters":{}}',
            10);

insert into credential(id, type, user_id, created_date, user_label, secret_data, credential_data, priority)
    values ('b30c2956-aebf-469c-afd7-1e96de999054',
            'password',
            '6fbba4dd-b094-4e46-bdcc-e018dff356c9',
            1689151850051,
            'My password',
            '{"value":"Z8rZiYDQmqqL+VWtKGIQjlOqaPiOh+bnOEmIS8zGfIU=","salt":"Me3ws8yUDD+uouMptbrp2w==","additionalParameters":{}}',
            '{"hashIterations":27500,"algorithm":"pbkdf2-sha256","additionalParameters":{}}',
            10);


insert into user_role_mapping(role_id, user_id)
    values ('f9db96d8-6e9e-4f89-9329-89134064c816', 'eae85ea6-1be7-46df-8231-ac355074fdf4');

insert into user_role_mapping(role_id, user_id)
    values ('9256706e-cee5-4975-ac5b-d52f1f70ca5f', 'f156142e-4cf1-4a6d-8534-2cb71dc0f709');

insert into user_role_mapping(role_id, user_id)
    values ('9256706e-cee5-4975-ac5b-d52f1f70ca5f', '6fbba4dd-b094-4e46-bdcc-e018dff356c9');