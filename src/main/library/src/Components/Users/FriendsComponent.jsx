import { useEffect, useState } from "react";
import ListUsersComponent from "./ListUsersComponent";
import useSWR from "swr";
import { Skeleton } from "@mui/material";
import { Button, Col, Row } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

export default function FriendsComponent() {
  const navigate = useNavigate();
  //const { data: users } = useSWR(`http://localhost:8081/user/all`);
  const loggedUser = JSON.parse(localStorage.getItem("user"));
  const [users, setUsers] = useState([]);
  useEffect(() => {
    async function fetchUsers() {
      const response = await fetch(
        `http://localhost:8081/user/getFriends/${loggedUser.id}`,
        {
          credentials: "include",
        }
      );
      const fetchedItems = await response.json(response);
      console.log(JSON.stringify(fetchedItems));
      setUsers(fetchedItems);
    }
    fetchUsers();
  }, []);
  return (
    <div className="container">
      <h3>List with friends</h3>
      <div className="row">
        {users.map((user) => (
          <div key={user.id} className="column">
            <div>
              <Button
                onClick={() => navigate(`/Profile/${user.id}`)}
                style={{
                  overflow: "hidden",
                  background: "transparent",
                  border: "none",
                  width: "160px",
                  height: "160px",
                  borderRadius: "100px",
                }}
              >
                {user.profilePicture ? (
                  <img
                    src={"/images/" + user.profilePicture}
                    style={{
                      width: "150px",
                      height: "150px",
                      borderRadius: "100px",
                    }}
                  ></img>
                ) : (
                  <img
                    src="/images/blank-profile-picture-973460_1280.webp"
                    style={{
                      width: "150px",
                      height: "150px",
                      borderRadius: "100px",
                    }}
                  ></img>
                )}
              </Button>
              <h4>
                {user.fName} {user.lName}
              </h4>
            </div>
            <div>
              <Button
                onClick={() => navigate(`/message/${user.id}`)}
                variant="outline-info"
              >
                Message
              </Button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
