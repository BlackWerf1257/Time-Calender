<?php
      $userName = $_POST["userName"];
      $doW = intval($_POST["dow"]);
      $classOrder = intval($_POST["classOrder"]);
      $subName = $_POST["subName"];
      $needFetch = $_POST["needFetch"]; //true: 값 배열로 새로 불러오기 //false: 특정 값 설정

	

      $conn = mysqli_connect('localhost', '', '', '') or die("Connection Failed");
      $connPrepare = mysqli_prepare($conn, "SELECT  doW, classOrder, subName FROM Calender Where UserName = '$userName' AND doW='$doW' AND classOrder='$classOrder';");
      mysqli_stmt_execute($connPrepare);
	
	mysqli_stmt_store_result($connPrepare);
	mysqli_stmt_bind_result($connPrepare, $doW, $classOrder, $subName);

      // 연결 확인
    if ($conn->connect_error)
        die("Connection failed: " . $conn->connect_error);

    if($needFetch == "true"){
        $fetch_Query = "SELECT * FROM Calender Where UserName = '$userName'";
        $fetch_Result = $conn->query($fetch_Query);

        $rowNum = mysqli_num_rows($fetch_Result);

        for($i=0; $i<$rowNum; $i++)
        {
            $row = mysqli_fetch_array($fetch_Result, MYSQLI_ASSOC); //연관배열로 읽어옴

        $res["doW"] = $row["doW"];
        $res["classOrder"] = $row['classOrder'];
        $res["subName"] = $row['subName'];

        //콤마로 값들을 구분하는 구분자를 붙이기[csv파일 형식]
        echo json_encode($res, JSON_UNESCAPED_UNICODE);
        }
    }
    else
    {
        $data_Existcheck_Sql = "SELECT * FROM Calender WHERE UserName = '$userName'";
        $exist_Confirm_response = mysqli_query($conn, $data_Existcheck_Sql)  or die('실패'.mysqli_error($conn));
        if($exist_Confirm_response->num_rows > 0)
            $isDataExists = 'true';
        else 
            $isDataExists = 'false';
        
        if($isDataExists == 'true'){
            $update_Sql = "UPDATE Calender SET subName = '$subName' AND doW = '$doW' AND  classOrder = '$classOrder' WHERE UserName = '$userName'";
            $update_response = mysqli_query($conn, $update_Sql) or  die('Failed to query database'.mysqli_error($conn));
            if($update_response)
                echo "시간표 수정에 성공했습니다.";
            else
                echo "시간표 수정에 실패했습니다.";
        }
        else{
            $insert_Sql = "INSERT INTO Calender VALUES('$userName', '$doW', '$classOrder', '$subName')";
            $insert_response = mysqli_query($conn, $insert_Sql) or  die('Failed to query database'.mysqli_error($conn));
            if($insert_response)
                echo "시간표 추가에 성공했습니다";
            else
                echo "시간표 추가에 실패했습니다";
        }

    }

      $conn->close();
?>
