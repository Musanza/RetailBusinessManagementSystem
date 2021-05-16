<?php
    $mysqli = new mysqli ('sqlite:rbms.db');

if ($mysqli->connect_error) {
   printf("Connection failed: %s\n", $mysqli->connect_error);
   exit();
}
?>
