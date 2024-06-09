import { useEffect, useState } from "react";
import { Button, Col, Row } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import SubNavComponent from "../SubNavComponent";

export default function AllUserComponent() {
  //const { data: users } = useSWR(`http://localhost:8081/user/all`);
  const loggedUser = JSON.parse(localStorage.getItem("user"));
  const [users, setUsers] = useState([]);
  const [friends, setFriends] = useState([]);
  const navigate = useNavigate();
  const [isFriend, setIsFrend] = useState(false);
  useEffect(() => {
    async function fetchUsers() {
      const response = await fetch(`http://localhost:8081/user/all`, {
        credentials: "include",
      });
      const fetchedItems = await response.json(response);
      setUsers(fetchedItems);
    }
    fetchUsers();
  }, []);
  useEffect(() => {
    async function fetchFriends() {
      const response = await fetch(
        `http://localhost:8081/user/getFriends/${loggedUser.id}`,
        {
          credentials: "include",
        }
      );
      const fetchedItems = await response.json(response);
      setFriends(fetchedItems);
    }
    fetchFriends();
  }, []);

  async function addFriend(id) {
    const response = await fetch(`http://localhost:8081/user/addFriend/${id}`, {
      method: "PUT",
      credentials: "include",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(),
    });
    const fetchedItems = await response.json(response);
    setFriends(fetchedItems);
  }

  const isFr = (id) => {
    friends.forEach((element) => {
      console.log(id);
      console.log(element.id);
      if (element.id === id) {
        setIsFrend(true);
      }
    });
  };

  return (
    <div>
      <SubNavComponent />
      <Button
        className="tab"
        onClick={() => {
          navigate("/friends");
        }}
      >
        My Friends
      </Button>
      <div className="container">
        <h3>People you may know</h3>
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
                {isFriend ? (
                  <Button>Message</Button>
                ) : (
                  <Button
                    onClick={() => addFriend(user.id)}
                    variant="outline-info"
                  >
                    Follow
                  </Button>
                )}
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
