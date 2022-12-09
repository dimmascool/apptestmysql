<?php
	$dbhost = 'localhost';
	$dbuser = 'root';
	$dbpass = '';
	$dbname = 'db_sales';

	$con = mysqli_connect($dbhost, $dbuser, $dbpass, $dbname) or die('Unable to Connect');

if($_SERVER['REQUEST_METHOD']=='POST'){
//Getting values
$kode = $_POST['kode'];
$nama = $_POST['nama'];
$buatan = $_POST['buatan'];

//Creating sql query
$sql = "INSERT INTO barang VALUES('".$kode."','".$nama."','".$buatan."')";

//executing query
$result = mysqli_query($con,$sql);

if($result){
//displaying success
	echo "success";
}else{
//displaying failure
	echo "failure";
} 
//-------close database 
mysqli_close($con);
}
?>