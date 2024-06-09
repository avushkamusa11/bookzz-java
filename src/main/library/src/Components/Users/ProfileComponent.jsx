import React from "react";
import {
  MDBCol,
  MDBContainer,
  MDBRow,
  MDBCard,
  MDBCardText,
  MDBCardBody,
  MDBCardImage,
  MDBBtn,
  MDBTypography,
} from "mdb-react-ui-kit";
import useSWR from "swr";
import MyBookShelfComponent from "./MyBookShelfComponent";
import { useEffect } from "react";
import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "../../styles/profile.css";

export default function ProfileComponent() {
  const { id } = useParams();
  const loggedUser = JSON.parse(localStorage.getItem("user"));
  const [user, setUser] = useState("");
  const navigate = useNavigate();
  const [count, setCount] = useState(0);
  useEffect(() => {
    async function fetchCount() {
      const response = await fetch(
        `http://localhost:8081/myLibrary/getCount/${id}`,
        {
          credentials: "include",
        }
      );
      const fetchedItems = await response.json(response);
      setCount(fetchedItems);
    }
    fetchCount();
  }, []);
  useEffect(() => {
    async function getUser() {
      const response = await fetch(`http://localhost:8081/user/get/${id}`, {
        credentials: "include",
      });
      const fetchedItems = await response.json(response);
      setUser(fetchedItems);
    }
    getUser();
  }, []);
  const isLoggedUser = loggedUser.id === user.id;
  return (
    <div>
      <MDBContainer className="py-5 h-100">
        <MDBRow className="justify-content-center align-items-center h-100">
          <MDBCol lg="9" xl="7">
            <MDBCard>
              <div
                className="rounded-top text-white d-flex flex-row"
                style={{ backgroundColor: "#000", height: "300px" }}
              >
                <div
                  className="ms-4 mt-5 d-flex flex-column"
                  style={{ width: "150px" }}
                >
                  <MDBCardImage
                    src={"/images/" + user.profilePicture}
                    alt="Generic placeholder image"
                    className="mt-4 mb-2 img-thumbnail"
                    fluid
                    style={{ width: "150px", height: "150px", zIndex: "1" }}
                  />
                  {isLoggedUser ? (
                    <MDBBtn
                      outline
                      className="profile-btn"
                      style={{ height: "36px", overflow: "visible" }}
                      onClick={() => navigate("/editProfile")}
                    >
                      Edit profile
                      <span className="first"></span>
                      <span className="second"></span>
                      <span className="third"></span>
                      <span className="fourth"></span>
                    </MDBBtn>
                  ) : (
                    <div> </div>
                  )}
                </div>
                <div className="ms-3" style={{ marginTop: "130px" }}>
                  <MDBTypography tag="h5">
                    {user.fName} {user.lName}
                  </MDBTypography>
                  <MDBCardText>{user.username}</MDBCardText>
                </div>
              </div>
              <div
                className="p-4 text-black"
                style={{ backgroundColor: "#f8f9fa" }}
              >
                <div className="d-flex justify-content-end text-center py-1">
                  <div>
                    <MDBCardText className="mb-1 h5">{count}</MDBCardText>
                    <MDBCardText className="small text-muted mb-0">
                      Count of books
                    </MDBCardText>
                  </div>
                  <div className="px-3">
                    <MDBCardText className="mb-1 h5">{user.singUp}</MDBCardText>
                    <MDBCardText className="small text-muted mb-0">
                      Join us
                    </MDBCardText>
                  </div>
                  <div>
                    <MDBCardText className="mb-1 h5">
                      {user.lastActivity}
                    </MDBCardText>
                    <MDBCardText className="small text-muted mb-0">
                      Last activity
                    </MDBCardText>
                  </div>
                </div>
              </div>
              <MDBCardBody className="text-black p-4">
                <div className="mb-5">
                  <p className="lead fw-normal mb-1">About</p>
                  <div className="p-4" style={{ backgroundColor: "#f8f9fa" }}>
                    <MDBCardText className="font-italic mb-1">
                      {user.biography}
                    </MDBCardText>
                  </div>
                </div>

                <MDBRow className="g-2">
                  <MDBCol className="mb-2">
                    <h3>{user.fName}'s bookshelf</h3>

                    <MyBookShelfComponent count={count} />
                  </MDBCol>
                </MDBRow>
              </MDBCardBody>
            </MDBCard>
          </MDBCol>
        </MDBRow>
      </MDBContainer>
    </div>
  );
}
