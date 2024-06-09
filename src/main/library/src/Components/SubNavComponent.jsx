import Button from "react-bootstrap/Button";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function SubNavComponent() {
  const [hasSchedule, setHasSchedule] = useState(false);
  const navigate = useNavigate();

  const user = JSON.parse(localStorage.getItem("user"));

  useEffect(() => {
    async function checkSchedule() {
      {
        const response = await fetch(`http://localhost:8081/schedule/check`, {
          credentials: "include",
        });
        const fetchedItems = await response.json(response);
        setHasSchedule(fetchedItems);
      }
    }
    checkSchedule();
  }, []);
  async function addSchedule(e) {
    e.preventDefault();
    if (hasSchedule) {
      navigate("/schedule");
    } else {
      const response = await fetch(`http://localhost:8081/schedule/add`, {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(),
      });
      navigate("/schedule");
    }
  }
  return (
    <div>
      <Button
        className="tab"
        onClick={() => {
          navigate("/myGoal");
        }}
      >
        My Goal
      </Button>

      <Button className="tab" onClick={addSchedule}>
        Schedule
      </Button>
      <Button className="tab" onClick={() => navigate("/stats")}>
        My Statistic
      </Button>
      <Button className="tab" onClick={() => navigate("/allUsers")}>
        Users
      </Button>
    </div>
  );
}
