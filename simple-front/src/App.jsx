import React, { useEffect, useState } from "react";

function Button({ children, onClick, className }) {
  return (
    <button
      className={`px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 focus:outline-none ${
        className || ""
      }`}
      onClick={onClick}
    >
      {children}
    </button>
  );
}

function Card({ children, className }) {
  return (
    <div className={`border rounded shadow p-4 bg-white ${className || ""}`}>
      {children}
    </div>
  );
}

function CardContent({ children, className }) {
  return <div className={`text-gray-700 ${className || ""}`}>{children}</div>;
}

export default function RandomCounterApp() {
  const [randomValue, setRandomValue] = useState("");
  const [rdbmsCount, setRdbmsCount] = useState(0);
  const [mongoCount, setMongoCount] = useState(0);

  const fetchAllCounts = async () => {
    try {
      const [rdbmsRes, mongoRes] = await Promise.all([
        fetch("http://localhost:8080/trash/count"),
        fetch("http://localhost:8000/trash/count"),
      ]);
      const rdbmsJson = await rdbmsRes.json();
      const mongoJson = await mongoRes.json();
      setRdbmsCount(rdbmsJson.count);
      setMongoCount(mongoJson.count);
    } catch (error) {
      console.error("Error fetching counts:", error);
    }
  };

  const fetchRandom = async () => {
    try {
      const res = await fetch("http://localhost:8080/trash/random");
      const json = await res.json();
      setRandomValue(json.value);
      await fetchAllCounts();
    } catch (error) {
      console.error("Error fetching random value:", error);
    }
  };

  useEffect(() => {
    fetchAllCounts();
  }, []);

  return (
    <div className="min-h-screen bg-gray-100 p-10">
      <div className="max-w-2xl mx-auto space-y-6">
        <h1 className="text-3xl font-bold text-center">
          🔢 랜덤 생성기 & 카운터
        </h1>

        <Card>
          <CardContent className="p-6 space-y-4">
            <div className="flex justify-between items-center">
              <span className="font-semibold">RDBMS:</span>
              <span>{rdbmsCount}</span>
            </div>
            <div className="flex justify-between items-center">
              <span className="font-semibold">MongoDB:</span>
              <span>{mongoCount}</span>
            </div>
          </CardContent>
        </Card>

        <Card>
          <CardContent className="p-6 space-y-4">
            <div className="text-lg text-gray-700">
              랜덤 값:{" "}
              <span className="font-mono text-blue-600">{randomValue}</span>
            </div>
            <Button className="w-full" onClick={fetchRandom}>
              랜덤 생성
            </Button>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
