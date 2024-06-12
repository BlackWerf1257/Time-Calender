<?php
      $id = $_POST["id"];
      $pw = $_POST["pw"];
      $userName = $_POST["userName"];
      $deptName = $_POST["deptName"];

        //Server Address, Server Host ID, Server Host Password, Server DB name
        $conn = mysqli_connect('localhost', '', '', '') or die("Connection Failed");

    if(!empty($id) && !empty($pw) && !empty($userName) && !empty($deptName)){
        $id_Sql = "SELECT * FROM Android_Register WHERE Id = '$id'";
        $id_response = mysqli_query($conn, $id_Sql)  or die('Failed to query database'.mysqli_error($conn));

        while($id_row = mysqli_fetch_array($id_response)){
        if($id_row[0] !== NULL)
            {
            $id_res["result"] = "ID가 중복입니다";
            die(json_encode($id_res, JSON_UNESCAPED_UNICODE));
            }
        }
        
        $userName_Sql = "SELECT * FROM Android_Register WHERE User_Name = '$userName'";
        $userName_response = mysqli_query($conn, $userName_Sql)  or die('Failed to query database'.mysqli_error($conn));
        $userName_result = array();

        while($userName_row = mysqli_fetch_array($userName_response)){
        if($userName_row[0] !== NULL)
            {
            $userName_res["result"] = "유저명이 중복입니다";
            die(json_encode($userName_res, JSON_UNESCAPED_UNICODE));
            }
        }
        
        
        $add_Sql = "INSERT INTO Android_Register VALUES('$id', '$pw', '$userName', '$deptName')";
        $add_response = mysqli_query($conn, $add_Sql)  or die('Failed to query database'.mysqli_error($conn));

        if($add_response)
        {
            $add_res["result"] = "계정이 생성되었습니다";
            echo json_encode($add_res, JSON_UNESCAPED_UNICODE);
        }
        else
        {
            $add_res["result"] = "계정 생성에 실패했습니다";
            echo json_encode($add_res, JSON_UNESCAPED_UNICODE);
        }
}
      $conn->close();
?>
