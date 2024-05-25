-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 25 Bulan Mei 2024 pada 16.26
-- Versi server: 10.4.20-MariaDB
-- Versi PHP: 7.4.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bioskop`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_film`
--

CREATE TABLE `tb_film` (
  `tiket_film` varchar(40) NOT NULL,
  `genre` varchar(40) NOT NULL,
  `sinopsis` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_film`
--

INSERT INTO `tb_film` (`tiket_film`, `genre`, `sinopsis`) VALUES
('Fate Grand Order Camelot', 'Action, Fantasy, History', 'Mash dan fujimaru masuk ke singularity ke 6 untuk mendapatkan cawan suci demi menyelamatkan dunia'),
('Hololive', 'Idol, Slice of Life', 'Idol banget'),
('Nijisanji', 'Drama', 'Sebuah Black Company'),
('Spiderman Now Way Home', 'Action', 'Identita'),
('Tenki No Ko', 'Romance, Drama', 'Kisah seorang laki-laki yang bertemu pawang hujan'),
('VSPO', 'Gaming', 'Company Vtuber gaming');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_tiket`
--

CREATE TABLE `tb_tiket` (
  `kode_pesanan` varchar(11) NOT NULL,
  `nama_pemesan` varchar(30) NOT NULL,
  `tiket_film` varchar(40) NOT NULL,
  `waktu` varchar(40) NOT NULL,
  `harga_tiket` int(30) NOT NULL,
  `jumlah_pesan` int(10) NOT NULL,
  `jumlah_bayar` int(30) NOT NULL,
  `uang_masuk` int(30) NOT NULL,
  `uang_keluar` int(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `tb_tiket`
--

INSERT INTO `tb_tiket` (`kode_pesanan`, `nama_pemesan`, `tiket_film`, `waktu`, `harga_tiket`, `jumlah_pesan`, `jumlah_bayar`, `uang_masuk`, `uang_keluar`) VALUES
('ID-0001', 'Suisei', 'Fate Grand Order Camelot', 'Weekend & Tanggal Merah', 75000, 1, 75000, 80000, 5000),
('ID-0004', 'Fujimaru Ritsuka', 'Fate Grand Order Camelot', 'Weekend & Tanggal Merah', 75000, 1, 75000, 76000, 1000),
('ID-0005', 'asa', 'Tenki No Ko', 'Senin-Kamis', 50000, 12, 600000, 70000000, 69400000),
('ID-0006', 'Rifa', 'Hololive', 'Senin-Kamis', 50000, 100000, 705032704, 1000000000, 294967296);

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id`, `username`, `password`) VALUES
(1, 'admin', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tb_film`
--
ALTER TABLE `tb_film`
  ADD PRIMARY KEY (`tiket_film`);

--
-- Indeks untuk tabel `tb_tiket`
--
ALTER TABLE `tb_tiket`
  ADD PRIMARY KEY (`kode_pesanan`),
  ADD KEY `tiket_film` (`tiket_film`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `tb_tiket`
--
ALTER TABLE `tb_tiket`
  ADD CONSTRAINT `tb_tiket_ibfk_1` FOREIGN KEY (`tiket_film`) REFERENCES `tb_film` (`tiket_film`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
