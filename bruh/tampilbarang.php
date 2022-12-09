<?php
    

    $dbhost = 'localhost';
	$dbuser = 'root';
	$dbpass = '';
	$dbname = 'db_sales';

	$koneksi = mysqli_connect($dbhost, $dbuser, $dbpass, $dbname) or die('Unable to Connect');
 
    $query = "SELECT * FROM barang"; //select table in database
    $sql = mysqli_query($koneksi, $query);
    $arraydata = array();
    while ($data = mysqli_fetch_array($sql)) {
        // $arraydata[] = $data;
        array_push($arraydata, array(
            'kode'  => $data['kode'],
            'nama'  => $data['nama'],
            'buatan'=> $data['buatan']
        ));
    }

    header('Content-Type: application/json; charset=utf-8');
 
    echo json_encode($arraydata);
    
    mysqli_close($koneksi);
 
?>