
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;


CREATE TABLE IF NOT EXISTS game_information (
    id int(11) unsigned NOT NULL,
    money int(11) DEFAULT '0',
    productivity int(11) NOT NULL DEFAULT '0',
    likeability int(11) NOT NULL DEFAULT '0',
    pc_level int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


INSERT INTO game_information (id, money, productivity, likeability, pc_level) VALUES
    (5, 500, 5, 4, 2);


CREATE TABLE IF NOT EXISTS project (
    id int(11) unsigned NOT NULL,
    name varchar(255) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;


INSERT INTO project (id, name) VALUES
    (1, 'Project A'),
    (2, 'Project B');


CREATE TABLE IF NOT EXISTS project_user (
    project_id int(11) unsigned NOT NULL,
    user_id int(11) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO project_user (project_id, user_id) VALUES
    (1, 5),
    (2, 5),
    (2, 6);


CREATE TABLE IF NOT EXISTS user (
    id int(11) unsigned NOT NULL,
    first_name varchar(100) DEFAULT NULL,
    last_name varchar(100) DEFAULT NULL,
    email varchar(255) DEFAULT NULL,
    game_information_id int(11) unsigned DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;



INSERT INTO user (id, first_name, last_name, email, game_information_id) VALUES
    (4, 'voornaam', 'achternaam', 'email@email.com', NULL),
    (5, 'Klaas', 'Met een achternaam', NULL, 5),
    (6, 'Pieter', 'Met een achternaam', NULL, NULL);


ALTER TABLE game_information
    ADD PRIMARY KEY (id);

ALTER TABLE project
    ADD PRIMARY KEY (id);

ALTER TABLE project_user
    ADD PRIMARY KEY (project_id,user_id), ADD KEY fk_projectuser_project_id (project_id), ADD KEY fk_projectuser_user (user_id);

ALTER TABLE user
    ADD PRIMARY KEY (id), ADD KEY fk_user_gameInformation (game_information_id);

ALTER TABLE game_information
    MODIFY id int(11) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;

ALTER TABLE project
    MODIFY id int(11) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;

ALTER TABLE user
    MODIFY id int(11) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;

ALTER TABLE project_user
    ADD CONSTRAINT fk_projectuser_project FOREIGN KEY (project_id) REFERENCES project (id) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT fk_projectuser_user FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE user
    ADD CONSTRAINT fk_user_gameInformation FOREIGN KEY (game_information_id) REFERENCES game_information (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE sonar_push ADD CONSTRAINT fk_soner_push_user FOREIGN KEY (user_id) REFERENCES user (id);

ALTER TABLE sonar_push ADD CONSTRAINT fk_soner_push_project FOREIGN KEY (project_id) REFERENCES project (id);



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
