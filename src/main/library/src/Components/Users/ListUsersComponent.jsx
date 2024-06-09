import { useEffect } from "react";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import Button from "react-bootstrap/Button";
import "../../styles/users-list-style.css";

export default function ListUsersComponent(users) {
  const navigate = useNavigate();
  return (
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
              <Button variant="outline-info">Follow</Button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
