/*
SQLyog Community v13.2.0 (64 bit)
MySQL - 10.4.32-MariaDB : Database - dbuniv
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`dbuniv` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `dbuniv`;

/*Table structure for table `dosen` */

DROP TABLE IF EXISTS `dosen`;

CREATE TABLE `dosen` (
  `kode_dsn` varchar(10) NOT NULL,
  `nama_dsn` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`kode_dsn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `dosen` */

insert  into `dosen`(`kode_dsn`,`nama_dsn`) values 
('101','Dr. Amanda'),
('102','Dr. Septiya'),
('103','Haris , M.Kom'),
('104','Budiman,M.Si'),
('105','Herlambang,MT'),
('106','Dr. Aris Salaman,M.Kom');

/*Table structure for table `jadwal` */

DROP TABLE IF EXISTS `jadwal`;

CREATE TABLE `jadwal` (
  `kode_mk` char(5) NOT NULL,
  `kelas` varchar(10) NOT NULL,
  `hari` varchar(6) DEFAULT NULL,
  `jam` varchar(11) DEFAULT NULL,
  `ruang` varchar(6) DEFAULT NULL,
  PRIMARY KEY (`kode_mk`,`kelas`),
  CONSTRAINT `jadwal_ibfk_2` FOREIGN KEY (`kode_mk`) REFERENCES `matakuliah` (`kode_mk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `jadwal` */

insert  into `jadwal`(`kode_mk`,`kelas`,`hari`,`jam`,`ruang`) values 
('101','001','Senin','07.00-08.40','H.3.1'),
('101','002','Senin','07.00-08.40','H.3.2'),
('101','003','Rabu','07.00-08.40','H.3.1'),
('102','001','Senin','08.40-10.20','H.3.1'),
('102','002','Kamis','08.40-10.20','H.3.2'),
('103','001','Senin','10.20-12.00','H.3.1'),
('103','002','Senin','10.20-12.00','H.3.2'),
('104','001','Selasa','07.00-08.40','H.3.1'),
('104','002','Selasa','07.00-08.40','H.3.2'),
('105','001','Selasa','08.40-10.20','H.3.1'),
('105','002','Selasa','08.40-10.20','H.3.2'),
('201','001','Selasa','10.20-12.00','H.3.1'),
('201','002','Selasa','10.20-12.00','H.3.2'),
('201','003','Selasa','10.20-12.00','H.3.3');

/*Table structure for table `krs` */

DROP TABLE IF EXISTS `krs`;

CREATE TABLE `krs` (
  `kode_mk` char(5) NOT NULL,
  `kelas` varchar(10) NOT NULL,
  `nim` varchar(14) NOT NULL,
  `status` varchar(10) DEFAULT 'baru' COMMENT 'baru/ulang',
  PRIMARY KEY (`kode_mk`,`kelas`,`nim`),
  KEY `nim` (`nim`),
  CONSTRAINT `krs_ibfk_2` FOREIGN KEY (`kode_mk`) REFERENCES `matakuliah` (`kode_mk`),
  CONSTRAINT `krs_ibfk_3` FOREIGN KEY (`nim`) REFERENCES `mhs` (`nim`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `krs` */

insert  into `krs`(`kode_mk`,`kelas`,`nim`,`status`) values 
('101','001','A11.2023.00001','baru'),
('101','001','A11.2023.00002','baru'),
('101','001','A11.2023.00003','baru'),
('101','001','A11.2023.00004','baru'),
('101','002','A11.2023.00005','baru'),
('101','002','A11.2023.00006','baru'),
('101','002','A11.2023.00007','baru'),
('101','002','A11.2023.00008','baru'),
('102','001','A11.2023.00001','baru'),
('102','001','A11.2023.00002','baru'),
('102','001','A11.2023.00003','baru'),
('102','002','A11.2023.00004','baru'),
('102','002','A11.2023.00005','baru');

/*Table structure for table `matakuliah` */

DROP TABLE IF EXISTS `matakuliah`;

CREATE TABLE `matakuliah` (
  `kode_mk` char(5) NOT NULL,
  `nama_mk` varchar(30) DEFAULT NULL,
  `sks` int(11) DEFAULT NULL,
  PRIMARY KEY (`kode_mk`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `matakuliah` */

insert  into `matakuliah`(`kode_mk`,`nama_mk`,`sks`) values 
('101','Kalkulus',3),
('102','Matematika',3),
('103','Bhs Inggris',3),
('104','Dasar Kewirausahaan',3),
('105','Dasar Pemrograman',3),
('201','Algoritma Pemrograman',3),
('202','Matematika Diskrit',3),
('203','Fisika',3),
('204','Pemrograman Berbasis Web',3);

/*Table structure for table `mhs` */

DROP TABLE IF EXISTS `mhs`;

CREATE TABLE `mhs` (
  `nim` varchar(14) NOT NULL,
  `nama` varchar(30) DEFAULT NULL,
  `alamat` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`nim`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `mhs` */

insert  into `mhs`(`nim`,`nama`,`alamat`) values 
('A11.2023.00001','Melati Putri Sekar','Jl. Kawi 1, no.26'),
('A11.2023.00002','Siva','Jl. Nakula 1'),
('A11.2023.00003','Rima','Jl. Dewi Kunti 22 '),
('A11.2023.00004','Roy','Jl. Sadewa 2 no 56'),
('A11.2023.00005','Roni Patinasatan','Jl. Abimanyu, no.60'),
('A11.2023.00006','Jaka Sembuh','Jl. Werkodoro no.226'),
('A11.2023.00007','Budi','Jl. Bima Barat 26'),
('A11.2023.00008','Umar','Jl. Sengkuni 123'),
('A11.2023.00009','Hasan','Jl. Werkodoro no.226'),
('A11.2023.00010','Ibnu','Jl. Bulu Lor no.11'),
('A11.2023.00011','Sofii','Jl. Bendungan Hilir ,12');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `iduser` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`iduser`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `user` */

insert  into `user`(`iduser`,`username`,`password`) values 
(1,'yogi','123'),
(2,'adam','123'),
(7,'ari','123');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
