-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Εξυπηρετητής: 127.0.0.1
-- Χρόνος δημιουργίας: 31 Μάη 2024 στις 13:43:48
-- Έκδοση διακομιστή: 10.4.32-MariaDB
-- Έκδοση PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Βάση δεδομένων: `libravision`
--

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `author`
--

CREATE TABLE `author` (
  `author_id` int(10) NOT NULL,
  `author_name` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `author`
--

INSERT INTO `author` (`author_id`, `author_name`) VALUES
(11, 'A. A. Milne'),
(7, 'Adrian Newey'),
(8, 'Ambrose Bierce'),
(5, 'Elijabeth'),
(14, 'J. K. Rowling'),
(12, 'Mo Willems'),
(10, 'Roald Dahl'),
(3, 'Sarah J. Mass'),
(1, 'Taylor Jenkins Reid'),
(4, 'Veronica Roth');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `book`
--

CREATE TABLE `book` (
  `book_id` varchar(20) NOT NULL,
  `page_num` int(10) NOT NULL,
  `release_date` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `description` varchar(300) NOT NULL,
  `rating` float NOT NULL,
  `url` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `book`
--

INSERT INTO `book` (`book_id`, `page_num`, `release_date`, `title`, `description`, `rating`, `url`) VALUES
('978-1-4088-3233-2', 123, 20240512, 'Throne of Glass', 'Test Description', 4.2, '/misc/book1.jpg'),
('978-1-5266-2288-4', 799, 20200507, 'House of Earth and Blood', 'Sarah J. Maas\'s bestselling Crescent City series begins with House of Earth and Blood, which follows the story of half-Fae and half-human Bryce Quinlan as she seeks revenge in a contemporary fantasy world of magic, danger, and searing romance.', 4.49, '/misc/book2.jpg'),
('9780385741262', 309, 20140312, 'We Were Liars', 'The TikTok phenomenon and #1 New York Times bestseller. A beautiful and distinguished family. A private island. A brilliant, damaged girl; a passionate, political boy. A group of four friends-the Liars-whose friendship turns destructive. A revolution. An accident. A secret. Lies upon lies. True love', 3.67, '/misc/book5.jpg'),
('9781406348248', 57, 1994, 'There is a bird on your head!', 'Test Description', 0, '/misc/book1.jpg'),
('9781713541714', 0, 20240512, 'Chosen Ones', 'null', 4.2, '/misc/book3.jpg'),
('9788702113990', 303, 1997, 'Harry Potter and the Philosopher\'s Stone', 'Test Description', 0, '/misc/book1.jpg'),
('9789605213121', 475, 20170502, 'The Seven Husbands of Evelyn Hugo', 'Aging and reclusive Hollywood movie icon Evelyn Hugo is finally ready to tell the truth about her glamorous and scandalous life. But when she chooses unknown magazine reporter Monique Grant for the job, no one is more astounded than Monique herself. Why her? Why now?', 4.42, '/misc/book4.jpg');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `book_author`
--

CREATE TABLE `book_author` (
  `id` int(10) NOT NULL,
  `book_id` varchar(20) NOT NULL,
  `author_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `book_author`
--

INSERT INTO `book_author` (`id`, `book_id`, `author_id`) VALUES
(1, '9789605213121', 1),
(2, '9780385741262', 1),
(4, '978-1-5266-2288-4', 3),
(6, '9781713541714', 4),
(7, '9781713541714', 5),
(8, '978-1-4088-3233-2', 3),
(15, '9781406348248', 12),
(16, '9788702113990', 14);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `book_category`
--

CREATE TABLE `book_category` (
  `id` int(10) NOT NULL,
  `book_id` varchar(20) NOT NULL,
  `category_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `book_category`
--

INSERT INTO `book_category` (`id`, `book_id`, `category_id`) VALUES
(1, '978-1-4088-3233-2', 1),
(2, '9789605213121', 1);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `book_genre`
--

CREATE TABLE `book_genre` (
  `id` int(10) NOT NULL,
  `book_id` varchar(20) NOT NULL,
  `genre_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `book_genre`
--

INSERT INTO `book_genre` (`id`, `book_id`, `genre_id`) VALUES
(1, '9789605213121', 1),
(2, '9789605213121', 2),
(5, '9780385741262', 2),
(7, '978-1-5266-2288-4', 2),
(9, '9781713541714', 2),
(10, '978-1-4088-3233-2', 2),
(36, '9781406348248', 35),
(37, '9781406348248', 36),
(38, '9781406348248', 27),
(39, '9781406348248', 38),
(40, '9781406348248', 29),
(41, '9788702113990', 40),
(42, '9788702113990', 41),
(43, '9788702113990', 25),
(44, '9788702113990', 43),
(45, '9788702113990', 44);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `book_review`
--

CREATE TABLE `book_review` (
  `review_id` int(10) NOT NULL,
  `book_id` varchar(20) NOT NULL,
  `user_id` int(10) NOT NULL,
  `submission_date` date NOT NULL,
  `stars` double NOT NULL,
  `details` varchar(400) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `book_review`
--

INSERT INTO `book_review` (`review_id`, `book_id`, `user_id`, `submission_date`, `stars`, `details`) VALUES
(3, '9789605213121', 1, '2024-05-15', 5, 'new book review details'),
(4, '9789605213121', 1, '2024-05-12', 5, 'Amazing'),
(5, '9789605213121', 1, '2024-05-18', 4, 'asdasd'),
(6, '9789605213121', 1, '2024-05-18', 4, 'asdasd'),
(7, '9789605213121', 1, '2024-05-18', 4, 'sdfsdfsd'),
(8, '9789605213121', 1, '2024-05-18', 4, 'asdasd'),
(9, '9789605213121', 1, '2024-05-18', 4, 'sdfsdf'),
(10, '9781713541714', 1, '2024-05-23', 5, 'sdf'),
(11, '9781713541714', 1, '2024-05-23', 5, 'asdas'),
(12, '9781713541714', 1, '2024-05-23', 5, 'HHH'),
(13, '9781713541714', 1, '2024-05-23', 4, 'DFG');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `borrowing`
--

CREATE TABLE `borrowing` (
  `borrowing_id` int(10) NOT NULL,
  `copy_id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  `borrowing_start` date NOT NULL,
  `borrowing_finish` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `borrowing`
--

INSERT INTO `borrowing` (`borrowing_id`, `copy_id`, `user_id`, `borrowing_start`, `borrowing_finish`) VALUES
(6, 2, 2, '2024-05-01', '2024-05-09'),
(7, 6, 2, '2024-05-01', '2024-05-11'),
(10, 8, 3, '2024-05-15', '2024-05-17'),
(11, 9, 2, '2024-05-16', '2024-05-11'),
(20, 11, 1, '2024-05-22', '2024-05-23'),
(21, 12, 1, '2024-05-23', '2024-05-29');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `category`
--

CREATE TABLE `category` (
  `category_id` int(10) NOT NULL,
  `category_name` varchar(40) NOT NULL,
  `user_id` int(10) NOT NULL,
  `url` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `category`
--

INSERT INTO `category` (`category_id`, `category_name`, `user_id`, `url`) VALUES
(1, 'My Book Category', 1, '/misc/book1.jpg');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `copy`
--

CREATE TABLE `copy` (
  `copy_id` int(10) NOT NULL,
  `book_id` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `copy`
--

INSERT INTO `copy` (`copy_id`, `book_id`) VALUES
(11, '978-1-4088-3233-2'),
(12, '978-1-4088-3233-2'),
(13, '978-1-4088-3233-2'),
(9, '978-1-5266-2288-4'),
(67, '9781406348248'),
(68, '9781406348248'),
(14, '9781713541714'),
(15, '9781713541714'),
(16, '9781713541714'),
(17, '9781713541714'),
(18, '9781713541714'),
(19, '9781713541714'),
(20, '9781713541714'),
(21, '9781713541714'),
(22, '9781713541714'),
(23, '9781713541714'),
(24, '9781713541714'),
(70, '9788702113990'),
(71, '9788702113990'),
(72, '9788702113990'),
(1, '9789605213121'),
(2, '9789605213121'),
(5, '9789605213121'),
(6, '9789605213121'),
(8, '9789605213121');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `donation`
--

CREATE TABLE `donation` (
  `donation_id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  `date` date NOT NULL,
  `isbn` varchar(20) NOT NULL,
  `book_num` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `donation`
--

INSERT INTO `donation` (`donation_id`, `user_id`, `date`, `isbn`, `book_num`) VALUES
(13, 1, '2024-05-23', '9781406348248', 3),
(14, 1, '2024-05-23', '9788702113990', 1);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `experience_review`
--

CREATE TABLE `experience_review` (
  `experience_id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  `submission_date` date NOT NULL,
  `details` varchar(300) NOT NULL,
  `staff_stars` double NOT NULL,
  `app_stars` double NOT NULL,
  `condition_stars` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `experience_review`
--

INSERT INTO `experience_review` (`experience_id`, `user_id`, `submission_date`, `details`, `staff_stars`, `app_stars`, `condition_stars`) VALUES
(2, 1, '2024-05-15', 'new book review details', 4, 3, 5),
(3, 1, '2024-05-12', 'Ämazing', 3, 2, 5),
(4, 1, '2024-05-18', 'asfdadfsafsdasfasf', 4, 5, 3),
(5, 1, '2024-05-23', 'AAAAA', 4, 4, 4);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `genre`
--

CREATE TABLE `genre` (
  `genre_id` int(10) NOT NULL,
  `genre_name` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `genre`
--

INSERT INTO `genre` (`genre_id`, `genre_name`) VALUES
(29, 'amistad'),
(9, 'automobile_engineering'),
(1, 'Biography'),
(33, 'cerdos_--_ficción_juvenil'),
(23, 'chapter_book'),
(30, 'cooperativeness'),
(11, 'dictionnaires'),
(38, 'elefantes'),
(32, 'elefantes_--_ficción_juvenil'),
(14, 'english_language_glossaries_vocabularies'),
(27, 'fiction'),
(25, 'friendship'),
(22, 'grands-parents'),
(10, 'great_britain_biography'),
(44, 'hechicería'),
(13, 'humor'),
(20, 'humorous_stories'),
(18, 'humor_(grappigheden)'),
(40, 'internats'),
(31, 'juvenile_fiction__humorous_stories'),
(21, 'medical_fiction'),
(36, 'nids'),
(12, 'nonfiction'),
(34, 'piggie_(fictitious_character__willems)'),
(24, 'quentin_blake'),
(16, 'religion__spirituality'),
(2, 'Romance'),
(41, 'schools'),
(19, 'semantics'),
(28, 'specimens'),
(35, 'swine'),
(26, 'toys'),
(43, 'ungeheuer'),
(15, 'vocabulary');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `librarian`
--

CREATE TABLE `librarian` (
  `librarian_id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  `major` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `library`
--

CREATE TABLE `library` (
  `library_id` int(10) NOT NULL,
  `name` varchar(40) NOT NULL,
  `address` varchar(40) NOT NULL,
  `telephone` varchar(10) NOT NULL,
  `city` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `library`
--

INSERT INTO `library` (`library_id`, `name`, `address`, `telephone`, `city`) VALUES
(1, 'Roumpini\'s Library', 'Dervenakiwn 105', '6948939730', 'Patras'),
(2, 'Jason\'s Library', 'Panourgia 13', '6984738748', 'Patras');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `library_days`
--

CREATE TABLE `library_days` (
  `id` int(10) NOT NULL,
  `library_id` int(10) NOT NULL,
  `closed` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `library_days`
--

INSERT INTO `library_days` (`id`, `library_id`, `closed`) VALUES
(1, 1, '2024-05-21'),
(2, 1, '2024-05-24'),
(3, 2, '0000-00-00'),
(4, 2, '0000-00-00');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `member`
--

CREATE TABLE `member` (
  `member_id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  `points` int(10) NOT NULL DEFAULT 20
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `member`
--

INSERT INTO `member` (`member_id`, `user_id`, `points`) VALUES
(1, 1, 16),
(2, 5, 20),
(4, 7, 20),
(5, 8, 20),
(6, 9, 20);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `notification`
--

CREATE TABLE `notification` (
  `notification_id` int(10) NOT NULL,
  `book_id` varchar(20) NOT NULL,
  `user_id` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `notification`
--

INSERT INTO `notification` (`notification_id`, `book_id`, `user_id`) VALUES
(8, '9780385741262', 1),
(9, '978-1-5266-2288-4', 1);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `reservation`
--

CREATE TABLE `reservation` (
  `reservation_id` int(10) NOT NULL,
  `book_id` varchar(20) NOT NULL,
  `user_id` int(10) NOT NULL,
  `datetime` date NOT NULL,
  `creation_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `reservation`
--

INSERT INTO `reservation` (`reservation_id`, `book_id`, `user_id`, `datetime`, `creation_date`) VALUES
(15, '9789605213121', 1, '2024-05-27', '2024-05-19'),
(16, '978-1-4088-3233-2', 1, '2024-05-23', '2024-05-22'),
(17, '9781406348248', 1, '2024-05-29', '2024-05-23');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `user`
--

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL,
  `type` enum('librarian','member') NOT NULL DEFAULT 'member',
  `fullname` varchar(40) NOT NULL,
  `username` varchar(40) NOT NULL,
  `age` int(2) DEFAULT NULL,
  `password` varchar(15) NOT NULL,
  `email` varchar(30) NOT NULL,
  `telephone` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `user`
--

INSERT INTO `user` (`user_id`, `type`, `fullname`, `username`, `age`, `password`, `email`, `telephone`) VALUES
(1, 'member', 'Roumpini Angoura', 'roubinie21', 21, 'Test1234!', 'roubinieag@gmail.com', '6948939730'),
(2, 'member', 'Iasonas Pavlopoulos', 'callmejason', 21, 'Test1234!', 'jason@gmail.com', '6984738748'),
(3, 'member', 'Theodore Giannakopoulos', 'TeoParty', 23, '!Test1234', 'onoma2001@gmail.com', '6986598688'),
(5, 'member', 'Prigkipissa Roumpini', 'princess21', 0, '1234', 'roubinieagg@gmail.com', '6948439730'),
(7, 'member', 'Jason Pavlopoulos', 'CallMeJasonYT', 22, 'JPpav2002!', 'Jasonpavlop1@gmail.com', '6975916405'),
(8, 'member', 'jasonnnn', 'oasdj', 18, 'asdsadsd', 'iajsd', '123'),
(9, 'member', 'ασδασ', 'δασδ', 22, 'asdsa', 'asdasda', '2312312');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `user_profile`
--

CREATE TABLE `user_profile` (
  `profile_id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  `fav_authors` varchar(200) NOT NULL,
  `fav_genres` varchar(200) NOT NULL,
  `pages` int(20) NOT NULL,
  `interests` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `user_profile`
--

INSERT INTO `user_profile` (`profile_id`, `user_id`, `fav_authors`, `fav_genres`, `pages`, `interests`) VALUES
(6, 1, 'Sarah J. Mass, Brandon Sanderson', 'Fantasy, Romance', 500, 'Reading, Singing, Programming'),
(7, 7, 'Max, Verstappen, Redbull', 'F1, Max Verstappen', 1000, 'F1, Volley, Coding'),
(8, 8, 'SDD', 'ASDASD', 22, 'DASD');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `wear`
--

CREATE TABLE `wear` (
  `wear_id` int(10) NOT NULL,
  `copy_id` int(10) NOT NULL,
  `user_id` int(10) NOT NULL,
  `details` varchar(300) NOT NULL,
  `submission_date` date NOT NULL,
  `url` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `wear`
--

INSERT INTO `wear` (`wear_id`, `copy_id`, `user_id`, `details`, `submission_date`, `url`) VALUES
(7, 2, 1, 'It has been damaged in the outer side', '2024-05-12', '/misc/book4.jpg'),
(8, 11, 1, '123123wedfs', '2024-05-18', '/misc/wornBook.jpg'),
(9, 14, 1, 'asdasdasdasd', '2024-05-20', '/misc/wornBook.jpg'),
(10, 15, 1, 'qweqweqwe', '2024-05-20', '/misc/wornBook.jpg'),
(11, 16, 1, '123', '2024-05-20', '/misc/wornBook.jpg'),
(12, 17, 1, 'asd', '2024-05-20', '/misc/wornBook.jpg'),
(13, 18, 1, '123', '2024-05-20', '/misc/wornBook.jpg'),
(14, 20, 1, '132', '2024-05-20', '/misc/wornBook.jpg'),
(15, 21, 1, '23', '2024-05-20', '/misc/wornBook.jpg'),
(16, 22, 1, '123', '2024-05-20', '/misc/wornBook.jpg'),
(17, 23, 1, '123', '2024-05-20', '/misc/wornBook.jpg'),
(18, 12, 1, 'sdfsdfsdf', '2024-05-23', '/misc/wornBook.jpg');

--
-- Ευρετήρια για άχρηστους πίνακες
--

--
-- Ευρετήρια για πίνακα `author`
--
ALTER TABLE `author`
  ADD PRIMARY KEY (`author_id`),
  ADD UNIQUE KEY `author_name` (`author_name`);

--
-- Ευρετήρια για πίνακα `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`book_id`);

--
-- Ευρετήρια για πίνακα `book_author`
--
ALTER TABLE `book_author`
  ADD PRIMARY KEY (`id`),
  ADD KEY `book_author_link` (`book_id`),
  ADD KEY `author_book_ink` (`author_id`);

--
-- Ευρετήρια για πίνακα `book_category`
--
ALTER TABLE `book_category`
  ADD PRIMARY KEY (`id`),
  ADD KEY `book_category_link` (`book_id`),
  ADD KEY `category_book_link` (`category_id`);

--
-- Ευρετήρια για πίνακα `book_genre`
--
ALTER TABLE `book_genre`
  ADD PRIMARY KEY (`id`),
  ADD KEY `book_genre_link` (`book_id`),
  ADD KEY `genre_book_link` (`genre_id`);

--
-- Ευρετήρια για πίνακα `book_review`
--
ALTER TABLE `book_review`
  ADD PRIMARY KEY (`review_id`),
  ADD KEY `book_review_link` (`book_id`),
  ADD KEY `user_review_link` (`user_id`);

--
-- Ευρετήρια για πίνακα `borrowing`
--
ALTER TABLE `borrowing`
  ADD PRIMARY KEY (`borrowing_id`),
  ADD KEY `copy_borrowing_link` (`copy_id`),
  ADD KEY `user_borrowing_link` (`user_id`);

--
-- Ευρετήρια για πίνακα `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`category_id`),
  ADD KEY `user_category_link` (`user_id`);

--
-- Ευρετήρια για πίνακα `copy`
--
ALTER TABLE `copy`
  ADD PRIMARY KEY (`copy_id`),
  ADD KEY `book_copy_link` (`book_id`);

--
-- Ευρετήρια για πίνακα `donation`
--
ALTER TABLE `donation`
  ADD PRIMARY KEY (`donation_id`),
  ADD KEY `user_docation_link` (`user_id`),
  ADD KEY `isbn_don` (`isbn`);

--
-- Ευρετήρια για πίνακα `experience_review`
--
ALTER TABLE `experience_review`
  ADD PRIMARY KEY (`experience_id`),
  ADD KEY `user_xpReview_link` (`user_id`);

--
-- Ευρετήρια για πίνακα `genre`
--
ALTER TABLE `genre`
  ADD PRIMARY KEY (`genre_id`),
  ADD UNIQUE KEY `genre_name` (`genre_name`);

--
-- Ευρετήρια για πίνακα `librarian`
--
ALTER TABLE `librarian`
  ADD PRIMARY KEY (`librarian_id`),
  ADD KEY `user_librarian_link` (`user_id`);

--
-- Ευρετήρια για πίνακα `library`
--
ALTER TABLE `library`
  ADD PRIMARY KEY (`library_id`);

--
-- Ευρετήρια για πίνακα `library_days`
--
ALTER TABLE `library_days`
  ADD PRIMARY KEY (`id`),
  ADD KEY `libraryid` (`library_id`);

--
-- Ευρετήρια για πίνακα `member`
--
ALTER TABLE `member`
  ADD PRIMARY KEY (`member_id`),
  ADD KEY `user_member_link` (`user_id`);

--
-- Ευρετήρια για πίνακα `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`notification_id`),
  ADD KEY `book_notification_link` (`book_id`),
  ADD KEY `user_notification_link` (`user_id`);

--
-- Ευρετήρια για πίνακα `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`reservation_id`),
  ADD KEY `book_reservation_link` (`book_id`),
  ADD KEY `user_reservation_link` (`user_id`);

--
-- Ευρετήρια για πίνακα `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `telephone` (`telephone`);

--
-- Ευρετήρια για πίνακα `user_profile`
--
ALTER TABLE `user_profile`
  ADD PRIMARY KEY (`profile_id`),
  ADD UNIQUE KEY `user_id` (`user_id`),
  ADD KEY `user_profile_link` (`user_id`);

--
-- Ευρετήρια για πίνακα `wear`
--
ALTER TABLE `wear`
  ADD PRIMARY KEY (`wear_id`),
  ADD KEY `copy_wear_link` (`copy_id`),
  ADD KEY `user_wear_link` (`user_id`);

--
-- AUTO_INCREMENT για άχρηστους πίνακες
--

--
-- AUTO_INCREMENT για πίνακα `author`
--
ALTER TABLE `author`
  MODIFY `author_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT για πίνακα `book_author`
--
ALTER TABLE `book_author`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT για πίνακα `book_category`
--
ALTER TABLE `book_category`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT για πίνακα `book_genre`
--
ALTER TABLE `book_genre`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=46;

--
-- AUTO_INCREMENT για πίνακα `book_review`
--
ALTER TABLE `book_review`
  MODIFY `review_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT για πίνακα `borrowing`
--
ALTER TABLE `borrowing`
  MODIFY `borrowing_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT για πίνακα `category`
--
ALTER TABLE `category`
  MODIFY `category_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT για πίνακα `copy`
--
ALTER TABLE `copy`
  MODIFY `copy_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=73;

--
-- AUTO_INCREMENT για πίνακα `donation`
--
ALTER TABLE `donation`
  MODIFY `donation_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT για πίνακα `experience_review`
--
ALTER TABLE `experience_review`
  MODIFY `experience_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT για πίνακα `genre`
--
ALTER TABLE `genre`
  MODIFY `genre_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT για πίνακα `librarian`
--
ALTER TABLE `librarian`
  MODIFY `librarian_id` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT για πίνακα `library`
--
ALTER TABLE `library`
  MODIFY `library_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT για πίνακα `library_days`
--
ALTER TABLE `library_days`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT για πίνακα `member`
--
ALTER TABLE `member`
  MODIFY `member_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT για πίνακα `notification`
--
ALTER TABLE `notification`
  MODIFY `notification_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT για πίνακα `reservation`
--
ALTER TABLE `reservation`
  MODIFY `reservation_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT για πίνακα `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT για πίνακα `user_profile`
--
ALTER TABLE `user_profile`
  MODIFY `profile_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT για πίνακα `wear`
--
ALTER TABLE `wear`
  MODIFY `wear_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Περιορισμοί για άχρηστους πίνακες
--

--
-- Περιορισμοί για πίνακα `book_author`
--
ALTER TABLE `book_author`
  ADD CONSTRAINT `author_book_link` FOREIGN KEY (`author_id`) REFERENCES `author` (`author_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `book_author_link` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Περιορισμοί για πίνακα `book_category`
--
ALTER TABLE `book_category`
  ADD CONSTRAINT `book_category_link` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `category_book_link` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Περιορισμοί για πίνακα `book_genre`
--
ALTER TABLE `book_genre`
  ADD CONSTRAINT `book_genre_link` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `genre_book_link` FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Περιορισμοί για πίνακα `book_review`
--
ALTER TABLE `book_review`
  ADD CONSTRAINT `book_review_link` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_review_link` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Περιορισμοί για πίνακα `borrowing`
--
ALTER TABLE `borrowing`
  ADD CONSTRAINT `copy_borrowing_link` FOREIGN KEY (`copy_id`) REFERENCES `copy` (`copy_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_borrowing_link` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Περιορισμοί για πίνακα `category`
--
ALTER TABLE `category`
  ADD CONSTRAINT `user_category_link` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Περιορισμοί για πίνακα `copy`
--
ALTER TABLE `copy`
  ADD CONSTRAINT `book_copy_link` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Περιορισμοί για πίνακα `donation`
--
ALTER TABLE `donation`
  ADD CONSTRAINT `isbn_don` FOREIGN KEY (`isbn`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_docation_link` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Περιορισμοί για πίνακα `experience_review`
--
ALTER TABLE `experience_review`
  ADD CONSTRAINT `user_xpReview_link` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Περιορισμοί για πίνακα `librarian`
--
ALTER TABLE `librarian`
  ADD CONSTRAINT `user_librarian_link` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Περιορισμοί για πίνακα `library_days`
--
ALTER TABLE `library_days`
  ADD CONSTRAINT `libraryid` FOREIGN KEY (`library_id`) REFERENCES `library` (`library_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Περιορισμοί για πίνακα `member`
--
ALTER TABLE `member`
  ADD CONSTRAINT `user_member_link` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Περιορισμοί για πίνακα `notification`
--
ALTER TABLE `notification`
  ADD CONSTRAINT `book_notification_link` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_notification_link` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Περιορισμοί για πίνακα `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `book_reservation_link` FOREIGN KEY (`book_id`) REFERENCES `book` (`book_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_reservation_link` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Περιορισμοί για πίνακα `user_profile`
--
ALTER TABLE `user_profile`
  ADD CONSTRAINT `user_profile_link` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Περιορισμοί για πίνακα `wear`
--
ALTER TABLE `wear`
  ADD CONSTRAINT `copy_wear_link` FOREIGN KEY (`copy_id`) REFERENCES `copy` (`copy_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_wear_link` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
