<?php

include("config.php");

$nama = $_POST['nama'];
$ttl = $_POST['ttl'];
$hp = $_POST['hp'];
$alamat = $_POST['alamat'];

$sql = "INSERT INTO penduduk VALUES ( NULL,'$nama' , '$ttl', '$hp','$alamat' )";
$query = mysqli_query($db , $sql);

// apakah query update berhasil ?
if ($query) {
  // code...
} else {
  // kalau gagal tampilkan pesan
  die("Gagal menyimpan perubahan");
}
