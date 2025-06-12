import React, { useState, useEffect } from "react";
import axios from "axios";

function RandomValue() {
  const [value, setValue] = useState(null);

  useEffect(() => {
    axios
      .get("http://localhost:8080/test/random")
      .then((response) => {
        // 응답에서 value만 추출
        setValue(response.data.value);
      })
      .catch((error) => {
        console.error("Error fetching value:", error);
      });
  }, []);

  return (
    <div style={{ fontSize: "72px", textAlign: "center", marginTop: "100px" }}>
      {value !== null ? value : "로딩 중..."}
    </div>
  );
}

export default RandomValue;
