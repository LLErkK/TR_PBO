-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 01, 2024 at 02:34 PM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `restoran`
--

-- --------------------------------------------------------

--
-- Table structure for table `detail_pesanan`
--

CREATE TABLE `detail_pesanan` (
  `id` int NOT NULL,
  `pesanan_id` int NOT NULL,
  `menu_id` int NOT NULL,
  `kuantitas` int NOT NULL,
  `subtotal` double(10,2) NOT NULL,
  `tanggal` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `detail_pesanan`
--

INSERT INTO `detail_pesanan` (`id`, `pesanan_id`, `menu_id`, `kuantitas`, `subtotal`, `tanggal`) VALUES
(1, 4, 1, 1, 15000.00, '2024-11-30 20:40:58'),
(2, 4, 2, 2, 20000.00, '2024-11-30 20:40:58'),
(3, 5, 1, 3, 45000.00, '2024-12-01 12:09:25'),
(4, 5, 2, 1, 10000.00, '2024-12-01 12:09:25'),
(5, 6, 1, 2, 30000.00, '2024-12-01 12:26:15'),
(6, 6, 2, 3, 30000.00, '2024-12-01 12:26:15'),
(7, 8, 2, 1, 10000.00, '2024-12-01 20:51:57'),
(8, 8, 8, 2, 6000.00, '2024-12-01 20:51:57'),
(9, 8, 6, 1, 12000.00, '2024-12-01 20:51:57');

-- --------------------------------------------------------

--
-- Table structure for table `history_topup`
--

CREATE TABLE `history_topup` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `amount` double(10,2) NOT NULL,
  `tanggal` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `history_topup`
--

INSERT INTO `history_topup` (`id`, `user_id`, `amount`, `tanggal`) VALUES
(1, 1, 90000.00, '2024-11-30 20:22:06'),
(2, 1, 200000.00, '2024-12-01 12:09:49');

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE `menu` (
  `id` int NOT NULL,
  `nama` varchar(255) NOT NULL,
  `harga` double(10,2) NOT NULL,
  `stok` int DEFAULT NULL,
  `foto` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `tipe` enum('minuman','makanan') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`id`, `nama`, `harga`, `stok`, `foto`, `tipe`) VALUES
(1, 'Udang Goreng', 15000.00, 50, '', 'makanan'),
(2, 'Ayam Goreng', 10000.00, 50, '', 'makanan'),
(4, 'Nasi Goreng', 12000.00, NULL, NULL, 'makanan'),
(5, 'Jus Mangga', 5000.00, NULL, NULL, 'minuman'),
(6, 'Lotek', 12000.00, NULL, NULL, 'makanan'),
(7, 'Bakso', 15000.00, NULL, NULL, 'makanan'),
(8, 'Es Teh', 3000.00, NULL, NULL, 'minuman'),
(9, 'Es Jeruk', 4000.00, NULL, NULL, 'minuman'),
(10, 'Ayam Geprek', 19000.00, NULL, NULL, 'makanan'),
(11, 'Mie Goreng', 5000.00, NULL, NULL, 'makanan'),
(12, 'Air Es', 1000.00, NULL, NULL, 'minuman'),
(13, 'Soda Gembira', 10000.00, NULL, NULL, 'minuman'),
(14, 'Es teh leci', 6000.00, NULL, NULL, 'minuman');

-- --------------------------------------------------------

--
-- Table structure for table `pesanan`
--

CREATE TABLE `pesanan` (
  `id` int NOT NULL,
  `user_id` int NOT NULL,
  `tanggal` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total` double(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `pesanan`
--

INSERT INTO `pesanan` (`id`, `user_id`, `tanggal`, `total`) VALUES
(4, 1, '2024-11-30 13:40:58', 35000.00),
(5, 1, '2024-12-01 05:09:25', 55000.00),
(6, 1, '2024-12-01 05:26:15', 60000.00),
(7, 1, '2024-12-01 13:51:15', 30000.00),
(8, 1, '2024-12-01 13:51:57', 28000.00);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(25) NOT NULL,
  `role` enum('admin','user') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'user',
  `balance` double(10,2) DEFAULT '0.00'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `email`, `password`, `role`, `balance`) VALUES
(1, 'test', 'test@gmail.com', 'test', 'user', 82000.00),
(4, 'admin', 'admin@gmail.com', 'admin', 'admin', 0.00);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `detail_pesanan`
--
ALTER TABLE `detail_pesanan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `history_topup`
--
ALTER TABLE `history_topup`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nama` (`nama`);

--
-- Indexes for table `pesanan`
--
ALTER TABLE `pesanan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `detail_pesanan`
--
ALTER TABLE `detail_pesanan`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `history_topup`
--
ALTER TABLE `history_topup`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `menu`
--
ALTER TABLE `menu`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `pesanan`
--
ALTER TABLE `pesanan`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
