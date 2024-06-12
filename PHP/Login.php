<?php
    $id = $_POST["id"];
    $pw = $_POST["pw"];

      $conn = new mysqli('localhost', 'bwserver', 'RePW2939!', 'bwserver')
      or die("Connection Failed");


      if(!empty($id) && !empty($pw))
      {
        $sql = "SELECT * FROM Android_Register WHERE Id = '$id' AND Password = '$pw'";
        $response = mysqli_query($conn, $sql)  or die('Failed to query database'.mysqli_error($conn));
        $result = array();

        while($row = mysqli_fetch_array($response))
        if($row[0] != NULL)
        {
            $res["User_Name"] = $row["User_Name"];
            $res["Dept_Name"] = $row["Dept_Name"];
            echo json_encode($res, JSON_UNESCAPED_UNICODE);
        }
      }

      $conn->close();
?>