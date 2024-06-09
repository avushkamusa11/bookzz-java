import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import "../../styles/add-forms.css";

export default function AddGenreComponent() {
  const [genreName, setGenreName] = useState("");
  const [genre, setGenre] = useState("");

  const navigate = useNavigate();

  const changeGenreNameHandler = (event) => {
    setGenreName(event.target.value);
  };

  async function saveGenre(e) {
    e.preventDefault();
    const response = await fetch(`http://localhost:8081/genre/${genreName}/`, {
      method: "POST",
      credentials: "include",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(),
    });
    const fetchedItems = await response.json(response);
    console.log(JSON.stringify(fetchedItems));
    setGenre(fetchedItems);
  }

  useEffect(() => {
    if (genre) {
      navigate(`/genres`);
    }
  }, [genre]);

  return (
    <div>
      <Form
        className="add-form authors"
        onSubmit={saveGenre}
        autoComplete="off"
      >
        <h2>Add Genre</h2>
        <Form.Group className="box" controlId="formGroupUsername">
          <Form.Label className="label">Genre Name</Form.Label>
          <Form.Control
            className="input"
            id="usernameTextField"
            type="text"
            placeholder="Enter Genre name"
            value={genreName}
            onChange={changeGenreNameHandler}
          />
        </Form.Group>

        <Button
          className="main-buttons"
          id="btnSubmit"
          variant="dark"
          type="submit"
        >
          <span></span>
          <span></span>
          <span></span>
          <span></span>
          SAVE
        </Button>
      </Form>
    </div>
  );
}
