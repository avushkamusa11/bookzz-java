import React, { useState, useEffect } from "react";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import NavDropdown from "react-bootstrap/NavDropdown";
import Navbar from "react-bootstrap/Navbar";
import { Buffer } from "buffer";
import { useNavigate } from "react-router-dom";
import { Button } from "react-bootstrap";
import "../styles/styles.css";

export default function HeaderComponent(refresh, onReRender) {
  const navigate = useNavigate();
  const [user, setUser] = useState([]);
  useEffect(() => {
    const user = JSON.parse(localStorage.getItem("user"));
    if (user) {
      setUser(user);
    }
  }, [refresh]);

  async function logOut(e) {
    e.preventDefault();
    const response = await fetch("http://localhost:8081/logout", {
      method: "POST",
      credentials: "include",
      headers: {
        Authorization:
          "Basic " +
          Buffer.from(user.username + ":" + user.password).toString("base64"),
      },
      body: JSON.stringify(),
    });
    const fetchedItems = await response.json(response);
    setUser(fetchedItems);
    localStorage.setItem("user", JSON.stringify(user));
    refresh = () => onReRender();
    navigate("/");
  }
  const isAdmin = user && user.role && user.role.roleName === "admin";
  const isAuth = user.id;
  return (
    <div>
      <Navbar
        className="nav"
        collapseOnSelect
        expand="lg"
        style={{ width: "100%" }}
      >
        <Container>
          <Navbar.Brand className="title" href="#home">
            𝓟𝓵𝓪𝓽𝓯𝓸𝓻𝓶 9 ¾{" "}
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="responsive-navbar-nav" />
          <Navbar.Collapse id="responsive-navbar-nav">
            <Nav className="me-auto">
              <Nav.Link className="a" id="listBoksLink" href="/home">
                Home
              </Nav.Link>
              <Nav.Link className="a" id="listBoksLink" href="/books">
                Books
              </Nav.Link>
              {isAuth ? (
                <Nav.Link className="a" id="myLibraryLink" href="/myLibrary">
                  My Library
                </Nav.Link>
              ) : (
                <div></div>
              )}
              {isAuth ? (
                <NavDropdown
                  title={<span className="dropdown">Quotes</span>}
                  id="navbarScrollingDropdown"
                >
                  <NavDropdown.Item
                    className="a dropdownItem"
                    href="/quotes/addQuote"
                  >
                    Add Quote
                  </NavDropdown.Item>
                  <NavDropdown.Item className="a dropdownItem" href="/quotes">
                    Quotes
                  </NavDropdown.Item>
                </NavDropdown>
              ) : (
                <Nav.Link></Nav.Link>
              )}
              {isAdmin ? (
                <NavDropdown
                  title={<span className="dropdown">CRUD</span>}
                  id="navbarScrollingDropdown"
                >
                  <NavDropdown.Item className="a dropdownItem" href="/book/add">
                    Add Book
                  </NavDropdown.Item>
                  <NavDropdown.Item
                    className="a dropdownItem"
                    href="/genre/add"
                  >
                    Add genre
                  </NavDropdown.Item>
                  <NavDropdown.Item
                    className="a dropdownItem"
                    href="/author/add"
                  >
                    Add Author
                  </NavDropdown.Item>
                  <NavDropdown.Item className="a dropdownItem" href="/genres">
                    Genres
                  </NavDropdown.Item>
                  <NavDropdown.Item className="a dropdownItem" href="/books">
                    Books
                  </NavDropdown.Item>
                  <NavDropdown.Item className="a dropdownItem" href="/authors">
                    Authors
                  </NavDropdown.Item>
                  <NavDropdown.Divider />
                </NavDropdown>
              ) : (
                <Nav.Link></Nav.Link>
              )}
            </Nav>
            {isAuth ? (
              <Nav>
                {" "}
                <Nav.Link
                  className="a"
                  onClick={() => {
                    navigate(`/Profile/${user.id}`);
                  }}
                  style={{
                    width: "65px",
                    height: "65px",
                    borderRadius: "100%",
                  }}
                >
                  <img
                    className="profileBtnImg"
                    src={"/images/" + user.profilePicture}
                    style={{
                      width: "50px",
                      height: "50px",
                      borderRadius: "100%",
                    }}
                  />
                </Nav.Link>
                <Nav.Link
                  className="a"
                  id="logoutLink"
                  onClick={logOut}
                  href="/"
                >
                  Log out
                </Nav.Link>
              </Nav>
            ) : (
              <Nav>
                <Nav.Link className="a" id="signUpLink" href="/registration">
                  Sign up
                </Nav.Link>
                <Nav.Link className="a" id="logInLink" href="/">
                  Log in
                </Nav.Link>{" "}
              </Nav>
            )}
          </Navbar.Collapse>
        </Container>
      </Navbar>
    </div>
  );
}
