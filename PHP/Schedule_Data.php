<?php
      $userName = $_POST["userName"];
      $date = $_POST["date"];
      $isSendMode = $_POST["isSendMode"];
      $task = $_POST["task"];

      $conn = new mysqli('localhost', 'bwserver', 'RePW2939!', 'bwserver')
      or die("Connection Failed");

    if(!empty($userName) && !empty($isSendMode)){
        $data_Existcheck_Sql = "SELECT * FROM Schedule WHERE UserName = '$userName' AND Date = '$date'";
        $exist_Confirm_response = mysqli_query($conn, $data_Existcheck_Sql)  or die('Failed to query database'.mysqli_error($conn));
        if($exist_Confirm_response->num_rows > 0)
            $isDataExists = 'true';
        else 
            $isDataExists = 'false';
        
        if($isDataExists == 'true'){
        //데이터 받기/설정하기 여부
          if($isSendMode == 'true') //서버 데이터 수정
          {
            $update_Sql = "UPDATE Schedule SET Work = '$task' WHERE UserName = '$userName' AND Date = '$date'";
            $update_response = mysqli_query($conn, $update_Sql) or  die('Failed to query database'.mysqli_error($conn));
            
            if($update_response)
            {
                $update_get_Sql = "SELECT * FROM Schedule WHERE UserName = '$userName' AND Date = '$date'";
                $update_get_response = mysqli_query($conn, $update_get_Sql)  or die('Failed to query database'.mysqli_error($conn));
                
                while($update_row = mysqli_fetch_array($update_get_response)){
                    if($update_row[0] != NULL)
                    {
                        $update_res["result"] = "일정 변경 성공";
                        $update_res["date"] = $row["Date"];
                        $update_res["task"] = $row["Work"];
                        echo json_encode($update_res, JSON_UNESCAPED_UNICODE);
                    }
                }
            }
            else
            {
                $res["result"] = "일정 변경 실패";
                    echo json_encode($res, JSON_UNESCAPED_UNICODE);
            }

            
          }
          else if($isSendMode == 'false') //서버 데이터 가져오기
          {
            $get_Sql = "SELECT * FROM Schedule WHERE UserName = '$userName' AND Date = '$date'";
            $get_response = mysqli_query($conn, $get_Sql)  or die('Failed to query database'.mysqli_error($conn));
            
            while($row = mysqli_fetch_array($get_response)){
                if($row[0] != NULL)
                {
                    $res["result"] = "성공";
                    $res["date"] = $row["Date"];
                    $res["task"] = $row["Work"];
                    echo json_encode($res, JSON_UNESCAPED_UNICODE);
                }
                else
                {
                    $res["result"] = "실패";
                    echo json_encode($res, JSON_UNESCAPED_UNICODE);
                }
            }
        }
    }
        else 
        {        
            $insert_Sql = "INSERT INTO Schedule VALUES('$userName', '$date', '$task')";
            $insert_response = mysqli_query($conn, $insert_Sql)  or die('Failed to query database'.mysqli_error($conn));
            if($insert_response)
                {
                    $get_Sql = "SELECT * FROM Schedule WHERE UserName = '$userName' AND Date = '$date'";
                    $get_response = mysqli_query($conn, $get_Sql)  or die('Failed to query database'.mysqli_error($conn));
            
                    while($row = mysqli_fetch_array($get_response)){
                    if($row[0] != NULL)
                    {
                        $res["result"] = "성공";
                        $res["date"] = $row["Date"];
                        $res["task"] = $row["Work"];
                        echo json_encode($res, JSON_UNESCAPED_UNICODE);
                    }
                    else
                    {
                        $res["result"] = "실패";
                        echo json_encode($res, JSON_UNESCAPED_UNICODE);
                    }
                }
            }
            else
                echo "일정 추가에 실패했습니다";
        }
}
else
    echo "값이 비어있음";

      $conn->close();
?>