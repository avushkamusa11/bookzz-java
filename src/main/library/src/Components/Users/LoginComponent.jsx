import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "../../styles/user-login-register-style.css";

export default function LoginComponent({ refresh, onReRender }) {
  const navigate = useNavigate();
  const [user, setUser] = useState([]);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const data = {
    username: username,
    password: password,
  };
  const changeNameHandler = (event) => {
    setUsername(event.target.value);
  };

  const changePasswordHandler = (event) => {
    setPassword(event.target.value);
  };

  async function saveUser(e) {
    e.preventDefault();
    const response = await fetch(
      `http://localhost:8081/login/${username}/${password}`,
      {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(),
      }
    );
    const fetchedItems = await response.json(response);
    setUser(fetchedItems);
    onReRender();
  }

  useEffect(() => {
    localStorage.setItem("user", JSON.stringify(user));
    if (user && user.id) {
      onReRender();
      navigate(`/home`);
    }
  }, [user]);

  return (
    <Form className="user-form" onSubmit={saveUser} autocomplete="off">
      <h2>LOGIN</h2>
      <Form.Group className="box" controlId="formGroupUsername">
        <Form.Label className="label" id="usernameLabel">
          Username
        </Form.Label>
        <Form.Control
          className="input"
          id="usernameTextField"
          type="text"
          placeholder="Enter username"
          value={username}
          onChange={changeNameHandler}
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
      <Button className="button" id="loginBtn" variant="dark" type="submit">
        <span></span>
        <span></span>
        <span></span>
        <span></span>
        Submit
      </Button>
    </Form>
  );
}
