import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

import { useState } from "react";
import { useNavigate } from "react-router-dom";

export default function RegistrationComponent() {
  const navigate = useNavigate();
  const [fName, setFName] = useState("");
  const [lName, setLName] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const changeUsernameHandler = (event) => {
    setUsername(event.target.value);
  };
  const changePasswordHandler = (event) => {
    setPassword(event.target.value);
  };
  const changeFNameHandler = (event) => {
    setFName(event.target.value);
  };
  const changeLNameHandler = (event) => {
    setLName(event.target.value);
  };
  async function saveUser(e) {
    e.preventDefault();
    const response = await fetch(
      `http://localhost:8081/user/${fName}/${lName}/${username}/${password}`,
      {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(),
      }
    );
    const fetchedItems = await response.json(response);
    navigate("/");
  }

  return (
    <Form className="user-form" onSubmit={saveUser}>
      <h2>SIGN UP</h2>
      <Form.Group className="box" controlId="formGroupUsername">
        <Form.Label className="label">Username</Form.Label>
        <Form.Control
          className="input"
          id="usernameTextField"
          type="text"
          placeholder="Enter username"
          value={username}
          onChange={changeUsernameHandler}
        />
      </Form.Group>
      <Form.Group className="box" controlId="formGroupPassword">
        <Form.Label className="label" id="passwordLabel">
          Password
        </Form.Label>
        <Form.Control
          className="input"
          id="passwordTextField"
          type="password"
          placeholder="Enter password"
          value={password}
          onChange={changePasswordHandler}
        />
      </Form.Group>
      <Form.Group className="box" controlId="formGroupFirstName">
        <Form.Label className="label" id="firstNameLabel">
          Firs name
        </Form.Label>
        <Form.Control
          className="input"
          id="usernameTextField"
          type="text"
          placeholder="Enter First Name"
          value={fName}
          onChange={changeFNameHandler}
        />
      </Form.Group>
      <Form.Group className="box" controlId="formGroupLastName">
        <Form.Label className="label" id="lastNameLabel">
          Last name
        </Form.Label>
        <Form.Control
          className="input"
          id="usernameTextField"
          type="text"
          placeholder="Enter Last Name"
          value={lName}
          onChange={changeLNameHandler}
        />
      </Form.Group>

      <Button className="button" id="btnSubmit" variant="dark" type="submit">
        <span></span>
        <span></span>
        <span></span>
        <span></span>
        Submit
      </Button>
    </Form>
  );
}
