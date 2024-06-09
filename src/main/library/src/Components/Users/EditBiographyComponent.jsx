import { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";

export default function EditBiogrphyComponent() {
  const [biography, setBiography] = useState("");
  const changeBiographyHandler = (event) => {
    setBiography(event.target.value);
  };

  async function saveBiography(e) {
    e.preventDefault();
    const response = await fetch(`http://localhost:8081/user/${biography}/`, {
      method: "PUT",
      credentials: "include",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(),
    });
  }
  return (
    <div>
      <Form className="form" onSubmit={saveBiography} autoComplete="off">
        <h2>Add Biography</h2>
        <Form.Group className="box">
          <Form.Label className="label">Biography</Form.Label>
          <Form.Control
            className="input"
            id="usernameTextField"
            as="textarea"
            rows={7}
            value={biography}
            onChange={changeBiographyHandler}
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
    </div>
  );
}
