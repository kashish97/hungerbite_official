<?php
session_start();
ob_start();
require_once('connection/config.php');
	$errmsg_arr = array();
	$errflag = false;
	
	function clean($str) {
		$str = @trim($str);
		if(get_magic_quotes_gpc()) {
			$str = stripslashes($str);
		}
		return mysql_real_escape_string($str);
	}
	
	//Sanitize the POST values
	$login = clean($_POST['username']);
	$password = clean($_POST['password']);


	
	$year = time() + 31536000;
	setcookie('remember_me', $_POST['login'], $year);
	
	//Create query
	$qry="SELECT * FROM members WHERE login='$login' AND passwd='".md5($_POST['password'])."'AND status='1'";
	$result=mysql_query($qry);
	
	//Check whether the query was successful or not
	if($result) {
		if(mysql_num_rows($result) == 1) {
			//Login Successful
			session_regenerate_id();
			$member = mysql_fetch_assoc($result);
			//print_r($member);
			$_SESSION['SESS_MEMBER_ID'] = $member['member_id'];
			$_SESSION['SESS_FIRST_NAME'] = $member['firstname'];
			$_SESSION['SESS_LAST_NAME'] = $member['lastname'];
			$_SESSION['refer_id'] = $member['refer_id']; 
			session_write_close();
			//header("location: member-index.php");
			
			exit();
		}else {
			//Login failed
			
			exit();
		}
	}else {
		die("Query failed");
	}
?>