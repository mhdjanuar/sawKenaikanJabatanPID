-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 12, 2025 at 07:40 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `saw_kenaikan_jabatan_pid`
--

-- --------------------------------------------------------

--
-- Table structure for table `alternatif`
--

CREATE TABLE `alternatif` (
  `id` int(11) NOT NULL,
  `id_employee` int(11) NOT NULL,
  `id_sub_kreteria` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `alternatif`
--

INSERT INTO `alternatif` (`id`, `id_employee`, `id_sub_kreteria`) VALUES
(29, 1, 1),
(30, 1, 6),
(31, 1, 11),
(32, 1, 16),
(33, 1, 21),
(34, 4, 2),
(35, 4, 9),
(36, 4, 12),
(37, 4, 18),
(38, 4, 23);

-- --------------------------------------------------------

--
-- Table structure for table `criteria`
--

CREATE TABLE `criteria` (
  `id` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `type` enum('benefit','cost') NOT NULL,
  `bobot` decimal(5,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `criteria`
--

INSERT INTO `criteria` (`id`, `nama`, `type`, `bobot`) VALUES
(1, 'Pengalaman Kerja', 'benefit', 0.25),
(2, 'Kedisiplinan', 'benefit', 0.20),
(3, 'Kinerja', 'benefit', 0.30),
(4, 'Pendidikan Terakhir', 'benefit', 0.15),
(5, 'Kepemimpinan', 'benefit', 0.10);

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `position` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`id`, `name`, `position`) VALUES
(1, 'Muhammad Januar', 'Developer'),
(4, 'Helmy Akbar', 'Staff'),
(5, 'Tanneke Iqbal', 'Head Office'),
(6, 'Galang Wijaya Ubah', 'HRD Ubah');

-- --------------------------------------------------------

--
-- Table structure for table `sub_criteria`
--

CREATE TABLE `sub_criteria` (
  `id` int(11) NOT NULL,
  `id_kreteria` int(11) NOT NULL,
  `jumlah_bobot` decimal(5,2) NOT NULL,
  `deskripsi` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sub_criteria`
--

INSERT INTO `sub_criteria` (`id`, `id_kreteria`, `jumlah_bobot`, `deskripsi`) VALUES
(1, 1, 5.00, '≥ 10 tahun'),
(2, 1, 4.00, '7–9 tahun'),
(3, 1, 3.00, '4–6 tahun'),
(4, 1, 2.00, '1–3 tahun'),
(5, 1, 1.00, '< 1 tahun'),
(6, 2, 5.00, 'Tidak pernah terlambat'),
(7, 2, 4.00, 'Terlambat ≤ 2 kali/bulan'),
(8, 2, 3.00, 'Terlambat 3–4 kali/bulan'),
(9, 2, 2.00, 'Terlambat 5–6 kali/bulan'),
(10, 2, 1.00, 'Terlambat > 6 kali/bulan'),
(11, 3, 5.00, 'Nilai kinerja ≥ 90'),
(12, 3, 4.00, 'Nilai kinerja 80–89'),
(13, 3, 3.00, 'Nilai kinerja 70–79'),
(14, 3, 2.00, 'Nilai kinerja 60–69'),
(15, 3, 1.00, 'Nilai kinerja < 60'),
(16, 4, 5.00, 'S2 atau lebih'),
(17, 4, 4.00, 'S1'),
(18, 4, 3.00, 'D3'),
(19, 4, 2.00, 'SMA/SMK'),
(20, 4, 1.00, 'SMP atau lebih rendah'),
(21, 5, 5.00, 'Sangat baik'),
(22, 5, 4.00, 'Baik'),
(23, 5, 3.00, 'Cukup'),
(24, 5, 2.00, 'Kurang'),
(25, 5, 1.00, 'Sangat kurang');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `alamat` text DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `alamat`, `username`, `password`, `email`) VALUES
(1, 'Administrator', 'Jl. Proklamasi No. 1, Jakarta', 'admin', 'admin', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `alternatif`
--
ALTER TABLE `alternatif`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_employee` (`id_employee`),
  ADD KEY `id_sub_kreteria` (`id_sub_kreteria`);

--
-- Indexes for table `criteria`
--
ALTER TABLE `criteria`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sub_criteria`
--
ALTER TABLE `sub_criteria`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_kreteria` (`id_kreteria`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `alternatif`
--
ALTER TABLE `alternatif`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `criteria`
--
ALTER TABLE `criteria`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `sub_criteria`
--
ALTER TABLE `sub_criteria`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `alternatif`
--
ALTER TABLE `alternatif`
  ADD CONSTRAINT `alternatif_ibfk_1` FOREIGN KEY (`id_employee`) REFERENCES `employees` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `alternatif_ibfk_2` FOREIGN KEY (`id_sub_kreteria`) REFERENCES `sub_criteria` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `sub_criteria`
--
ALTER TABLE `sub_criteria`
  ADD CONSTRAINT `sub_criteria_ibfk_1` FOREIGN KEY (`id_kreteria`) REFERENCES `criteria` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
